layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['jquery', 'element'], function () {
    var $ = layui.$
        , element = layui.element;

    var partList = eval("(" + parts + ")");

    // 动态加载tab
    var tabTitle = '',
        tabContent = '';
    var bottomPage = '';
    for (var i = 0; i < partList.length; i++) {
        //tabTitle += appendTabTitle(partList[i]);
        //tabContent += appendTabContent(partList[i]);

        if (partList[i].cdMedAsse == cdMedAsse) {
            bottomPage = appendTabContent(partList[i]);
        }
    }
    console.log(bottomPage)
    $('#bottomPage').html(bottomPage);

    //$('#tabTitle').html(tabTitle);
    //$('#tabContent').html(tabContent);

    // tab 定位
    element.tabChange('templateTab', cdMedAsse);

    // tab 选项拼接
    function appendTabTitle(part) {
        var tabResult = '<li lay-id="' + part.cdMedAsse + '"';
        if (part.cdMedAsse != cdMedAsse) {
            tabResult += 'class="layui-disabled">'
        } else {
            tabResult += '>'
        }
        tabResult += part.name + '</li>';
        return tabResult;
    };

    // tab 内容拼接
    function appendTabContent(part) {
        var result = '';
        if (part.script) {
            result = '<iframe id="iframe' + part.cdMedAsse + '" class="layui-col-xs12" frameborder="0" ';

            if (part.cdMedAsse == cdMedAsse) {
                result += 'src="' + basePath + part.script + '?idMedCase=' + idMedCase + '"';
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


