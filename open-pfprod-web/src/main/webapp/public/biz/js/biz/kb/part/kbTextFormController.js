
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common;

    var selectUrl = basePath + '/pf/r/kb/part/text/select';
    var bizData  = {};
    bizData.idMedCase = idMedCase;
    layer.load(2);
    $.ajax({
        url: selectUrl,
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bizData),
        success: function (data) {
            layer.closeAll('loading');
            if (data.code != 0) {
                common.errorMsg(data.msg);
                return false;
            } else {
                form.val("kbPartTextFilter", data.data);
                return true;
            }
        },
        error: function () {
            layer.closeAll('loading');
            common.errorMsg("查询失败");
            return false;
        }
    });


    form.on('submit(addKbPartText)', function (data) {
        data.field.idMedCase = idMedCase;
        var url = basePath + '/pf/r/kb/part/text/save';
        return common.commonPost(url, data.field, '保存');
    });


    form.on('submit(saveAsKbPartText)', function (data) {
        data.field.idMedCase = idMedCase;
        var url = basePath + '/pf/r/kb/part/text/save';
        return common.commonPost(url, data.field, '另存');
    });

});


