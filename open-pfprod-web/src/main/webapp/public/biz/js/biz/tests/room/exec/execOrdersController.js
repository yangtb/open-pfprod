layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'jquery', 'tableSelect', 'form', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , common = layui.common
        , form = layui.form
        , tableSelect = layui.tableSelect;

    $(document).ready(function () {
        initForm();
    });

    function tableRender() {
        //执行渲染
        table.render({
            elem: '#shortTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'shortTableId'
            , height: '500' //容器高度
            , toolbar: '#toolbarShort'
            , defaultToolbar: []
            , cols: [[
                {field: 'idShortDrugsText', minWidth: 150, title: '临时用药'},
                {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#drugBar'}
            ]] //设置表头
            , url: basePath + '/pf/p/waiting/room/test/orders/drug/short/list'
            , where: {
                idTestexecResultOrder: $('#idTestexecResultOrder').val()
            }
            , page: false
        });

        //执行渲染
        table.render({
            elem: '#longTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'longTableId'
            , height: '500' //容器高度
            , toolbar: '#toolbarLong'
            , defaultToolbar: []
            , cols: [[
                {field: 'idLongDrugsText', minWidth: 150, title: '长期用药'},
                {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#drugBar'}
            ]] //设置表头
            , url: basePath + '/pf/p/waiting/room/test/orders/drug/long/list'
            , where: {
                idTestexecResultOrder: $('#idTestexecResultOrder').val()
            }
            , page: false
        });

        if ($('#idTestexecResultOrder').val()) {
            controlBtn();
        }
        tableSelectRender();
    }

    function tableSelectRender() {
        tableSelect.render({
            elem: '#addDrugBtn',
            searchKey: 'keywords',
            checkedKey: 'addAnswerBtnId',
            searchPlaceholder: '药品名称/拼音码',
            table: {
                url: basePath + '/pf/p/drug/info/list',
                cols: [[
                    {type: 'checkbox'},
                    {field: 'name', title: '药品名称'},
                    {field: 'spec', title: '药品规格'},
                    {field: 'unit', title: '药品单位', width: 90}
                ]]
                , limits: [10, 20, 50]
                , page: true
            },
            done: function (elem, data) {
                addDrugs('short', data.data);
            }
        });

        tableSelect.render({
            elem: '#addLongDrugBtn',
            searchKey: 'keywords',
            checkedKey: 'addAnswerBtnId',
            searchPlaceholder: '药品名称/拼音码',
            table: {
                url: basePath + '/pf/p/drug/info/list',
                cols: [[
                    {type: 'checkbox'},
                    {field: 'name', title: '药品名称'},
                    {field: 'spec', title: '药品规格'},
                    {field: 'unit', title: '药品单位', width: 90}
                ]]
                , limits: [10, 20, 50]
                , page: true
            },
            done: function (elem, data) {
                addDrugs('long', data.data);
            }
        });
    }

    function addDrugs(type, data) {
        var url = basePath + '/pf/r/waiting/room/orders/drugs/save';
        var reqData = new Array();
        $.each(data, function (index, content) {
            reqData.push(content.idDrugs);
        });
        var bizData = {
            list: reqData,
            extId: $('#idTestexecResultOrder').val(),
            extType: type
        };
        var selectId = type == 'short' ? 'addDrugBtn' : 'addLongDrugBtn';
        common.commonPost(url, bizData, '添加', selectId, addDrugsCallback);
    }

    function addDrugsCallback(data) {
        if (data.data == 'long') {
            table.reload('longTableId', {
                where: {
                    idTestexecResultOrder: $('#idTestexecResultOrder').val()
                }
            });
        } else {
            table.reload('shortTableId', {
                where: {
                    idTestexecResultOrder: $('#idTestexecResultOrder').val()
                }
            });
        }
        controlBtn();
        tableSelectRender()
    }

    form.on('submit(addOrders)', function (data) {
        var url = basePath + '/pf/r/waiting/room/orders/save';
        data.field.idTestexecResult = idTestexecResult;
        common.commonPost(url, data.field, '保存', 'addOrders', saveOrdersCallback)
        return false;
    });

    function saveOrdersCallback(data) {
        $('#idTestexecResultOrder').val(data.data);
        controlBtn();
    };

    function controlBtn() {
        $('#addDrugBtn').removeClass("layui-btn-disabled");
        $('#addDrugBtn').removeAttr("disabled");
        $('#addLongDrugBtn').removeClass("layui-btn-disabled");
        $('#addLongDrugBtn').removeAttr("disabled");
    }

    function initForm() {
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/orders/select',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    if (data.data) {
                        $("#ordersForm").autofill(data.data);
                        form.render();
                    }
                    tableRender();
                }
            },
            error: function () {
                layui.common.errorMsg("获取医嘱信息失败");
                return false;
            }
        });
    }

    table.on('tool(shortTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的要删除药品【' + data.idShortDrugsText + '】么？', function (index) {
                var bizData = {
                    id: obj.data.idOrderShortDrugs,
                    type: 'short'
                };
                common.commonPost(basePath + '/pf/r/waiting/room/orders/drugs/del', bizData, '删除');
                layer.close(index);
                obj.del();
            });
        }
    });

    table.on('tool(longTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的要删除药品【' + data.idLongDrugsText + '】么？', function (index) {
                var bizData = {
                    id: obj.data.idOrderLongDrugs,
                    type: 'long'
                };
                common.commonPost(basePath + '/pf/r/waiting/room/orders/drugs/del', bizData, '删除');
                layer.close(index);
                obj.del();
            });
        }
    });

});

