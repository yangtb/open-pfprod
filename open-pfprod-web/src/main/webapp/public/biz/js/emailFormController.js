/**
 * 菜单表单
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'common'], function () {
    var $ = layui.$
        , form = layui.form
        , common = layui.common;

    form.on('submit(addEmail)', function (data) {
        var url = basePath + '/pf/r/set/email';
        if (!data.field.sendSwitch) {
            data.field.sendSwitch = "N";
        }
        return common.commonPost(url, data.field, "设置");
    });

    form.on('submit(emailTo)', function (data) {
        var url = basePath + '/pf/r/set/email/send';
        return common.commonPost(url, data.field, "发送");
    });

    //监听指定开关
    form.on('switch(switchSend)', function (data) {
        if (this.checked == false) {
            layer.tips('温馨提示：关闭后，将无法发送系统邮件', data.othis)
        }
    });

});

