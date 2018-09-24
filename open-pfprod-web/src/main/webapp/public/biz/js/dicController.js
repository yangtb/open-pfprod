/**
 * 字典管理
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
        elem: '#dicTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'dicTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true},
            {field: 'dictName', width: 150, title: '字典名称'},
            {fixed: 'right', width: 82, title: '操作', align: 'center', toolbar: '#dicBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/dic/list'
        , even: true
        , page: false
    });

    //执行渲染
    table.render({
        elem: '#enumTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'enumTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            //{field: 'id', width: 60, title: 'ID', fixed: true},
            {field: 'groupName', width: 150, title: '字典名称'},
            {field: 'dictName', width: 150, title: '枚举名称'},
            {field: 'dictCode', width: 150, title: '枚举编码'},
            {field: 'sortNum', width: 70, sort: true, title: '排序'},
            {field: 'remark', width: 150, title: '备注'},
            {field: 'gmtCreate', width: 170, sort: true, title: '添加时间'},
            {field: 'operator', width: 120, title: '最后修改人'},
            {fixed: 'right', width: 100, title: '操作', align: 'center', toolbar: '#enumBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/dic/enum/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听提交
    form.on('submit(dicSearchFilter)', function (data) {
        table.reload('dicTableId', {
            where: {
                dicName: data.field.dicName
            },
            height: 'full-68'
        });
    });

    //监听提交
    form.on('submit(enumSearchFilter)', function (data) {
        table.reload('enumTableId', {
            where: {
                enumName: data.field.enumName
            },
            height: 'full-68'
        });
    });

    var _groupName = null,
        _groupCode = null;
    //监听工具条
    table.on('tool(dicTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'detail') {
            _groupName = data.dictName;
            _groupCode = data.dictCode;
            _dicDetail(data.dictCode);
        }
    });

    var _dicDetail = function (groupCode) {
        table.reload('enumTableId', {
            where: {
                groupCode: groupCode
            },
            height: 'full-68'
        });
    }

    $('.addDic').on('click', function () {
        _addOrEditDic("add");
    });

    $('.editDic').on('click', function () {
        var currentData = _getDicCheckData();
        if (currentData.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        if (currentData.length > 1) {
            common.toastTop("请选中一行记录进行操作");
            return;
        }
        _addOrEditDic("edit", currentData[0]);

    });

    // 获取编辑行数据
    var _getDicCheckData = function () {
        var checkStatus = table.checkStatus('dicTableId')
            , data = checkStatus.data;
        return data;
    }

    var _addOrEditDic = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增字典', 'form?formType=' + formType, 350, 300);
        } else {
            common.open('编辑字典', 'form?formType=' + formType, 350, 300, _successDicFunction(currentEditData));
        }
    };

    var _successDicFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    }

    $('.addEnum').on('click', function () {
        if (!_groupName && !_groupCode) {
            common.toastTop("请先在左侧列表点击【详情】");
            return;
        }
        var data = {};
        data.groupName = _groupName;
        data.groupCode = _groupCode;
        _addOrEditEnum("add", data);
    });

    $('.editEnum').on('click', function () {
        var currentData = _getEnumCheckData();
        if (currentData.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        if (currentData.length > 1) {
            common.toastTop("请选中一行记录进行操作");
            return;
        }
        _addOrEditEnum("edit", currentData[0]);
    });

    // 获取编辑行数据
    var _getEnumCheckData = function () {
        var checkStatus = table.checkStatus('enumTableId')
            , data = checkStatus.data;
        return data;
    }

    var _addOrEditEnum = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增枚举', 'enum/form?formType=' + formType, 350, 480, _successEnumFunction(currentEditData));
        } else {
            common.open('编辑枚举', 'enum/form?formType=' + formType, 350, 480, _successEnumFunction(currentEditData));
        }
    };

    var _successEnumFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    }

    //监听工具条
    table.on('tool(enumTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEditEnum("edit", data);
        }
    });

    $(".delDic").on('click', function () {
        var checkStatus = table.checkStatus('dicTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        _delDic(data);
    });

    var _delDic = function (currentData) {
        var url = basePath + '/pf/r/dic/del';
        var reqData = new Array();
        var name = '';
        $.each(currentData, function (index, content) {
            if (name) {
                name += ', ';
            }
            name += '【' + content.dictName + '】';
            reqData.push(content.id);
        });
        var data = {};
        data.list = reqData;
        layer.confirm('真的要删除字典' + name + '么？', {
            title: '删除字典提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "删除", 'dic');
        })
    }

    $(".delEnum").on('click', function () {
        var checkStatus = table.checkStatus('enumTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        _delEnum(data);
    });

    var _delEnum = function (currentData) {
        var url = basePath + '/pf/r/dic/del';
        var reqData = new Array();
        var name = '';
        $.each(currentData, function (index, content) {
            if (name) {
                name += ', ';
            }
            name += '【' + content.dictName + '】';
            reqData.push(content.id);
        });
        var data = {};
        data.list = reqData;
        layer.confirm('真的要删除枚举' + name + '么？', {
            title: '删除枚举提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "删除", 'enum');
        })
    }

    var _commonAjax = function (index, url, reqData, msg, type) {
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(reqData),
            success: function (data) {
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    common.sucMsg(msg + "成功");
                    if (index) {
                        layer.close(index);
                    }
                    if (type == 'dic') {
                        _dicTableReload();
                    } else if (type == 'enum') {
                        _enumTableReload();
                    }
                    return true;
                }
            },
            error: function () {
                common.errorMsg(msg + "失败");
                return false;
            }
        });
    }

    var _dicTableReload = function () {
        table.reload('dicTableId', {
            height: 'full-68'
        });
    }

    var _enumTableReload = function () {
        table.reload('enumTableId', {
            height: 'full-68'
        });
    }

    $('.refreshCache').on('click', function () {

        $.ajax({
            url: basePath + '/pf/r/dic/refreshCache',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    common.sucMsg("刷新缓存成功");
                    return true;
                }
            },
            error: function () {
                common.errorMsg("刷新缓存失败");
                return false;
            }
        });
    });

});
