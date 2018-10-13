layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common;

    //执行渲染
    table.render({
        elem: '#partDefineTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'partDefineTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            {field: 'fgActive', width: 100, title: '状态',fixed: true, templet: '#fgActiveTpl'},
            {field: 'cdMedAsse', width: 100, title: '组件编码'},
            {field: 'name', width: 150, title: '组件名称'},
            {field: 'descript', width: 150, title: '组件描述'},
            {field: 'sdMedAsse', width: 120, title: '嵌入类型', templet: '#sdMedAsseTpl'},
            {field: 'script', width: 160, title: '嵌入代码'},
            {field: 'gmtCreate', width: 170, sort: true, title: '创建时间'},
            {field: 'operator', width: 100, title: '创建人'},
            {fixed: 'right', width: 120, title: '操作', align: 'center', toolbar: '#partDefine'}
        ]] //设置表头
        , url: basePath + '/pf/p/clinic/part/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听工具条
    table.on('tool(partDefineTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        }
    });

    //监听提交
    form.on('submit(partDefineSearchFilter)', function (data) {
        var name = data.field.name;
        var fgActive = data.field.fgActive;
        table.reload('partDefineTableId', {
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

    $('#add').on('click', function () {
        _addOrEdit("add");
    });

    $('#edit').on('click', function () {
        var checkStatus = table.checkStatus('partDefineTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#edit', {tips: 1});
            return;
        }
        if (data.length > 1) {
            layer.tips('请选中一行记录进行编辑', '#edit', {tips: 1});
            return;
        }
        var currentEditData = data[0];
        _addOrEdit("edit", currentEditData);
    });

    var _addOrEdit = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增病历组件', basePath + '/pf/p/clinic/part/form?formType=' + formType, 430, 435);
        } else {
            common.open('编辑病历组件', basePath + '/pf/p/clinic/part/form?formType=' + formType, 430, 435, _successFunction(currentEditData));
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
    table.on('rowDouble(partDefineTableFilter)', function (obj) {
        _addOrEdit("edit", obj.data);
    });

    $("#del").on('click', function () {
        var checkStatus = table.checkStatus('partDefineTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#del', {tips: 1});
            return;
        }
        _delPartDefine(data);
    });

    var _delPartDefine = function (currentData) {
        var url = basePath + '/pf/r/clinic/part/del';
        var reqData = new Array();
        var messageTitle = '';
        $.each(currentData, function (index, content) {
            if (messageTitle) {
                messageTitle += ', ';
            }
            messageTitle += '【' + content.name + '】';
            reqData.push(content.idMedAsse);
        });
        var data = {};
        data.list = reqData;
        data.status = '1';
        layer.confirm('确定删除' + messageTitle + '么？', {
            title: '删除病历组件提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "删除");
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
                    common.sucChildMsg(msg + "成功");
                    if (index) {
                        layer.close(index);
                    }
                    _partDefineTableReload();
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

    var _partDefineTableReload = function () {
        table.reload('partDefineTableId', {
            where: {
                //type: type
            },
            height: 'full-68'
        });
    }

    //监听删除操作
    form.on('switch(fgActiveCheckFilter)', function (obj) {
        var reqData = new Array();
        var data = {}, msg;
        reqData.push(this.value);
        data.list = reqData;
        if (obj.elem.checked) {
            data.status = '1';
            msg = '启用';
        } else {
            data.status = '0';
            msg = '停用';
        }
        console.log(obj.othis)
        common.commonPost(basePath + '/pf/r/clinic/part/updateStatus', data, msg, obj.othis);
    });

});

