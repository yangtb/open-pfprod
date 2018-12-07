
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common;


    $(document).ready(function () {
        if (previewFlag == '1') {
            var formIdArr = new Array('name', 'cdEvaAsse', 'descript', 'fgGroup', 'fgActive', 'saveBtn');
            common.setFormStatus('0', formIdArr);
        }
    });

    form.verify({
        commonLength: function (value) {
            if (value.length > 64) {
                return '长度不能超过64个字';
            }
        },
        descript : function (value) {
            if (value && value.length > 255) {
                return '长度不能超过255个字';
            }
        }
    });

    //监听提交
    form.on('submit(addKbAssess)', function (data) {
        if (!data.field.fgActive) {
            data.field.fgActive = '0';
        }
        var url = basePath + '/pf/r/kb/assess/';
        if (formType == 'add') {
            url += 'add';
        } else if (formType == 'edit') {
            url += 'edit';
        }
        return common.commonParentFormPost(url, data.field, formType, 'kbAssessTableId', '保存');
    });

});


