layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'layer', 'element', 'tableSelect', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common
        , tableSelect = layui.tableSelect;

    //执行渲染
    table.render({
        elem: '#kbTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'kbTableId'
        , height: '340' //容器高度
        , cols: [[
            {type: 'radio'},
            {field: 'sdEva', width: 90, title: '评估阶段', templet: '#sdEvaTpl'},
            {field: 'itemName', minWidth: 90, title: '评估项'},
            {field: 'scoreEva', width: 60, title: '分值'},
            {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#kbBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/kb/assess/effciency/list'
        , where: {
            idEvaCase: idEvaCase,
            cdEvaAsse : cdEvaAsse
        }
        , page: false
    });

    form.verify({
        desAnswer: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        }
    });

    table.on('radio(kbTableFilter)', function (obj) {
        $('#reset').click();
        $("#kbForm").autofill(obj.data);
        form.render();

    });

    //监听提交
    form.on('submit(saveAnswer)', function (data) {

        data.field.idEvaCase = idEvaCase;
        data.field.cdEvaAsse = cdEvaAsse;
        data.field.sdEvaEffciency = data.field.sdEva;

        var url = basePath + '/pf/r/kb/assess/effciency/save';
        return common.commonPost(url, data.field, '保存', '', _callBack);
    });

    var _callBack = function (data) {
        _kbTableReload();
        $('#idEvaCaseItem').val(data.data);
    }


    var _kbTableReload = function () {
        table.reload('kbTableId', {
            where: {
                idEvaCase: idEvaCase,
                cdEvaAsse : cdEvaAsse
            }
        });
    };

    $('#add').on('click', function () {
        $('#reset').click();
        $('#save').click();
    });

    //监听工具条
    table.on('tool(kbTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {

            layer.confirm('真的要删除评估项【' + data.itemName + '】么？', function (index) {
                var url = basePath + '/pf/r/kb/assess/common/del';
                var reqData = new Array();
                reqData.push(obj.data.idEvaCaseItem);
                var data = {};
                data.list = reqData;
                data.status = '1';
                data.extType = 'effciency';
                common.commonPost(url, data, '删除');
                obj.del();
            });

        }
    });


    $('#saveAs').on('click', function () {
        layer.tips('正在开发...', '#saveAs', {tips: 1});
    });


});

