layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'layer', 'element', 'tableSelect', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common
        , tableSelect = layui.tableSelect;

    //执行渲染
    table.render({
        elem: '#kbTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'kbTableId'
        , height: '558' //容器高度
        , cols: [[
            {type: 'radio'},
            {field: 'sdEva', width: 90, title: '评估阶段', templet: '#sdEvaTpl'},
            {field: 'itemName', minWidth: 90, title: '评估项'},
            {field: 'scoreEva', width: 60, title: '分值'},
            {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#kbBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/kb/assess/cover/list'
        , where: {
            idEvaCase: idEvaCase,
            cdEvaAsse: cdEvaAsse
        }
        , page: false
    });

    form.verify({
        desAnswer: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        },
        scoreEva: function (value) {
            if (value < scoreLower || value > scoreUpper) {
                return '分值值域[' + scoreLower + ',' + scoreUpper + ']';
            }
        }
    });

    table.on('radio(kbTableFilter)', function (obj) {
        $('#reset').click();
        $("#kbForm").autofill(obj.data);
        form.render();

        var bizData = {
            idEvaCaseItem: obj.data.idEvaCaseItem,
            sdType: obj.data.sdEva
        }
        _tableSelectRender(obj.data.sdEva)
        _postReload(bizData);
    });

    var _postReload = function (bizData) {
        //layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/kb/assess/cover/list',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                //layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    table.reload('answerTableId', {
                        data: data.data
                    });
                    return true;
                }
            },
            error: function () {
                //layer.closeAll('loading');
                return false;
            }
        });
    };

    //执行渲染
    table.render({
        elem: '#answerTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'answerTableId'
        , title: '等效答案'
        , height: '315' //容器高度
        , defaultToolbar: []
        , cols: [[
            {type: 'numbers'},
            {field: 'desText', minWidth: 90, title: '等效答案'},
            {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#answerBar'}
        ]] //设置表头
        //, url: basePath + '/pf/p/kb/assess/cover/list'
        , where: {
            idEvaCase: idEvaCase
        }
        , page: false
        , data: []
    });

    //监听工具条
    table.on('tool(answerTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            var tableData = table.cache["answerTableId"];
            if (tableData.length == 1) {
                layer.tips("等效答案至少一个，请先添加后再删除", obj.othis);
                return false;
            }

            if (obj.data.idEvaCaseItemList != null) {
                layer.confirm('真的要删除【' + data.desText + '】么？', function (index) {
                    layer.close(index);
                    //向服务端发送删除指令
                    if (obj.data.idEvaCaseItemList != null) {
                        var url = basePath + '/pf/r/kb/assess/cover/del';
                        var reqData = new Array();
                        reqData.push(obj.data.idEvaCaseItemList);
                        var data = {};
                        data.list = reqData;
                        data.status = '1';
                        common.commonPost(url, data, '删除');
                    }
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                });
            } else {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
            }

        }
    });

    $('#addAnswerBtn').on('click', function () {
        if (!$('#sdEvaType').val()) {
            layer.tips('请先选择评估阶段', '#addAnswerBtn', {tips: 1});
            return false;
        }
    });

    form.on('select(sdEvaTypeSelectFilter)', function (data) {
        table.reload('answerTableId', {
            data: []
        });

        _tableSelectRender(data.value);
    });

    function _tableSelectRender(type) {
        if (type == '1') {
            tableSelect.render({
                elem: '#addAnswerBtn',
                searchKey: 'keywords',
                checkedKey: 'addAnswerBtnId',
                searchPlaceholder: '请输入关键字',
                table: {
                    url: basePath + '/pf/p/inquisition/question/list',
                    cols: [[
                        {type: 'checkbox'},
                        {field: 'idInquesCaText', title: '目录'},
                        {field: 'desInques', title: '问题内容'}
                    ]]
                    , limits: [10, 20, 50]
                    , page: true
                },
                done: function (elem, data) {
                    tableSelectDone(elem, data, type);
                }
            });
        }
        if (type == '2') {
            tableSelect.render({
                elem: '#addAnswerBtn',
                searchKey: 'keywords',
                checkedKey: 'addAnswerBtnId',
                searchPlaceholder: '请输入关键字',
                table: {
                    url: basePath + '/pf/p/check/question/list',
                    cols: [[
                        {type: 'checkbox'},
                        {field: 'idBodyCaText', title: '目录'},
                        {field: 'desBody', title: '检查部位描述'}
                    ]]
                    , limits: [10, 20, 50]
                    , page: true
                },
                done: function (elem, data) {
                    tableSelectDone(elem, data, type);
                }
            });
        }
        if (type == '3') {
            tableSelect.render({
                elem: '#addAnswerBtn',
                searchKey: 'keywords',
                checkedKey: 'addAnswerBtnId',
                searchPlaceholder: '请输入关键字',
                table: {
                    url: basePath + '/pf/p/exam/question/list',
                    cols: [[
                        {type: 'checkbox'},
                        {field: 'idInspectText', title: '目录'},
                        {field: 'naItem', title: '项目'}
                    ]]
                    , limits: [10, 20, 50]
                    , page: true
                },
                done: function (elem, data) {
                    tableSelectDone(elem, data, type);
                }
            });
        }
    };

    function tableSelectDone(elem, data, type) {
        var dieList = data.data;
        var oldData = table.cache["answerTableId"];
        if (!oldData) {
            oldData = [];
        }
        for (var i = 0; i < dieList.length; i++) {
            var selectData = {};
            selectData.idEvaCaseItem = $('#idEvaCaseItem').val();
            selectData.sdEvaEffciency = $('#sdEvaType').val();
            if (type == '1') {
                selectData.idInques = dieList[i].idInques;
                selectData.desText = dieList[i].desInques;
            } else if (type == '2') {
                selectData.idBody = dieList[i].idBody;
                selectData.desText = dieList[i].desBody;
            } else if (type == '3') {
                selectData.idInspectItem = dieList[i].idInspectItem;
                selectData.desText = dieList[i].naItem;
            }
            oldData.push(selectData)
        }
        table.reload('answerTableId', {
            data: oldData
        });
    };

    tableSelect.render({
        elem: '#idDieText',
        checkedKey: 'idDieTextId',
        searchKey: 'keywords',
        table: {
            url: basePath + '/pf/p/disease/info/list'
            , cols: [[
                {type: 'radio', fixed: true},
                {field: 'name', minWidth: 160, title: '疾病名称'},
                {field: 'cdDieclassText', minWidth: 120, title: '疾病目录'},
                {field: 'icd', width: 80, title: 'ICD'}
            ]] //设置表头
            , limits: [10, 20, 50]
            , page: true
        },
        done: function (elem, data) {
            var selectData = data.data[0];
            $('#idDie').val(selectData.idDie);
            $('#idDieText').val(selectData.name);
        }
    });

    //监听提交
    form.on('submit(saveAnswer)', function (data) {

        var tableData = table.cache["answerTableId"];
        if (tableData.length == 0) {
            layer.tips('请添加等效答案', '#addAnswerBtn', {tips: 1});
            return false;
        }

        data.field.idEvaCase = idEvaCase;
        data.field.cdEvaAsse = cdEvaAsse;
        data.field.list = tableData;

        console.log(data.field)

        var url = basePath + '/pf/r/kb/assess/cover/save';
        return common.commonPost(url, data.field, '保存', '', _callBack);
    });

    var _callBack = function (data) {
        _postReload({idEvaCaseItem: data.data, sdType: $('#sdEvaType').val()});
        _kbTableReload();
        $('#idEvaCaseItem').val(data.data);
    }


    var _kbTableReload = function () {
        table.reload('kbTableId', {
            where: {
                idEvaCase: idEvaCase,
                cdEvaAsse: cdEvaAsse
            }
        });
    };

    $('#add').on('click', function () {
        $('#reset').click();
        table.reload('answerTableId', {
            data: []
        });
        $('#save').click();
        $('#scoreEva').val(defaultScoreEva);
    });

    //监听工具条
    table.on('tool(kbTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {

            layer.confirm('真的要删除评估项【' + data.itemName + '】么？', function (index) {
                var url = basePath + '/pf/r/kb/assess/common/del';
                var reqData = new Array();
                reqData.push(obj.data.idEvaCaseItem);
                var data = {};
                data.list = reqData;
                data.status = '1';
                data.extType = 'cover';
                common.commonPost(url, data, '删除');
                obj.del();
            });

        }
    });


    $('#saveAs').on('click', function () {
        layer.tips('重载成功', '#saveAs', {tips: 1});
    });


});

