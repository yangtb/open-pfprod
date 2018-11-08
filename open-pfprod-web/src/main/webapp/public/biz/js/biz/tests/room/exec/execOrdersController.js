layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'jquery', 'tableSelect'], function () {
    var $ = layui.$
        , table = layui.table
        , common = layui.common
        , tableSelect = layui.tableSelect;

    //执行渲染
    table.render({
        elem: '#partConsTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'partConsTableId'
        , height: '500' //容器高度
        , toolbar: '#toolbarDemo1'
        , defaultToolbar: []
        , cols: [[
            {field: 'desInques', minWidth: 150, title: '临时用药'},
            {checkbox: true, fixed: true}
        ]] //设置表头
        , url: basePath + '/pf/p/kb/part/cons/list'
        , where: {
            idMedCase: 1
        }
        , page: false
    });


    //执行渲染
    table.render({
        elem: '#partConsTable1' //指定原始表格元素选择器（推荐id选择器）
        , id: 'partConsTableId1'
        , height: '500' //容器高度
        , toolbar: '#toolbarDemo2'
        , defaultToolbar: []
        , cols: [[
            {field: 'desInques', minWidth: 150, title: '长期用药'},
            {checkbox: true, fixed: true}
        ]] //设置表头
        , url: basePath + '/pf/p/kb/part/cons/list'
        , where: {
            idMedCase: 1
        }
        , page: false
    });

    tableSelect.render({
        elem: '#addDrugBtn',
        searchKey: 'keywords',
        checkedKey: 'addAnswerBtnId',
        searchPlaceholder: '药品名称/拼音码',
        table: {
            url: basePath + '/pf/p/drug/info/list',
            cols: [[
                {type: 'checkbox'},
                {field: 'name', title: '药品名称'},
                {field: 'spec', title: '药品规格'},
                {field: 'unit', title: '药品单位', width: 90}
            ]]
            , limits: [10, 20, 50]
            , page: true
        },
        done: function (elem, data) {

        }
    });

    tableSelect.render({
        elem: '#addLongDrugBtn',
        searchKey: 'keywords',
        checkedKey: 'addAnswerBtnId',
        searchPlaceholder: '药品名称/拼音码',
        table: {
            url: basePath + '/pf/p/drug/info/list',
            cols: [[
                {type: 'checkbox'},
                {field: 'name', title: '药品名称'},
                {field: 'spec', title: '药品规格'},
                {field: 'unit', title: '药品单位', width: 90}
            ]]
            , limits: [10, 20, 50]
            , page: true
        },
        done: function (elem, data) {

        }
    });

});

