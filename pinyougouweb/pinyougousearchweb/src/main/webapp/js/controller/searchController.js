window.onload = function (ev) {

    var vue = new Vue({

        el:"#app",
        data:{
            searchParam:{keywords:"",category:"",brand:"",price:"",spec:{},pageNum:1,sortField:'',sortValue:''},
            resultMap:{totalPages:''},
            pageNums:[],
            jumpPage:1,
            firstDot:false,
            lastDot:false,

        },
        methods:{
            search:function () {
                axios.post("/search",this.searchParam).then(
                    function (response) {
                        vue.resultMap = response.data;
                        vue.initPageNums();
                    }
                )
            },
            addSearchItem:function (key,value) {
                if(key == "category" || key =="brand" || key == "price"){
                    this.searchParam[key] = value;
                }else {
                    //规格选项
                    this.searchParam.spec[key] = value;
                }
                //执行搜索
                this.search();
            },
            removeSearchItem:function (key) {
                if(key == "category" || key =="brand" || key == "price"){
                    this.searchParam[key] = "";
                }else {
                    /** 规格选项 */
                    delete this.searchParam.spec[key];
                }
                //执行搜索
                this.search();
            },
            //初始化页码
            initPageNums:function () {
                this.pageNums = [];
                this.firstDot = false;
                this.lastDot = false;

                //当前页码
                var currentPage = this.searchParam.pageNum;

                //总页数
                var total = this.resultMap.totalPages;
                //首页
                var first = 1;
                //尾页
                var last = total;

                //每次显示页码数5


                
                //如果总页数>5
                if(total > 5 ){
                    //起始页等于当前页-显示页码的一半
                    first = currentPage -2;

                    //结束页等于当前页-显示页码的一半
                    last = currentPage +2;

                    //如果计算之后的起始页<=0，说明当前页码在小于显示页码一半的位置（在很靠前的位置，调整一下，至少显示出5页）
                    if(currentPage - 2 <=0){
                        first = 1;
                        last = 5;
                    }

                    //如果计算之后的结束页>=总页数，说明当前页码（在很靠后的位置，调整一下，至少显示出5页）
                    if(currentPage + 2 >= total){
                        last = total;
                        first = total-4;
                    }
                }

                if(first != 1)  this.firstDot = true;
                if(last != total)  this.lastDot = true;


                //循环生成页码数组
                for(var i = first ; i <=last ; i++){
                    this.pageNums.push(i);
                }

            },
            pageSearch:function (page) {
                page = parseInt(page);

                //调整一下页码
                if(page > 1 && page <= this.resultMap.totalPages && page != this.searchParam.pageNum) {
                    this.searchParam.pageNum = page;
                    this.search();
                }
            },
            sortSearch:function (key,value) {
                if(key != '' && value != ''){
                    this.searchParam.sortField = key;
                    this.searchParam.sortValue = value;
                    this.search();
                }
            }
        },
        created:function () {
            this.search();
        }

    });
}