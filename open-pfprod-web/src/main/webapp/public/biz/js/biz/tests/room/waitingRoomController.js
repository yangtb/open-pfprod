layui.config({
    base: basePath + '/public/layui/plugins/'
}).extend({
    index: 'lib/index' //主入口模块
}).use(['table', 'form', 'jquery', 'index', 'common', 'view'], function () {
    var $ = layui.$,
        table = layui.table,
        form = layui.form,
        common = layui.common
        , view = layui.view;

    //执行渲染
    table.render({
        elem: '#roomTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'roomTableId'
        , title: '候诊室'
        , toolbar: '#toolbarDemo1'
        , defaultToolbar: []
        , height: 'full-68' //容器高度
        , cols: [[
            {type: 'radio', fixed: true},
            {field: 'status', width: 90, title: '接诊状态', templet: '#statusTpl'},
            {field: 'patName', width: 90, title: '患者姓名'},
            {field: 'patSex', width: 70, title: '性别', templet: '#sexTpl'},
            {field: 'distributeDoc', width: 120, title: '分配医师'},
            {field: 'naTestplan', width: 160, title: '测试计划'},
            {field: 'naTestpaper', width: 160, title: '试卷名称', templet: '#naTestpaperTpl'}
        ]] //设置表头
        , url: basePath + '/pf/p/waiting/room/list'
        , limit: 15
        , even: true
        , page: {//支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            , groups: 1 //只显示 1 个连续页码
            , first: false //不显示首页
            , last: false //不显示尾页
            , limits: [15, 30, 100]
        }
    });

    //执行渲染
    table.render({
        elem: '#enumTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'enumTableId'
        , title: '接诊列表'
        , toolbar: '#toolbarDemo2'
        , defaultToolbar: []
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            //{field: 'id', width: 60, title: 'ID', fixed: true},
            {field: 'groupName', width: 150, title: '字典名称'},
            {field: 'roomtName', width: 150, title: '枚举名称'},
            {field: 'roomtCode', width: 150, title: '枚举编码'},
            {field: 'sortNum', width: 70, sort: true, title: '排序'},
            {field: 'remark', width: 150, title: '备注'},
            {field: 'gmtCreate', width: 170, sort: true, title: '添加时间'},
            {field: 'operator', width: 120, title: '最后修改人'},
            {fixed: 'right', width: 100, title: '操作', align: 'center', toolbar: '#enumBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/room/enum/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听提交
    form.on('submit(roomSearchFilter)', function (data) {
        table.reload('roomTableId', {
            where: {
                roomName: data.field.roomName
            },
            height: 'full-68'
        });
    });

    //监听提交
    form.on('submit(enumSearchFilter)', function (data) {
        table.reload('enumTableId', {
            where: {
                enumName: data.field.enumName
            },
            height: 'full-68'
        });
    });

    //监听行双击事件
    table.on('rowDouble(roomTableFilter)', function (obj) {
        setAttr(obj.data)
    });

    $('#receivePat').on('click', function () {
        var currentData = _getEnumCheckData();
        if (currentData.length == 0) {
            layer.tips('请先选中一行记录', '#receivePat', {tips: 1});
            return;
        }
        setAttr(currentData[0])
    });
    // 获取编辑行数据
    var _getEnumCheckData = function () {
        var checkStatus = table.checkStatus('roomTableId')
            , data = checkStatus.data;
        return data;
    }

    function setAttr(data) {
        $('#test').attr('lay-href',
            basePath + '/pf/p/waiting/room/exam/page?idTestplanDetail=' + data.idTestplanDetail
            + '&idDemo=' + data.idDemo + '&idTestplan=' + data.idTestplan + '&idStudent=' + data.idStudent
            + '&idTestpaper=' + data.idTestpaper + '&idMedicalrec=' + data.idMedicalrec);
        $('#patName').text(data.patName);
        $('#test').click();
    }


});

