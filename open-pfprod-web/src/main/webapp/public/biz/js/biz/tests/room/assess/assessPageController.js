layui.config({
    base: basePath + '/public/layui/plugins/'
}).extend({
    index: 'lib/index' //主入口模块
}).use(['table', 'form', 'jquery', 'index', 'common', 'element', 'view'], function () {
    var $ = layui.$,
        table = layui.table,
        form = layui.form,
        common = layui.common
        , view = layui.view
        , element = layui.element;

    function setAttr(data) {
        $('#test').attr('lay-href',
            basePath + '/pf/p/waiting/room/exam/page?idTestplanDetail=' + data.idTestplanDetail
            + '&idDemo=' + data.idDemo + '&idTestplan=' + data.idTestplan + '&idStudent=' + data.idStudent
            + '&idTestpaper=' + data.idTestpaper
            + '&idMedicalrec=' + data.idMedicalrec
            + '&idMedCase=' + data.idMedCase);
        $('#test').click();
    }

    $('#queryMed').on('click', function () {
        // 获取患者信息idMedCase
        layer.load(2);
        var bizData = {
            idTestplanDetail: idTestplanDetail
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/assess/pat/idMedCase',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    var bizData = {
                        idTestplanDetail: idTestplanDetail,
                        idDemo: idDemo,
                        idTestplan: idTestplan,
                        idStudent: idStudent,
                        idTestpaper: idTestpaper,
                        idMedicalrec: idMedicalrec,
                        idMedCase: data.data
                    }
                    setAttr(bizData);
                    return false;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
        return false;
    });

    $('#accessMed').on('click', function () {
        assessCase();
    });

    function assessCase() {
        layer.load(2);
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/med/eva',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    initData();
                    layer.tips('评估完成', '#accessMed', {tips: 1});
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    }

    $(document).ready(function () {
        if (autoAssess == 1) {
            assessCase();
        } else {
            initData();
        }
    });

    function initData() {
        var filterFlag = autoAssess == 1 ? 1 : 0;
        //layer.msg('正在加载数据，请稍后...', {icon: 16, shade: 0.01});
        var bizData = {
            idTestexecResult: idTestexecResult,
            filterFlag : filterFlag
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/eva/list',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    // 加载评估得分
                    appendScoreHtml(data.data)
                    // 加载评估列表
                    listEva(data.data);
                    return true;
                }
                layer.closeAll('loading');
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    }

    var parData = [];

    function appendScoreHtml(data) {
        parData = [];
        $.each(data, function (i, item) {
            if (!item.parDimemsion) {
                parData.push(item)
            }
        });
        var titleHtml = '', scoreHtml = '';
        var totalScore = 0;
        if (parData.length == 0) {
            defaultHtml();
            return;
        }
        $.each(parData, function (i, item) {
            titleHtml += '<th>' + item.pgItem + '</th>';
            scoreHtml += '<td>' + item.scoreDimemsion + '</td>';
            if (i == 1) {
                titleHtml += '<th>临床思维评价</th>';
                scoreHtml += '<td id="pjResult"></td>';
            }
            totalScore += item.weightScoreDimemsion;
        });
        titleHtml += '<th>总得分</th>';
        scoreHtml += '<td>' + totalScore.toFixed(2) + '</td>';

        $('#scoreTitle').empty();
        $('#scoreTitle').append(titleHtml);
        $('#score').empty();
        $('#score').append(scoreHtml);
    }

    function defaultHtml() {
        var titleHtml = '<th>诊断表现得分</th>\n' +
            '<th>临床思维得分</th>\n' +
            '<th>临床思维评价</th>\n' +
            '<th>医嘱得分</th>\n' +
            '<th>病例书写得分</th>\n' +
            '<th>知识掌握得分</th>\n' +
            '<th>总得分</th>';

        var scoreHtml = '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>';

        $('#scoreTitle').empty();
        $('#scoreTitle').append(titleHtml);
        $('#score').empty();
        $('#score').append(scoreHtml);
    }

    function listEva(data) {
        tableRender(data);
        //evaLog();
        setPjResult();
        loadGuideContent();
    }

    function tableRender(list) {
        if (list && list.length > 0) {
            list[0].nzName = 'pgResult';
        }
        //展示已知数据
        table.render({
            elem: '#demo'
            , id: 'demoTableId'
            , cols: [[ //标题栏
                {field: 'pgItem', title: '评估项', width: 130, style:'font-weight: bold;'}
                , {field: 'nzName', minWidth: 120, align: 'left', templet:function(d){
                        return d.nzName != 'pgResult' ? d.nzName : '<span id="pgResult"></span>';
                    }}
                , {field: 'weightDimemsion', title: '权重', width: 80, align: 'left', templet:function(d){
                        return d.weightDimemsion + "%";
                    }}
                , {field: 'scoreDimemsion', title: '得分', width: 120, align: 'center', templet: '#scoreDimemsionTpl'}
                //, {field: 'weightScoreDimemsion', title: '加权分', width: 80, align: 'left'}
            ]]
            , data: list
            , skin: 'line' //表格风格
            , page: false
            , limit: 1000
        });
    }

    element.on('tab(tagTabFilter)', function (data) {
        if (data.index == 1) {
            execLog();
        }
        if (data.index == 2) {
            loadItem();
        }
        if (data.index == 3) {
            loadChart();
        }
    });

    function execLog() {
        layer.load(2);
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/exec/log/list',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    execLogTableRender(data.data);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    }

    function execLogTableRender(list) {
        //展示已知数据
        table.render({
            elem: '#execLog'
            , id: 'execLogTableId'
            , cols: [[ //标题栏
                {field: 'stage', title: '阶段', width: 80}
                , {field: 'operation', title: '操作', width: 200, align: 'left'}
                , {field: 'answer', title: '答案', minWidth: 250, align: 'left'}
                , {field: 'logDate', title: '时间', width: 180, align: 'left'}
            ]]
            , data: list
            , skin: 'line' //表格风格
            , page: false
            , limit: 1000
        });
    }

    var evaLogList;

    function evaLog() {
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/eva/log/list',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    evaLogList = data.data;
                    tableEvaLogRender(data.data);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });

    }

    function tableEvaLogRender(list) {
        //展示已知数据
        table.render({
            elem: '#evaLog'
            , id: 'evaLogTableId'
            , cols: [[ //标题栏
                {field: 'nameEva', title: '评估标准', minWidth: 80}
                , {field: 'scoreEva', title: '标准分值', width: 90, align: 'left'}
                , {field: 'nameResult', title: '病例结果', width: 90, align: 'left'}
                , {field: 'scoreResult', title: '病例得分', width: 90, align: 'left'}
                , {field: 'desLog', title: '描述', minWidth: 80, align: 'left'}
            ]]
            , data: list
            , skin: 'line' //表格风格
            , page: false
            , limit: 1000
        });
    }

    //监听行双击事件
    table.on('rowDouble(demoFilter)', function (obj) {
        /*var resultData = [];
        $.each(evaLogList, function (i, item) {
            if (obj.data.idTestexecResultDimension == item.idTestexecResultDimension) {
                resultData.push(item);
            }
        });

        element.tabChange('evaTabFilter', '1'); //切换tab

        table.reload('evaLogTableId', {
            data: resultData
        });*/
    });

    //监听行双击事件
    table.on('row(demoFilter)', function (obj) {
        console.log(obj.data) //得到当前行数据
        let fgSystemAlgorithm = obj.data.fgSystemAlgorithm; // 1=系统 0主观
        //CASE WHEN 类型 = 主观 OR 评分项名称 = 诊断表现  THEN  打开文本页  ELSE 打开LIST页
        if (fgSystemAlgorithm == 0 || obj.data.pgItem == '诊断表现') {
            $('#guideContentText').show();
            //$('#dimensionResult').hide();
            $('div[lay-id="dimensionResultTableId"]').hide();
        } else {
            $('#guideContentText').hide();
            $('div[lay-id="dimensionResultTableId"]').show();
            // 加载table
            dimensionResult(obj.data.idTestexecResultDimension);
        }
        // 加载病史小结
        if (obj.data.pgItem == '病例书写得分'
            || obj.data.nzName == '主观资料(S)' || obj.data.nzName == '客观资料(O)' || obj.data.nzName == '评估资料(A)' || obj.data.nzName == '计划(P)') {
            $('#bsxj').show();
            loadSummary(1);
        } else {
            $('#bsxj').hide();
        }
        // 能力和素养
        if (obj.data.nzName == '病情描述') {
            $('#bqms').show();
            loadSummary(2);
        } else {
            $('#bqms').hide();
        }
        if (obj.data.nzName == '诊断论证') {
            //ROLE_EXM0040
            $('#zdlz').show();
            loadJbzdList();
        } else {
            $('#zdlz').hide();
        }
        if (obj.data.nzName == '判读' || obj.data.nzName == '理由') {
            $('#readyReasonHtml').show();
            if (obj.data.nzName == '判读') {
                $('#nlText').text('判读的项');
                loadReadyReason(1);
            }
            if (obj.data.nzName == '理由') {
                $('#nlText').text('说明理由的项');
                loadReadyReason(2);
            }
        } else {
            $('#readyReasonHtml').hide();
        }

    });

    function loadReadyReason(status) {
        table.render({
            elem: '#readyReason'
            , id: 'readyReasonTableId'
            , size: 'sm'
            , cols: [[ //标题栏
                {type: 'numbers'},
                {field: 'idText', minWidth: 150, title: '问题', align : 'left'},
                {field: 'resultText', minWidth: 150, title: '结果', align : 'left'},
                {field: 'sdEvaEffciency', width: 80, title: '阶段', templet: '#sdEvaTpl'}
            ]]
            , url: basePath + '/pf/p/waiting/room/test/die/ready/reason/list?idTestexecResult=' + idTestexecResult+ '&status=' + status
            , page: false
            , limit: 1000
        });
    }

    function loadJbzdList() {
        let bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/identify/list',
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
                        refreshJbzd(data.data);
                    }
                }
            },
            error: function () {
                layui.common.errorMsg("网络异常");
                return false;
            }
        });
    }

    function refreshJbzd(dataList){
        if (dataList.length == 0) {
            return;
        }
        let result = '';
        $.each(dataList, function (i, item) {
            result += jbzdHtml(i, item);
        });
        $('#jbzdField').empty();
        $('#jbzdField').append(result);
    }

    function jbzdHtml(i, data) {

        let newHtml = '         <div id="jbzd-'+ data.idTestexecResultIdentify +'" class="layui-row referral">\n' +
            '                        <div style="margin: 15px 0 0 5px">\n' +
            '                            <span class="disc"></span>\n' +
            '                            <span class="diagnose-title">鉴别诊断'+(i + 1)+'：'+ data.naDie +' <span style="color: red; font-size: 20px;"><i class="iconfont icon-zhu"></i></span></span>\n' +
            '                        </div>\n' +
            '                        <div style="margin: 5px 0 0px 5px">\n' +
            '                            <span class="disc"></span>\n' +
            '                            诊断说明：<span class="diagnose-title" id="desDieReason'+ data.idTestexecResultIdentify +'">'+ data.desDieReason +'</span>\n' +
            '                        </div>\n';
        return newHtml;
    }

    function loadSummary(flag) {
        let bizData = {
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
                        if (data.data) {
                            if (flag == 1 && data.data.desSumaryHpi) {
                                $("#desSumaryHpi").val(data.data.desSumaryHpi);
                            }
                            if (flag == 2) {
                                if ( data.data.desConditionHpi) {
                                    $("#desConditionHpi").val(data.data.desConditionHpi);
                                }
                                if ( data.data.desConditionPe) {
                                    $("#desConditionPe").val(data.data.desConditionPe);
                                }
                                if ( data.data.desConditionFe) {
                                    $("#desConditionFe").val(data.data.desConditionFe);
                                }
                            }
                        }
                    }
                }
            },
            error: function () {
                layer.msg("网络异常");
                return false;
            }
        });
    }

    function dimensionResult(idTestexecResultDimension) {
        //展示已知数据
        table.render({
            elem: '#dimensionResult'
            , id: 'dimensionResultTableId'
            , cols: [[ //标题栏
                {type: 'numbers', title: ''}
                , {field: 'flag', title: '学员操作', width: 100, align: 'center', templet : function(d) {
                        return d.flag == true ? '√' : '';
                }}
                , {field: 'nameEva', title: '评估项', minwidth: 200, align: 'left'}
            ]]
            , url: basePath + '/pf/p/waiting/room/eva/result/dimension?idTestexecResultDimension=' + idTestexecResultDimension
            //, skin: 'row' //表格风格
            , page: false
            , limit: 1000
        });
    }

    function setPjResult() {
        let bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/eva/result/select',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    if (data.data && data.data.sdTitleDic) {
                        $('#pjResult').text(data.data.sdTitleDic);
                        $('#pgResult').text("(" + data.data.sdTitleDic + ")");
                    }
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    }
    
    function loadItem() {
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/eva/diagnostic/analysis/list',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    tableZdRender(data.data);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    }

    function tableZdRender(list) {
        var qzList = [], pznzList = [];
        if (list.length > 0) {
            $.each(list, function (index, item) {
                if (item.type == 1) {
                    qzList.push(item);
                } else {
                    pznzList.push(item);
                }
            });

        }
        //展示已知数据
        table.render({
            elem: '#qzItem'
            , id: 'qzItemTableId'
            , cols: [[ //标题栏
                {field: 'itemName', title: '确诊项'},
                {fixed: 'right', width: 60, align: 'center', toolbar: '#statusBar'}
            ]]
            , data: qzList
            , skin: 'line' //表格风格
            , even: false
            , page: false
            , limit: 1000
            , done: function (res, curr, count) {
                $("div[lay-id='qzItemTableId'] th").css({'background-color': '#5FB878', 'color': '#fff'});
            }
        });

        table.render({
            elem: '#pcnzItem'
            , id: 'pcnzItemTableId'
            , cols: [[ //标题栏
                {field: 'itemName', title: '排除拟诊项'},
                {fixed: 'right', width: 60, align: 'center', toolbar: '#statusBar'}
            ]]
            , data: pznzList
            , skin: 'line' //表格风格
            , even: false
            , page: false
            , limit: 1000
            , done: function (res, curr, count) {
                $("div[lay-id='pcnzItemTableId'] th").css({'background-color': '#5FB878', 'color': '#fff'});
            }
        });
    }

    //单击行选中radio
    table.on('row(qzItemFilter)', function (obj) {
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');//选中行样式
        rowClick(obj)
    });

    //单击行选中radio
    table.on('row(pcnzItemFilter)', function (obj) {
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');//选中行样式
        rowClick(obj)
    });

    function rowClick(obj) {
        var bizData = {
            idTestexecResult: idTestexecResult,
            idDieStr: obj.data.idDieStr,
            type : obj.data.type,
            idMedicalrec : idMedicalrec
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/eva/diagnostic/analysis/list/detail',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    loadTreeChart(data.data, obj.data.itemName);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    };

    function buildChartData(item) {
        var picName;
        if (item.flag == 0) {
            picName = 'circle';
        } else if (item.flag == 1) {
            picName = 'semi-circle';
        } else if (item.flag == 2) {
            picName = 'tick';
        }
        var data = {
            "name": "Q："+ item.question +"\nA："+ item.answer +"",
            symbolSize: [26, 26],
            symbol: 'image://' + basePath + '/public/biz/img/chart/' + picName + '.png'
        }
        return data;
    }


    function loadTreeChart(list, itemName) {

        var inquiryList = [], bodyList = [], checkList = [];
        if (list.length > 0) {
            $.each(list, function (index, item) {
                if (item.sdEvaEffciency == 1) {
                    inquiryList.push(buildChartData(item));
                } else if (item.sdEvaEffciency == 2) {
                    bodyList.push(buildChartData(item));
                } else if (item.sdEvaEffciency == 3) {
                    checkList.push(buildChartData(item));
                }
            });
        }

        var data = {
                "name": itemName,
                "children": [
                    {
                        "name": "问诊",
                        itemStyle:{ color: '#7a84f5'} ,
                        lineStyle:{color: '#7a84f5'},
                        "children": inquiryList
                    },
                    {
                        "name": "体格检查",
                        itemStyle:{ color: '#7a84f5'} ,
                        lineStyle:{color: '#7a84f5'},
                        "children": bodyList
                    },
                    {
                        "name": "辅助检查检验",
                        itemStyle:{ color: '#7a84f5'} ,
                        lineStyle:{color: '#7a84f5'},
                        "children": checkList
                    },

                ]
            };
        showTreeChart(data);
    }

    function showTreeChart(data) {
        if (data.length == 0) {
            return;
        }
        var dom = document.getElementById("treeContainer");
        var myChart = echarts.init(dom, 'macarons');
        var option = {
            tooltip: {
                trigger: 'item',
                triggerOn: 'mousemove'
            },
            series: [
                {
                    type: 'tree',
                    data: [data],
                    top: '1%',
                    left: '15%',
                    bottom: '1%',
                    right: '50%',
                    //layout: 'radial',
                    symbol: 'circle',
                    symbolSize: 14,
                    hoverable: false,
                    roam: true,
                    //nodePadding: 20,
                    initialTreeDepth: 4,
                    animationDurationUpdate: 750,
                    itemStyle:{
                        color: {
                            type: 'radial',
                            x: 0.5,
                            y: 0.5,
                            r: 0.5,
                            colorStops: [{
                                offset: 0, color: '#108ee9' // 0% 处的颜色
                            }, {
                                offset: 1, color: '#62b7f4' // 100% 处的颜色
                            }],
                            globalCoord: false // 缺省为 false
                        },
                        borderWidth:0
                    },
                    lineStyle:{
                        color: '#ff6600'
                    },
                    label: {
                        normal: {
                            position: 'left',
                            verticalAlign: 'middle',
                            align: 'right',
                            fontSize: 12
                        }
                    },

                    leaves: {
                        label: {
                            normal: {
                                position: 'right',
                                verticalAlign: 'middle',
                                align: 'left'
                            }
                        }
                    },

                }
            ]
        };

        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }

    }

    function loadChart() {
        layer.load(2);
        var textData = [], personalData = [], avgData = [];
        var bizData = {
            idTestexecResult: idTestexecResult,
            idMedicalrec: idMedicalrec
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/eva/avg/score/select',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    $.each(data.data, function (i, item) {
                        avgData.push(item.avgScore);
                    })
                    $.each(parData, function (i, item) {
                        console.log("****************")
                        console.log(item)
                        console.log("****************")
                        textData.push({
                            text: item.pgItem,
                            max: item.weightScoreMax
                        });
                        personalData.push(item.weightScoreDimemsion);
                    })
                    console.log("=================")
                    console.log(textData)
                    console.log(personalData)
                    console.log(avgData)
                    console.log("=================")
                    showChart(textData, personalData, avgData);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    }

    function showChart(textData, personalData, avgData) {
        if (textData.length == 0) {
            return;
        }
        var dom = document.getElementById("container");
        var myChart = echarts.init(dom, 'macarons');
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                x: 'center',
                data: ['个人成绩', '团队成绩']
            },
            /*toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },*/
            calculable: true,
            polar: [
                {
                    indicator: textData,
                    radius: 200
                }
            ],
            series: [
                {
                    //name: '完全实况球员数据',
                    type: 'radar',
                    itemStyle: {
                        normal: {
                            areaStyle: {
                                type: 'default'
                            }
                        }
                    },
                    data: [
                        {
                            value: personalData,
                            name: '个人成绩'
                        },
                        {
                            value: avgData,
                            name: '团队成绩'
                        },

                    ]
                }
            ]
        };

        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    }

    
    function loadGuideContent() {
        var bizData = {
            idTestplanDetail: idTestplanDetail
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/eva/guide/content',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    if (data.data) {
                        $('#guideContent').val(data.data);
                    }
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    }

});


