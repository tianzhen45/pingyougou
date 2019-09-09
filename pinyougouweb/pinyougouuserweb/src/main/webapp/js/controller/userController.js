// 窗口加载完
window.onload = function () {
    var vue = new Vue({
        el : '#app', // 元素绑定
        data : { // 数据模型
            user:{},
            password:'',
            smsCode:''
        },
        methods : { // 操作方法
           save:function () {
               if(this.user.password != this.password){
                   alert("密码不一致,请重新输入")
                   return;
               }
               axios.post("/user/save?smsCode="+this.smsCode,this.user).then(function (response) {
                   if(response.data){
                       alert("注册成功！");
                       this.user = {};
                       this.password = "";
                       this.smsCode = "";
                   }else {
                       alert("注册失败！");
                   }
               });
           },
            sendMessage:function () {
                if(this.user.phone){
                    axios.get("/user/sendCode?phone="+this.user.phone).then(
                        function (response) {

                            if(response.data){
                                 alert("短信已成功发送，请注意查收短信");
                            }else {
                                alert("发送短信失败，请确认手机号码");
                            }
                        }
                    )
                }  else{
                    alert("请输入手机号码")
                }
            }
        },
        created : function () { // 创建生命周期
           
        }
    });
};