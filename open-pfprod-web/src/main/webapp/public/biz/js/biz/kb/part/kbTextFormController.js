layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common;

    init();

    function init() {
        if (tagFlag == '1' && idMedCase == '') {
            // 查询idMedCase
            var medData = {
                idMedicalrec: idMedicalrec,
                idTag: idTag
            }
            $.ajax({
                url: basePath + '/pf/r/case/history/select/med/tag',
                type: 'post',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(medData),
                success: function (data) {
                    layer.closeAll('loading');
                    if (data.code != 0) {
                        common.errorMsg(data.msg);
                        return false;
                    } else {
                        idMedCase = data.data.idMedCase;
                        loadInfo()
                        return true;
                    }
                },
                error: function () {
                    layer.closeAll('loading');
                    common.errorMsg("查询失败");
                    return false;
                }
            });
        } else {
            loadInfo();
        }
    };


    function loadInfo() {
        if (!idMedCase) {
            return;
        }
        var selectUrl = basePath + '/pf/r/kb/part/text/select';
        var bizData = {
            idMedCase: idMedCase
        };
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
    }


    form.on('submit(addKbPartText)', function (data) {
        data.field.idMedCase = idMedCase;
        data.field.tagFlag = tagFlag;
        if (tagFlag == '1') {
            data.field.caseName = caseName;
            data.field.idMedicalrec = idMedicalrec;
            data.field.idTag = idTag;
        }
        var url = basePath + '/pf/r/kb/part/text/save';
        return common.commonPost(url, data.field, '保存');
    });


});


