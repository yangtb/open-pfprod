layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['element', 'jquery'], function () {
    var element = layui.element;
    var $ = jQuery = layui.jquery;

    init();

    function init() {
        var li = document.querySelectorAll("li");
        for (var i = 0; i < li.length; i++) {
            li[i].addEventListener('click', function () {
                loadIframe(this.getAttribute('data-url'));
            });
        }
    };


    function loadIframe(dataUrl) {
        if (dataUrl === basePath || !dataUrl) {
            return;
        }
        $('#caseHistoryTag').attr('src', dataUrl + '?idDemo=' + idDemo);
    };

    FrameWH();

    function FrameWH() {
        var h = $(window).height() - 70
        $("iframe").css("height", h + "px");
    }

    $(window).resize(function () {
        FrameWH();
    });

    /*element.on('tab(tagTabFilter)', function (data) {
        if (data.index == 1) {
            if (!$("#assessTag").attr("src")) {
                $('#assessTag').attr('src', basePath + '/pf/p/case/history/tag/assess/form?idDemo=' + idDemo);
            }
        }
    });*/



});


