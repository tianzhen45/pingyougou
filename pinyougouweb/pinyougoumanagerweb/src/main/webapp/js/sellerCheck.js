
window.onload = function (ev) {
    var vue = new Vue({
        el:"#app",
        data:{
            sellerList:[],
            entity:{}
        },
        methods:{
            findAll:function () {
                axios.get("/seller/findAll.do").then(function (response) {
                   vue.sellerList = response.data;
                });
            },
            findById:function (sellerId) {
                axios.get("/seller/findById.do?sellerId="+sellerId).then(function (response) {
                    vue.entity = response.data;
                });
            },
            updateStatus:function (sellerId,status) {
                axios.get("/seller/updateStatus.do?sellerId="+sellerId+"&status="+status).
                    then(function (response) {
                        if(response.data){

                        }else {
                            alert("操作失败");
                        }
                    });
            }
        },
        created:function () {
            this.findAll();
        }
    })
}