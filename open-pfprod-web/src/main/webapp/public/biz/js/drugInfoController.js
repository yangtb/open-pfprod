
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common;

    //执行渲染
    table.render({
        elem: '#drugInfoTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'drugInfoTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},

            {field: 'name', width: 180, title: '药品名称', fixed: true},
            {field: 'cdDrugsclass', width: 150, title: '药品目录'},
            {field: 'spec', width: 150, title: '药品规格'},
            {field: 'unit', width: 100, title: '药品单位'},
            {field: 'pinyin', width: 120, title: '拼音助记符'},
            {field: 'fgActive', width: 60, title: '激活', templet: '#fgActiveIconTpl'},
            {field: 'gmtCreate', width: 170, sort: true, title: '创建时间'},
            {field: 'operator', width: 100, title: '最后修改人'},
            {field: 'gmtModify', width: 170, sort: true, title: '修改时间'},
            {fixed: 'right', width: 160, title: '操作', align: 'center', toolbar: '#drugInfo'}
        ]] //设置表头
        , url: basePath + '/pf/p/drug/info/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听工具条
    table.on('tool(drugInfoTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        }
    });

    //监听提交
    form.on('submit(drugInfoSearchFilter)', function (data) {
        var name = data.field.name;
        var fgActive = data.field.fgActive;
        var type = data.field.type;
        var queryCondition = data.field.queryCondition;
        table.reload('drugInfoTableId', {
            where: {
                name: name,
                fgActive: fgActive,
                type : type,
                queryCondition : queryCondition
            }
            , height: 'full-68'
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    $('.add').on('click', function () {
        _addOrEdit("add");
    });

    $('.edit').on('click', function () {
        var checkStatus = table.checkStatus('drugInfoTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        if (data.length > 1) {
            common.toastTop("请选中一行记录进行编辑");
            return;
        }
        var currentEditData = data[0];
        _addOrEdit("edit", currentEditData);
    });

    var _addOrEdit = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增药品信息', basePath + '/pf/p/drug/info/form?formType=' + formType, 350, 430);
        } else {
            common.open('编辑药品信息', basePath + '/pf/p/drug/info/form?formType=' + formType, 350, 430, _successFunction(currentEditData));
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
    table.on('rowDouble(drugInfoTableFilter)', function (obj) {
        _addOrEdit("edit", obj.data);
    });

    $(".del").on('click', function () {
        var checkStatus = table.checkStatus('drugInfoTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        _authOrg(data);
    });

    var _authOrg = function (currentData) {
        var url = basePath + '/pf/r/drug/info/del';
        var reqData = new Array();
        var messageTitle = '';
        $.each(currentData, function (index, content) {
            if (messageTitle) {
                messageTitle += ', ';
            }
            messageTitle += '【' + content.name + '】';
            reqData.push(content.idDrugs);
        });
        var data = {};
        data.list = reqData;
        data.status = '1';
        layer.confirm('确定删除' + messageTitle + '么？', {
            title: '删除药品信息提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "删除");
        })
    }

    var _commonAjax = function (index, url, reqData, msg) {
        layer.load(1);
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
                    common.sucMsg(msg + "成功");
                    if (index) {
                        layer.close(index);
                    }
                    _drugInfoTableReload();
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

    var _drugInfoTableReload = function () {
        table.reload('drugInfoTableId', {
            where: {
                //type: type
            },
            height: 'full-68'
        });
    }

});

