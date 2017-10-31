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
        pass : [/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[\s\S]{8,16}$/, '至少包含1个大写字母，1个小写字母和1个数字的8-16位密码']
    });

    //监听提交
    form.on('submit(resetPsw)', function (data) {
        var url = basePath + "/pf/r/user/resetPsw";
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(data.field),
            success: function (data) {
                console.log(data)
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    common.sucMsg("重置密码成功");
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                    return true;
                }
            },
            error: function () {
                common.errorMsg("重置密码失败");
                return false;
            }
        });
        return false;
    });

});

