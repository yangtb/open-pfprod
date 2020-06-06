/**
 * 菜单表单
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'layedit', 'common'], function () {
    var $ = layui.$
        , form = layui.form
        , layedit = layui.layedit
        , common = layui.common;

    layedit.build('emailContent', {
        tool: [
        'strong' //加粗
        ,'italic' //斜体
        ,'underline' //下划线
        ,'del' //删除线
        ,'|' //分割线
        ,'left' //左对齐
        ,'center' //居中对齐
        ,'right' //右对齐
        ,'link' //超链接
        ,'unlink' //清除链接
        ,'face' //表情
    ]
    });

    form.on('submit(addEmail)', function (data) {
        var url = basePath + '/pf/r/set/email';
        if (!data.field.sendSwitch) {
            data.field.sendSwitch = "N";
        }
        return common.commonPost(url, data.field, "设置");
    });

    form.on('submit(emailTo)', function (data) {
        if (!data.field.recipients) {
            $('#recipients').focus();
            layer.tips("请填写收件人邮箱", '#recipients', {tips: 1});
            return false;
        }
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

