/**
 * 消息模板
 * @constructor
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'laydate', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        ,laydate = layui.laydate
        , common = layui.common;

    laydate.render({
        elem: '#gmtApplyDate'
        ,type: 'month'
        ,range: '~'
        ,format: 'yyyy-MM'
    });

    //执行渲染
    table.render({
        elem: '#orgAuthTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'orgAuthTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            {field: 'sdReg', width: 100, title: '状态', fixed: true, templet: '#sdRegTpl'},
            {field: 'name', width: 180, title: '机构名称', fixed: true},
            {field: 'phone', width: 150, title: '联系电话'},
            {field: 'email', width: 170, title: '邮箱'},
            {field: 'applyor', width: 100, title: '申请人'},
            {field: 'confirmor', width: 100, title: '处理人'},
            {field: 'gmtConfirm', width: 170, sort: true, title: '处理时间'},
            {fixed: 'right', width: 160, title: '操作', align: 'center', toolbar: '#orgBar'}

        ]] //设置表头
        , url: basePath + '/pf/p/org/list/auth'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听工具条
    table.on('tool(orgAuthTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        }
    });

    form.on('checkbox(expiredFilter)', function(data){
        if (data.elem.checked) {
            $("#fgActive").val("");
            form.render('select');
        }
    });

    //监听提交
    form.on('submit(orgAuthSearchFilter)', function (data) {
        var name = data.field.name;
        var sdReg = data.field.sdReg;
        var gmtApply = data.field.gmtApply;
        table.reload('orgAuthTableId', {
            where: {
                name: name,
                sdReg: sdReg,
                gmtApplyStart: gmtApply.substr(0, 7),
                gmtApplyEnd: gmtApply.substr(10, gmtApply.length)
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
        var checkStatus = table.checkStatus('orgAuthTableId')
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
        var url = basePath + '/pf/p/org/';
        if (formType == 'add') {
            var index = common.open('新增机构', url+'form?formType=' + formType, 990, 460);
            layer.full(index);
        } else {
            var index = common.open('编辑机构', url+'form?formType=' + formType, 990, 460, _successFunction(currentEditData));
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
    table.on('rowDouble(orgAuthTableFilter)', function (obj) {
        _addOrEdit("edit", obj.data);
    });

    $(".auth").on('click', function () {
        var checkStatus = table.checkStatus('orgAuthTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        _authOrg(data);
    });

    var _authOrg = function (currentData) {
        var url = basePath + '/pf/r/org/auth';
        var idRegList = new Array();
        var idOrgList = new Array();
        var messageTitle = '';
        $.each(currentData, function (index, content) {
            if (messageTitle) {
                messageTitle += ', ';
            }
            messageTitle += '【' + content.name + '】';
            idRegList.push(content.idReg);
            idOrgList.push(content.idOrg);
        });
        var data = {};
        data.idRegList = idRegList;
        data.idOrgList = idOrgList;
        layer.confirm('确定认证' + messageTitle + '么？', {
            title: '认证机构提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "认证");
        })
    };

    $(".reject").on('click', function () {
        var checkStatus = table.checkStatus('orgAuthTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        _rejectApply(data);
    });

    var _rejectApply = function (currentData) {
        var url = basePath + '/pf/r/org/reject';
        var idRegList = new Array();
        var messageTitle = '';
        $.each(currentData, function (index, content) {
            if (messageTitle) {
                messageTitle += ', ';
            }
            messageTitle += '【' + content.name + '】';
            idRegList.push(content.idReg);
        });
        var data = {};
        data.idRegList = idRegList;
        layer.confirm('确定驳回' + messageTitle + '的申请么？', {
            title: '认证驳回提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "驳回");
        })
    };

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
                    _orgAuthTableReload();
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

    var _orgAuthTableReload = function () {
        table.reload('orgAuthTableId', {
            where: {
                //type: type
            },
            height: 'full-68'
        });
    }

});

