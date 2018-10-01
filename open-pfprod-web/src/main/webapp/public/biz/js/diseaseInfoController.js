
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common;

    //执行渲染
    table.render({
        elem: '#diseaseInfoTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'diseaseInfoTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},

            {field: 'name', width: 180, title: '疾病名称', fixed: true},
            //{field: 'cdDieclass', width: 150, title: '疾病目录'},
            {field: 'cdDieclassText', width: 150, title: '疾病目录'},
            {field: 'icd', width: 150, title: 'ICD编码'},
            {field: 'pinyin', width: 150, title: '拼音助记符'},
            {field: 'fgActive', width: 60, title: '激活', templet: '#fgActiveIconTpl'},
            {field: 'gmtCreate', width: 170, sort: true, title: '创建时间'},
            {field: 'operator', width: 100, title: '最后修改人'},
            {field: 'gmtModify', width: 170, sort: true, title: '修改时间'},
            {fixed: 'right', width: 160, title: '操作', align: 'center', toolbar: '#diseaseInfo'}
        ]] //设置表头
        , url: basePath + '/pf/p/disease/info/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听工具条
    table.on('tool(diseaseInfoTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        }
    });

    //监听提交
    form.on('submit(diseaseInfoSearchFilter)', function (data) {
        var name = data.field.name;
        var fgActive = data.field.fgActive;
        var type = data.field.type;
        var queryCondition = data.field.queryCondition;
        table.reload('diseaseInfoTableId', {
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
        var checkStatus = table.checkStatus('diseaseInfoTableId')
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
            common.open('新增疾病信息', basePath + '/pf/p/disease/info/form?formType=' + formType, 512, 380);
        } else {
            common.open('编辑疾病信息', basePath + '/pf/p/disease/info/form?formType=' + formType, 512, 380, _successFunction(currentEditData));
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
    table.on('rowDouble(diseaseInfoTableFilter)', function (obj) {
        _addOrEdit("edit", obj.data);
    });

    $(".del").on('click', function () {
        var checkStatus = table.checkStatus('diseaseInfoTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        _authOrg(data);
    });

    var _authOrg = function (currentData) {
        var url = basePath + '/pf/r/disease/info/del';
        var reqData = new Array();
        var messageTitle = '';
        $.each(currentData, function (index, content) {
            if (messageTitle) {
                messageTitle += ', ';
            }
            messageTitle += '【' + content.name + '】';
            reqData.push(content.idDie);
        });
        var data = {};
        data.list = reqData;
        data.status = '1';
        layer.confirm('确定删除' + messageTitle + '么？', {
            title: '删除疾病信息提示',
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
                    _diseaseInfoTableReload();
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

    var _diseaseInfoTableReload = function () {
        table.reload('diseaseInfoTableId', {
            where: {
                //type: type
            },
            height: 'full-68'
        });
    }

});

