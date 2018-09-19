/**
 * 菜单表单
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'element', 'common'], function () {
    var $ = layui.$
        , form = layui.form
        , common = layui.common
        , element = layui.element;

    element.tabChange('templateTab', templateType);

    form.on('submit(addSms)', function (data) {
        return _postTemplateData(data);
    });

    //监听提交
    form.on('submit(addEmail)', function (data) {
        if (!UE.getEditor('contentEditor').getContent()) {
            common.errorMsg("请填写邮件模板内容");
            return false;
        }
        return _postTemplateData(data);
    });

    var _postTemplateData = function (data) {
        var url = basePath + '/pf/r/message/';
        if (formType == 'add') {
            url += 'add';
        } else if (formType == 'edit') {
            url += 'edit';
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
                common.errorMsg("保存失败");
                return false;
            }
        });
        return false;
    }
});

