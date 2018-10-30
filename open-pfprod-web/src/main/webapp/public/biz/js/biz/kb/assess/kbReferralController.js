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
        , url: basePath + '/pf/p/kb/assess/referral/list'
        , where: {
            idEvaCase: idEvaCase,
            cdEvaAsse : cdEvaAsse
        }
        , page: false
    });

    form.verify({
        desAnswer: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        }
    });

    table.on('radio(kbTableFilter)', function (obj) {
        $('#reset').click();
        $("#kbForm").autofill(obj.data);
        form.render();

        var bizData = {idEvaCaseItem: obj.data.idEvaCaseItem}
        _postReload(bizData);
    });

    var _postReload = function(bizData) {
        //layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/kb/assess/referral/list',
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
        //, toolbar: '#toolbarDemo'
        , defaultToolbar: []
        , cols: [[
            {type: 'numbers'},
            {field: 'idDieText', minWidth: 90, title: '等效答案'},
            {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#answerBar'}
        ]] //设置表头
        //, url: basePath + '/pf/p/kb/assess/referral/list'
        , where: {
            idEvaCase: idEvaCase
        }
        , page: false
        , data: []
    });

    /*table.on('toolbar(answerTableFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        if (layEvent === 'addAnswer') { //查看
            var sdEva = $('#sdEvaType').val();
            var idEvaCaseItem = $('#idEvaCaseItem').val();

            if (!sdEva) {
                common.toastTop('请先选择评估阶段')
                return;
            }
            common.open('添加等效答案', basePath + '/pf/p/kb/assess/referral/answer/page?idEvaCaseItem=' + idEvaCaseItem + '&sdEva=' + sdEva, 600, 350);
        }
    });*/

    //监听工具条
    table.on('tool(answerTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            var tableData = table.cache["answerTableId"];
            if (tableData.length == 1) {
                layer.tips("等效答案至少一个，请先添加后再删除", obj.othis);
                return false;
            }
            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
            if (obj.data.idEvaCaseItemList != null) {
                layer.confirm('真的要删除【' + data.idDieText + '】么？', function (index) {
                    layer.close(index);
                    //向服务端发送删除指令
                    if (obj.data.idEvaCaseItemList != null) {
                        var url = basePath + '/pf/r/kb/assess/referral/del';
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

    tableSelect.render({
        elem: '#addAnswerBtn',
        searchKey: 'keywords',
        checkedKey: 'addAnswerBtnId',
        searchPlaceholder: '疾病名称/ICD/拼音码',
        table: {
            url: basePath + '/pf/p/disease/info/list',
            cols: [[
                {type: 'checkbox'},
                {field: 'name', title: '疾病名称'},
                {field: 'cdDieclassText', title: '疾病目录'},
                {field: 'icd', title: 'ICD编码'},
            ]]
            , limits: [10, 20, 50]
            , page: true
        },
        done: function (elem, data) {
            console.log(data)
            var dieList = data.data;
            var oldData = table.cache["answerTableId"];
            console.log(oldData)
            if (!oldData) {
                //oldData = [];
            }
            for (var i = 0; i < dieList.length; i++) {
                var selectData = {};
                selectData.idEvaCaseItem = $('#idEvaCaseItem').val();
                selectData.sdEvaReferral = $('#sdEvaType').val();
                selectData.idDie = dieList[i].idDie;
                selectData.idDieText = dieList[i].name;
                oldData.push(selectData)
            }
            console.log(oldData)
            table.reload('answerTableId', {
                data: oldData
            });
        }
    });

    //监听提交
    form.on('submit(saveAnswer)', function (data) {

        var tableData = table.cache["answerTableId"];
        if (tableData.length == 0) {
            layer.tips('请添加等效答案', '#addAnswer', {tips: 1});
            return false;
        }

        data.field.idEvaCase = idEvaCase;
        data.field.cdEvaAsse = cdEvaAsse;
        data.field.list = tableData;

        console.log(data.field)

        var url = basePath + '/pf/r/kb/assess/referral/save';
        return common.commonPost(url, data.field, '保存', '', _callBack);
    });

    var _callBack = function (data) {
        _postReload({idEvaCaseItem: data.data});
        _kbTableReload();
        $('#idEvaCaseItem').val(data.data);
    }


    var _kbTableReload = function () {
        table.reload('kbTableId', {
            where: {
                idEvaCase: idEvaCase,
                cdEvaAsse : cdEvaAsse
            }
        });
    };

    $('#add').on('click', function () {
        $('#reset').click();
        table.reload('answerTableId', {
            data: []
        });
        $('#save').click();
    });


    $('#saveAs').on('click', function () {
        layer.tips('正在开发...', '#saveAs', {tips: 1});
    });


});

