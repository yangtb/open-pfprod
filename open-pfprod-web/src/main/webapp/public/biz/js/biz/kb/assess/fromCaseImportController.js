layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['layer', 'table', 'form', 'jquery', 'treeSelect', 'common', 'element'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , element = layui.element
        , treeSelect = layui.treeSelect
        , common = layui.common;


    var consIdMedCase = '', checkIdMedCase = '', examIdMedCase = '';

    element.on('tab(tagTabFilter)', function (data) {
        if (data.index == 1) {
            selectIdMedCase('005');
        }
        if (data.index == 2) {
            selectIdMedCase('006');
        }
    });

    init();

    function init() {
        selectIdMedCase('004');
    }

    function selectIdMedCase(cdMedAsse) {
        // 查询idMedCase
        let medData = {
            idMedicalrec: idMedicalrec,
            cdMedAsse: cdMedAsse
        }
        $.ajax({
            url: basePath + '/pf/r/case/history/select/med/tag',
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
                        if (cdMedAsse == '004') {
                            consIdMedCase = data.data.idMedCase;
                            loadConsInfo();
                        } else if (cdMedAsse == '005') {
                            checkIdMedCase = data.data.idMedCase;
                            loadCheckInfo();
                        }  else if (cdMedAsse == '006') {
                            examIdMedCase = data.data.idMedCase;
                            loadExamInfo();
                        }
                    }

                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("查询失败");
                return false;
            }
        });
    }

    treeSelect.render({
        elem: '#idInquesCaSearch',
        data: basePath + '/pf/r/inquisition/question/classify/tree/select',
        type: 'post',
        placeholder: '请选择',
        search: true,
        click: function (d) {
            $("#idInquesCaSearch").val(d.current.id);
            reloadTable($('#consKeyword').val(), $('#idInquesCaSearch').val())
        }
    });

    function reloadTable(consKeyword, idInquesCa) {
        if (idInquesCa == "0") {
            idInquesCa = null;
        }
        table.reload('partConsTableId', {
            where: {
                keyword: consKeyword,
                idInquesCa : idInquesCa
            }, page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    }

    function loadConsInfo() {
        //执行渲染
        table.render({
            elem: '#partConsTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'partConsTableId'
            , height: 'full-120' //容器高度
            , size: 'sm'
            //, defaultToolbar: []
            , cols: [[
                {type: 'checkbox'},
                {field: 'desInques', minWidth: 200, title: '问题'},
                {field: 'desAnswer', minWidth: 180, title: '答案'},
            ]] //设置表头
            , url: basePath + '/pf/p/kb/part/cons/list?idMedCase=' + consIdMedCase
            , limit: 15
            , even: true
            , limits: [15, 30, 100]
            , page : true
        });
    }

    //监听提交
    form.on('submit(consQueryFilter)', function (data) {
        reloadTable(data.field.consKeyword, data.field.idInquesCa)
    });


    treeSelect.render({
        elem: '#idBodyCaSearch',
        data: basePath + '/pf/r/check/question/classify/tree/select',
        type: 'post',
        placeholder: '请选择',
        search: true,
        click: function (d) {
            $("#idBodyCaSearch").val(d.current.id);
            reloadCheckTable($('#checkKeyword').val(), $('#idBodyCaSearch').val())
        }
    });


    function reloadCheckTable(checkKeyword, idBodyCa) {
        if (idBodyCa == "0") {
            idBodyCa = null;
        }
        table.reload('partCheckTableId', {
            where: {
                keyword: checkKeyword,
                idBodyCa : idBodyCa
            }, page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    }

    //监听提交
    form.on('submit(checkQueryFilter)', function (data) {
        reloadCheckTable(data.field.checkKeyword)
    });

    $('#checkKeyword').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            reloadCheckTable($('#checkKeyword').val(), $('#idBodyCa').val())
            return false;
        }
    });

    function loadCheckInfo() {
        //执行渲染
        table.render({
            elem: '#partCheckTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'partCheckTableId'
            , height: 'full-120' //容器高度
            , size: 'sm'
            , cols: [[
                {type: 'checkbox'},
                {field: 'desBody', minWidth: 140, title: '部位描述'},
                {field: 'desResult', minWidth: 110, title: '检查结果'},
            ]] //设置表头
            , url: basePath + '/pf/p/kb/part/check/list?idMedCase=' + checkIdMedCase
            , limit: 15
            , even: true
            , limits: [15, 30, 100]
            , page:  true
        });
    }



    treeSelect.render({
        elem: '#idInspectSearch',
        data: basePath + '/pf/r/exam/question/classify/tree/select',
        type: 'post',
        placeholder: '请选择',
        search: true,
        click: function (d) {
            $("#idInspectSearch").val(d.current.id);
            reloadExamTable($('#examKeyword').val(), $('#idInspectSearch').val())
        }
    });

    function loadExamInfo() {
        //执行渲染
        table.render({
            elem: '#partExamTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'partExamTableId'
            , height: 'full-50' //容器高度
            , height: 'full-120' //容器高度
            , size: 'sm'
            , cols: [[
                {type: 'checkbox'},
                {field: 'naItem', minWidth: 140, title: '项目'},
                {field: 'valResult', minWidth: 110, title: '结果'},
            ]] //设置表头
            , url: basePath + '/pf/p/kb/part/exam/list?idMedCase=' + examIdMedCase
            , limit: 15
            , even: true
            , limits: [15, 30, 100]
            , page: true
        });
    }

    //监听提交
    form.on('submit(examQueryFilter)', function (data) {
        reloadExamTable(data.field.examKeyword, $('#idInspectSearch').val())
    });

    $('#examKeyword').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            reloadTable($('#examKeyword').val(), $('#idInspectSearch').val())
            return false;
        }
    });

    function reloadExamTable(examKeyword, idInspect) {
        if (idInspect == "0") {
            idInspect = null;
        }
        table.reload('partExamTableId', {
            where: {
                keyword: examKeyword,
                idInspect : idInspect
            }, page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    }


    //==============引入=====================
    $('#consImport').on('click', function () {
        let checkStatus = table.checkStatus('partConsTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        let names = new Array();
        $.each(data, function (index, item) {
            names.push(item.desInques);
        });
        fromCaseSave(1, names);
    });

    $('#checkImport').on('click', function () {
        let checkStatus = table.checkStatus('partCheckTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        let names = new Array();
        $.each(data, function (index, item) {
            names.push(item.desBody);
        });
        fromCaseSave(2, names);
    });

    $('#examImport').on('click', function () {
        let checkStatus = table.checkStatus('partExamTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        let names = new Array();
        $.each(data, function (index, item) {
            names.push(item.naItem);
        });
        fromCaseSave(3, names);
    });

    function fromCaseSave(sdEva, names) {
        let bizData = {
            module: module,
            idEvaCase: idEvaCase,
            cdEvaAsse: cdEvaAsse,
            tagFlag : tagFlag,
            sdEva : sdEva,
            names : names,
            fromCaseFlag : 1
        }

        if (tagFlag == '1') {
            bizData.caseName = caseName;
            bizData.idMedicalrec = idMedicalrec;
            bizData.idTag = idTag;
        }

        let url = basePath + '/pf/r/kb/assess/from/case/save';
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    layer.msg("引入成功");
                    _callBack(cdEvaAsse);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("查询失败");
                return false;
            }
        });

    }

    function _callBack(cdEvaAsse) {

        let medData = {
            idMedicalrec: idMedicalrec,
            cdEvaAsse: cdEvaAsse
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
                        let idEvaCase = data.data.idEvaCase;
                        parent.layui.table.reload('kbTableId', {
                            where: {
                                idEvaCase: idEvaCase,
                                cdEvaAsse: cdEvaAsse
                            }
                        });
                    }
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("查询失败");
                return false;
            }
        });
    }

});

