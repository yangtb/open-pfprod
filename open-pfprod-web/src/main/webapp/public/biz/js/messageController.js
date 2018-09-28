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
        elem: '#templateTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'templateTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            //{field: 'templateId', width: 100, title: '模板id', fixed: true},
            {field: 'templateName', width: 180, title: '模板名称', fixed: true},
            {field: 'templateCode', width: 150, title: '模板编码',},
            {field: 'templateType', width: 100, title: '模板类型'},
            {field: 'content', width: 250, title: '内容'},
            {field: 'isDeleted', width: 90, title: '是否删除', templet: '#isDeletedTpl'},
            {field: 'operator', width: 100, title: '最后修改人'},
            {field: 'gmtCreate', width: 170, sort: true, title: '创建时间'},
            {field: 'gmtModify', width: 170, sort: true, title: '修改时间'},
            {fixed: 'right', width: 160, title: '操作', align: 'center', toolbar: '#templateBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/message/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听工具条
    table.on('tool(templateTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        }
    });

    //监听提交
    form.on('submit(templateSearchFilter)', function (data) {
        var templateName = data.field.templateName;
        var templateType = data.field.templateType;
        var isDeleted = data.field.isDeleted;
        table.reload('templateTableId', {
            where: {
                templateName: templateName,
                templateType: templateType,
                isDeleted: isDeleted
            },
            height: 'full-68'
        });
    });

    $('.add').on('click', function () {
        _addOrEdit("add");
    });

    $('.edit').on('click', function () {
        var checkStatus = table.checkStatus('templateTableId')
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
            var index = common.open('新增模板', 'form?formType=' + formType + '&templateType=sms', 800, 430);
            layer.full(index);
        } else {
            var index = common.open('编辑模板', 'form?formType=' + formType + '&templateType=' + currentEditData.templateType, 800, 430, _successFunction(currentEditData));
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
    table.on('rowDouble(templateTableFilter)', function (obj) {
        _addOrEdit("edit", obj.data);
    });

    //监听删除操作
    form.on('switch(isDeletedCheckFilter)', function (obj) {
        var reqData = new Array();
        var data = {};
        reqData.push(this.value);
        data.list = reqData;
        if (obj.elem.checked) {
            data.status = 'N';
        } else {
            data.status = 'Y';
        }
        layer.load(1);
        $.ajax({
            url: basePath + '/pf/r/message/updateStatus',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.tips(data.msg, obj.othis);
                    return false;
                } else {
                    layer.tips("修改成功", obj.othis);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                layer.tips("修改失败", obj.othis);
                return false;
            }
        });
    });

    $(".del").on('click', function () {
        var checkStatus = table.checkStatus('templateTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        _delmessage(data);
    });

    var _delmessage = function (currentData) {
        var url = basePath + '/pf/r/message/updateStatus';
        var reqData = new Array();
        var messageTitle = '';
        $.each(currentData, function (index, content) {
            if (messageTitle) {
                messageTitle += ', ';
            }
            messageTitle += '【' + content.templateName + '】';
            reqData.push(content.templateId);
        });
        var data = {};
        data.list = reqData;
        data.status = 'Y'; // 删除状态
        layer.confirm('真的要删除模板' + messageTitle + '么？', {
            title: '删除模板提示',
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
                    _messageTableReload();
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

    var _messageTableReload = function () {
        table.reload('templateTableId', {
            where: {
                //type: type
            },
            height: 'full-68'
        });
    }

});

