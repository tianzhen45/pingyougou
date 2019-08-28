
// 注册组件
Vue.component('v-select', VueSelect.VueSelect);



window.onload = function () {
    var vue = new Vue({
        el:"#app",
        data:{
            dataList:[],
            catList1:[],
            catList2:[],
            entity:{typeId:"",name:"",parentId:0},
            ids:[] ,//选中id
            checked:false,
            grade:1,
            c2:{},
            c3:{},
            templateList:[],
        },
        methods:{
            findByParentId:function (parentId) {
                axios.get("/itemCategory/findByParentId?parentId="+parentId).then(function (response) {
                    vue.dataList = response.data;
                })
            },
            selectList:function (category) {
                if(this.grade == 1){
                    this.c2 = category;
                    this.catList1 = this.dataList;
                }
                if(this.grade == 2){
                    this.c3 = category;
                    this.catList2 = this.dataList;
                }
                this.grade++;
                this.findByParentId(category.id);
            },
            backPage:function (index) {
                if(this.grade != index){
                    if(index == 1){
                        this.dataList = this.catList1;
                    }
                    if(index == 2){
                        this.dataList = this.catList2;
                    }
                    this.grade = index;
                }
            }
            ,
            save:function () {
                if(this.grade == 2){
                    this.entity.parentId = this.c2.id;
                }
                if(this.grade == 3){
                    this.entity.parentId = this.c3.id;
                }

                this.entity.typeId = this.entity.typeId.id;

                axios.post("/itemCategory/save",this.entity).then(function (response) {
                    if(response.data){

                        vue.findByParentId(vue.entity.parentId);
                    }else {
                        alert("操作失败");
                    }
                })
            },
            del:function () {
                if(confirm("确定要删除吗？")) {
                    axios.post("/itemCategory/delete", this.ids).then(function (response) {
                        if (response.data ) {
                            alert("删除成功");
                            vue.findByParentId(this.ids[0]);
                        } else {
                            alert("删除失败,请先删除该分类的下级分类");
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
            show:function (category) {
                this.entity.id = category.id;
                this.entity.name = category.name;
                this.entity.typeId = this.findObjByKey(category.typeId,this.templateList);
            },
            findTemplateByIdAndName:function () {
                axios.get("/typeTemplate/findByIdAndName").then(function (response) {
                   vue.templateList = response.data;
                });
            },
            selected:function (value) {
                this.typeId = value;
            },
            initSelect: function () {
                this.entity={typeId:"",name:"",parentId:0};

            },
            findObjByKey:function (key,arr) {
                for(var i = 0 ; i < arr.length ;i ++){
                    if(arr[i].id == key){
                        return arr[i];
                    }
                }
                return "";
            }

        },
        created:function () {
            console.log("vue is created");
            this.findByParentId(0);
            this.findTemplateByIdAndName();
        },
        updated:function(){//更新数据生命周期
            //每次更新数据之后检测，全选按钮选中状态
            this.checked = this.ids.length==this.dataList.length;
        }
    });
}