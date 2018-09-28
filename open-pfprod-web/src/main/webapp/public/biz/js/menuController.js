/**
 * 菜单
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
        elem: '#menuTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'menuTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            //{field: 'id', width: 60, title: 'ID', fixed: true},
            {field: 'name', width: 120, title: '名称', fixed: true},
            {field: 'code', width: 180, title: '编码'},
            {field: 'parentCode', width: 180, title: '父编码'},
            {field: 'functionUrl', width: 180, title: '权限url'},
            {field: 'level', width: 70, title: '级别', templet: '#levelTpl'},
            {field: 'sortNum', width: 70, sort: true, title: '排序'},
            {field: 'functionType', width: 180, title: '资源类别', templet: '#functionTypeTpl'},
            {field: 'platformType', width: 150, title: '所属平台'},
            {field: 'iconType', width: 150, title: '图标类型', templet: '#iconTypeTpl'},
            {field: 'iconSource', width: 150, title: '图标', templet: '#imgTpl'},
            {field: 'status', width: 70, title: '状态', templet: '#statusTpl'},
            {field: 'gmtModify', width: 170, sort: true, title: '最后修改时间'},
            {fixed: 'right', width: 150, title: '操作', align: 'center', toolbar: '#barDemo'}
        ]] //设置表头
        , url: basePath + '/pf/p/menu/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
        , where: {
            status : $("select[name='status']").val()
        },
    });

    //监听工具条
    table.on('tool(menuTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        } else if (obj.event === 'stop' || obj.event === 'recover') {
            _changeMenuStatus(data);
        }
    });

    var _changeMenuStatus = function (currentData) {
        var data = new Array();
        data.push(currentData);
        _changeMenu(data);
    }

    var _changeMenu = function (currentData) {
        var url = basePath + '/pf/r/menu/changeStatus';
        var reqData = new Array();
        var name = '';
        $.each(currentData, function (index, content) {
            if (name) {
                name += ', ';
            }
            name += '【' + content.name + '】';
            reqData.push(content.id);
        });
        var data = {};
        data.list = reqData;

        if (currentData[0].status == 'enabled') {
            data.status = 'disabled';
        } else {
            data.status = 'enabled';
        }

        var msg = $('.bach-invalid').text();
        layer.confirm('真的要' + msg + '菜单：' + name + '么？', {
            title: msg + '菜单提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, msg);
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
                    _tableReload();
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

    //监听提交
    form.on('submit(menuSearchFilter)', function (data) {
        if (data.field.status == 'enabled') {
            $('.bach-invalid').html('<i class="iconfont icon-batch-reduce"></i>' + ' 批量停用');
        } else {
            $('.bach-invalid').html('<i class="iconfont icon-icon-import"></i>' + ' 批量启用');
        }
        table.reload('menuTableId', {
            where: {
                name: data.field.menuName,
                level: data.field.menuLevel,
                status: data.field.status
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
    table.on('rowDouble(menuTableFilter)', function(obj){
        _addOrEdit("edit", obj.data);
    });

    // 获取编辑行数据
    var _getCheckData = function () {
        var checkStatus = table.checkStatus('menuTableId')
            , data = checkStatus.data;
        return data;
    }

    $('.bach-invalid').on('click', function () {
        var checkStatus = table.checkStatus('menuTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        _changeMenu(data);
    });

    var _addOrEdit = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增菜单', 'form?formType=' + formType, 700, 390);
        } else {
            common.open('编辑菜单', 'form?formType=' + formType, 700, 390, _successFunction(currentEditData));
        }
    };

    var _successFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    }

    var _tableReload = function () {
        var name = $("input[name='menuName']").val();
        var level = $("select[name='menuLevel']").val();
        var status = $("select[name='status']").val();
        table.reload('menuTableId', {
            where: {
                name: name,
                level: level,
                status: status
            },
            height: 'full-68'
        });
    }

});

