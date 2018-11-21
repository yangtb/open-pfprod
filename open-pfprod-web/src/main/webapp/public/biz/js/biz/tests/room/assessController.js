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
            {type: 'numbers', title: 'R'},
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
            //{checkbox: true, fixed: true},
            {type: 'numbers', title: 'R'},
            {field: 'status', width: 90, title: '接诊状态', templet: '#assessStatusTpl'},
            {field: 'receiveDate', width: 170, title: '接诊时间'},
            {field: 'receiveConsumingTime', width: 120, align: 'right', title: '接诊耗时(m)'},
            {field: 'patName', width: 100, title: '患者姓名'},
            {field: 'patSex', width: 150, title: '性别', templet: '#sexTpl'},
            {field: 'age', width: 80, sort: true, align: 'right', title: '年龄'},
            {field: 'receiveDoc', width: 120, title: '病例'},
            {field: 'medicalrecName', width: 120, title: '接诊医师'},
            {field: 'naTestplan', width: 170, title: '测试计划'},
            {field: 'naTestpaper', width: 170, title: '试卷', templet: '#naTestpaperTpl'},
            {field: 'score', width: 120, title: '评分'},
            {field: 'ch', width: 120, title: '称号', templet: '#chTpl'},
            {field: 'AssessTeacher', width: 120, title: '评估老师'},
            {field: 'AssessDate', width: 170, title: '评估日期'}
        ]] //设置表头
        , url: basePath + '/pf/p/waiting/room/receive/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听提交
    form.on('submit(enumSearchFilter)', function (data) {
        var queryType = data.field.queryType;
        table.reload('enumTableId', {
            where: {
                fgAsses : data.field.fgAsses,
                medicalrecName: queryType == '1' ? data.field.keyword : '',
                naTestplan: queryType == '2' ? data.field.keyword : '',
                naTestpaper: queryType == '3' ? data.field.keyword : ''
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

    $('#exam').on('click', function () {
        layer.tips('暂未开放', '#exam', {tips: 1});
    });

    //监听行双击事件
    table.on('rowDouble(enumTableFilter)', function (obj) {
        setAttrAssess(obj.data)
    });

    function setAttrAssess(data) {
        $('#testAssess').attr('lay-href',
            basePath + '/pf/p/waiting/room/test/assess/page?idTestplanDetail=' + data.idTestplanDetail
            + '&idDemo=' + data.idDemo + '&idTestplan=' + data.idTestplan + '&idStudent=' + data.idStudent
            + '&idTestpaper=' + data.idTestpaper + '&idMedicalrec=' + data.idMedicalrec
            + '&idTestexecResult=' + data.idTestexecResult);
        $('#patNameAssess').text(data.patName);
        $('#testAssess').click();
    };


});

