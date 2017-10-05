/**
 * 首页
 */
var message;
layui.config({
    base: 'public/layui/build/js/'
}).use(['app', 'message', 'common'], function () {
    var app = layui.app,
        $ = layui.jquery,
        common = layui.common,
        layer = layui.layer,
        message = layui.message;

    //主入口
    app.set({
        type: 'iframe'
    }).init();

    $("#modifyPass").on('click', function () {
        common.open('修改密码',  basePath + '/user/modifyPass', 600, 300, null, '4');
    });

    var url = basePath + '/openplatformlogout';
    $('#logout').on('click', function () {
        common.logOut('退出登录提示！', '你真的确定要退出系统吗？', url)
    })
});