
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'common', 'tableSelect'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common
        , tableSelect = layui.tableSelect;

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

    tableSelect.render({
        elem: '#reasonIn',
        checkedKey: 'reasonInId',
        table: {
            url: basePath + '/pf/p/waiting/room/test/die/ready/reason/list'
            , cols: [[
                {type: 'checkbox'},
                {field: 'idText', minWidth: 150, title: '确诊理由'},
                {field: 'sdEvaEffciency', width: 80, title: '阶段', templet: '#sdEvaTpl'}
            ]] //设置表头
            , where: {
                idTestexecResult: idTestexecResult
            }
            , limit: 1000
            , limits: [30, 50]
        },
        done: function (elem, data) {
            var NEWJSON = []
            layui.each(data.data, function (index, item) {
                NEWJSON.push(item.idText)
            })
            elem.val(NEWJSON.join("；"))
        }
    });

});

