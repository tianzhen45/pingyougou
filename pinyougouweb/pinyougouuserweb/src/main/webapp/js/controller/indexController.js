// 窗口加载完
window.onload = function () {
    var vue = new Vue({
        el: '#app' ,// 元素绑定
        data:{
            loginName:""
        },
        methods:{
            showName:function () {
                axios.get("/user/showName").then(function (response) {
                    vue.loginName = response.data.loginName;
                });
            }
        },
        created:function () {
            this.showName();
        }
    });

}