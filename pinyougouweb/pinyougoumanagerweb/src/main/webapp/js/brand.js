

window.onload = function () {
        var vue = new Vue({
            el:"#app",
            data:{
                dataList:[],
                entity:{},
                pageNum:1, // 当前页码
                pages:0 ,//总页数
                ids:[] ,//选中的品牌id
                checked:false
            },
            methods:{
                search:function (pageNum) {

                    axios.get("/brand/findByPage?pageNum="+pageNum).then(function (response) {

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
                    axios.post("/brand/save",this.entity).then(function (response) {
                        if(response.data){
                            vue.search();
                        }else {
                            alert("操作失败");
                        }
                    })
                },
                del:function () {
                    axios.post("/brand/delete",this.ids).then(function (response) {
                        if(response){
                            alert("删除成功");
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
                    this.entity = b;
                    // this.brand.name = b.name;
                    // this.brand.firstChar = b.firstChar;
                    // 把b对象转化成json字符串
                    // var jsonStr = JSON.stringify(b);
                    // 把json字符串转化成一个新的json对象
                    // this.brand = JSON.parse(jsonStr);

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