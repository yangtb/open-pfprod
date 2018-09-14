/**
 * demo
 * @constructor
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'upload', 'laydate', 'common'], function () {
    var $ = layui.$
        , upload = layui.upload
        , table = layui.table
        , form = layui.form
        , laydate = layui.laydate
        , common = layui.common;


    layui.use('upload', function () {
        var $ = layui.jquery
            , upload = layui.upload;

        //执行渲染
        table.render({
            elem: '#bannerTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'bannerTableId'
            , height: 'full-68' //容器高度
            , cols: [[
                {checkbox: true, fixed: true},
                //{field: 'status', width: 100, title: '状态', fixed: true},
                {field: 'status', title:'状态', width:95, templet: '#switchTpl', unresize: true},
                {field: 'title', width: 180, title: '标题'},
                {field: 'shortName', width: 180, title: 'banner简称'},
                {field: 'money', width: 80, title: '城市'},
                {field: 'money1', width: 80, title: '人群'},
                {field: 'noticeType', width: 180, title: 'banner位名称'},
                {field: 'sortNum', width: 80, title: '排序'},
                {field: 'startTime', width: 120, title: '开始时间'},
                {field: 'endTime', width: 120, title: '结束时间'},
                {field: 'operator', width: 100, title: '操作人员'},
                {fixed: 'right', width: 150, title: '操作', align: 'center', toolbar: '#bannerBar'}
            ]] //设置表头
            , url: basePath + '/pf/p/banner/list'
            , limit: 15
            , even: true
            , limits: [15, 30, 100]
            , page: true
            , where: {
                status: $("select[name='status']").val()
            },
        });

        $('.add').on('click', function () {
            _addOrEdit("add");
        });

        var _addOrEdit = function (formType, currentEditData) {
            if (formType == 'add') {
                common.open('新增banner', 'form?formType=' + formType, 700, 470);
            } else {
                common.open('编辑banner', 'form?formType=' + formType, 700, 470, _successFunction(currentEditData));
            }
        };

        var _successFunction = function (data) {
            return function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                //调用子页面的全局函数
                iframe.fullForm(data);
            }
        }
    });
});
