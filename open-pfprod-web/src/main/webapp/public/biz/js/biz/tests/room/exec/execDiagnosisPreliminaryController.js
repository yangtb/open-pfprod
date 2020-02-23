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
        loadCbzdList();
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
        let bizData = {
            idTestexecResult: idTestexecResult,
            flag : true
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

        let url = basePath + '/pf/r/waiting/room/summary/diagnosis/save';
        let catalogue = $('#question-select').find("option:selected").attr("catalogue");
        let idDie;
        if (catalogue == 1) {
            idDie = $('#question-select-detail option:selected').val()
        } else {
            idDie = $('#question-select option:selected').val();
        }
        if (!idDie) {
            layer.msg("请先选择疾病");
            return;
        }
        let bizData = {
            idTestexecResult: idTestexecResult,
            idDie: idDie,
            idTestexecResultReferral : $("#question-select").find("option:selected").attr("referral-id"),
            fgDieClass : $("#question-select").find("option:selected").attr("catalogue"),
            desDieReason : $('#desDieReason').val(),
            mainFlag : $("input[name='mainFlag']:checked").val()
        }

        common.commonPost(url, bizData, '添加', null, _callbackSaveDiagnosis, true);
    });

    function _callbackSaveDiagnosis() {
        loadCbzdList()
        loadChartData()
    }

    $('#question-select').on('change', function () {
        $("#question-select-detail").empty();
        $('#desDieReason').val('');
        $(":radio[name='mainFlag'][value='0']").prop("checked", "checked");
        loadDieReasonCommon();
    });

    $('#question-select-detail').on('change', function () {
        loadDieReason();
    });

    function loadDieReasonCommon() {
        let catalogue = $('#question-select').find("option:selected").attr("catalogue");
        if (catalogue == 1) {
            $('#zdDetail').show();
            questionSelectDetail();
        } else {
            $('#zdDetail').hide();
            loadDieReason();
        }
    }

    function questionSelectDetail() {
        // 如果是目录，查询该目录的疾病
        let reqData = {
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
                let html = '<option class="item-option" value="">请选择</option>';
                $.each(data, function (i, item) {
                    let idDieText = item.name;
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
        let idDie;
        if (catalogue == 1) {
            idDie = $('#question-select-detail option:selected').val()
        } else {
            idDie = $('#question-select option:selected').val();
        }
        if (!idDie) {
            return;
        }
        let bizData = {
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
                    console.log(data.data)
                    if (data.data) {
                        console.log(data.data.mainFlag)
                        $('#desDieReason').val(data.data.desDieReason ? data.data.desDieReason : '' );
                        $(":radio[name='mainFlag'][value='" + data.data.mainFlag + "']").prop("checked", "checked");

                    } else {
                        $(":radio[name='mainFlag'][value='0']").prop("checked", "checked");
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
        loadChartData()
    });

    function loadChartData() {
        let bizData = {
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
    }

    function loadChart(dataScource) {
        $('#chart-container').empty();
        $('#chart-container').orgchart({
            'data' : dataScource,
            'nodeContent': 'title',
            'direction': 't2b',
            'createNode': function ($node, data) {
                if (data.type ) {
                    if (data.type == 1) {
                        $node.html('<button class="layui-btn layui-btn-radius">' + data.name + '</button>');
                    } else if (data.type == 2) {
                        let fgExcludeHtml = '';
                        let mainFlagStyle = data.mainFlag == 1 ? ' <span style="color: red;"><i class="iconfont icon-zhu"></i></span>' : '';
                        if (data.fgExclude == '1') {
                            fgExcludeHtml = '<span style="text-decoration: line-through; color: #FF5722;">' + data.name + '</span>';
                        } else {
                            fgExcludeHtml = data.name;
                        }
                        $node.html('<button class="layui-btn layui-bg-blue" style="height: 50px;">' + fgExcludeHtml + mainFlagStyle +'</button>');
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

    // ============ 思维导图 end ==============

    function tableSelectRender(id, type) {
        // type = 1 加载拟诊下面的检查，检验（且都是单选的）
        let idDie = id.substring(5, id.length);
        // type = 2 加载拟诊下的检查检验（只加载多选）
        table = $.extend(table, {config: {checkName: 'fgClue'}});
        tableSelect.render({
            elem: '#' + id,
            checkedKey: 'id',
            searchKey: 'keywords',
            table: {
                url: basePath + '/pf/p/waiting/room/diagnostic/chart/list?type=' + type
                    + '&idTestexecResult=' + idTestexecResult
                    + '&idDie=' + idDie
                , cols: [[
                    {type: 'checkbox', fixed: true},
                    {field: 'checkItem', minWidth: 160, title: '检查项'},
                    {field: 'desResult', minWidth: 120, title: '结果'},
                ]] //设置表头
            },
            done: function (elem, data) {
                let selectData = data.data;
                saveFgClue(selectData, type);
            }
        });
    }

    function saveFgClue(data, type) {
        let reqBodyData = new Array();
        let reqExamData = new Array();
        $.each(data, function (index, content) {
            let idArr = content.id.split("-");
            if (idArr[0] == 'body') {
                reqBodyData.push(idArr[1]);
            } else {
                reqExamData.push(idArr[1]);
            }
        });

        if (reqBodyData.length >= 1) {
            let bizBodyData = {
                list : reqBodyData,
                status : '1',
                extId : idTestexecResult,
                operationType : type,
                extType : 1 // 先删后插
            };
            common.commonPost(basePath + '/pf/r/waiting/room/check/qa/status', bizBodyData, null);
        }
        if (reqExamData.length >= 1) {
            let bizExamData = {
                list : reqExamData,
                status : '1',
                extId : idTestexecResult,
                operationType : type,
                extType : 1 // 先删后插
            };
            common.commonPost(basePath + '/pf/r/waiting/room/exam/qa/status', bizExamData, null);
        }

    }

    $('#addDiagnosis').on('click', function () {
        $("#question-select-detail option:first").prop("selected", 'selected');
        $('#desDieReason').val('');

    });

    function loadCbzdList() {
        let bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/summary/diagnosis/list',
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
                        refreshCbzd(data.data);
                    }
                }
            },
            error: function () {
                layui.common.errorMsg("网络异常");
                return false;
            }
        });
    }

    function refreshCbzd(dataList){
        if (dataList.length == 0) {
            return;
        }
        let result = '';
        $.each(dataList, function (i, item) {
            result += cbzdHtml(i, item);
        });
        $('#cbzdField').empty();
        $('#cbzdField').append(result);

        registerOutCbzdEvent();

    }

    function cbzdHtml(i, data) {
        let desDieReason = data.desDieReason ? data.desDieReason : '';

        let mainFlagStyle = data.mainFlag == 1 ? ' <span style="color: red; font-size: 20px;"><i class="iconfont icon-zhu"></i></span>' : '';

        let newHtml = '           <div id="cbzd-'+ data.idTestexecResultDiagnosis +'" class="layui-row referral">\n' +
            '                        <div style="margin: 15px 0 0 5px">\n' +
            '                            <span class="disc"></span>\n' +
            '                            <span class="diagnose-title">初步诊断' + (i + 1) + '：' + data.idDieText + mainFlagStyle +'</span>\n' +
            '                            <span>\n' +
            '                                <button data-content-id="' + data.idTestexecResultDiagnosis +'" data-content="' + data.idDieText +'" style="float: right; margin-right: 10px;" class="layui-btn layui-btn-xs layui-btn-danger delCbzdBtn" id="out'+ i +'">\n' +
            '                                   <i class="layui-icon layui-icon-delete"></i><span id="outBtnText0">删除</span>\n' +
            '                                </button>\n' +
            '                            </span>\n' +
            '                        </div>\n' +
            '                        <div style="margin: 5px 0 10px 5px">\n' +
            '                            <span class="disc"></span>\n' +
            '                            <span class="diagnose-title">诊断理由：' + desDieReason +'</span>\n' +
            '                        </div>\n' +
            '                    </div>';
        return newHtml;
    }


    function registerOutCbzdEvent() {
        let outCbzdBtns = document.querySelectorAll(".delCbzdBtn");
        for (let i = 0; i < outCbzdBtns.length; i++) {
            outCbzdBtns[i].addEventListener('click', function () {
                let idTestexecResultDiagnosis = this.getAttribute("data-content-id");
                let idDieText = this.getAttribute("data-content");

                layui.layer.confirm('确定删除初步诊断【' + idDieText + '】么？', {
                    shade: 0
                }, function (index) {
                    delCbzdPost(idTestexecResultDiagnosis);
                    layer.close(index);
                });
            });
        }
    }

    function delCbzdPost(idTestexecResultDiagnosis) {
        let bizData = {
            idTestexecResultDiagnosis: idTestexecResultDiagnosis
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/summary/diagnosis/del',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg);
                    return false;
                } else {
                    $('#cbzd-' + idTestexecResultDiagnosis).remove();
                    loadChartData();
                    layer.tips("排除成功");
                    $("#question-select-detail option:first").prop("selected", 'selected');
                    $('#desDieReason').val('');
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                layui.common.errorMsg(data.msg + "失败");
                return false;
            }
        });
    }


})
