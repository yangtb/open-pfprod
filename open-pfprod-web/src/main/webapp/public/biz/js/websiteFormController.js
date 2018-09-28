/**
 * 菜单表单
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'common'], function () {
    var $ = layui.$
        , form = layui.form
        , common = layui.common;

    form.on('submit(addWebsite)', function (data) {
        var url = basePath + '/pf/r/set/website';
        if (!data.field.logSwitch) {
            data.field.logSwitch = "N";
        }
        layer.load(1);
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
                    common.sucMsg("设置成功");
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("设置失败");
                return false;
            }
        });
        return false;
    });

    //监听指定开关
    form.on('switch(switchLog)', function (data) {
        if (this.checked == false) {
            layer.tips('温馨提示：关闭后，将无法产生用户操作日志', data.othis)
        }
    });

});

