/**
 * 菜单表单
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'element','layedit', 'common'], function () {
    var $ = layui.$
        , form = layui.form
        , element = layui.element
        , layedit = layui.layedit
        , common = layui.common;

    var editIndex = layedit.build('emailContent'); //建立编辑器

    element.tabChange('templateTab', templateType);

    form.verify({
        templateName: function (value) {
            if (value.length > 64) {
                return '模板名称最多64个字';
            }
        },
        templateCode: function (value) {
            if (value.length > 32) {
                return '模板编码最多32个字符';
            }
        }
    });

    form.on('submit(addSms)', function (data) {
        return _postTemplateData(data);
    });

    //监听提交
    form.on('submit(addEmail)', function (data) {
        if (formType == 'add') {
            data.field.emailContent = layedit.getContent(editIndex);
        } else if (formType == 'edit') {
            data.field.emailContent = $('#emailContent').val();
        }
        if (!data.field.emailContent.trim()) {
            common.errorMsg("请填写邮件模板内容");
            return false;
        }
        return _postTemplateData(data);
    });

    var _postTemplateData = function (data) {
        var url = basePath + '/pf/r/message/' + formType;
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
                    common.sucMsg("保存成功");
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                    //刷新父页面table
                    if (formType == 'edit') {
                        parent.layui.common.refreshCurrentPage();
                    } else {
                        parent.layui.common.refreshCurrentPage();
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
    };

});



