layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'jquery', 'upload', 'common'], function () {
    var $ = layui.$
        , form = layui.form
        , upload = layui.upload
        , common = layui.common;

    init();

    $(document).ready(function () {
        if (previewFlag == '1') {
            var formIdArr = new Array('uploadImg', 'addKbPart');
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
    }

    function loadInfo(){
        if (!idMedCase) {
            return;
        }
        var selectUrl = basePath + '/pf/r/kb/part/pic/select';
        var bizData = {};
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
                    if (data.data)  {
                        form.val("kbPartPicFilter", data.data);
                        $('#expertImg').attr('src', data.data.path);
                    }
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

    upload.render({
        elem: '#uploadImg'
        , url: basePath + '/upload'
        , field: 'file'
        , accept: 'images' //普通文件
        , exts: 'jpg|png|bmp|jpeg'
        , before: function (obj) {
            layer.msg('正在上传图片', {icon: 16, shade: 0.01});
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#expertImg').attr('src', result);
            });
        }
        , done: function (res) {
            $('#kbPartPicPath').val(res.data.path);
            $('#idMedia').val(res.data.idMedia);
            layer.closeAll('loading');
        }
        , error: function () {
            layer.closeAll('loading');
        }
    });

    form.on('submit(addKbPartPic)', function (data) {
        if (!data.field.idMedia) {
            layer.tips('请上传图片', '#uploadImg', {tips: 1});
            return false;
        }
        data.field.idMedCase = idMedCase;
        data.field.tagFlag = tagFlag;
        if (tagFlag == '1') {
            data.field.caseName = caseName;
            data.field.idMedicalrec = idMedicalrec;
            data.field.idTag = idTag;
        }
        var url = basePath + '/pf/r/kb/part/pic/save';
        return common.commonPost(url, data.field, '保存');
    });

    $('#expertImg').on('click', function () {
        if (window.parent.openPhoto) {
            window.parent.openPhoto($('#expertImg')[0].src);
        } else {
            common.openSinglePhoto($('#expertImg')[0].src);
        }
    });

});

