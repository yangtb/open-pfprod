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
            + '&idTestpaper=' + data.idTestpaper + '&idMedicalrec=' + data.idMedicalrec);
        $('#test').click();
    }

    $('#queryMed').on('click', function () {
        var bizData = {
            idTestplanDetail: idTestplanDetail,
            idDemo: idDemo,
            idTestplan: idTestplan,
            idStudent: idStudent,
            idTestpaper: idTestpaper,
            idMedicalrec: idMedicalrec
        }
        setAttr(bizData);
        return false;
    });

    $('#accessMed').on('click', function () {
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
    });


    $(document).ready(function () {
        initData();
    });

    function initData() {
        layer.msg('正在加载数据，请稍后...', {icon: 16, shade: 0.01});
        var bizData = {
            idTestexecResult: idTestexecResult
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
            scoreHtml += '<td>' + item.weightScoreDimemsion + '</td>';
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
            '<th>病历书写得分</th>\n' +
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
        evaLog();
        setPjResult();
    }

    function tableRender(list) {
        //展示已知数据
        table.render({
            elem: '#demo'
            , id: 'demoTableId'
            , cols: [[ //标题栏
                {field: 'pgItem', title: '评估项', width: 130}
                , {field: 'nzName', minWidth: 120, align: 'left'}
                , {field: 'weightDimemsion', title: '权重', width: 80, align: 'left'}
                , {field: 'scoreDimemsion', title: '得分', width: 80, align: 'left', templet: '#scoreDimemsionTpl'}
                , {field: 'weightScoreDimemsion', title: '加权分', width: 80, align: 'left'}
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
        var resultData = [];
        $.each(evaLogList, function (i, item) {
            if (obj.data.idTestexecResultDimension == item.idTestexecResultDimension) {
                resultData.push(item);
            }
        });

        table.reload('evaLogTableId', {
            data: resultData
        });
    });

    function setPjResult() {
        var bizData = {
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
                        textData.push({
                            text: item.pgItem,
                            max: item.weightScoreMax
                        });
                        personalData.push(item.weightScoreDimemsion);
                    })
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
                    layer.tips('修改成功，请刷新页面查看结果', '#dimension-' + idTestexecResultDimension, {tips: 1});
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

function saveTips(idTestexecResultDimension) {
    layer.tips('修改后，按回车键保存', '#dimension-' + idTestexecResultDimension, {tips: 1});
}