function updateScore(ele, idTestexecResultDimension) {
    if (event.keyCode == 13) {
        var scoreDimemsion = $('#dimension-' + idTestexecResultDimension).val();
        if (!scoreDimemsion.match(/(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/)) {
            layer.tips('数字或精确到小数点后2位', '#dimension-' + idTestexecResultDimension, {tips: 1});
            return;
        }
        var bizData = {
            idTestexecResultDimension: idTestexecResultDimension,
            scoreDimemsion: scoreDimemsion
        }
        $.ajax({
            url: basePath + '/pf/r/waiting/room/eva/edit',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    layer.tips('修改成功', '#dimension-' + idTestexecResultDimension, {tips: 1});
                    assessCase(idTestexecResultDimension)
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });

    }
}

function assessCase(idTestexecResultDimension) {
    layer.load(2);
    var bizData = {
        idTestexecResult: idTestexecResult
    };
    $.ajax({
        url: basePath + '/pf/r/waiting/room/med/eva',
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bizData),
        success: function (data) {
            layer.closeAll('loading');
            if (data.code != 0) {
                layer.msg(data.msg, {icon: 5});
                return false;
            } else {
                initData(idTestexecResultDimension);
                //layer.tips('评估完成', '#accessMed', {tips: 1});
                return true;
            }
        },
        error: function () {
            layer.closeAll('loading');
            return false;
        }
    });
}
function initData(idTestexecResultDimension) {
    var filterFlag = autoAssess == 1 ? 1 : 0;
    //layer.msg('正在加载数据，请稍后...', {icon: 16, shade: 0.01});
    var bizData = {
        idTestexecResult: idTestexecResult,
        filterFlag : filterFlag
    };
    $.ajax({
        url: basePath + '/pf/r/waiting/room/eva/list',
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bizData),
        success: function (data) {
            layer.closeAll('loading');
            if (data.code != 0) {
                layer.msg(data.msg, {icon: 5});
                return false;
            } else {
                // 加载评估得分
                appendScoreHtml(data.data)
                // 加载评估列表
                tableRender(data.data, idTestexecResultDimension);
                return true;
            }
            layer.closeAll('loading');
        },
        error: function () {
            layer.closeAll('loading');
            return false;
        }
    });
}
var parData = [];

