layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'layer', 'element', 'tableSelect', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common
        , tableSelect = layui.tableSelect;

    $(document).ready(function () {
        if (previewFlag == '1') {
            var formIdArr = new Array('add', 'save', 'itemName', 'scoreEva', 'sdEvaType', 'quaUpper');
            common.setFormStatus('0', formIdArr);
            layui.form.render('select');
        }
    });

    init();

    function init() {
        if (tagFlag == '1') {
            // 查询idMedCase
            var medData = {
                idMedicalrec: idMedicalrec,
                idTag: idTag
            }
            $.ajax({
                url: basePath + '/pf/r/case/history/select/eva/tag',
                type: 'post',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(medData),
                success: function (data) {
                    layer.closeAll('loading');
                    if (data.code != 0) {
                        common.errorMsg(data.msg);
                        return false;
                    } else {
                        if (data.data) {
                            idEvaCase = data.data.idEvaCase;
                        }
                        loadInfo()
                        return true;
                    }
                },
                error: function () {
                    layer.closeAll('loading');
                    common.errorMsg("查询失败");
                    return false;
                }
            });
        } else {
            loadInfo();
        }
    };

    function loadInfo() {
        //执行渲染
        table.render({
            elem: '#kbTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'kbTableId'
            , height: '340' //容器高度
            , cols: [[
                {type: 'radio'},
                {field: 'sdEva', width: 90, title: '评估阶段', templet: '#sdEvaTpl'},
                {field: 'itemName', minWidth: 90, title: '评估项'},
                {field: 'scoreEva', width: 60, title: '分值'},
                {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#kbBar'}
            ]] //设置表头
            , url: basePath + '/pf/p/kb/assess/effciency/list'
            , where: {
                idEvaCase: idEvaCase,
                cdEvaAsse: cdEvaAsse
            }
            , page: false
        });
    }


    form.verify({
        desAnswer: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        },
        scoreEva: function (value) {
            if (value < scoreLower || value > scoreUpper) {
                return '分值值域[' + scoreLower + ',' + scoreUpper + ']';
            }
        }
    });

    //单击行选中radio
    table.on('row(kbTableFilter)', function (obj) {
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');//选中行样式
        obj.tr.find('input[lay-type="layTableRadio"]').prop("checked", true);
        form.render('radio');
        rowClick(obj)
    });

    function rowClick(obj) {
        $('#reset').click();
        $("#kbForm").autofill(obj.data);
        form.render();
    };

    //监听提交
    form.on('submit(saveAnswer)', function (data) {

        data.field.idEvaCase = idEvaCase;
        data.field.cdEvaAsse = cdEvaAsse;
        data.field.sdEvaEffciency = data.field.sdEva;

        data.field.tagFlag = tagFlag;
        if (tagFlag == '1') {
            data.field.caseName = caseName;
            data.field.idMedicalrec = idMedicalrec;
            data.field.idTag = idTag;
        }

        var url = basePath + '/pf/r/kb/assess/effciency/save';
        return common.commonPost(url, data.field, '保存', '', _callBack);
    });

    var _callBack = function (data) {
        if (idEvaCase) {
            _kbTableReload();
            $('#idEvaCaseItem').val(data.data);
        } else {
            window.location.reload();
        }
    }


    var _kbTableReload = function () {
        table.reload('kbTableId', {
            where: {
                idEvaCase: idEvaCase,
                cdEvaAsse: cdEvaAsse
            }
        });
    };

    $('#add').on('click', function () {
        $('#reset').click();
        $('#save').click();
        $('#scoreEva').val(defaultScoreEva);
    });

    //监听工具条
    table.on('tool(kbTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {

            layer.confirm('真的要删除评估项【' + data.itemName + '】么？', function (index) {
                var url = basePath + '/pf/r/kb/assess/common/del';
                var reqData = new Array();
                reqData.push(obj.data.idEvaCaseItem);
                var data = {};
                data.list = reqData;
                data.status = '1';
                data.extType = 'effciency';
                common.commonPost(url, data, '删除');
                obj.del();
            });

        }
    });


    $('#saveAs').on('click', function () {
        layer.tips('重载成功', '#saveAs', {tips: 1});
    });


});

