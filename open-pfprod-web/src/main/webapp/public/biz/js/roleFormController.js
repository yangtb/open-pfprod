/**
 * 菜单表单
 */
layui.config({
    base: '../public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common;

    //监听提交
    form.on('submit(addRole)', function (data) {
        var url = '';
        if (formType == 'add') {
            url = 'add';
        } else if (formType == 'edit') {
            url = 'edit';
        }
        console.log(formType)
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
                    parent.layui.table.reload('roleTableId', {
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

