layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['element', 'jquery'], function () {
    var element = layui.element;
    var $ = jQuery = layui.jquery;
    var items = ["位运算"];
    FrameWH();
    for (var i = 0; i < items.length; i++) {
        console.log(items[i]);
    }

    function FrameWH() {
        var h = $(window).height() - 70
        $("iframe").css("height", h + "px");
    }

    $(window).resize(function () {
        FrameWH();
    });


});


