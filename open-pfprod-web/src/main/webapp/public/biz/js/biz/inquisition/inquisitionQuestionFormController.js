
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common;

    form.verify({
        desInques: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        }
    });

    //监听提交
    form.on('submit(addQuestion)', function (data) {
        if (!data.field.fgActive) {
            data.field.fgActive = '0';
        }
        var url = basePath + '/pf/r/inquisition/question/';
        if (formType == 'add') {
            url += 'add';
        } else if (formType == 'edit') {
            url += 'edit';
        }
        return common.commonParentFormPost(url, data.field, formType, 'inquisitionQuestionTableId', '保存');
    });
});

