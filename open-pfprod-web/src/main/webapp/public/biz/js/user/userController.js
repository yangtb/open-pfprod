/**
 * 用户
 * @constructor
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$,
        table = layui.table,
        form = layui.form,
        common = layui.common;

    table.render({
        elem: '#userTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'userTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            //{field: 'user_id', width: 80, hidden: true, title: 'ID'},
            {field: 'user_name', width: 130, title: '账号', fixed: true},
            {field: 'real_name', width: 140, title: '姓名'},
            {field: 'sex', width: 70, templet: '#sexTpl', title: '性别'},
            {field: 'phone_no', width: 120, title: '手机号'},
            {field: 'email', width: 160, title: '邮箱'},
            {field: 'enabled', width: 70, title: '状态', templet: '#enabledTpl'},
            //{field: 'enabled', title:'状态', width:100, templet: '#switchTpl', unresize: true},
            /*{
                field: 'role_type',
                width: 110,
                title: '账户类型',
                style: 'background-color: #5FB878; color: #fff;',
                templet: '#roleTypeTpl'
            },*/
            {field: 'gmt_create', width: 170, sort: true, title: '创建时间'},
            {field: 'gmt_modify', width: 170, sort: true, title: '修改时间'},
            {field: 'operator', width: 100, title: '操作人员'},
            {fixed: 'right', width: 180, title: '操作', align: 'center', toolbar: '#userBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/user/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    $('#add').on('click', function () {
        _addOrEdit("add");
    });

    $('#edit').on('click', function () {
        var currentData = _getCheckData();
        if (currentData.length == 0) {
            layer.tips('请先选中一行记录', '#edit', {tips: 1});
            return;
        }
        if (currentData.length > 1) {
            layer.tips('请选中一行记录进行操作', '#edit', {tips: 1});
            return;
        }
        _addOrEdit("edit", currentData[0]);

    });

    //监听行双击事件
    table.on('rowDouble(userTableFilter)', function (obj) {
        _addOrEdit("edit", obj.data);
    });

    $('#del').on('click', function () {
        var currentData = _getCheckData();
        if (currentData.length == 0) {
            layer.tips('请先选中一行记录', '#del', {tips: 1});
            return;
        }
        _delUser(currentData);
    });

    $('#freeze').on('click', function () {
        var currentData = _getCheckData();
        if (currentData.length == 0) {
            layer.tips('请先选中一行记录', '#freeze', {tips: 1});
            return;
        }
        _freezeUser(currentData);
    });

    $('#resetPass').on('click', function () {
        var currentData = _getCheckData();
        if (currentData.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        if (currentData.length > 1) {
            common.toastTop("请选中一行记录进行操作");
            return;
        }
        _resetPass(currentData[0]);
    });

    var _resetPass = function (data) {
        common.open('密码重置', 'resetPassword', 480, 220, _successFunction(data));
    }

    // 获取编辑行数据
    var _getCheckData = function () {
        var checkStatus = table.checkStatus('userTableId')
            , data = checkStatus.data;
        return data;
    }
    //监听提交
    form.on('submit(userSearchFilter)', function (data) {
        table.reload('userTableId', {
            where: {
                type: data.field.type,
                conditionValue: data.field.queryCondition,
                idOrg : data.field.idOrg
            }
            , height: 'full-68'
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //监听工具条
    table.on('tool(userTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        } else if (obj.event === 'resetPass') {
            _resetPass(data);
        }
    });

    var _addOrEdit = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增用户', 'form?formType=' + formType, 700, 420);
        } else {
            common.open('编辑用户', 'form?formType=' + formType + "&userId=" + currentEditData.user_id, 700, 360, _successFunction(currentEditData));
        }
    };

    var _successFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    }

    var _delUser = function (currentData) {
        var url = basePath + '/pf/r/user/del';
        var reqData = new Array();
        var username = '';
        $.each(currentData, function (index, content) {
            if (username) {
                username += ', ';
            }
            username += content.user_name;
            reqData.push(content.user_id);
        });
        var data = {};
        data.list = reqData;
        layer.confirm('真的要删除用户【' + username + '】么？', {
            title: '删除用户提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "删除");
        })
    }

    var _freezeUser = function (currentData) {
        var url = basePath + '/pf/r/user/freeze';
        var reqData = new Array();
        var username = '';
        $.each(currentData, function (index, content) {
            if (username) {
                username += ', ';
            }
            username += content.user_name;
            reqData.push(content.user_id);
        });
        var data = {};
        data.list = reqData;
        layer.confirm('真的要冻结用户【' + username + '】么？', {
            title: '冻结用户提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "冻结");
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
                    layui.common.refreshCurrentPage();
                    //_userTableReload();
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

    var _userTableReload = function () {
        var type = $("select[name='type']").val();
        var queryCondition = $("select[name='queryCondition']").val();
        table.reload('userTableId', {
            where: {
                type: type,
                conditionValue: queryCondition
            },
            height: 'full-68'
        });
    }

    //监听性别操作
    form.on('switch(statusSwitch)', function (obj) {
        if (obj.elem.checked) {
            common.sucMsg("保存成功");
            //layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        } else {
            common.errorMsg("保存失败");
        }
    });


});

