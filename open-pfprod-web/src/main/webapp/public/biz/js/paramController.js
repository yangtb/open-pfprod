/**
 * 参数管理
 * @constructor
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$,
        table = layui.table,
        form = layui.form,
        common = layui.common;

    //执行渲染
    table.render({
        elem: '#paramTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'paramTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            //{field: 'id', width: 60, title: 'ID', fixed: true},
            {field: 'paramCode', width: 170, title: '参数编码', fixed: true},
            {field: 'paramName', width: 150, title: '参数名称'},
            {field: 'paramValue', width: 120, title: '参数值'},
            {field: 'defaultValue', width: 120, title: '默认值'},
            {field: 'dataType', width: 120, title: '参数数据'},
            {field: 'bizModule', width: 100, title: '作用模块', templet: '#bizModuleTpl'},
            {field: 'sysType', width: 120, title: '作用系统'},
            {field: 'status', width: 70, title: '状态', templet: '#statusTpl'},
            {field: 'extendValue', width: 120, title: '扩展数据项'},
            {field: 'remark', width: 200, title: '描述'},
            {field: 'gmtModify', width: 170, title: '更新时间'},
            {field: 'operator', width: 120, title: '操作人员'},
            {fixed: 'right', width: 160, title: '操作', align: 'center', toolbar: '#paramBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/param/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听提交
    form.on('submit(paramSearchFilter)', function (data) {
        if (data.field.status == 'enabled') {
            $('.stop').html('<i class="iconfont icon-stop"></i>' + ' 停用');
        } else {
            $('.stop').html('<i class="iconfont icon-save"></i>' + ' 启用');
        }
        table.reload('paramTableId', {
            where: {
                paramName: data.field.paramName,
                status: data.field.status
            },
            height: 'full-68'
        });
    });

    //监听工具条
    table.on('tool(paramTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        } else if (obj.event === 'stop' || obj.event === 'recover') {
            _changeParamStatus(data);
        }
    });

    var _changeParamStatus = function (currentData) {
        var data = new Array();
        data.push(currentData);
        _changeParam(data);
    }

    $('.add').on('click', function () {
        _addOrEdit("add");
    });

    $('.edit').on('click', function () {
        var currentData = _getCheckData();
        if (currentData.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        if (currentData.length > 1) {
            common.toastTop("请选中一行记录进行操作");
            return;
        }
        _addOrEdit("edit", currentData[0]);
    });

    //监听行双击事件
    table.on('rowDouble(paramTableFilter)', function(obj){
        _addOrEdit("edit", obj.data);
    });

    // 获取编辑行数据
    var _getCheckData = function () {
        var checkStatus = table.checkStatus('paramTableId')
            , data = checkStatus.data;
        return data;
    }

    var _addOrEdit = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增参数', 'form?formType=' + formType, 340, 505);
        } else {
            common.open('编辑参数', 'form?formType=' + formType, 350, 505, _successDicFunction(currentEditData));
        }
    };

    var _successDicFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    }

    $(".stop").on('click', function () {
        var checkStatus = table.checkStatus('paramTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        _changeParam(data);
    });

    var _changeParam = function (currentData) {
        var url = basePath + '/pf/r/param/changeStatus';
        var reqData = new Array();
        var name = '';
        $.each(currentData, function (index, content) {
            if (name) {
                name += ', ';
            }
            name += '【' + content.paramName + '】';
            reqData.push(content.id);
        });
        var data = {};
        data.list = reqData;

        if (currentData[0].status == 'enabled') {
            data.status = 'disabled';
        } else {
            data.status = 'enabled';
        }

        var msg = $('.stop').text();
        layer.confirm('真的要' + msg + '参数' + name + '么？', {
            title: msg + '参数提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, msg);
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
                    common.sucMsg(msg + "成功");
                    if (index) {
                        layer.close(index);
                    }
                    _paramTableReload();
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

    var _paramTableReload = function () {
        table.reload('paramTableId', {
            height: 'full-68'
        });
    }
});

