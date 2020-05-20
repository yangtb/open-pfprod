layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'jquery','form',  /*'tableSelect',*/'common'], function () {
    var $ = layui.$
        , table = layui.table
        , common = layui.common
        , form = layui.form
        /*, tableSelect = layui.tableSelect*/;

    var index_layedit;
    $(document).ready(function () {
        $('.summernote').summernote({
            toolbar: [
                ['style', ['style']],
                ['font', ['bold', 'italic', 'underline', 'clear']],
                ['fontname', ['fontname']],
                ['color', ['color']],
                ['para', ['ul', 'ol', 'paragraph']],
                ['height', ['height']],
                ['table', ['table']],
                ['insert', ['link', 'hr']],
            ],
            height: 530,
            tabsize: 2,
            lang: 'zh-CN'
        });

        initData();
    });


    $('.summernote').on('summernote.blur', function () {
        let desSumaryHpi = $('.summernote').summernote('code');
        if (!desSumaryHpi) { return}
        let bizData = {
            desSumaryHpi: desSumaryHpi
        }
        saveSummary(bizData);
    });

    function initData() {
        // 加载诊断
        //initForm();
        // 加载确诊理由
        //loadZd();

        // 加载小结信息
        loadSummary();
    }

    function loadSummary() {
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/summary/select',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                if (data.code != 0) {
                    layer.msg(data.msg);
                    return false;
                } else {
                    if (data.data) {
                        form.val("summaryFormFilter", data.data);
                        $('.summernote').summernote('code', data.data.desSumaryHpi);
                    }
                }
            },
            error: function () {
                layer.msg("网络异常");
                return false;
            }
        });
    }


    $("#demo-desSumaryHpi").change(function () {
        alert(1)
        var bizData = {
            desSumaryHpi: $('#desSumaryHpi').val()
        }
        saveSummary(bizData);
    });

    $("#desSumaryPe").change(function () {
        var bizData = {
            desSumaryPe: $('#desSumaryPe').val()
        }
        saveSummary(bizData);
    });

    $("#desSumaryFe").change(function () {
        var bizData = {
            desSumaryFe: $('#desSumaryFe').val()
        }
        saveSummary(bizData);
    });

    function saveSummary(bizData) {
        bizData.idTestexecResultSumary = $('#idTestexecResultSumary').val();
        bizData.idTestexecResult = idTestexecResult;
        common.commonPost(basePath + '/pf/r/waiting/room/summary/save', bizData, null, null, saveDieSumaryCallback);
    }

    function saveDieSumaryCallback(data) {
        $('#idTestexecResultSumary').val(data.data);
    };

    $('#refresh').on('click', function () {
        window.location.reload();
    });

    /*function initForm() {
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
        checkedKey: 'idDie',
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

        var html = "            <div class=\"layui-col-md6\">\n" +
            "                <div class=\"layui-card\" style='box-shadow: none'>\n" +
            "                    <div class=\"layui-card-header\" style=\"border: none\">\n" +
            "                        <span class=\"disc\"></span>\n" +
            "                        <span class='diagnose-title'>诊断" + (i + 1) + "： " + data.idDieText + "</span>\n" +
            "                    </div>\n" +
            "                    <div class=\"layui-card-body\">\n" +
            "                        <div class=\"layui-row\" id=\"zdField\">\n" +
                                    '   <div>\n' +
                                    '       <table id="zdTable' + i + '" lay-filter="zdTableFilter' + i + '">\n' +
                                    '       </table>\n' +
                                    '   </div>\n' +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>";

        return html;
    }

    function renderZdSelect(i, idTestexecResultDiagnosis) {
        tableSelect.render({
            elem: '#addDieReason' + i,
            checkedKey: 'id',
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
                //{fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#summaryBar'}
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
    }*/


})

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

