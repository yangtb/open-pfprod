layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'common'], function () {
    var form = layui.form,
        common = layui.common;

    form.verify({
        name: function (value) {
            if (value.length > 64) {
                return '长度不能超过64个字';
            }
        }
    });

    //监听提交
    form.on('submit(addTemplate)', function (data) {
        if (!data.field.fgActive) {
            data.field.fgActive = '0';
        }
        var url = basePath + '/pf/r/case/history/template/';
        if (formType == 'add') {
            url += 'add';
        } else if (formType == 'edit') {
            url += 'edit';
        }
        return common.commonParentFormPost(url, data.field, formType, 'templateTableId', '保存');
    });
});

