/**
 * 消息模板
 * @constructor
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common;

    //执行渲染
    table.render({
        elem: '#orgTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'orgTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            {field: 'fgActive', width: 60, title: '激活', fixed: true, templet: '#fgActiveIconTpl'},
            {field: 'name', width: 180, title: '机构名称', fixed: true},
            {field: 'gmtValid', width: 120, title: '有效期', templet: '#gmtValidTpl'},
            {field: 'phone', width: 150, title: '联系电话'},
            {field: 'des', width: 150, title: '描述'},
            {field: 'addr', width: 150, title: '联系地址'},
            {field: 'email', width: 170, title: '邮箱'},
            //{field: 'fgValid', width: 90, title: '是否删除', templet: "#fgValidTpl"},
            {field: 'fgPlat', width: 100, title: '机构类型', templet: "#fgPlatTpl"},
            {field: 'operator', width: 100, title: '最后修改人'},
            {field: 'gmtCreate', width: 170, sort: true, title: '创建时间'},
            {field: 'gmtModify', width: 170, sort: true, title: '修改时间'},
            {fixed: 'right', width: 160, title: '操作', align: 'center', toolbar: '#orgBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/org/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听工具条
    table.on('tool(orgTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        }
    });

    //监听提交
    form.on('submit(orgSearchFilter)', function (data) {
        var name = data.field.name;
        var fgActive = data.field.fgActive;
        table.reload('orgTableId', {
            where: {
                name: name,
                fgActive: fgActive
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
        var checkStatus = table.checkStatus('orgTableId')
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
            var index = common.open('新增机构', 'form?formType=' + formType, 990, 460);
            layer.full(index);
        } else {
            var index = common.open('编辑机构', 'form?formType=' + formType, 990, 460, _successFunction(currentEditData));
            layer.full(index);
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
    table.on('rowDouble(orgTableFilter)', function (obj) {
        _addOrEdit("edit", obj.data);
    });

    //监听删除操作
    form.on('switch(fgActiveCheckFilter)', function (obj) {
        var reqData = new Array();
        var data = {};
        reqData.push(this.value);
        data.list = reqData;
        if (obj.elem.checked) {
            data.status = '1';
        } else {
            data.status = '0';
        }
        $.ajax({
            url: basePath + '/pf/r/org/auth',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (data) {
                if (data.code != 0) {
                    layer.tips(data.msg, obj.othis);
                    return false;
                } else {
                    layer.tips("修改成功", obj.othis);
                    return true;
                }
            },
            error: function () {
                layer.tips("修改失败", obj.othis);
                return false;
            }
        });
    });

    $(".auth").on('click', function () {
        var checkStatus = table.checkStatus('orgTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        _authOrg(data);
    });

    var _authOrg = function (currentData) {
        var url = basePath + '/pf/r/org/auth';
        var reqData = new Array();
        var messageTitle = '';
        $.each(currentData, function (index, content) {
            if (messageTitle) {
                messageTitle += ', ';
            }
            messageTitle += '【' + content.name + '】';
            reqData.push(content.idOrg);
        });
        var data = {};
        data.list = reqData;
        data.status = '1';
        layer.confirm('确定认证' + messageTitle + '么？', {
            title: '认证机构提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "认证");
        })
    }

    var _commonAjax = function (index, url, reqData, msg) {
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
                    _orgTableReload();
                    return true;
                }
            },
            error: function () {
                common.errorMsg(msg + "失败");
                return false;
            }
        });
    }

    var _orgTableReload = function () {
        table.reload('orgTableId', {
            where: {
                //type: type
            },
            height: 'full-68'
        });
    }

});

