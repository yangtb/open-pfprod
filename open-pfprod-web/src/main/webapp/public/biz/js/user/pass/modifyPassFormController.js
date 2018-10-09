/**
 * 修改密码表单
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'table', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common,
        layer = layui.layer;

    //自定义验证规则
    form.verify({
        pass : [/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[\s\S]{8,16}$/, '至少包含1个大写字母，1个小写字母和1个数字']
    });

    //监听提交
    form.on('submit(modifyPass)', function (data) {

        // 判断2次密码是不是一致
        if ($("#clearNewPassword").val() != $("#clearNewPassword2").val()) {
            common.errorMsg("两次输入【新】密码不一致，请重新输入");
            return false;
        }

        var url = basePath + "/pf/r/user/updatePsw";
        layer.load(2);
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(data.field),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    common.sucMsg("修改密码成功");
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("修改密码失败");
                return false;
            }
        });
        return false;
    });

});

