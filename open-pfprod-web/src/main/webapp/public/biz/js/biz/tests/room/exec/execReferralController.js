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
        , height: '734' //容器高度
        , cols: [[
            {field: 'aa', minWidth: 150, title: '疾病名称'},
            {field: 'ca', minWidth: 150, title: '疾病目录'},
            {field: 'icd', title: 'ICD'},
            {checkbox: true, fixed: true}
        ]] //设置表头
        , url: basePath + '/pf/p/kb/part/cons/list'
        , where: {
            idMedCase: 1
        }
        , page: false
    });



});

