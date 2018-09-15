/**
 * 用户表单
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
        username: [/^[A-Za-z0-9_]{3,32}$/, '必须由字母、数字、下划线组成且至少3个字符'],
        pass : [/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[\s\S]{8,16}$/, '至少包含1个大写字母，1个小写字母和1个数字的8-16位密码']
    });

    //监听指定开关
    form.on('switch(userEnabledSwitch)', function (data) {
        if (this.checked == false) {
            layer.tips('温馨提示：状态置为无效，用户将无法登陆', data.othis)
        }
    });

    //监听提交
    form.on('submit(addUser)', function (data) {
        var roles = new Array();
        $("input:checkbox[name='role']:checked").each(function () {
            roles.push($(this).val());
        });
        if (roles.length == 0) {
            common.errorMsg("请选择用户角色");
            return false;
        }
        data.field.roles = roles;

        // 判断2次密码是不是一致
        if ($("#clearPassword").val() != $("#clearPassword2").val()) {
            common.errorMsg("两次输入密码不一致，请重新输入");
            return false;
        }

        var url = basePath + "/pf/r/user/" + formType;
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(data.field),
            success: function (data) {
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    common.sucMsg("保存成功");
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                    //刷新父页面table
                    if (formType == 'edit') {
                        parent.layui.common.refreshCurrentPage();
                    } else {
                        parent.layui.table.reload('userTableId', {
                            height: 'full-68'
                        });
                    }
                    return true;
                }
            },
            error: function () {
                common.errorMsg("保存失败");
                return false;
            }
        });
        return false;
    });
});

