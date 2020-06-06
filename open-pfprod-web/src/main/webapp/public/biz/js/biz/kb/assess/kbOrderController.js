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
            var formIdArr = new Array('add', 'save', 'itemName', 'scoreEva', 'sdEvaType');
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
            , height: '558' //容器高度
            , cols: [[
                {type: 'radio'},
                {field: 'sdEva', width: 90, title: '评估类别', templet: '#sdEvaTpl'},
                {field: 'itemName', minWidth: 90, title: '评估项'},
                {field: 'scoreEva', width: 60, title: '分值'},
                {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#kbBar'}
            ]] //设置表头
            , url: basePath + '/pf/p/kb/assess/order/list'
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

        var bizData = {
            idEvaCaseItem: obj.data.idEvaCaseItem,
            sdType: obj.data.sdEva
        }
        _tableSelectRender(obj.data.sdEva)
        _postReload(bizData);
    };

    var _postReload = function (bizData) {
        //layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/kb/assess/order/list',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                //layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    table.reload('answerTableId', {
                        data: data.data
                    });

                    console.log(bizData)
                    var sdType = bizData.sdType;
                    if (data.data && data.data.length > 0) {
                        var tsSelected = '',
                            num = data.data.length;
                        $.each(data.data, function (index, context) {
                            console.log(context)
                            if (sdType == '1' || sdType == '6') {
                                tsSelected += context.idShortDrugs;
                            } else if (sdType == '2') {
                                tsSelected += context.sdNursRout;
                            } else if (sdType == '3') {
                                tsSelected += context.cdNursLevel;
                            } else if (sdType == '4') {
                                tsSelected += context.sdDiet;
                            } else if (sdType == '5') {
                                tsSelected += context.sdPosition;
                            }

                            if (index < num - 1) {
                                tsSelected += ',';
                            }
                        })
                        $('#addAnswerBtn').attr('ts-selected', tsSelected);
                    }
                    return true;
                }
            },
            error: function () {
                //layer.closeAll('loading');
                return false;
            }
        });
    };

    //执行渲染
    table.render({
        elem: '#answerTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'answerTableId'
        , title: '等效答案'
        , height: '315' //容器高度
        , defaultToolbar: []
        , cols: [[
            {type: 'numbers'},
            {field: 'desText', minWidth: 90, title: '等效答案'},
            {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#answerBar'}
        ]] //设置表头
        //, url: basePath + '/pf/p/kb/assess/order/list'
        , where: {
            idEvaCase: idEvaCase
        }
        , page: false
        , data: []
    });

    //监听工具条
    table.on('tool(answerTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            var tableData = table.cache["answerTableId"];
            if (tableData.length == 1) {
                layer.tips("等效答案至少一个，请先添加后再删除", obj.othis);
                return false;
            }

            if (obj.data.idEvaCaseItemList != null) {
                layer.confirm('真的要删除【' + data.desText + '】么？', function (index) {
                    layer.close(index);
                    //向服务端发送删除指令
                    if (obj.data.idEvaCaseItemList != null) {
                        var url = basePath + '/pf/r/kb/assess/order/del';
                        var reqData = new Array();
                        reqData.push(obj.data.idEvaCaseItemList);
                        var data = {};
                        data.list = reqData;
                        data.status = '1';
                        common.commonPost(url, data, '删除');
                    }
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                });
            } else {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
            }

        }
    });

    $('#addAnswerBtn').on('click', function () {
        if (!$('#sdEvaType').val()) {
            layer.tips('请先选择评估类别', '#addAnswerBtn', {tips: 1});
            return false;
        }
    });

    form.on('select(sdEvaTypeSelectFilter)', function (data) {
        $('#addAnswerBtn').attr('ts-selected', '');
        table.reload('answerTableId', {
            data: []
        });

        _tableSelectRender(data.value);
    });

    function _tableSelectRender(type) {
        if (type == '1' || type == '6') {
            tableSelect.render({
                elem: '#addAnswerBtn',
                searchKey: 'keywords',
                checkedKey: 'idDrugs',
                searchPlaceholder: '药品名称/拼音码',
                table: {
                    url: basePath + '/pf/p/drug/info/list',
                    cols: [[
                        {type: 'checkbox'},
                        {field: 'name', title: '药品名称'},
                        {field: 'spec', title: '药品规格'},
                        {field: 'unit', title: '药品单位', width: 90}
                    ]]
                    , limits: [10, 20, 50]
                    , page: true
                },
                done: function (elem, data) {
                    tableSelectDone(elem, data, type);
                }
            });
        } else {
            var dicData = [];
            if (type == '2') {
                if (!sdNursRout) {
                    common.errorMsg("护理常规未配置");
                    return;
                }
                dicData = eval("(" + sdNursRout + ")");
            } else if (type == '3') {
                if (!sdNursRout) {
                    common.errorMsg("护理级别未配置");
                    return;
                }
                dicData = eval("(" + cdNursLevel + ")");
            } else if (type == '4') {
                if (!sdNursRout) {
                    common.errorMsg("饮食字典未配置");
                    return;
                }
                dicData = eval("(" + sdDiet + ")");
            } else if (type == '5') {
                if (!sdNursRout) {
                    common.errorMsg("体位字典未配置");
                    return;
                }
                dicData = eval("(" + sdPosition + ")");
            }
            tableSelect.render({
                elem: '#addAnswerBtn',
                //searchKey: 'keywords',
                checkedKey: 'dictCode',
                //searchPlaceholder: '请输入关键字',
                table: {
                    cols: [[
                        {type: 'checkbox'},
                        {field: 'dictName', title: '字典名称'}
                    ]]
                    , page: false
                    , data: dicData
                },
                done: function (elem, data) {
                    tableSelectDone(elem, data, type);
                }
            });
        }

    };

    function tableSelectDone(elem, data, type) {
        var dieList = data.data;
        var oldData = table.cache["answerTableId"];
        if (!oldData) {
            oldData = [];
        }
        for (var i = 0; i < dieList.length; i++) {
            var selectData = {};
            selectData.idEvaCaseItem = $('#idEvaCaseItem').val();
            selectData.sdEvaEffciency = $('#sdEvaType').val();
            if (type == '1') {
                selectData.idShortDrugs = dieList[i].idDrugs;
                selectData.desText = dieList[i].name;
            } else if (type == '2') {
                selectData.sdNursRout = dieList[i].dictCode;
                selectData.desText = dieList[i].dictName;
            } else if (type == '3') {
                selectData.cdNursLevel = dieList[i].dictCode;
                selectData.desText = dieList[i].dictName;
            } else if (type == '4') {
                selectData.sdDiet = dieList[i].dictCode;
                selectData.desText = dieList[i].dictName;
            } else if (type == '5') {
                selectData.sdPosition = dieList[i].dictCode;
                selectData.desText = dieList[i].dictName;
            } else if (type == '6') {
                selectData.idLongDrugs = dieList[i].idDrugs;
                selectData.desText = dieList[i].name;
            }
            if (oldData.length == 0) {
                oldData.push(selectData)
            } else {
                let flag = false;
                for (let j = 0; j < oldData.length; j++) {
                    if (type == '1') {
                        if (oldData[j].idShortDrugs && selectData.idShortDrugs == oldData[j].idShortDrugs) {
                            flag = true;
                            break;
                        }
                    } else if (type == '2') {
                        if (oldData[j].sdNursRout && selectData.sdNursRout == oldData[j].sdNursRout) {
                            flag = true;
                            break;
                        }
                    } else if (type == '3') {
                        if (oldData[j].cdNursLevel && selectData.cdNursLevel == oldData[j].cdNursLevel) {
                            flag = true;
                            break;
                        }
                    } else if (type == '4') {
                        if (oldData[j].sdDiet && selectData.sdDiet == oldData[j].sdDiet) {
                            flag = true;
                            break;
                        }
                    } else if (type == '5') {
                        if (oldData[j].sdPosition && selectData.sdPosition == oldData[j].sdPosition) {
                            flag = true;
                            break;
                        }
                    } else if (type == '6') {
                        if (oldData[j].idLongDrugs && selectData.idLongDrugs == oldData[j].idLongDrugs) {
                            flag = true;
                            break;
                        }
                    }
                }

                if (flag == false) {
                    oldData.push(selectData);
                }
            }
        }
        table.reload('answerTableId', {
            data: oldData
        });
    };

    //监听提交
    form.on('submit(saveAnswer)', function (data) {

        var tableData = table.cache["answerTableId"];
        if (tableData.length == 0) {
            layer.tips('请添加等效答案', '#addAnswerBtn', {tips: 1});
            return false;
        }

        data.field.idEvaCase = idEvaCase;
        data.field.cdEvaAsse = cdEvaAsse;
        data.field.list = tableData;

        data.field.tagFlag = tagFlag;
        if (tagFlag == '1') {
            data.field.caseName = caseName;
            data.field.idMedicalrec = idMedicalrec;
            data.field.idTag = idTag;
        }

        var url = basePath + '/pf/r/kb/assess/order/save';
        return common.commonPost(url, data.field, '保存', '', _callBack);
    });

    var _callBack = function (data) {
        if (idEvaCase) {
            _postReload({idEvaCaseItem: data.data, sdType: $('#sdEvaType').val()});
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
        table.reload('answerTableId', {
            data: []
        });
        $('#save').click();
        $('#addAnswerBtn').attr('ts-selected', '');
        $('#scoreEva').val(defaultScoreEva);
        $('#addAnswerBtn').attr('ts-selected', '');
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
                data.extType = 'order';
                common.commonPost(url, data, '删除');
                obj.del();
            });

        }
    });

    $('#saveAs').on('click', function () {
        layer.tips('重载成功', '#saveAs', {tips: 1});
    });


});

