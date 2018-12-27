layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['element', 'jquery'], function () {
    var element = layui.element;
    var $ = jQuery = layui.jquery;
    FrameWH();

    function FrameWH() {
        var h = $(window).height() - 70
        $("iframe").css("height", h + "px");
    }

    $(window).resize(function () {
        FrameWH();
    });

    element.on('tab(tagTabFilter)', function (data) {
        if (data.index == 1) {
            if (!$("#assessTag").attr("src")) {
                $('#assessTag').attr('src', basePath + '/pf/p/test/plan/tag/student/page?idTestplan=' + idTestplan);
            }
        }

        if (data.index == 2) {
            if (!$("#dimensionTag").attr("src")) {
                $('#dimensionTag').attr('src', basePath + '/pf/p/test/plan/tag/detail/page?idTestplan=' + idTestplan);
            }
        }
    });


});


