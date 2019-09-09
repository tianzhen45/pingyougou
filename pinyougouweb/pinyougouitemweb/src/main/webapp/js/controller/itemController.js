// 窗口加载完
window.onload = function () {
    var vue = new Vue({
        el : '#app', // 元素绑定
        data : { // 数据模型
            buyNum:0,
            specEntity:{},
            sku:{}
        },
        methods : { // 操作方法
           incr:function () {
               var num = parseInt(this.buyNum);
               if(num >= 0){
                   this.buyNum = num+1;
               }
           },
            decr:function () {
                var num = parseInt(this.buyNum);
                if(this.buyNum > 1){
                    this.buyNum = num-1;
                }
            },
            selectSpec:function (key,value) {
                Vue.set(this.specEntity,key,value);
                //查找对应的SKU商品
                this.searchSku();
            },
            isSelected:function (key,value) {
                return this.specEntity[key] == value;
            },
            //加载默认的SKU
            loadSku:function () {
               //默认第一个SKU
                this.sku = itemList[0];
                //获取商品规格选项
                this.specEntity = JSON.parse(this.sku.spec);
            },
            searchSku:function () {
                for (var i = 0; i < itemList.length; i++){
                    /** 判断规格选项是不是当前用户选中的 */
                    if(itemList[i].spec == JSON.stringify(this.specEntity)){
                        this.sku = itemList[i];
                        return;
                    }
                }
            },
            addToCart:function () {
                alert("sku商品id"+this.sku.id+",购买数量："+this.buyNum);
            }

        },
        created : function () { // 创建生命周期
           this.loadSku();
        }
    });
};