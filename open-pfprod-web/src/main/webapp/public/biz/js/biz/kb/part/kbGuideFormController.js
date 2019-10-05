layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'jquery', 'common', "upload"], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common
        , upload = layui.upload;

    init();

    $(document).ready(function () {
        if (previewFlag == '1') {
            var formIdArr = new Array('content', 'saveBtn');
            common.setFormStatus('0', formIdArr);
        }
    });

    function init() {
        if (tagFlag == '1') {
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
                        if (data.data) {
                            idMedCase = data.data.idMedCase;
                        }
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
        var selectUrl = basePath + '/pf/r/kb/part/guide/select';
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
                    form.val("kbPartGuideFilter", data.data);
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


    form.on('submit(addKbPartGuide)', function (data) {
        data.field.idMedCase = idMedCase;
        data.field.tagFlag = tagFlag;
        if (tagFlag == '1') {
            data.field.caseName = caseName;
            data.field.idMedicalrec = idMedicalrec;
            data.field.idTag = idTag;
        }
        var url = basePath + '/pf/r/kb/part/guide/save';
        return common.commonPost(url, data.field, '保存');
    });


    upload.render({
        elem: '#LAY_avatarUpload'
        , url: basePath + '/upload'
        , field: 'file'
        , accept: 'file' //普通文件
        , number : 0
        , exts: 'pdf|PDF'
        , before: function (obj) {
            layer.msg('正在上传指南说明，若文件过大，请耐心等待', {
                icon: 16,
                shade: 0.01,
                time: false
            });
        }
        , done: function (res) {
            if (res.code != '0') {
                layer.tips(res.msg, '#LAY_avatarUpload', {
                    tips: [1, '#FF5722'],
                    time: 5000
                });
                return;
            }
            $('#guideNotesUrl').val(res.data.path);
            layer.closeAll();
        }
        , error: function(index, upload) {
            layer.closeAll();
        }
    });

    $('#preview').on('click', function () {
        var guideNotesUrl = $('#guideNotesUrl').val();
        if (!guideNotesUrl) {
            layer.tips('请先上传文件', '#LAY_avatarUpload');
            return;
        }
        window.open(guideNotesUrl, "_blank");
    });


});


