layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common', 'tableSelect'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common
        , tableSelect = layui.tableSelect;

    initData();

    function initData() {
        layer.load(2);
        loadNz();
        layer.closeAll('loading');
    };

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

    $('#addNz').on('click', function () {
        if (!$('#idDie').val()) {
            layer.tips('请先选择拟诊', '#searchAnswer', {tips: 1});
            return false;
        }
        // 保存诊断
        saveReferral();
        return false;
    });

    function saveReferral() {
        var url = basePath + '/pf/r/waiting/room/referral/save';
        var bizData = {
            idTestexecResult: idTestexecResult,
            idDie: $('#idDie').val()
        }
        common.commonPost(url, bizData, '添加', 'addNz', saveReferralCallback)
    }

    function saveReferralCallback(data) {
        $('#idDie').val('');
        $('#idDieText').val('');
        loadNz();
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
                        refreshNz(data.data);
                    }
                }
            },
            error: function () {
                layui.common.errorMsg("获取拟诊失败");
                return false;
            }
        });
    }

    function refreshNz(dataList){
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
            renderZdSelect(i, item.idTestexecResultReferral);
            renderDelSelect(i, item.idTestexecResultReferral);
            renderInTable(i, item);
            renderOutTable(i, item);
        });
    }

    function zdHtml(i, data) {
        var html = '<fieldset class="layui-elem-field">\n' +
            '            <legend style="font-size: 16px;">拟诊' + (i + 1) + ' - <strong style="font-style: italic">' + data.idDieText +'</strong></legend>\n' +
            '            <div class="layui-field-box">\n' +
            '                <div class="layui-row layui-col-space5">\n' +
            '                    <div class="layui-col-xs6">\n' +
            '                        <button class="layui-btn layui-btn-xs';
            if(data.fgExclude == '1') {
                html +=' layui-btn-disabled" disabled';
            } else {
                html +='" ';
            }
            html +=' id="addReason' + i + '">\n' +
            '                            <i class="layui-icon layui-icon-add-1"></i>加入原因\n' +
            '                        </button>\n' +
            '                        <table id="inTable' + i + '" lay-filter="inTableFilter' + i + '">\n' +
            '                        </table>\n' +
            '                    </div>\n' +
            '                    <div class="layui-col-xs6">\n' +
            '                        <button class="layui-btn layui-btn-xs ' ;
            if(data.fgExclude == '1') {
                html +=' layui-btn-disabled" disabled style="color:red;" ';
            } else {
                html +='" ';
            }
            html +=  ' id="out' + i + '" ' +
            '       onclick="outNz(' + i + ', ' + data.idTestexecResultReferral + ', \'' + data.idDieText + '\')">\n' +
            '                            <i class="layui-icon layui-icon-delete"></i><span id="outBtnText' + i + '"';
            if(data.fgExclude == '1') {
                html +='>已排除';
            } else {
                html +='>排除';
            }
            html += '</span>\n' +
            '                        </button>\n' +
            '                        <button class="layui-btn layui-btn-xs " id="delReason' + i + '" >\n' +
            '                            <i class="layui-icon layui-icon-add-1"></i>排除原因\n' +
            '                        </button>\n' +
            '                        <table id="outTable' + i + '" lay-filter="outTableFilter' + i + '">\n' +
            '                        </table>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </fieldset>';
        return html;
    }

    function renderZdSelect(i, idTestexecResultReferral) {
        tableSelect.render({
            elem: '#addReason' + i,
            checkedKey: 'addReason' + i,
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
                , limit: 1000
                , limits: [200]
            },
            done: function (elem, data) {
                var selectData = data.data;
                saveDieReason(selectData, idTestexecResultReferral, i, '0');
            }
        });
    }

    function renderDelSelect(i, idTestexecResultReferral) {
        tableSelect.render({
            elem: '#delReason' + i,
            checkedKey: 'delReason' + i,
            table: {
                url: basePath + '/pf/p/waiting/room/test/die/ready/reason/list'
                , cols: [[
                    {type: 'checkbox'},
                    {field: 'idText', minWidth: 150, title: '排除原因'},
                    {field: 'sdEvaEffciency', width: 80, title: '阶段', templet: '#sdEvaTpl'}
                ]] //设置表头
                , where: {
                    idTestexecResult: idTestexecResult
                }
                , limit: 1000
                , limits: [200]
            },
            done: function (elem, data) {
                var selectData = data.data;
                saveDieReason(selectData, idTestexecResultReferral, i, '1');
            }
        });
    }

    function saveDieReason(data, idTestexecResultReferral, i, fgExclude) {
        var reqData = new Array();
        $.each(data, function (index, content) {
            var bizData = {
                idTestexecResultReferral: idTestexecResultReferral,
                fgExclude: fgExclude,
                sdEvaEffciency: content.sdEvaEffciency,
                idMedCaseList: content.id,
                extId: content.extId
            }
            reqData.push(bizData);
        });

        $.ajax({
            url: basePath + '/pf/r/waiting/room/referral/reason/save',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(reqData),
            success: function (data) {
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    saveDieReasonCallback(i, idTestexecResultReferral, fgExclude);
                }
            },
            error: function () {
                layui.common.errorMsg("保存失败");
                return false;
            }
        });
    }

    function saveDieReasonCallback(i, idTestexecResultReferral, fgExclude) {
        if (fgExclude == '0') {
            reloadInTable(i, idTestexecResultReferral);
        } else {
            reloadOutTable(i, idTestexecResultReferral);
        }

    }

    function reloadInTable(i, idTestexecResultReferral) {
        table.reload('inTableId' + i, {
            where: {
                idTestexecResultReferral: idTestexecResultReferral
            }
        });
    }

    function reloadOutTable(i, idTestexecResultReferral) {
        table.reload('outTableId' + i, {
            where: {
                idTestexecResultReferral: idTestexecResultReferral
            }
        });
    }


    function renderInTable(i, data) {
        //执行渲染
        table.render({
            elem: '#inTable' + i //指定原始表格元素选择器（推荐id选择器）
            , id: 'inTableId' + i
            , height: '200' //容器高度
            , defaultToolbar: []
            , cols: [[
                {type: 'numbers'},
                {field: 'idText', minWidth: 150, title: '加入原因'},
                {field: 'sdEvaEffciency', width: 80, title: '阶段', templet: '#sdEvaTpl'},
            ]] //设置表头
            , url: basePath + '/pf/p/waiting/room/referral/reason/in/list'
            , where: {
                idTestexecResultReferral: data.idTestexecResultReferral
            }
            , page: false
            , limit: 1000
        });
    }

    function renderOutTable(i, data) {
        //执行渲染
        table.render({
            elem: '#outTable' + i //指定原始表格元素选择器（推荐id选择器）
            , id: 'outTableId' + i
            , height: '200' //容器高度
            , defaultToolbar: []
            , cols: [[
                {type: 'numbers'},
                {field: 'idText', minWidth: 150, title: '排除原因'},
                {field: 'sdEvaEffciency', width: 80, title: '阶段', templet: '#sdEvaTpl'},
            ]] //设置表头
            , url: basePath + '/pf/p/waiting/room/referral/reason/out/list'
            , where: {
                idTestexecResultReferral: data.idTestexecResultReferral
            }
            , page: false
            , limit: 1000
        });
    }

})


