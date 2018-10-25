layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'jquery', 'upload', 'common'], function () {
    var $ = layui.$
        , form = layui.form
        , upload = layui.upload
        , common = layui.common;

    init();

    function init() {
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
                    form.val("kbPartPicFilter", data.data);
                    $('#expertImg').attr('src', data.data.path);
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
        data.field.idMedCase = idMedCase;
        var url = basePath + '/pf/r/kb/part/pic/save';
        return common.commonPost(url, data.field, '保存');
    });


    form.on('submit(saveAsKbPartPic)', function (data) {
        data.field.idMedCase = idMedCase;
        var url = basePath + '/pf/r/kb/part/pic/save';
        return common.commonPost(url, data.field, '重载');
    });

    $('#expertImg').on('click', function () {
        window.parent.openPhoto($('#expertImg')[0].src);
    });

});

