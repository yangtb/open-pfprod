layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common', 'tableSelect'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common
        , tableSelect = layui.tableSelect;

    //执行渲染
    table.render({
        elem: '#diseaseInfoTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'diseaseInfoTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {field: 'name', minWidth: 160, title: '疾病名称', fixed: true},
            {field: 'cdDieclassText', minWidth: 120, title: '疾病目录'},
            {field: 'icd', width: 80, title: 'ICD'},
            {field: 'qa', width: 100, title: '', fixed: 'right', align: 'center', templet: '#qaTpl'}
        ]] //设置表头
        , url: basePath + '/pf/p/disease/info/list'
        , limit: 25
        , page: {//支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            , groups: 1 //只显示 1 个连续页码
            , first: false //不显示首页
            , last: false //不显示尾页
            , limits: [25, 30, 100]
        }
    });

    //监听提交
    form.on('submit(queryFilter)', function (data) {
        if (!data.field.keywords) {
            table.reload('diseaseInfoTableId', {
                data: []
            });
            return false;
        }
        reloadDiseaseInfoTable(data.field.keywords);
    });

    $('#keywords').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            reloadDiseaseInfoTable($('#keywords').val());
            return false;
        }
    });

    function reloadDiseaseInfoTable(keywords) {
        table.reload('diseaseInfoTableId', {
            url: basePath + '/pf/p/disease/info/list'
            , where: {
                keywords: keywords
            }
            , height: 'full-68'
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    };

    //监听行双击事件
    table.on('rowDouble(diseaseInfoTableFilter)', function (obj) {
        var data = obj.data;
        var bizData = {
            idTestexecResult: idTestexecResult,
            idDie: data.idDie,
            idDieText: data.name
        }
        var y = $(this).offset().top;
        var x = $(this).offset().left;
        _addOrEdit("add", bizData, x, y);
    });

    //监听工具条
    table.on('tool(diseaseInfoTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'add') {
            var bizData = {
                idTestexecResult: idTestexecResult,
                idDie: data.idDie,
                idDieText: data.name
            }
            var y = $(this).offset().top;
            var x = $(this).offset().left;
            _addOrEdit("add", bizData, x, y);
        }
    });

    var _addOrEdit = function (formType, currentEditData, x, y) {
        if (formType == 'add') {
            var index = layui.layer.open({
                title: '<b>添加拟诊</b>',
                //offset: [y + 'px', x + 'px'],
                type: 2,
                area: ['650px', '450px'],
                fixed: false, //不固定
                maxmin: true,
                content: basePath + '/pf/p/waiting/room/test/referral/form?idTestexecResult=' + idTestexecResult,
                shadeClose: true,
                success: _successFunction(currentEditData)
            });
            return index;
        }
    };

    var _successFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    };

});

$(document).ready(function () {
    initReferralList();
});

function initReferralList() {
    layui.use('common', function () {
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/referral/list',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                if (data.code != 0) {
                    layui.common.errorMsg(data.msg);
                    return false;
                } else {
                    loadReferralList(data.data);
                    return true;
                }
            },
            error: function () {
                layui.common.errorMsg("保存拟诊失败");
                return false;
            }
        });
    });
};

function loadReferralList(data) {
    layui.use('form', function () {
        var result = '';
        for (var i = 0; i < data.length; i++) {
            result += referralForm(i, data[i]);
        }
        $('#referralList').empty();
        $('#referralList').append(result);
        for (var i = 0; i < data.length; i++) {
            layui.form.val("outForm" + i, data[i]);
        }
        layui.form.render();

        $.each(data, function (i, item) {
            renderNzTable(item.idTestexecResultReferral, item);
        });
    });
}

