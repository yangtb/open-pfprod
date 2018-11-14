layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'jquery', 'tableSelect', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , common = layui.common
        , tableSelect = layui.tableSelect;

    $(document).ready(function () {
        initData();
    });

    function initData() {
        // 加载诊断
        initForm();
        // 加载确诊理由
    }

    function initForm() {
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/summary/diagnosis/select',
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
                        $('#idTestexecResultDiagnosis').val(data.data.idTestexecResultDiagnosis);
                        $('#idDieText').val(data.data.idDieText);
                        $('#idTestexecResultSumary').val(data.data.idTestexecResultSumary);
                        $('#dieSumary').val(data.data.dieSumary);
                    }
                    tableRender();
                    controlBtn()
                }
            },
            error: function () {
                layui.common.errorMsg("获取医嘱信息失败");
                return false;
            }
        });
    }

    function tableRender() {
        //执行渲染
        table.render({
            elem: '#partConsTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'partConsTableId'
            , height: '300' //容器高度
            , toolbar: '#toolbarDemo1'
            , defaultToolbar: []
            , cols: [[
                {type: 'numbers'},
                {field: 'idText', minWidth: 150, title: '确诊理由'},
                {field: 'sdEvaEffciency', width: 80, title: '阶段', templet: '#sdEvaTpl'},
                {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#summaryBar'}
            ]] //设置表头
            , url: basePath + '/pf/p/waiting/room/test/die/reason/list'
            , where: {
                idTestexecResultDiagnosis: $('#idTestexecResultDiagnosis').val()
            }
            , page: false
        });

        tableSelectRender();
    }

    tableSelect.render({
        elem: '#searchAnswer',
        checkedKey: 'searchAnswerId',
        searchKey: 'keywords',
        searchPlaceholder: '名称/拼音码/ICD编码',
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
            $('#idDieText').val(selectData.name);
            // 保存诊断
            saveDiagnosis(selectData);
        }
    });

    function saveDiagnosis(data) {
        var url = basePath + '/pf/r/waiting/room/summary/diagnosis/save';
        var bizData = {
            idTestexecResultDiagnosis: $('#idTestexecResultDiagnosis').val(),
            idTestexecResult: idTestexecResult,
            idDie: data.idDie
        }
        common.commonPost(url, bizData, null, null, saveDiagnosisCallback)
    }

    function saveDiagnosisCallback(data) {
        $('#idTestexecResultDiagnosis').val(data.data);
        controlBtn();
    }

    $('#delSummary').on('click', function () {
        if (!$('#idTestexecResultDiagnosis').val()) {
            layer.tips('请选择诊断', '#searchAnswer', {tips: 1});
            return false;
        }
        layer.confirm('确定删除诊断【' + $('#idDieText').val() + '】么？', {
            offset: [$(this).offset().top + 'px', $(this).offset().left + 'px'],
        }, function (index) {
            var bizData = {
                idTestexecResultDiagnosis: $('#idTestexecResultDiagnosis').val()
            };
            common.commonPost(basePath + '/pf/r/waiting/room/summary/diagnosis/del', bizData, '删除', 'delSummary', delSummaryCallBack);
            layer.close(index);
        });
        return false;
    });

    function delSummaryCallBack() {
        $('#idTestexecResultDiagnosis').val('');
        $('#idDieText').val('');
        reloadDieReasonTable();
        invalidBtn();
    }

    function controlBtn() {
        if ($('#idTestexecResultDiagnosis').val()) {
            $('#addDieReason').removeClass("layui-btn-disabled");
            $('#addDieReason').removeAttr("disabled");
        }
    }

    function invalidBtn() {
        $('#addDieReason').addClass("layui-btn-disabled");
        $('#addDieReason').attr("disabled");
    }

    function tableSelectRender() {
        tableSelect.render({
            elem: '#addDieReason',
            checkedKey: 'addDieReasonId',
            //searchKey: 'keywords',
            //searchPlaceholder: '请输入搜索关键字',
            table: {
                url: basePath + '/pf/p/waiting/room/test/die/ready/reason/list'
                , cols: [[
                    {type: 'checkbox'},
                    {field: 'idText', minWidth: 150, title: '确诊理由'},
                    {field: 'sdEvaEffciency', width: 80, title: '阶段', templet: '#sdEvaTpl'}
                ]] //设置表头
                , where: {
                    idTestexecResult: idTestexecResult
                }
                , page: false
            },
            done: function (elem, data) {
                var selectData = data.data;
                saveDieReason(selectData);
            }
        });
    }

    function saveDieReason(data) {
        var url = basePath + '/pf/r/waiting/room/summary/die/reason/save';
        var reqData = new Array();

        $.each(data, function (index, content) {
            var bizData = {
                idDieReason: content.idDieReason,
                idTestexecResultDiagnosis: $('#idTestexecResultDiagnosis').val(),
                sdEvaEffciency: content.sdEvaEffciency,
                idMedCaseList: content.id
            }
            reqData.push(bizData);
        });
        common.commonPost(url, reqData, null, null, saveDieReasonCallback);
    }

    function saveDieReasonCallback() {
        reloadDieReasonTable();
        controlBtn();
    }


    function reloadDieReasonTable() {
        table.reload('partConsTableId', {
            where: {
                idTestexecResultDiagnosis: $('#idTestexecResultDiagnosis').val()
            }
        });
    }

    table.on('tool(partConsTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('确定要删除确诊理由【' + data.idText + '】么？', {
                offset: [$(this).offset().top + 'px', ($(this).offset().left - 360) + 'px'],
            }, function (index) {
                var bizData = {
                    idDieReason: obj.data.idDieReason
                };
                common.commonPost(basePath + '/pf/r/waiting/room/summary/reason/del', bizData, '删除');
                layer.close(index);
                obj.del();
            });
        }
    });

    $("#dieSumary").blur(function () {
        var bizData = {
            idTestexecResultSumary: $('#idTestexecResultSumary').val(),
            idTestexecResult: idTestexecResult,
            dieSumary: $('#dieSumary').val()
        }
        common.commonPost(basePath + '/pf/r/waiting/room/summary/save', bizData, null, null, saveDieSumaryCallback);
    });

    function saveDieSumaryCallback(data) {
        $('#idTestexecResultSumary').val(data.data);
    }

});

