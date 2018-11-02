layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common;

    //执行渲染
    table.render({
        elem: '#planTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'planTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            {field: 'fgActive', width: 100, title: '数据状态', fixed: true, templet: '#fgActiveTpl'},
            {field: 'naTestplan', minWidth: 180, title: '测试计划名称'},
            {field: 'desTestplan', width: 200, title: '测试计划描述'},
            {field: 'idTestpaperText', width: 160, title: '测试试卷'},
            {field: 'sdTestplan', width: 150, title: '计划状态', templet: '#sdTestPlanTpl'},
            {field: 'orgName', width: 150, title: '归属机构'},
            {field: 'gmtCreate', width: 170, sort: true, title: '创建时间'},
            {field: 'operator', width: 100, title: '创建人'},
            {fixed: 'right', width: 180, title: '操作', align: 'center', toolbar: '#plan'}
        ]] //设置表头
        , url: basePath + '/pf/p/test/plan/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听工具条
    table.on('tool(planTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        } if (obj.event === 'addPlanDetail') {
            _addPlanDetail(data.naTestplan, data.idTestplan);
        }
    });

    //监听提交
    form.on('submit(planSearchFilter)', function (data) {
        table.reload('planTableId', {
            where: {
                naTestplan: data.field.naTestplan
            }
            , height: 'full-68'
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    $('#add').on('click', function () {
        _addOrEdit("add");
    });

    $('#edit').on('click', function () {
        var checkStatus = table.checkStatus('planTableId')
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
            common.open('新增模拟计划', basePath + '/pf/p/test/plan/form?formType=' + formType, 560, 435);
        } else {
            common.open('编辑模拟计划', basePath + '/pf/p/test/plan/form?formType=' + formType, 560, 435, _successFunction(currentEditData));
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
    table.on('rowDouble(planTableFilter)', function (obj) {
        _addOrEdit("edit", obj.data);
    });

    $("#del").on('click', function () {
        var checkStatus = table.checkStatus('planTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#del', {tips: 1});
            return;
        }
        _delPartDefine(data);
    });

    var _delPartDefine = function (currentData) {
        var url = basePath + '/pf/r/test/plan/del';
        var reqData = new Array();
        var messageTitle = '';
        var dontDelPlan = '';
        $.each(currentData, function (index, content) {
            if (messageTitle) {
                messageTitle += ', ';
            }
            messageTitle += '【' + content.naTestplan + '】';
            reqData.push(content.idTestplan);
            if (content.sdTestplan == '2') {
                if (dontDelPlan) {
                    dontDelPlan += ', ';
                }
                dontDelPlan += '【' + content.naTestplan + '】';
            }
        });
        if (dontDelPlan) {
            layer.alert('测试计划' + dontDelPlan + '已执行，<span style="color: red; font-weight: bold;">不允许删除</span>！');
            return;
        }
        var data = {};
        data.list = reqData;
        data.status = '1';
        layer.confirm('确定删除' + messageTitle + '么？', {
            title: '删除模拟计划提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "删除");
        })
    }

    var _commonAjax = function (index, url, reqData, msg) {
        layer.load(2);
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(reqData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    common.sucChildMsg(msg + "成功");
                    if (index) {
                        layer.close(index);
                    }
                    _planTableReload();
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg(msg + "失败");
                return false;
            }
        });
    }

    var _planTableReload = function () {
        table.reload('planTableId', {
            where: {
                //type: type
            },
            height: 'full-68'
        });
    }

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
        common.commonPost(basePath + '/pf/r/test/plan/updateStatus', data, msg, obj.othis);
    });


    $('#addPlanDetail').on('click', function () {
        var checkStatus = table.checkStatus('planTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#addPlanDetail', {tips: 1});
            return;
        }
        if (data.length > 1) {
            layer.tips('请选中一行记录进行编辑', '#addPlanDetail', {tips: 1});
            return;
        }
        _addPlanDetail(data[0].naTestplan, data[0].idTestplan);
    });

    var _addPlanDetail = function (title, id) {
        var index = common.open('【' + '<span style="color: red">' + title + '</span>】计划明细',
            basePath + '/pf/p/test/plan/tag/form?idTestplan=' + id, 900, 460);
        layer.full(index);
    };

});

