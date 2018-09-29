/**
 * 用户
 * @constructor
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['layer', 'table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$,
        table = layui.table,
        form = layui.form,
        common = layui.common;

    //执行渲染
    table.render({
        elem: '#roleTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'roleTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            //{field: 'roleId', width: 80, hidden: true, title: '角色ID'},
            {field: 'name', width: 100, title: '角色名称', fixed: true},
            {field: 'code', width: 100, title: '角色编码'},
            {field: 'resume', width: 200, title: '描述'},
            {field: 'state', width: 70, sort: true, templet: '#stateTpl', title: '状态'},
            {field: 'level', width: 70, sort: true, title: '级别'},
            {field: 'operator', width: 100, title: '创建人'},
            {field: 'gmtCreate', width: 170, sort: true, title: '创建时间'},
            {fixed: 'right', width: 120, title: '操作', align: 'center', toolbar: '#viewTreeBar'}
        ]] //设置表头
        , url: 'list'
        , limit: 15
        //, even: true
        , limits: [15, 30, 100]
        , page: true
        , where: {
            state: $("select[name='state']").val()
        }
    });

    //监听提交
    form.on('submit(roleSearchFilter)', function (data) {
        var state = data.field.state;
        if (state == 0) {
            $('.cancel').text('作废');
        } else {
            $('.cancel').text('恢复');
        }
        table.reload('roleTableId', {
            where: {
                state: state,
                conditionValue: data.field.queryCondition
            },
            height: 'full-68'
        });
    });

    var _addOrEdit = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增角色', 'form?formType=' + formType, 400, 380);
        } else {
            common.open('编辑角色', 'form?formType=' + formType, 400, 380, _successFunction(currentEditData));
        }
    };

    var _successFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    }

    $('.add').on('click', function () {
        _addOrEdit("add");
    });

    $('.edit').on('click', function () {
        var checkStatus = table.checkStatus('roleTableId')
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

    //监听行双击事件
    table.on('rowDouble(roleTableFilter)', function(obj){
        _addOrEdit("edit", obj.data);
    });

    $('.cancel').on('click', function () {
        var checkStatus = table.checkStatus('roleTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        var state = $("select[name='state']").val();
        var reqData = new Array();
        var roleName = '';
        $.each(data, function (index, content) {
            if (roleName) {
                roleName += ', ';
            }
            roleName += content.name;
            var param = {};
            param.roleId = content.roleId;
            param.state = state == 0 ? 1 : 0;
            reqData.push(param);
        });
        var data = {};
        data.roles = reqData;

        if (state == 0) {
            layer.confirm('真的要作废角色【' + roleName + '】么？', {
                title: '作废角色提示',
                resize: false,
                btn: ['确定', '取消'],
                btnAlign: 'c',
                icon: 3
            }, function (index) {
                _cancelRole(index, data, "作废");
            })
        } else {
            _cancelRole(null, data, "恢复");
        }

    });

    var _cancelRole = function (index, reqData, msg) {
        layer.load(1);
        $.ajax({
            url: basePath + '/pf/r/role/cancel',
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
                    _roleTableReload();
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


    $('.del').on('click', function () {
        var checkStatus = table.checkStatus('roleTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        var reqData = new Array();
        var roleName = '';
        $.each(data, function (index, content) {
            if (roleName) {
                roleName += ', ';
            }
            roleName += content.name;
            var param = {};
            param.roleId = content.roleId;
            reqData.push(param);
        });

        layer.confirm('真的要删除角色【' + roleName + '】么？', {
            title: '删除角色提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _delRole(index, reqData);
        })
    });

    var _delRole = function (index, reqData) {
        layer.load(1);
        $.ajax({
            url: basePath + '/pf/r/role/del',
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
                    common.sucMsg("删除成功");
                    layer.close(index);
                    _roleTableReload();
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("删除失败");
                return false;
            }
        });
    }

    var _roleTableReload = function () {
        var state = $("select[name='state']").val();
        table.reload('roleTableId', {
            where: {
                state: state
            },
            height: 'full-68'
        });
    }

    //监听工具条
    table.on('tool(roleTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'editAuth') {
            $('#treeRoleName').html('【' + data.name + '】');
            // 查询权限树，权限树选中赋值
            _queryRoleZtree(data.roleId);
        }
    });

    var zTreeRoleId;
    var _queryRoleZtree = function (roleId) {
        zTreeRoleId = roleId;
        var reqData = {roleId: roleId}
        layer.load(1);
        $.ajax({
            url: basePath + '/pf/r/role/list/role/tree',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(reqData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    return false;
                } else {
                    var checkNodes = data.data;
                    _checkRoleZtree(checkNodes);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    }

    $('#saveSet').on('click', function () {
        if (!zTreeRoleId) {
            common.toastTip('rt', "请点击左侧【查看权限】按钮")
            return;
        }
        var reqData = {};
        reqData.roleMenus = _getCheckedAllZtree(zTreeRoleId);
        if (reqData.roleMenus.length == 0) {
            common.errorMsg("请先选择角色权限", 'rt');
            return;
        }
        _saveRoleMenu(reqData);
    });

    var _saveRoleMenu = function (reqData) {
        layer.load(1);
        $.ajax({
            url: basePath + '/pf/r/role/save/roleMenu',
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
                    common.sucMsg("保存成功");
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("保存失败");
                return false;
            }
        });
    }


    // ===============  ztree =========================//
    /**
     * 获取所有选中节点的值
     */
    var _getCheckedAllZtree = function (roleId) {
        var treeObj = $.fn.zTree.getZTreeObj("roleTree");
        var nodes = treeObj.getCheckedNodes(true);

        var checkedNodeData = new Array();
        for (var i = 0; i < nodes.length; i++) {
            var data = {};
            data.menuId = nodes[i].menuId;
            data.roleId = roleId;
            checkedNodeData.push(data);
        }
        return checkedNodeData;
    }

    var _checkRoleZtree = function (checkNodes) {
        //根据 treeId 获取 zTree 对象
        var treeObj = $.fn.zTree.getZTreeObj("roleTree");
        for (var i = 0; i < checkNodes.length; i++) {
            treeObj.checkNode(treeObj.getNodeByParam("id", checkNodes[i].id, null), checkNodes[i].checked, checkNodes[i].checked);
        }
    }

    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    var zNodes = [];

    $(document).ready(function () {
        layer.load(1);
        $.ajax({
            url: basePath + '/pf/r/role/list/tree',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    return false;
                } else {
                    zNodes = data.data;
                    $.fn.zTree.init($("#roleTree"), setting, zNodes);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
        var bodyHeight = $(this).height() - $("#treeTitle").height() - 31;
        $("#treeDiv").css("max-height", bodyHeight);

    });
});

