
// 注册组件
Vue.component('v-select', VueSelect.VueSelect);


window.onload = function () {

    var vue = new Vue({
        el:"#app",
        data:{
            dataList:[],
            entity:{brandIds:[],specIds:[],customAttributeItems:[],name:""},
            pageNum:1, // 当前页码
            pages:0 ,//总页数
            ids:[] ,//选中的品牌id
            checked:false,
            brandList:[],
            specifications:[]
        },
        methods:{
            search:function (pageNum) {

                axios.get("/typeTemplate/findByPage?pageNum="+pageNum).then(function (response) {

                    var pageInfo = response.data;

                    vue.dataList = pageInfo.list;

                    vue.pages = pageInfo.pages;

                    vue.pageNum = pageInfo.pageNum;

                    //翻页时清空ids
                    vue.ids = [];
                }).catch(function (reason) {
                    console.log(reason);
                });
            },
            save:function () {
                axios.post("/typeTemplate/save",this.entity).then(function (response) {
                    if(response.data){
                        vue.search(1);
                    }else {
                        alert("操作失败");
                    }
                })
            },
            del:function () {
                if(confirm("确定要删除吗？")) {

                    axios.post("/typeTemplate/delete", this.ids).then(function (response) {
                        if (response) {
                            alert("删除成功");
                        } else {
                            alert("删除失败");
                        }
                    })
                }
            },
            checkAll:function (ev) {
                //清空数组
                this.ids = [];
                //判断全选框是否被选中
                if(ev.target.checked){
                    for(var i = 0; i < this.dataList.length ; i++){
                        this.ids.push(this.dataList[i].id);
                    }
                }
            }
            ,
            show:function (b) {

                // this.typeTemplate.name = b.name;
                // this.typeTemplate.firstChar = b.firstChar;
                //把b对象转化成json字符串
                var jsonStr = JSON.stringify(b);
               // 把json字符串转化成一个新的json对象
                this.entity = JSON.parse(jsonStr);
                // 把品牌json字符串转化成json数组
                this.entity.brandIds = JSON.parse(this.entity.brandIds);
                // 把规格json字符串转化成json数组
                this.entity.specIds = JSON.parse(this.entity.specIds);
                // 把扩展属性json字符串转化成json数组
                this.entity.customAttributeItems = JSON
                    .parse(this.entity.customAttributeItems);


            },
            findBrandList:function () {
                axios.get("/brand/findAllByIdAndName").then(function (response) {
                   vue.brandList = response.data
                });
            },
            findSpecifications:function () {
                axios.get("/specification/findAllByIdAndName").then(function (response) {
                    vue.specifications = response.data;
                });
            },
            delTableRow:function (index) {
                vue.entity.customAttributeItems.splice(index,1);
            },
            addTableRow:function () {
                vue.entity.customAttributeItems.push({});
            },
            jsonArr2Str : function (jsonArrStr) {
            // 提取数组中json某个属性，返回拼接的字符串(逗号分隔)
            // 把jsonArrStr转化成JSON数组对象
            var jsonArr = JSON.parse(jsonArrStr);
            // 定义新数组
            var resArr = [];
            // 迭代json数组
            for (var i = 0; i < jsonArr.length; i++){
                // 取数组中的一个元素
                var json = jsonArr[i];
                // 把json对象的值添加到新数组
                resArr.push(json.text);
            }
            // 返回数组中的元素用逗号分隔的字符串
            return resArr.join(",");
            }

        },
        created:function () {
            console.log("vue is created")
            this.search(1);
            this.findBrandList();
            this.findSpecifications();
        },
        updated:function(){//更新数据生命周期
            //每次更新数据之后检测，全选按钮选中状态
            this.checked = this.ids.length==this.dataList.length;
        }
    });
}