function outNz(i, idTestexecResultReferral, idDieText) {
    layui.config({
        base: basePath + '/public/layui/build/js/'
    }).use(['layer', 'common'], function () {
        layui.layer.confirm('确定排除拟诊【' + idDieText + '】么？', {
            offset: [$('#out' + i).offset().top + 'px', $('#out' + i).offset().left + 'px'],
        }, function (index) {
            outNzPost(i, idTestexecResultReferral);
            layer.close(index);
        });

        function outNzPost(i, idTestexecResultReferral) {
            var bizData = {
                idTestexecResultReferral: idTestexecResultReferral
            };
            $.ajax({
                url: basePath + '/pf/r/waiting/room/referral/out',
                type: 'post',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(bizData),
                success: function (data) {
                    layer.closeAll('loading');
                    if (data.code != 0) {
                        layer.tips(data.msg, '#out' + i, {tips: 1});
                        return false;
                    } else {
                        layer.tips("排除成功", '#out' + i, {tips: 1});
                        outCallBack(i);
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

        function outCallBack(i) {
            $('#out' + i).addClass("layui-btn-disabled");
            $('#out' + i).attr("disabled", "disabled");
            $('#out' + i).css("color","red");
            $('#outBtnText' + i).text('已排除');

            $('#addReason' + i).addClass("layui-btn-disabled");
            $('#addReason' + i).attr("disabled", "disabled");
        }
    });
    return false;
}

