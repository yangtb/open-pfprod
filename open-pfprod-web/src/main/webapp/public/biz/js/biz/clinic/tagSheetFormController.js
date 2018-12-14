layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'upload', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , common = layui.common;

    //执行渲染
    table.render({
        elem: '#tagTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'tagTableId'
        , height: 'full-20' //容器高度
        , cols: [[
            {type: 'radio', fixed: true},
            {field: 'sort', title: '', width: 40},
            {field: 'path', width: 60, title: 'logo', align: 'center', templet: '#imgTpl'},
            {field: 'name', width: 100, title: '标签名称'},
            {field: 'fgActive', width: 60, title: '状态', align: 'center', templet: '#fgActiveTpl'},
            {fixed: 'right', title: '操作', align: 'center', toolbar: '#tagBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/clinic/template/tag/sheet/list'
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
        $('#demo1').attr('src', basePath + '/public/biz/img/photo-default.png'); //图片链接
    });

    form.on('submit(saveTag)', function (data) {
        if (!data.field.fgActive) {
            data.field.fgActive = '0';
        }
        data.field.fgShowMake = data.field.fgShowMake ? '1' : '0';
        data.field.fgShowExec = data.field.fgShowExec ? '1' : '0';
        data.field.idDemo = idDemo;
        common.commonPost(basePath + '/pf/r/clinic/template/tag/sheet/save', data.field, '保存', '', _callBack);
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
        var url = basePath + '/pf/r/clinic/template/tag/sheet/del';
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
            height: 'full-20'
        });
    };

    //单击行选中radio
    table.on('row(tagTableFilter)', function(obj){
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');//选中行样式
        obj.tr.find('input[lay-type="layTableRadio"]').prop("checked",true);
        form.render('radio');

        $('#reset').click();
        $("#tagForm").autofill(obj.data);
        layui.use('form', function () {
            layui.form.render();
        });
        $('#demo1').attr('src', obj.data.path ? obj.data.path : basePath + '/public/biz/img/photo-default.png'); //图片链接

    });

    /*table.on('radio(tagTableFilter)', function (obj) {
        $('#reset').click();
        $("#tagForm").autofill(obj.data);
        layui.use('form', function () {
            layui.form.render();
        });
        $('#demo1').attr('src', obj.data.path ? obj.data.path : basePath + '/public/biz/img/photo-default.png'); //图片链接
    });*/

    upload.render({
        elem: '#test3'
        , url: basePath + '/upload'
        , field: 'file'
        , accept: 'images' //普通文件
        , exts: 'jpg|png|bmp|jpeg'
        , before: function (obj) {
            layer.msg('正在上传图片', {icon: 16, shade: 0.01});
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#demo1').attr('src', result); //图片链接
            });
        }
        , done: function (res) {
            if (res.code != '0') {
                layer.tips(res.msg, '#test3', {
                    tips: [1, '#FF5722'],
                    time: 5000
                });
                return;
            }
            $('#partPath').val(res.data.path);
            $('#idMediaPart').val(res.data.idMedia);
            layer.closeAll('loading');
        }
        , error: function () {
            layer.closeAll('loading');
        }
    });

    $('#demo1').on('click', function () {
        common.openSinglePhoto($('#demo1')[0].src);
    });

});

