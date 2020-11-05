//这里给所有ajax请求添加一个complete函数
$.ajaxSetup({
    complete: function (xhr, status) {
        //拦截器实现超时跳转到登录页面
        // 通过xhr取得响应头
        var REDIRECT = xhr.getResponseHeader("REDIRECT");
        //如果响应头中包含 REDIRECT 则说明是拦截器返回的
        if (REDIRECT == "REDIRECT" || REDIRECT == "REDIRECT2") {
            var win = window;
            if (REDIRECT == "REDIRECT") {
                while (win != win.top) {
                    win = win.top;
                }
            }
            //重新跳转
            win.location.href = xhr.getResponseHeader("CONTEXTPATH");
        }
    }
})
;

//获取当前日期
function GetCurrDate() {
    var curr_time = new Date();
    var strDate = curr_time.getFullYear() + "-";
    strDate += curr_time.getMonth() + 1 + "-";
    strDate += curr_time.getDate();
    return strDate;
}

var shadeDiv = "<div id='PageLoadingTip' style='position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDD; text-align: center;'>	<h1 style='top: 48%; position: relative; color: white;'>页面加载中···</h1>	</div>"

document.write(shadeDiv);

function _PageLoadingTip_Closes() {
    $("#PageLoadingTip").fadeOut("normal", function () {
        $(this).remove();
    });
}

var _pageloding_pc;

$.parser.onComplete = function () {
    if (_pageloding_pc)
        clearTimeout(_pageloding_pc);
    _pageloding_pc = setTimeout(_PageLoadingTip_Closes, 100);
}