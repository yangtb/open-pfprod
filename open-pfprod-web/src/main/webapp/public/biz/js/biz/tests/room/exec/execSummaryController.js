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
        loadZd();
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
                        $('#idTestexecResultSumary').val(data.data.idTestexecResultSumary);
                        $('#dieSumary').val(data.data.dieSumary);
                    }
                }
            },
            error: function () {
                layui.common.errorMsg("获取医嘱信息失败");
                return false;
            }
        });
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
            $('#idDie').val(selectData.idDie);
            $('#idDieText').val(selectData.name);
        }
    });

    $('#addZd').on('click', function () {
        if (!$('#idDie').val()) {
            layer.tips('请先选择诊断', '#searchAnswer', {tips: 1});
            return false;
        }
        // 保存诊断
        saveDiagnosis();
        return false;
    });

    function saveDiagnosis() {
        var url = basePath + '/pf/r/waiting/room/summary/diagnosis/save';
        var bizData = {
            idTestexecResultDiagnosis: $('#idTestexecResultDiagnosis').val(),
            idTestexecResult: idTestexecResult,
            idDie: $('#idDie').val()
        }
        common.commonPost(url, bizData, '添加', 'addZd', saveDiagnosisCallback)
    }

    function saveDiagnosisCallback(data) {
        $('#idDie').val('');
        $('#idDieText').val('');
        loadZd();
    }

    $('#delSummary').on('click', function () {
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
    }

    function saveDieReason(data, idTestexecResultDiagnosis, i) {
        var reqData = new Array();
        $.each(data, function (index, content) {
            var bizData = {
                idDieReason: content.idDieReason,
                idTestexecResultDiagnosis: idTestexecResultDiagnosis,
                sdEvaEffciency: content.sdEvaEffciency,
                idMedCaseList: content.id,
                extId: content.extId
            }
            reqData.push(bizData);
        });

        $.ajax({
            url: basePath + '/pf/r/waiting/room/summary/die/reason/save',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(reqData),
            success: function (data) {
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    saveDieReasonCallback(i, idTestexecResultDiagnosis);
                }
            },
            error: function () {
                layui.common.errorMsg("保存确诊理由失败");
                return false;
            }
        });
    }

    function saveDieReasonCallback(i, idTestexecResultDiagnosis) {
        reloadDieReasonTable(i, idTestexecResultDiagnosis);
        //controlBtn();
    }


    function reloadDieReasonTable(i, idTestexecResultDiagnosis) {
        table.reload('zdTableId' + i, {
            where: {
                idTestexecResultDiagnosis: idTestexecResultDiagnosis
            }
        });
    }

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
    };

    function loadZd() {
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/diagnosis/select',
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
                        refreshZd(data.data);
                    }
                }
            },
            error: function () {
                layui.common.errorMsg("获取医嘱信息失败");
                return false;
            }
        });
    }

    function refreshZd(dataList) {
        if (dataList.length == 0) {
            return;
        }
        var result = '';
        $.each(dataList, function (i, item) {
            result += zdHtml(i, item);
        });
        $('#zdField').empty();
        $('#zdField').append(result);

        $.each(dataList, function (i, item) {
            renderZdSelect(i, item.idTestexecResultDiagnosis);
            renderZdTable(i, item);
        });
    }

    function zdHtml(i, data) {
        var html =
            '<fieldset id="fieldset' + i + '" class="layui-elem-field">\n' +
            '   <legend>[' + (i + 1) + ']' +
            '       <button id="delSummary' + i + '" class="layui-btn layui-btn-sm layui-btn-danger" ' +
            '       onclick="delZd(' + i + ', ' + data.idTestexecResultDiagnosis + ', \'' + data.idDieText + '\')">\n' +
            '           <i class="layui-icon layui-icon-delete"></i>删除\n' +
            '       </button>\n' +
            '       <button class="layui-btn layui-btn-sm" id="addDieReason' + i + '">\n' +
            '           <i class="layui-icon layui-icon-add-1"></i>确诊理由\n' +
            '       </button>\n' + '诊断 -' + data.idDieText +
            '   </legend>\n' +
            '   <div class="layui-field-box">\n' +
            '       <table id="zdTable' + i + '" lay-filter="zdTableFilter' + i + '">\n' +
            '       </table>\n' +
            '   </div>\n' +
            '</fieldset>';
        return html;
    }

    function renderZdSelect(i, idTestexecResultDiagnosis) {
        tableSelect.render({
            elem: '#addDieReason' + i,
            checkedKey: 'addDieReasonId' + i,
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
                , limit: 1000
                , limits: [30, 50]
            },
            done: function (elem, data) {
                var selectData = data.data;
                saveDieReason(selectData, idTestexecResultDiagnosis, i);
            }
        });
    }

    function renderZdTable(i, data) {
        //执行渲染
        table.render({
            elem: '#zdTable' + i //指定原始表格元素选择器（推荐id选择器）
            , id: 'zdTableId' + i
            , height: '200' //容器高度
            , defaultToolbar: []
            , cols: [[
                {type: 'numbers'},
                {field: 'idText', minWidth: 150, title: '确诊理由'},
                {field: 'sdEvaEffciency', width: 80, title: '阶段', templet: '#sdEvaTpl'},
                {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#summaryBar'}
            ]] //设置表头
            , url: basePath + '/pf/p/waiting/room/test/die/reason/list'
            , where: {
                idTestexecResultDiagnosis: data.idTestexecResultDiagnosis
            }
            , page: false
            , limit: 1000
            , limits: [30, 50]
        });
        tableTool(i);
    }

    function tableTool(i) {
        table.on('tool(zdTableFilter' + i + ')', function (obj) {
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
    }

});

function delZd(i, idTestexecResultDiagnosis, idDieText) {
    layui.config({
        base: basePath + '/public/layui/build/js/'
    }).use(['layer', 'common'], function () {
        layui.layer.confirm('确定删除诊断【' + idDieText + '】么？', {
            offset: [$('#delSummary' + i).offset().top + 'px', $('#delSummary' + i).offset().left + 'px'],
        }, function (index) {
            var bizData = {
                idTestexecResultDiagnosis: idTestexecResultDiagnosis
            };
            layui.common.commonPost(basePath + '/pf/r/waiting/room/summary/diagnosis/del',
                bizData, '删除', null, delSummaryCallBack);
            layer.close(index);
        });

        function delSummaryCallBack() {
            $('#fieldset' + i).remove();
        }
    });
    return false;
}