function appendScoreHtml(data) {
    parData = [];
    $.each(data, function (i, item) {
        if (!item.parDimemsion) {
            parData.push(item)
        }
    });
    var titleHtml = '', scoreHtml = '';
    var totalScore = 0;
    if (parData.length == 0) {
        defaultHtml();
        return;
    }
    $.each(parData, function (i, item) {
        titleHtml += '<th>' + item.pgItem + '</th>';
        scoreHtml += '<td>' + item.scoreDimemsion + '</td>';
        if (i == 1) {
            titleHtml += '<th>临床思维评价</th>';
            scoreHtml += '<td id="pjResult"></td>';
        }
        totalScore += item.weightScoreDimemsion;
    });
    titleHtml += '<th>总得分</th>';
    scoreHtml += '<td>' + totalScore.toFixed(2) + '</td>';

    $('#scoreTitle').empty();
    $('#scoreTitle').append(titleHtml);
    $('#score').empty();
    $('#score').append(scoreHtml);

    console.log('==================')
}

function defaultHtml() {
    var titleHtml = '<th>诊断表现得分</th>\n' +
        '<th>临床思维得分</th>\n' +
        '<th>临床思维评价</th>\n' +
        '<th>医嘱得分</th>\n' +
        '<th>病例书写得分</th>\n' +
        '<th>知识掌握得分</th>\n' +
        '<th>总得分</th>';

    var scoreHtml = '<td></td>\n' +
        '<td></td>\n' +
        '<td></td>\n' +
        '<td></td>\n' +
        '<td></td>\n' +
        '<td></td>\n' +
        '<td></td>';

    $('#scoreTitle').empty();
    $('#scoreTitle').append(titleHtml);
    $('#score').empty();
    $('#score').append(scoreHtml);
}

function tableRender(list, idTestexecResultDimension) {
    if (list && list.length > 0) {
        list[0].nzName = 'pgResult';
    }
    layui.use('table', function () {
        layui.table.reload('demoTableId', {
            data: list
        });
        //console.log("%%%%%%%%%%%%%")
    });
    //console.log($('#dimension-' + idTestexecResultDimension))

    document.getElementById('dimension-'+idTestexecResultDimension).focus();
    //setTimeout("document.getElementById('dimension-" + idTestexecResultDimension + "').focus()", 1000);
}

function saveTips(idTestexecResultDimension) {
    layer.tips('修改后，按回车键保存', '#dimension-' + idTestexecResultDimension, {tips: 1});
}
