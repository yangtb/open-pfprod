layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'jquery', 'table', 'tableSelect', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , tableSelect = layui.tableSelect
        , common = layui.common;

    initData();

    function initData() {
        layer.load(2);
        // 加载小结信息
        loadSummary();
        // 排除理由
        loadIdentifyReason()
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

    function loadIdentifyReason() {
        //执行渲染
        table.render({
            elem: '#outTable'
            , id: 'outTableId'
            , height: '200' //容器高度
            , defaultToolbar: []
            , cols: [[
                {type: 'numbers'},
                {field: 'idText', minWidth: 150, title: '诊断原因'},
                {field: 'sdEvaEffciency', width: 80, title: '阶段', templet: '#sdEvaTpl'},
            ]] //设置表头
            //, url: basePath + '/pf/p/waiting/room/referral/reason/out/list'
            , data : []
            , page: false
            , limit: 1000
        });
    }

    tableSelect.render({
        elem: '#addReason',
        checkedKey: 'id',
        table: {
            url: basePath + '/pf/p/waiting/room/test/die/ready/reason/list'
            , cols: [[
                {type: 'checkbox'},
                {field: 'idText', minWidth: 150, title: '加入原因'},
                {field: 'sdEvaEffciency', width: 80, title: '阶段', templet: '#sdEvaTpl'}
            ]] //设置表头
            , where: {
                idTestexecResult: idTestexecResult
            }
            , height: '350'
            , limit: 1000
            , limits: [200]
        },
        done: function (elem, data) {
            var selectData = data.data;
            var oldData = table.cache["outTableId"];
            if (!oldData) {

            }

            $.each(selectData, function (index, item) {
                var newData = {}
                newData.idMedCaseList = item.id;
                newData.sdEvaEffciency = item.sdEvaEffciency;
                newData.extId = item.extId;
                newData.idText = item.idText;
                oldData.push(newData)
            });
            table.reload('outTableId', {
                data: oldData
            });
        }
    });

    // 保存诊断
    $('#saveIdentify').on('click', function () {
        if (!$('#naDie').val()) {
            $('#naDie').focus();
            layer.tips('请输入鉴别诊断', '#naDie');
            return;
        }
        layer.load(2);
        var url = basePath + '/pf/r/waiting/room/summary/diagnosis/identify/save';
        var bizData = {
            idTestexecResultIdentify : $('#idTestexecResultIdentify').val(),
            idTestexecResult: idTestexecResult,
            naDie: $('#naDie').val(),
            desDieReason : $('#desDieReason').val(),
            reasonList : table.cache["outTableId"]
        }
        common.commonPost(url, bizData, '保存', null, callbackSaveIdentify);
    });

    function callbackSaveIdentify(data) {
        $('#idTestexecResultIdentify').val(data.data);

    }

    // ============ 思维导图 begin ============

    $(function(){

        var bizData = {
            idTestexecResult: idTestexecResult,
            chartType : 3
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
                    console.log(data)
                    if (data.type ) {
                        if (data.type == 1) {
                            $node.html('<button class="layui-btn layui-btn-radius">' + data.name + '</button>');
                        } else if (data.type == 2) {
                            $node.html('<button class="layui-btn layui-bg-blue">' + data.name + '</button>');
                        } else {
                            $node.html('<button class="layui-btn">' + data.name + '</button>');
                        }
                    } else {
                        $node.html('<button class="layui-btn">' + data.name + '</button>');
                    }
                }
            });
        }

    });

    // ============ 思维导图 end ==============

})
