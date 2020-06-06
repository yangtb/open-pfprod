layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['layer', 'table', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , common = layui.common;

    table.render({
        elem: '#questionTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'questionTableId'
        , height: 'full-48' //容器高度
        , cols: [[
            {type: 'numbers', fixed: true},
            {field: 'realName', width: 160, title: '学生', fixed: true},
            {field: 'phoneNo', width: 160, title: '联系方式'},
            {field: 'caseName', width: 160, title: '病例'},
            {field: 'sdLevel', width: 120, title: '病例级别', templet: "#sdLevelTpl"},
            {field: 'sort', width: 90, title: '排序', style: 'text-align:right'},
            {field: 'sdTestplanDetail', width: 120, title: '状态', templet: "#sdTestPlanTpl"}
        ]] //设置表头
        , url: basePath + '/pf/p/test/plan/detail/list'
        , where: {
            idTestplan: idTestplan
        }
        , page: false
    });

    var _tableReload = function () {
        table.reload('questionTableId', {
            where: {
                idTestplan: idTestplan
            }
            , height: 'full-48'
        });
    };

    $('#generatePlan').on('click', function () {
        var url = basePath + '/pf/r/test/plan/detail/generate';
        var bizData ={
            idTestplan: idTestplan
        };
        return common.commonPost(url, bizData, '生成计划', '', _callback);
    });

    function _callback() {
        _tableReload();
    }

});

