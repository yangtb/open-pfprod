/**
 * 用户表单
 */
layui.config({
    base: '../public/layui/build/js/'
}).use(['form', 'layer', 'table', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common,
        layer = layui.layer;

    //自定义验证规则
    form.verify({
        username: [/^[A-Za-z0-9_]{3,32}$/, '必须由字母、数字、下划线组成且至少3个字符']
        // TODO 校验
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
        console.log(JSON.stringify(data.field))

        var url = contextPath + "/user/" + formType;
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
                    parent.layui.table.reload('userTableId', {
                        height: 'full-68'
                    });
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

