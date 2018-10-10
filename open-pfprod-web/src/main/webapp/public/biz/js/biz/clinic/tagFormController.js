layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'upload', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common;

    //执行渲染
    table.render({
        elem: '#tagTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'tagTableId'
        , height: 'full-85' //容器高度
        , cols: [[
            {type: 'radio'},
            {field: 'fgActive', minWidth: 73, title: '状态', templet: '#fgActiveTpl'},
            {field: 'name', minWidth: 130, title: '标签名称'},
            {fixed: 'right', title: '操作', align: 'center', toolbar: '#tagBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/clinic/template/tag/list'
        , where: {
            idDemo: idDemo,
        }
        , page: false
    });

    form.verify({
        name: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        },
        sort: [/^[1-9]\d*$/, '排序必须是正整数']
    });

    $('#add').on('click', function () {
        $('#reset').click();
        $('#save').click();
    });

    form.on('submit(saveTag)', function (data) {
        if (!data.field.fgActive) {
            data.field.fgActive = '0';
        }
        data.field.fgShowExec = data.field.fgShowExec ? '1' : '0';
        data.field.idDemo = idDemo;
        common.commonPost(basePath + '/pf/r/clinic/template/tag/save', data.field, '保存', '', _callBack);
        return false;
    });

    var _callBack = function (data) {
        _tableReload();
        $('#idTag').val(data.data);
    }

    //监听工具条
    table.on('tool(tagTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            _delTag(data);
        }
    });

    var _delTag = function (currentData) {
        var url = basePath + '/pf/r/clinic/template/tag/del';
        var reqData = new Array();
        var name = '【' + currentData.name + '】';
        reqData.push(currentData.idTag);
        var data = {};
        data.list = reqData;
        data.status = '1';

        layer.confirm('真的要删除标签：' + name + '么？', {
            title: '删除标签提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            common.commonPost(url, data, '删除', '', _tableReload);
            $('#reset').click();
            layer.closeAll(index);
        })
    };

    var _tableReload = function () {
        table.reload('tagTableId', {
            where: {
                idDemo: idDemo
            },
            height: 'full-85'
        });
    };

    table.on('radio(tagTableFilter)', function (obj) {
        $('#reset').click();
        $("#tagForm").autofill(obj.data);
        layui.use('form', function () {
            layui.form.render();
        });
    });

});

