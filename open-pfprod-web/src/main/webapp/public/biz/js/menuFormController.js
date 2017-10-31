/**
 * 菜单表单
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
        sort: [/^[1-9]\d*$/, '排序必须是正整数']
    });

    //监听指定开关
    form.on('switch(menuDisableSwitch)', function (data) {
        if (this.checked == false) {
            layer.tips('温馨提示：状态置为无效，将无法展示菜单', data.othis)
        }
    });
    //监听提交
    form.on('submit(addMenu)', function (data) {
        var url = basePath + '/pf/r/menu/';
        if (formType == 'add') {
            url += 'add';
        } else if (formType == 'edit') {
            url += 'edit';
        }
        if (!data.field.disable) {
            data.field.disable = 1;
        }
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
                    //parent.location.reload();//刷新父页面
                    parent.layui.table.reload('menuTableId', {
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

