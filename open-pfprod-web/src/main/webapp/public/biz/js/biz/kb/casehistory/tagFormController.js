layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common;

    iFrameHeight();

    function iFrameHeight() {
        var ifm = document.getElementById("assessTag");
        var subWeb = document.frames ? document.frames["assessTag"].document : ifm.contentDocument;      // IE和其他浏览器兼容选择
        if (ifm != null && subWeb != null) {
            ifm.height = subWeb.body.offsetHeight;              //offsetHeight 页面高度
        }
    }

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
        $('#csTag').attr('src', dataUrl + '?idDemo=' + idDemo);
    }


});

