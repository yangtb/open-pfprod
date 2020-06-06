layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['jquery', 'element'], function () {
    var $ = layui.$
        , element = layui.element;

    var sheetList = eval("(" + sheets + ")");

    // 动态加载tab
    var bottomPage = '';
    for (var i = 0; i < sheetList.length; i++) {
        if (sheetList[i].cdEvaAsse == cdEvaAsse) {
            bottomPage = appendTabContent(sheetList[i]);
        }
    }
    $('#bottomPage').html(bottomPage);

    // tab 定位
    element.tabChange('templateTab', cdEvaAsse);

    // tab 内容拼接
    function appendTabContent(sheet) {
        var result = '';
        if (sheet.script) {
            result = '<iframe id="iframe' + sheet.cdEvaAsse + '" class="layui-col-xs12" frameborder="0" ';

            if (sheet.cdEvaAsse == cdEvaAsse) {
                result += 'src="' + basePath + sheet.script + '?idEvaCase=' + idEvaCase + '"';
            }
            result += '></iframe>';
        } else {
            result = ' <blockquote class="layui-elem-quote" style="margin: 10px 10px 10px 10px">\n' +
                '   暂无模板<br>\n' +
                '   <i class="iconfont icon-xiaolian" style="font-size:2rem; "></i>\n' +
                ' </blockquote>';
        }
        return result;
    };

    /**
     * iframe 高度自适应
     */
    FrameWH();

    function FrameWH() {
        var h = $(window).height() - 110
        $("iframe").css("height", h + "px");
    }

    $(window).resize(function () {
        FrameWH();
    });

});


