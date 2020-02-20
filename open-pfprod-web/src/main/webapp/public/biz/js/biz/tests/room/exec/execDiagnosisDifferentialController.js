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
        loadJbzdList();
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
        elem: '#queryBtn',
        checkedKey: 'naDie',
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
            let selectData = data.data[0];
            //$('#idDie').val(selectData.idDie);
            $('#naDie').val(selectData.name);
        }
    });

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
            let selectData = data.data;
            let oldData = table.cache["outTableId"];
            if (!oldData) {

            }

            $.each(selectData, function (index, item) {
                let newData = {}
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


    $('#addIdentify').on('click', function () {
        $('#idTestexecResultIdentify').val('');
        $('#naDie').val('');
        $('#desDieReason').val('');
        $('#naDie').focus();
        table.reload('outTableId', {
            data: []
        });
    });

    // 保存诊断
    $('#saveIdentify').on('click', function () {
        if (!$('#naDie').val()) {
            $('#naDie').focus();
            layer.tips('请输入鉴别诊断', '#naDie', {tips: 1});
            return;
        }
        layer.load(2);
        let url = basePath + '/pf/r/waiting/room/summary/diagnosis/identify/save';
        let bizData = {
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
        loadJbzdList();
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
                            let fgExcludeHtml = '';
                            if (data.fgExclude == '1') {
                                fgExcludeHtml = '<span style="text-decoration: line-through; color: #FF5722;">' + data.name + '</span>';
                            } else {
                                fgExcludeHtml = data.name;
                            }
                            $node.html('<button class="layui-btn layui-bg-blue" style="height: 50px;">' + fgExcludeHtml + '</button>');
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

        registerOutJbzdEvent();
        registerUpdateJbzdEvent();

        reasonTabelRender(dataList);
    }

    function jbzdHtml(i, data) {

        let newHtml = '         <div id="jbzd-'+ data.idTestexecResultIdentify +'" class="layui-row referral">\n' +
            '                        <div style="margin: 15px 0 0 5px">\n' +
            '                            <span class="disc"></span>\n' +
            '                            <span class="diagnose-title">鉴别诊断'+(i + 1)+'：'+ data.naDie +' <span style="color: red; font-size: 20px;"><i class="iconfont icon-zhu"></i></span></span>\n' +
            '                            <span>\n' +
            '                                    <button data-content-id="'+ data.idTestexecResultIdentify +'" data-content="'+ data.naDie +'" style="float: right; margin-right: 10px;" class="layui-btn layui-btn-xs layui-btn-danger delCbzdBtn">\n' +
            '                                       <i class="layui-icon layui-icon-delete"></i><span>删除</span>\n' +
            '                                    </button>\n' +
            '                                    <button data-content-id="'+ data.idTestexecResultIdentify +'" data-content="'+ data.naDie +'" style="float: right; margin-right: 10px;" class="layui-btn layui-btn-xs updateCbzdBtn">\n' +
            '                                       <i class="layui-icon layui-icon-edit"></i><span>修改</span>\n' +
            '                                    </button>\n' +
            '                                </span>\n' +
            '                        </div>\n' +
            '                        <div style="margin: 5px 0 0px 5px">\n' +
            '                            <span class="disc"></span>\n' +
            '                            诊断说明：<span class="diagnose-title" id="desDieReason'+ data.idTestexecResultIdentify +'">'+ data.desDieReason +'</span>\n' +
            '                        </div>\n' +
            '                        <div style="margin: 5px 0 0px 5px">\n' +
            '                            <span class="disc"></span>\n' +
            '                            <span class="diagnose-title">排除理由：</span>\n' +
            '                        </div>\n' +
            '                        <div style="padding: 2px">\n' +
            '                            <table id="outTable' + data.idTestexecResultIdentify + '" lay-filter="outTableFilter' + data.idTestexecResultIdentify + '">\n' +
            '                            </table>\n' +
            '                        </div>\n' +
            '                    </div>';
        return newHtml;
    }


    function reasonTabelRender(dataList) {
        $.each(dataList, function (i, item) {
            //执行渲染
            table.render({
                elem: '#outTable' + item.idTestexecResultIdentify
                , id: 'outTableId' + item.idTestexecResultIdentify
                , height: '200' //容器高度
                , defaultToolbar: []
                , cols: [[
                    {type: 'numbers'},
                    {field: 'idText', minWidth: 130, title: '诊断原因'},
                    {field: 'sdEvaEffciency', width: 80, title: '阶段', templet: '#sdEvaTpl'},
                ]] //设置表头
                //, url: basePath + '/pf/p/waiting/room/referral/reason/out/list'
                , data : []
                , page: false
                , limit: 1000
            });

            loadJbzdReason(item.idTestexecResultIdentify, true);
        });

    }

    function registerOutJbzdEvent() {
        let outCbzdBtns = document.querySelectorAll(".delCbzdBtn");
        for (let i = 0; i < outCbzdBtns.length; i++) {
            outCbzdBtns[i].addEventListener('click', function () {
                let idTestexecResultIdentify = this.getAttribute("data-content-id");
                let idDieText = this.getAttribute("data-content");

                layui.layer.confirm('确定删除鉴别诊断【' + idDieText + '】么？', {
                    shade: 0
                }, function (index) {
                    delJbzdPost(idTestexecResultIdentify);
                    layer.close(index);
                });
            });
        }
    }

    function registerUpdateJbzdEvent() {
        let updateCbzdBtns = document.querySelectorAll(".updateCbzdBtn");
        for (let i = 0; i < updateCbzdBtns.length; i++) {
            updateCbzdBtns[i].addEventListener('click', function () {
                let idTestexecResultIdentify = this.getAttribute("data-content-id");
                let idDieText = this.getAttribute("data-content");

                $('#idTestexecResultIdentify').val(idTestexecResultIdentify);
                $('#naDie').val(idDieText);
                $('#desDieReason').val($('#desDieReason'+ idTestexecResultIdentify).text());
                $('#naDie').focus();
                
                // 加载诊断原因
                loadJbzdReason(idTestexecResultIdentify, false);
            });
        }
    }
    
    function loadJbzdReason(idTestexecResultIdentify, flag) {
        let bizData = {
            idTestexecResultIdentify: idTestexecResultIdentify
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/identify/reason/list',
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
                    if (flag) {
                        table.reload('outTableId' + idTestexecResultIdentify, {
                            data: data.data
                        });
                    } else {
                        table.reload('outTableId', {
                            data: data.data
                        });
                    }

                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                layui.common.errorMsg("查询失败");
                return false;
            }
        });
    }

    function delJbzdPost(idTestexecResultIdentify) {
        let bizData = {
            idTestexecResultIdentify: idTestexecResultIdentify
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/identify/del',
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
                    $('#jbzd-' + idTestexecResultIdentify).remove();
                    $('#addIdentify').trigger('click');
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                layui.common.errorMsg("查询失败");
                return false;
            }
        });
    }



})
