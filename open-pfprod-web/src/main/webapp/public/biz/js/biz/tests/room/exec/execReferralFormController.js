
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common;

    form.verify({
        desLength: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        }
    });

    //监听提交
    form.on('submit(addReferral)', function (data) {
        layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/waiting/room/referral/save',
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
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.initReferralList();
                    parent.layer.close(index);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("保存拟诊失败");
                return false;
            }
        });
        return false;
    });

});

