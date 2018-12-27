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
            elem: '#demoTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'demoTableId'
            , height: 'full-68' //容器高度
            , cols: [[
                {checkbox: true, fixed: true},
                //{field: 'id', width: 60, title: 'ID', fixed: true},
                {field: 'name', width: 120, title: '姓名', fixed: true},
                {field: 'phoneNo', width: 180, title: '手机号'},
                {field: 'registerTime', width: 180, title: '注册时间'},
                {field: 'money', width: 180, title: '补贴(元)'},
                {fixed: 'right', width: 150, title: '操作', align: 'center', toolbar: '#barDemo'}
            ]] //设置表头
            , url: basePath + '/pf/p/biz/excel/list'
            , limit: 15
            , even: true
            , limits: [15, 30, 100]
            , page: true
            , where: {
                status: $("select[name='status']").val()
            },
        });

        //日期范围
        laydate.render({
            elem: '#uploadTime'
            //, theme: '#393D49'
            , theme: 'molv'
            , range: true
            , calendar: true
            , max: 0
        });

        $('.downloadExcel').on('click', function () {
            document.getElementById("exportForm").action = basePath + "/pf/p/biz/excel/downloadExcel";
            document.getElementById("exportForm").submit();
        });

        upload.render({
            elem: '#test3'
            , url: '/upload/'
            , accept: 'file' //普通文件
            , done: function (res) {
                console.log(res)
            }
        });
    });
});
