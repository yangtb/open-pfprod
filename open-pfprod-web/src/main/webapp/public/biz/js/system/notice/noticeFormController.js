/**
 * 菜单表单
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common;

    //自定义验证规则
    form.verify({
        title: function (value) {
            if (value.length < 4) {
                return '标题至少得4个字符';
            }
        },
    });

    $("#preview").on('click', function () {
        var data = {};
        data.noticeTitle = $("input[name='noticeTitle']").val();
        data.noticeType = $("select[name='noticeType']").val();
        data.noticeContent = UE.getEditor('noticeContentEditor').getContent(); //获取html
        var index = common.open('公告预览', basePath + '/pf/p/notice/detail', 880, 430, _successFunction(data));
        layer.full(index);
    });

    $("#refresh").on('click', function () {
        window.location.reload();
    });

    var _successFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    }

    //监听提交
    form.on('submit(addNotice)', function (data) {
        if (!UE.getEditor('noticeContentEditor').getContent()) {
            common.errorMsg("请填写公告内容");
            return false;
        }
        var url = basePath + '/pf/r/notice/';
        if (formType == 'add') {
            url += 'add';
        } else if (formType == 'edit') {
            url += 'edit';
        }
        layer.load(2);
        $.ajax({
            url: url,
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
                    common.sucMsg("保存成功");
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                    //刷新父页面table
                    if (formType == 'edit') {
                        parent.layui.common.refreshCurrentPage();
                    } else {
                        parent.layui.table.reload('noticeTableId', {
                            height: 'full-68'
                        });
                    }
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("保存失败");
                return false;
            }
        });
        return false;
    });
});

