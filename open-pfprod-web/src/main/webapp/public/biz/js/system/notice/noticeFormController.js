/**
 * 菜单表单
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'layedit', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        layedit = layui.layedit,
        common = layui.common;

    var editIndex = layedit.build('noticeContent', {
        tool: [
            'strong' //加粗
            , 'italic' //斜体
            , 'underline' //下划线
            , 'del' //删除线
            , '|' //分割线
            , 'left' //左对齐
            , 'center' //居中对齐
            , 'right' //右对齐
            , 'link' //超链接
            , 'unlink' //清除链接
            , 'face' //表情
        ]
    }); //建立编辑器

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
        data.noticeContent = formType == 'add' ? layedit.getContent(editIndex) : $('#noticeContent').val();
        var index = common.open('公告预览', basePath + '/pf/p/notice/detail', 880, 430, _successFunction(data));
        layer.full(index);
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
        if (formType == 'add') {
            data.field.noticeContent = layedit.getContent(editIndex);
        } else if (formType == 'edit') {
            data.field.noticeContent = $('#noticeContent').val();
        }
        if (!data.field.noticeContent.trim()) {
            common.errorMsg("请填写公告内容");
            return false;
        }
        var url = basePath + '/pf/r/notice/' + formType;
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

