

window.onload = function () {
    var vue = new Vue({
        el:"#app",
        data:{
            dataList:[],
            entity:{specificationOptions:[],specName:"",id:""},
            pageNum:1, // 当前页码
            pages:0 ,//总页数
            ids:[] ,//选中的品牌id
            checked:false,

        },
        methods:{
            search:function (pageNum) {

                axios.get("/specification/findByPage?pageNum="+pageNum).then(function (response) {

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
                axios.post("/specification/save",this.entity).then(function (response) {
                    if(response.data){
                        vue.search(1);
                    }else {
                        alert("操作失败");
                    }
                })
            },
            del:function () {
                axios.post("/specification/delete",this.ids).then(function (response) {
                    if(response){
                        alert("删除成功");
                        vue.search(1);
                    }else {
                        alert("删除失败");
                    }
                })
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
                //修改规格前
                this.entity.specName = b.specName;
                this.entity.id = b.id;
               //查出该条规格下的规格选项
                axios.get("/specification/findSpecificationOptions?id="+b.id).then(
                    function (response) {
                        vue.entity.specificationOptions = response.data;
                    }
                );

            },
            addTableRow:function () {
                this.entity.specificationOptions.push({});
            },
            delTableRow:function (index) {
                this.entity.specificationOptions.splice(index,1); //删除数组元素：从index向后删除1个
            }
        },
        created:function () {
            console.log("vue is created")
            this.search(1);
        },
        updated:function(){//更新数据生命周期
            //每次更新数据之后检测，全选按钮选中状态
            this.checked = this.ids.length==this.dataList.length;
        }
    });
}