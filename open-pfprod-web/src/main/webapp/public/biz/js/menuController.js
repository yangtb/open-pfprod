/**
 * 菜单
 * @constructor
 */
layui.config({
    base: '../public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$,
        table = layui.table,
        form = layui.form,
        common = layui.common;

    //执行渲染
    table.render({
        elem: '#menuTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'menuTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            {field: 'menuId', width: 60, title: 'ID', fixed: true},
            {field: 'name', width: 120, sort: true, title: '菜单名称', fixed: true},
            {field: 'url', width: 180, title: '映射地址'},
            {field: 'level', width: 70, title: '级别', templet: '#levelTpl'},
            {field: 'parentId', width: 90, title: '父菜单ID'},
            {field: 'sort', width: 70, sort: true, title: '排序'},
            {field: 'img', width: 150, title: '图标', templet: '#imgTpl'},
            {field: 'disable', width: 70, title: '状态', templet: '#disableTpl'},
            {field: 'gmtCreate', width: 170, sort: true, title: '创建时间'},
            {fixed: 'right', width: 150, title: '操作', align: 'center', toolbar: '#barDemo'}
        ]] //设置表头
        , url: 'list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
        //, size: 'sm'
    });

    //监听工具条
    table.on('tool(menuTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        } else if (obj.event === 'disable') {
            layer.confirm('真的停用菜单【' + data.name + '】么？', {
                title: '停用菜单提示',
                resize: false,
                btn: ['确定', '取消'],
                btnAlign: 'c',
                icon: 3
            }, function (index) {
                _operateMenu(index, obj, 'disable', '停用')
            })
        } else if (obj.event === 'enable') {
            layer.confirm('真的启用菜单【' + data.name + '】么？', {
                title: '启用菜单提示',
                resize: false,
                btn: ['确定', '取消'],
                btnAlign: 'c',
                icon: 3
            }, function (index) {
                _operateMenu(index, obj, 'enable', '启用')
            })
        }
    });

    var _operateMenu = function (index, obj, status, msg) {
        var disable;
        if (status === 'disable') {
            disable = '1';
        } else {
            disable = '0';
        }
        var param = {
            menuId: obj.data.menuId,
            disable: disable
        }
        $.ajax({
            url: 'changeStatus',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(param),
            success: function (data) {
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    common.sucMsg(msg + "成功");
                    obj.del();
                    layer.close(index);
                    return true;
                }
            },
            error: function () {
                common.errorMsg(msg + "失败");
                return false;
            }
        });

    }

    var rowStatus;
    //监听提交
    form.on('submit(menuSearchFilter)', function (data) {
        if (data.field.menuStatus == '0') {
            $('.bach-invalid').html('<i class="iconfont icon-stop"></i>' + ' 批量停用');
            rowStatus = 0;
        } else {
            $('.bach-invalid').html('<i class="iconfont icon-save"></i>' + ' 批量启用');
            rowStatus = 1;
        }
        table.reload('menuTableId', {
            where: {
                name: data.field.menuName,
                level: data.field.menuLevel,
                disable: data.field.menuStatus
            },
            height: 'full-68'
        });
    });

    $('.add').on('click', function () {
        _addOrEdit("add");
    });
    $('.bach-invalid').on('click', function () {
        _bachInvalidMenu();
    });

    var _addOrEdit = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增菜单', 'form?formType=' + formType, 700, 340);
        } else {
            common.open('编辑菜单', 'form?formType=' + formType, 700, 340, _successFunction(currentEditData));
        }
    };

    var _successFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    }

    var _bachInvalidMenu = function () {
        var checkStatus = table.checkStatus('menuTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        var arr = new Array();
        $.each(data, function (index, content) {

            var param = {};
            param.menuId = content.menuId;
            var disable = content.disable;
            if (rowStatus == 0) {
                disable = '1';
            } else {
                disable = '0';
            }
            param.disable = disable;
            arr.push(param);
        });
        var msg = $('.bach-invalid').text();
        $.ajax({
            url: 'batchChangeStatus',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(arr),
            success: function (data) {
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    common.sucMsg(msg + "成功");
                    _tableReload();
                    return true;
                }
            },
            error: function () {
                common.errorMsg(msg + "失败");
                return false;
            }
        });
    }

    var _tableReload = function () {
        var name = $("input[name='menuName']").val();
        var level = $("select[name='menuLevel']").val();
        var status = $("input[name='menuStatus']:checked").val();
        table.reload('menuTableId', {
            where: {
                name: name,
                level: level,
                disable: status
            },
            height: 'full-68'
        });
    }

});

