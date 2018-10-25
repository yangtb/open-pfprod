layui.config({
    base: basePath + '/public/layui/plugins/'
}).extend({
    index: 'lib/index' //主入口模块
}).use(['layer', 'table', 'form', 'jquery', 'common', 'index'], function () {

    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common;

    //执行渲染
    table.render({
        elem: '#tagTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'tagTableId'
        , height: 'full-20' //容器高度
        //, skin: 'row'
        , cols: [[
            {type: 'radio', fixed: true},
            {field: 'name', minWidth: 100, title: '组件'},
        ]] //设置表头
        , url: basePath + '/pf/r/clinic/sheet/all'
        , page: false
        , done: function (res, curr, count) {
            var default_cdEvaAsse = res.data.length >= 1 ? res.data[0].cdEvaAsse : '';
            rightTableRender(default_cdEvaAsse);
        }
    });

    function rightTableRender(default_cdEvaAsse) {
        //执行渲染
        table.render({
            elem: '#kbAssessTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'kbAssessTableId'
            , height: 'full-59' //容器高度
            , cols: [[
                {checkbox: true, fixed: true},
                {field: 'fgActive', width: 100, title: '状态', templet: '#fgActiveTpl'},
                {field: 'name', width: 190, title: '用例名称'},
                {field: 'descript', width: 200, title: '用例描述'},
                {field: 'cdEvaAsse', width: 140, title: '组件类型', templet: '#cdEvaAsseTpl'},
                {field: 'orgName', width: 170, title: '归属机构'},
                {
                    field: 'count', width: 100, title: '使用次数',
                    style: 'background-color: #5FB878; color: #fff;text-align:right'
                },
                {field: 'creator', width: 100, title: '创建人'},
                {field: 'gmtCreate', width: 170, sort: true, title: '创建时间'},
                {field: 'operator', width: 100, title: '修改人'},
                {field: 'gmtModify', width: 170, sort: true, title: '修改时间'},
                {fixed: 'right', width: 180, title: '操作', align: 'center', toolbar: '#kbAssess'}
            ]] //设置表头
            , url: basePath + '/pf/p/kb/assess/list'
            , limit: 15
            //, even: true
            , limits: [15, 30, 100]
            , page: true
        });
    };

    table.on('radio(tagTableFilter)', function (obj) {

        table.reload('kbAssessTableId', {
            url: basePath + '/pf/p/kb/assess/list'
            , where: {
                cdEvaAsse: obj.data.cdEvaAsse
            }
            , height: 'full-59' //容器高度
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    $('#add').on('click', function () {
        _addOrEdit("add");
    });

    $('#edit').on('click', function () {
        var checkStatus = table.checkStatus('kbAssessTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#edit', {tips: 1});
            return;
        }
        if (data.length > 1) {
            layer.tips('请选中一行记录进行编辑', '#edit', {tips: 1});
            return;
        }
        var currentEditData = data[0];
        _addOrEdit("edit", currentEditData);
    });

    var _addOrEdit = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增评估组件用例', basePath + '/pf/p/kb/assess/form?formType=' + formType, 420, 360, _successFunction(currentEditData));
        } else {
            common.open('编辑评估组件用例', basePath + '/pf/p/kb/assess/form?formType=' + formType, 420, 360, _successFunction(currentEditData));
        }
    };

    var _successFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    };

    //监听行双击事件
    table.on('rowDouble(kbAssessTableFilter)', function (obj) {
        _addOrEdit("edit", obj.data);
    });

    //监听工具条
    table.on('tool(kbAssessTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        }
        if (obj.event === 'editUseCase') {
            _editUseCase(data);
        }
    });

    $('#editUseCase').on('click', function () {
        var checkStatus = table.checkStatus('kbAssessTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#editUseCase', {tips: 1});
            return;
        }
        if (data.length > 1) {
            layer.tips('请选中一行记录进行编辑', '#editUseCase', {tips: 1});
            return;
        }
        _editUseCase(data[0]);
    });

    //监听删除操作
    form.on('switch(fgActiveCheckFilter)', function (obj) {
        var reqData = new Array();
        var data = {}, msg;
        reqData.push(this.value);
        data.list = reqData;
        if (obj.elem.checked) {
            data.status = '1';
            msg = '启用';
        } else {
            data.status = '0';
            msg = '停用';
        }
        common.commonPost(basePath + '/pf/r/kb/assess/updateStatus', data, msg, obj.othis);
    });

    var _editUseCase = function (data) {
        var index = common.open('组件用例维护 -> ' + '<span style="color: red">' + data.name + '</span>',
            basePath + '/pf/p/kb/assess/useCase/form?cdEvaAsse=' + data.cdEvaAsse + '&idMedCase=' + data.idMedCase, 900, 460, _successFillUseCase(data));
        layer.full(index);
    };

    var _successFillUseCase = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    };

    //监听提交
    form.on('submit(kbAssessSearchFilter)', function (data) {
        _tableReload(data.field);
    });

    var _tableReload = function (data) {
        if (!data.queryType) {
            data.queryType = 0;
        }
        table.reload('kbAssessTableId', {
            url: basePath + '/pf/p/kb/assess/list'
            , where: {
                name: data.queryType == '1' ? data.keyword : null,
                orgName: data.queryType == '2' ? data.keyword : null,
                creator: data.queryType == '3' ? data.keyword : null,
                fgPlat: data.fgPlat
            }
            , height: 'full-59' //容器高度
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    };
});

