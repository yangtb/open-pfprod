layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'jquery'], function () {
    var $ = layui.$
        , table = layui.table
        , common = layui.common;

    //执行渲染
    table.render({
        elem: '#partConsTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'partConsTableId'
        , height: '300' //容器高度
        , toolbar: '#toolbarDemo1'
        , defaultToolbar: []
        , cols: [[
            {type: 'numbers'},
            {field: 'desInques', minWidth: 150, title: '确诊理由'},
            {checkbox: true, fixed: true}
        ]] //设置表头
        , url: basePath + '/pf/p/kb/part/cons/list'
        , where: {
            idMedCase: 1
        }
        , page: false
    });



});

