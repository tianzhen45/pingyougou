// 窗口加载完
$(function(){
    // 创建Vue对象
    var vue = new Vue({
        el : '#app', // 元素绑定
        data : { // 数据模型
            goods : {goodsDesc:{itemImages:[],customAttributeItems:[],specificationItems:[]},
                    category1Id:"",category2Id:"",category3Id:"",
                    typeTemplateId : "",
                    brandId:"",
                    items:[],
                    isEnableSpec:0
                     } ,// 数据封装对象(表单)
            picEntity:{url:'',color:''},
            itemCatList1:[],
            itemCatList2:[],
            itemCatList3:[],
            brandList:[],
            message:"请点击上传按钮上传图片，上传完成后点击保存",
            showButton:false,
            specList:[]
        },
        methods : { // 定义操作方法
            saveOrUpdate : function () { // 添加或修改

                //获取富文本编辑器中的内容
                this.goods.goodsDesc.introduction = editor.html();

                // 发送异步请求
                axios.post("/goods/save", this.goods)
                    .then(function(response){
                    // 获取响应数据
                    if (response.data){ // 操作成功
                        // 清空表单数据
                        vue.goods = {category1Id:'',goodsDesc:{},items:[]};

                        //清空富文本编辑器
                        editor.html('');

                    }else {
                        alert('操作失败！');
                    }
                });
            },
            uploadFile:function () { //上传图片，并返回图片url
                this.message = "图片正在上传，请等待。。。";


                /** 创建表单对象 */
                var formData = new FormData();
                /** 追加需要上传的文件 */
                formData.append("file", file.files[0]);
                // 异步请求上传文件
                axios({
                    method : "post", // 请求方式
                    url : "/goods/upload", // 请求URL
                    data : formData, // 请求参数
                    headers : {"Content-Type" : "multipart/form-data"} // 请求头
                }).then(function(response){
                    // 获取响应数据 response.data : {status : 200, url : ''}
                    if (response.data.status == "200"){
                        vue.picEntity.url = response.data.url;
                        vue.message = "图片上传成功！请点击保存";
                        vue.showButton = true;
                    }else {
                        vue.message = "图片上传失败！请刷新重试";
                    }
                });
            },
            addPic:function () {
                //点击保存，将图片加入到goodsDesc的itemImages中
                this.goods.goodsDesc.itemImages.push(this.picEntity);

                this.message = "请点击上传按钮上传图片，上传完成后点击保存";
                this.showButton = false;
            },

            delPic:function (index) {//删除图片
                this.goods.goodsDesc.itemImages.splice(index,1);
            },
            findItemCatByParentId:function (parentId,name) {
                axios.get("/itemCat/findItemCatByParentId").then(
                    function (response) {
                        vue[name] = response.data;
                    }
                );
            },
            //对规格下的规格选项选中，保存
            selectSpecAttr:function (e,specName,optionName) {
                /** 根据json对象的key到json数组中搜索该key值对应的对象 */

                //根据规格名字，找到this.goods.goodsDesc.specificationItems规格数组 中的规格对象
                var obj = this.searchJsonByKey(this.goods.goodsDesc
                    .specificationItems,'attributeName', specName);

                //this.goods.goodsDesc.specificationItems 格式
                //[{"attributeValue":["移动4G","联通4G"],"attributeName":"网络"},{"attributeValue":["16G"],"attributeName":"机身内存"}]

                //obj格式
                // {"attributeValue":["移动4G","联通4G"],"attributeName":"网络"}

                /** 判断对象是否为空 */
                if(obj){
                    /** 判断checkbox是否选中 */
                    if(e.target.checked){
                        /** 添加该规格选项到数组中 */
                        obj.attributeValue.push(optionName);
                    }else{
                        /** 取消勾选，从数组中删除该规格选项 */
                        obj.attributeValue.splice(obj.attributeValue
                            .indexOf(optionName), 1);
                        /** 如果选项都取消了，将此条记录删除 */
                        if(obj.attributeValue.length == 0){
                            this.goods.goodsDesc.specificationItems.splice(
                                this.goods.goodsDesc
                                    .specificationItems.indexOf(obj),1);
                        }
                    }
                }else{
                    /** 如果为空，则新增数组元素 */
                    this.goods.goodsDesc.specificationItems.push(
                        {"attributeName":specName,"attributeValue":[optionName]});
                }
            },
            /** 从json数组中根据key查询指定的json对象 */
            searchJsonByKey : function(jsonArr, key, value){
                /** 迭代json数组 */
                for(var i = 0; i < jsonArr.length; i++){
                    if(jsonArr[i][key] == value){
                        return jsonArr[i];
                    }
                }
            },
            createItems:function () {
                this.goods.items = [{spec:{}, price:0, num:9999,
                    status:'0', isDefault:'0'}];

                /** 定义选中的规格选项数组 */
                var specItems = this.goods.goodsDesc.specificationItems;
                /** 循环选中的规格选项数组 */
                for(var i = 0; i < specItems.length; i++){
                    /** 调用扩充原SKU数组方法，得到新的SKU数组 */
                    this.goods.items = this.swapItems(this.goods.items,
                        specItems[i].attributeName,
                        specItems[i].attributeValue);
                }

            },
            // 扩充SKU数组
            swapItems : function (items, attributeName, attributeValue) {
                /** 创建新的SKU数组 */
                var newItems = new Array();
                /** 迭代旧的SKU数组，循环扩充 */
                for(var i = 0; i < items.length; i++){
                    /** 获取一个SKU商品 */
                    var item = items[i];
                    /** 迭代规格选项值数组 */
                    for(var j = 0; j < attributeValue.length; j++){
                        /** 克隆旧的SKU商品，产生新的SKU商品 */
                        var newItem = JSON.parse(JSON.stringify(item));
                        /** 增加新的key与value */
                        newItem.spec[attributeName] = attributeValue[j];
                        /** 添加到新的SKU数组 */
                        newItems.push(newItem);
                    }
                }
                return newItems;
            }
        },
        created : function () { // 创建生命周期(初始化方法)
            this.findItemCatByParentId("0","itemCatList1");
        },
        watch: {// 监控data中的变量
            // 监控 goods.category1Id变量发生改变，查询二级分类
            "goods.category1Id":function (newVal,oldVal) {
                // 清空数据
                this.goods.category2Id = "";
                if(newVal){
                    this.findItemCatByParentId(newVal,"itemCatList2");
                }else {
                    this.itemCatList2 = [];
                }
            },
            // 监控 goods.category2Id变量发生改变，查询三级分类
            "goods.category2Id":function (newVal,oldVal) {
                // 清空数据
                this.goods.category3Id = "";
                if(newVal){
                    this.findItemCatByParentId(newVal,"itemCatList3");
                }else {
                    this.itemCatList3 = [];
                }
            },
            // 监控 goods.category3Id变量发生改变，从三级分类中获取类型模板id
            "goods.category3Id":function (newVal,oldVal) {
                // 清空数据
                this.goods.typeTemplateId = "";
                if(newVal){
                    for(var i = 0 ; i < this.itemCatList3.length ; i++){

                        var itemCat = this.itemCatList3[i];
                        if(newVal == itemCat.id){
                            this.goods.typeTemplateId = itemCat.typeId;
                            break;
                        }
                    }
                }
            },
            // 根据分类，获得模板，根据模板得到品牌列表和自定义属性列表
            // 监控 goods.typeTemplateId变量发生改变，查询类型模板对象
            "goods.typeTemplateId":function (newVal,oldVal) {
                if(newVal){
                    axios.get("/typeTemplate/findById?id="+newVal).then(
                        function (response) {
                            var typeTemplate = response.data;
                            vue.brandList = JSON.parse(typeTemplate.brandIds);
                            //扩展属性
                            vue.goods.goodsDesc.customAttributeItems = JSON.parse(typeTemplate.customAttributeItems);
                        }
                    );

                    //查询模板对应的规格和规格选项
                    axios.get("/typeTemplate/findSpecByTemplateId?id="+newVal).then(
                        function (response) {
                            vue.specList = response.data;
                        }
                    )

                }else {
                    vue.goods.brandList = [];
                    vue.goods.goodsDesc.customAttributeItems = [];
                }
            }

        }
    });
});