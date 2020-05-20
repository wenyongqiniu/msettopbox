Vue.prototype.$http = axios;
var app = new Vue({
    el: "#list",
    data: {
        tit: '医学微视—tv',
        webdata: {
            searth: "",
            time: ""
        }
    },
    mounted() {
        this.getTime()
        console.log(http)
    },
    updated() {

    },
    methods: {
        list1_ck: function() {
            console.log()
        },
        list2_ck: function() {
            console.log()
        },
        getTime: function() {
            var myDate = new Date();
            var H = myDate.getHours(); //获取当前小时数(0-23)
            var M = myDate.getMinutes(); //获取当前分钟数(0-59)
            this.webdata.time = H + ":" + M;
            var that = this;
            var timer = setInterval(function() {
                H = myDate.getHours(); //获取当前小时数(0-23)
                M = myDate.getMinutes(); //获取当前分钟数(0-59)

                that.webdata.time = (H + ":" + M);
            }, 5000)
        }
    }
})