function referralForm(index, data) {
    if (!data.reasonOut) {
        data.reasonOut = '';
    }
    var html = '<fieldset class="layui-elem-field">\n' +
        '            <legend>\n' +
        '                <span>拟诊' + (index + 1) + '</span>\n' +
        '                <button id="outBtn' + data.idTestexecResultReferral + '" onclick="referralOut(' + data.idTestexecResultReferral + ')" class="layui-btn layui-btn-sm ';
    if (data.fgExclude == '1') {
        html += 'layui-btn-disabled" ';
    }
    html += 'id="referralOut' + index + '"\n';
    if (data.fgExclude == '1') {
        html += 'disabled';
    }
    html += '><i class="layui-icon layui-icon-delete"';
    if (data.fgExclude == '1') {
        html += 'style="color: red;"></i>';
    } else {
        html += '></i>';
    }

    if (data.fgExclude == '1') {
        html += '<span style="color: red; font-weight: bold;">已排除</span>\n';
    } else {
        html += '<span style="font-weight: bold;">排除</span>\n';
    }

    html += '                </button>\n' +
        '            </legend>\n' +
        '            <div class="layui-field-box">\n' +
        '                <form class="layui-form" id="outForm' + index + '">\n' +
        '                     <div>\n' +
        '                        <input name="idTestexecResultReferral" id="idTestexecResultReferral' + index + '" value="' + data.idTestexecResultReferral + '" hidden>\n' +
        '                    </div>' +
        '                    <div class="layui-form-item form-item-my5">\n' +
        '                        <label class="layui-form-label">疾病</label>\n' +
        '                        <div class="layui-input-block">\n' +
        '                            <input type="text" id="idDieText' + data.idTestexecResultReferral + '" lay-verify="required" lay-vertype="tips"\n' +
        '                                   autocomplete="off" class="layui-input"\n';
    if (data.fgExclude == '1') {
        html += 'style="text-decoration: line-through" ';
    }
    html += 'value="' + data.idDieText + '" disabled>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="layui-form-item form-item-my5">\n' +
        '                        <label class="layui-form-label">阶段</label>\n' +
        '                        <div class="layui-input-block">\n' +
        '                            <select name="sdEvaReferral" autocomplete="off"\n' +
        '                                    lay-vertype="tips" disabled>\n' +
        '                                <option value="">请选择</option>\n' +
        '                                <option value="1"';
    if (data.sdEvaReferral == 1) {
        html += 'selected';
    }
    html += '>问诊</option>\n' +
        '                                <option value="2"';
    if (data.sdEvaReferral == 2) {
        html += 'selected';
    }
    html += '>检查</option>\n' +
        '                                <option value="3"';
    if (data.sdEvaReferral == 3) {
        html += 'selected';
    }
    html += '>检验</option>\n' +
        '                            </select>\n' +
        '\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '\n' +
        '                    <div class="layui-form-item form-item-my5">\n' +
        '                        <label class="layui-form-label">加入原因</label>\n' +
        '                        <div class="layui-input-block">\n' +
        '                            <textarea name="reasonIn"\n' +
        '                                      class="layui-textarea" placeholder="请输入加入原因"\n' +
        '                                      style="min-height: 50px;" disabled>' + data.reasonIn + '</textarea>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '\n' +
        '                    <div class="layui-form-item form-item-my5">\n' +
        '                        <label class="layui-form-label">排除原因</label>\n' +
        '                        <div class="layui-input-block">\n' +
        '                    <textarea id="reasonOut' + data.idTestexecResultReferral + '" lay-verify="required" lay-vertype="tips"\n' +
        '                              class="layui-textarea" placeholder="请输入排除原因" style="min-height: 50px;"';
    if (data.fgExclude == '1') {
        html += 'disabled';
    }
    html += '>' + data.reasonOut + '</textarea>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '\n' +
        '                </form>\n' +
        '            </div>\n' +
        '        </fieldset>';
    return html;
}

function referralOut(id) {
    var reasonOut = $('#reasonOut' + id).val();
    if (!reasonOut) {
        layui.use('layer', function () {
            $('#reasonOut' + id).focus();
            layui.layer.tips('请输入排除原因', '#reasonOut' + id, {tips: 1});
        });
        return;
    }
    var url = basePath + '/pf/r/waiting/room/referral/out';
    var bizData = {
        idTestexecResultReferral: id,
        reasonOut: reasonOut
    }
    layui.use('common', function () {
        layui.common.commonPost(url, bizData, '排除', 'outBtn' + id, _outCallback);
    });
}

function _outCallback(data) {
    var id = data.data;
    $('#outBtn' + id).addClass("layui-btn-disabled");
    $('#outBtn' + id).attr("disabled", "disabled");
    $('#idDieText' + id).css("text-decoration", "line-through")
}

function renderNzTable(i, data) {
    layui.config({
        base: basePath + '/public/layui/build/js/'
    }).use(['table', 'jquery', 'tableSelect'], function () {
        var tableSelect = layui.tableSelect;
        tableSelect.render({
            elem: '#reasonOut' + i,
            checkedKey: 'reasonOutId' + i,
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
                , limit: 30
                , limits: [30, 50]
            },
            done: function (elem, data) {
                var NEWJSON = []
                layui.each(data.data, function (index, item) {
                    NEWJSON.push(item.idText)
                })
                elem.val(NEWJSON.join(","))
            }
        });
    });

}

