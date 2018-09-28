/**
 * 菜单表单
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common;

    form.verify({
        name: function (value) {
            if (value.length > 32) {
                return '角色名称最多32个字';
            }
        },
        code: function (value) {
            if (value.length > 32) {
                return '角色编码最多32个字';
            }
        },
        resume: function (value) {
            if (value.length > 255) {
                return '角色描述最多255个字';
            }
        }
    });

    //监听提交
    form.on('submit(addRole)', function (data) {
        var url = basePath + '/pf/r/role/';
        if (formType == 'add') {
            url += 'add';
        } else if (formType == 'edit') {
            url += 'edit';
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
                    common.sucMsg("保存成功");
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                    //刷新父页面table
                    if (formType == 'edit') {
                        parent.layui.common.refreshCurrentPage();
                    } else {
                        parent.layui.table.reload('roleTableId', {
                            height: 'full-68'
                        });
                    }
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("保存失败");
                return false;
            }
        });
        return false;
    });
});

