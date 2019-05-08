//这里给所有ajax请求添加一个complete函数
$.ajaxSetup({
    complete : function(xhr, status) {
        //拦截器实现超时跳转到登录页面
        // 通过xhr取得响应头
        var REDIRECT = xhr.getResponseHeader("REDIRECT");
        //如果响应头中包含 REDIRECT 则说明是拦截器返回的
        if (REDIRECT == "REDIRECT")
        {
            var win = window;
            while (win != win.top)
            {
                win = win.top;
            }
            //重新跳转
            win.location.href = xhr.getResponseHeader("CONTEXTPATH");
        }
    }
});

//获取当前日期
function GetCurrDate() {
    var curr_time = new Date();
    var strDate = curr_time.getFullYear() + "-";
    strDate += curr_time.getMonth() + 1 + "-";
    strDate += curr_time.getDate();
    return strDate;
}