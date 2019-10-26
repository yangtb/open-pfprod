layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'jquery', 'common', 'table', 'tableSelect'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common
        , table = layui.table
        , tableSelect = layui.tableSelect;


    initData();

    function initData() {
        layer.load(2);
        // 加载小结信息
        loadSummary();
        loadNz();
        layer.closeAll('loading');
    };


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
                        $('#desSumaryHpi').text(data.data.desSumaryHpi);
                        $('#desSumaryPe').text(data.data.desSumaryPe);
                        $('#desSumaryFe').text(data.data.desSumaryFe);
                    }
                }
            },
            error: function () {
                layer.msg("网络异常");
                return false;
            }
        });
    }

    function loadNz() {
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/referral/select/all',
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
                        var html = '';
                        $.each(data.data, function (i, item) {
                            var idDieText = item.idDieText;
                            if (item.catalogue == 1) {
                                idDieText += '（目录）';
                            } else {
                                idDieText += '（疾病）';
                            }
                            html += '<option class="item-option" value="' + item.idDie + '" ' +
                                'catalogue="' + item.catalogue + '" referral-id="' + item.idTestexecResultReferral + '">' + idDieText + '</option>';
                        });
                        $('#question-select').append(html);

                        loadDieReasonCommon()
                    }
                }
            },
            error: function () {
                layer.msg("网络异常");
                return false;
            }
        });
    }

    // 保存诊断
    $('#saveDiagnosis').on('click', function () {
        layer.load(2);
        var url = basePath + '/pf/r/waiting/room/summary/diagnosis/save';
        let catalogue = $('#question-select').find("option:selected").attr("catalogue");
        let idDie;
        if (catalogue == 1) {
            idDie = $('#question-select-detail option:selected').val()
        } else {
            idDie = $('#question-select option:selected').val();
        }
        var bizData = {
            idTestexecResult: idTestexecResult,
            idDie: idDie,
            idTestexecResultReferral : $("#question-select").find("option:selected").attr("referral-id"),
            fgDieClass : $("#question-select").find("option:selected").attr("catalogue"),
            desDieReason : $('#desDieReason').val()
        }
        common.commonPost(url, bizData, '添加');
    });

    $('#question-select').on('change', function () {
        $("#question-select-detail").empty();
        loadDieReasonCommon();
    });

    $('#question-select-detail').on('change', function () {
        loadDieReason();
    });

    function loadDieReasonCommon() {
        let catalogue = $('#question-select').find("option:selected").attr("catalogue");
        if (catalogue == 1) {
            questionSelectDetail();
        } else {
            loadDieReason();
        }
    }

    function questionSelectDetail() {
        // 如果是目录，查询该目录的疾病
        var reqData = {
            queryChildCatalogue: 1,
            id : $('#question-select option:selected').val()
        }
        $.ajax({
            url: basePath + '/pf/r/disease/catalogue/tree',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(reqData),
            success: function (data) {
                var html = '';
                $.each(data, function (i, item) {
                    var idDieText = item.name;
                    html += '<option class="item-option" value="' + item.idDie + '" ' +
                        '>' + idDieText + '</option>';
                });
                $('#question-select-detail').append(html);
                loadDieReason();
                return true;
            },
            error: function () {
                layer.msg("网络异常");
                return false;
            }
        });
    }

    function loadDieReason() {
        let catalogue = $('#question-select').find("option:selected").attr("catalogue");
        var idDie;
        if (catalogue == 1) {
            idDie = $('#question-select-detail option:selected').val()
        } else {
            idDie = $('#question-select option:selected').val();
        }
        var bizData = {
            idTestexecResult: idTestexecResult,
            idDie: idDie,
            idTestexecResultReferral : $("#question-select").find("option:selected").attr("referral-id"),
            fgDieClass : $("#question-select").find("option:selected").attr("catalogue")
        };
        if (bizData.fgDieClass == 0) {
            bizData.fgDieClass = '2';
        }
        $.ajax({
            url: basePath + '/pf/r/waiting/room/summary/diagnosis/select',
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
                        $('#desDieReason').val(data.data.desDieReason ? data.data.desDieReason : '' );
                    } else {
                        $('#desDieReason').val('');
                    }
                }
            },
            error: function () {
                layer.msg("网络异常");
                return false;
            }
        });
    }

    // ============ 思维导图 begin ============

    $(function(){

        var bizData = {
            idTestexecResult: idTestexecResult,
            chartType : 2
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/referral/chart/data',
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
                        loadChart(data.data);
                    }
                }
            },
            error: function () {
                layer.msg("网络异常");
                return false;
            }
        });

        function loadChart(dataScource) {
            $('#chart-container').orgchart({
                'data' : dataScource,
                'nodeContent': 'title',
                'direction': 't2b',
                'createNode': function ($node, data) {
                    if (data.type ) {
                        if (data.type == 1) {
                            $node.html('<button class="layui-btn layui-btn-radius">' + data.name + '</button>');
                        } else if (data.type == 2) {
                            var fgExcludeHtml = '';
                            if (data.fgExclude == '1') {
                                fgExcludeHtml = '<span style="text-decoration: line-through; color: #FF5722;">' + data.name + '</span>';
                            } else {
                                fgExcludeHtml = data.name;
                            }
                            $node.html('<button class="layui-btn layui-bg-blue" style="height: 50px;">' + fgExcludeHtml + '</button>');
                        } else if (data.type == 3) {
                            $node.html('<button class="layui-btn layui-btn-primary thirdChart" id="zdfx-' + data.id + '" style="border-color: #4A92D8">' + data.name + '</button>');
                        } else if (data.type == 4){
                            $node.html('<button class="layui-btn fourChart" id="jbzd-' + data.id + '" style="background-color: #64C092">' + data.name + '</button>');
                        } else{
                            $node.html('<button class="layui-btn">' + data.name + '</button>');
                        }
                    } else {
                        $node.html('<button class="layui-btn">' + data.name + '</button>');
                    }
                }
            });

            $('.orgchart > table > tbody').find('.nodes').find(".lines:even").hide();
            $('.orgchart > table > tbody > tr ').find('.nodes').find(".lines").hide();

            // 绑定点击事件
            var thirdCharts = document.querySelectorAll(".thirdChart");
            for (var i = 0; i < thirdCharts.length; i++) {
                tableSelectRender(thirdCharts[i].getAttribute("id"), 1);
            }

            var fourCharts = document.querySelectorAll(".fourChart");
            for (var i = 0; i < fourCharts.length; i++) {
                tableSelectRender(fourCharts[i].getAttribute("id"), 2);
            }
        }

    });

    // ============ 思维导图 end ==============

    function tableSelectRender(id, type) {
        // type = 1 加载拟真下面的检查，检验（且都是单选的）
        // type = 2 加载拟真下的检查检验（只加载多选）
        table = $.extend(table, {config: {checkName: 'fgClue'}});
        tableSelect.render({
            elem: '#' + id,
            checkedKey: 'id',
            searchKey: 'keywords',
            table: {
                url: basePath + '/pf/p/waiting/room/diagnostic/chart/list?type=' + type + '&idTestexecResult=' + idTestexecResult
                , cols: [[
                    {type: 'checkbox', fixed: true},
                    {field: 'checkItem', minWidth: 160, title: '检查项'},
                    {field: 'desResult', minWidth: 120, title: '结果'},
                ]] //设置表头
            },
            done: function (elem, data) {
                var selectData = data.data;
                saveFgClue(selectData, type);
            }
        });
    }

    function saveFgClue(data, type) {
        var reqBodyData = new Array();
        var reqExamData = new Array();
        $.each(data, function (index, content) {
            var idArr = content.id.split("-");
            if (idArr[0] == 'body') {
                reqBodyData.push(idArr[1]);
            } else {
                reqExamData.push(idArr[1]);
            }
        });

        if (reqBodyData.length >= 1) {
            var bizBodyData = {
                list : reqBodyData,
                status : '1',
                extId : idTestexecResult,
                operationType : type,
                extType : 1 // 先删后插
            };
            common.commonPost(basePath + '/pf/r/waiting/room/check/qa/status', bizBodyData, null);
        }
        if (reqExamData.length >= 1) {
            var bizExamData = {
                list : reqExamData,
                status : '1',
                extId : idTestexecResult,
                operationType : type,
                extType : 1 // 先删后插
            };
            common.commonPost(basePath + '/pf/r/waiting/room/exam/qa/status', bizExamData, null);
        }

    }


})
