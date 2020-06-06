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
            if (($("#idBodyCaSearch").nextAll('.layui-treeSelect').length) == 0) {
                treeSelect.render({
                    elem: '#idBodyCaSearch',
                    data: basePath + '/pf/r/check/question/classify/tree/select',
                    type: 'post',
                    placeholder: '请选择',
                    search: true,
                    click: function (d) {
                        $("#idBodyCaSearch").val(d.current.id);
                        reloadCheckTable($('#checkKeyword').val(), $('#idBodyCaSearch').val())
                    },
                    // 加载完成后的回调函数
                    success: function (d) {
                    }
                });
            }

            selectIdMedCase('005');
        }
        if (data.index == 2) {
            if (($("#idInspectSearch").nextAll('.layui-treeSelect').length) == 0) {
                treeSelect.render({
                    elem: '#idInspectSearch',
                    data: basePath + '/pf/r/exam/question/classify/tree/select',
                    type: 'post',
                    placeholder: '请选择',
                    search: true,
                    click: function (d) {
                        $("#idInspectSearch").val(d.current.id);
                        reloadExamTable($('#examKeyword').val(), $('#idInspectSearch').val())
                    },
                    // 加载完成后的回调函数
                    success: function (d) {
                        console.log("tree------")
//                选中节点，根据id筛选
//                treeSelect.checkNode('tree', 3);

//                获取zTree对象，可以调用zTree方法
//                var treeObj = treeSelect.zTree('tree');
//                console.log(treeObj);

//                刷新树结构
                        //treeSelect.refresh();
                    }
                });
            }

            selectIdMedCase('006');
        }
    });

    $(document).ready(function () {
        treeSelect.render({
            elem: '#idInquesCaSearch',
            data: basePath + '/pf/r/inquisition/question/classify/tree/select',
            type: 'post',
            placeholder: '请选择',
            search: true,
            click: function (d) {
                $("#idInquesCaSearch").val(d.current.id);
                reloadTable($('#consKeyword').val(), $('#idInquesCaSearch').val())
            },
            // 加载完成后的回调函数
            success: function (d) {
                console.log("tree------")
            }
        });




        init();
    });


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
                {field: 'desInques', minWidth: 150, title: '问题'},
                {field: 'desAnswer', minWidth: 130, title: '答案'},
            ]] //设置表头
            , url: basePath + '/pf/p/kb/part/cons/list?idMedCase=' + consIdMedCase
            , limit: 15
            , even: true
            , limits: [15, 30, 100]
            , page : true
        });

        table.render({
            elem: '#tempPartConsTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'tempPartConsTableId'
            , height: 'full-120' //容器高度
            , size: 'sm'
            //, defaultToolbar: []
            , cols: [[
                {type: 'numbers'},
                {field: 'desInques', minWidth: 150, title: '问题'},
                {fixed: 'right', width:70, align:'center', toolbar: '#barDemo'}
            ]] //设置表头
            , even: true
            , page : false
            , limit: 500
            , data : []
        });
    }

    table.on('checkbox(partConsTableFilter)', function(obj){
        let checkFlag = obj.checked;
        // console.log(obj.checked); //当前是否选中状态
        // console.log(obj.data); //选中行的相关数据
        // console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
        let oldData = table.cache["tempPartConsTableId"];
        if (!oldData) {
            oldData = [];
        }
        if (obj.type == 'one') {
            let selectData = {
                idMedCaseList : obj.data.idMedCaseList,
                idInques : obj.data.idInques,
                desInques : obj.data.desInques
            };
            if (checkFlag) {
                // add
                oldData.push(selectData);
            } else {
                // del
                let index = oldData.map(function (item) { return item.idMedCaseList; }).indexOf(selectData.idMedCaseList);
                console.log(index);
                if (index >= 0) {
                    oldData.splice(index, 1);
                }
            }
        } else {
            let checkStatus = table.checkStatus('partConsTableId')
                , selectDataList = checkStatus.data;
            if (checkFlag) {
                // add
                $.each(selectDataList, function (index, item) {
                    let selectData = {
                        idMedCaseList : item.idMedCaseList,
                        idInques : item.idMedCaseList,
                        desInques : item.desInques
                    };
                    oldData.push(selectData);
                })
            } else {
                let selectDataList = table.cache["partConsTableId"];
                // del
                $.each(selectDataList, function (index, item) {
                    let indexItem = oldData.map(function (item1) { return item1.idMedCaseList; }).indexOf(item.idMedCaseList);
                    console.log(indexItem);
                    if (indexItem >= 0) {
                        oldData.splice(indexItem, 1);
                    }
                })
            }
        }
        oldData = uniq(oldData);
        table.reload('tempPartConsTableId', {
            data: oldData
        });
    });

    table.on('tool(tempPartConsTableFilter)', function (obj) {
        if (obj.event === 'del') {
            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
        }
    });

    function uniq(array) {
        //console.log(array);
        let result = [];
        let obj = {};
        for (let i = 0; i < array.length; i++) {
            //console.log(array[i])
            if(array[i] && array[i] != null && array[i].idMedCaseList) {
                if (!obj[array[i].idMedCaseList]) {
                    result.push(array[i]);
                    obj[array[i].idMedCaseList] = true;
                }
            }
        }
        //console.log("---------------");
        //console.log(result);
        return result;
    }

    //监听提交
    form.on('submit(consQueryFilter)', function (data) {
        reloadTable(data.field.consKeyword, data.field.idInquesCa)
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

        table.render({
            elem: '#tempPartCheckTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'tempPartCheckTableId'
            , height: 'full-120' //容器高度
            , size: 'sm'
            //, defaultToolbar: []
            , cols: [[
                {type: 'numbers'},
                {field: 'desBody', minWidth: 140, title: '部位描述'},
                {fixed: 'right', width:70, align:'center', toolbar: '#barDemo'}
            ]] //设置表头
            , even: true
            , page : false
            , limit: 500
            , data : []
        });
    }

    function loadExamInfo() {
        //执行渲染
        table.render({
            elem: '#partExamTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'partExamTableId'
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

        table.render({
            elem: '#tempPartExamTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'tempPartExamTableId'
            , height: 'full-120' //容器高度
            , size: 'sm'
            , cols: [[
                {type: 'checkbox'},
                {field: 'naItem', minWidth: 140, title: '项目'},
                {fixed: 'right', width:70, align:'center', toolbar: '#barDemo'}
            ]] //设置表头
            , even: true
            , page : false
            , limit: 500
            , data : []
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

    table.on('checkbox(partCheckTableFilter)', function(obj){
        let checkFlag = obj.checked;
        // console.log(obj.checked); //当前是否选中状态
        // console.log(obj.data); //选中行的相关数据
        // console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
        let oldData = table.cache["tempPartCheckTableId"];
        if (!oldData) {
            oldData = [];
        }
        if (obj.type == 'one') {
            let selectData = {
                idMedCaseList : obj.data.idMedCaseList,
                idBody : obj.data.idBody,
                desBody : obj.data.desBody
            };
            if (checkFlag) {
                // add
                oldData.push(selectData);
            } else {
                // del
                let index = oldData.map(function (item) { return item.idMedCaseList; }).indexOf(selectData.idMedCaseList);
                //console.log(index);
                if (index >= 0) {
                    oldData.splice(index, 1);
                }
            }
        } else {
            let checkStatus = table.checkStatus('partCheckTableId')
                , selectDataList = checkStatus.data;
            if (checkFlag) {
                // add
                $.each(selectDataList, function (index, item) {
                    let selectData = {
                        idMedCaseList : item.idMedCaseList,
                        idBody : item.idBody,
                        desBody : item.desBody
                    };
                    oldData.push(selectData);
                })
            } else {
                let selectDataList = table.cache["partCheckTableId"];
                // del
                $.each(selectDataList, function (index, item) {
                    let indexItem = oldData.map(function (item1) { return item1.idMedCaseList; }).indexOf(item.idMedCaseList);
                    //console.log(indexItem);
                    oldData.splice(indexItem, 1);
                })
            }
        }
        oldData = uniq(oldData);
        table.reload('tempPartCheckTableId', {
            data: oldData
        });
    });

    table.on('tool(tempPartCheckTableFilter)', function (obj) {
        if (obj.event === 'del') {
            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
        }
    });

    table.on('checkbox(partExamTableFilter)', function(obj){
        let checkFlag = obj.checked;
        let oldData = table.cache["tempPartExamTableId"];
        if (!oldData) {
            oldData = [];
        }
        if (obj.type == 'one') {
            let selectData = {
                idMedCaseList : obj.data.idMedCaseList,
                idInspectItem : obj.data.idInspectItem,
                naItem : obj.data.naItem
            };
            if (checkFlag) {
                // add
                oldData.push(selectData);
            } else {
                // del
                let index = oldData.map(function (item) { return item.idMedCaseList; }).indexOf(selectData.idMedCaseList);
                if (index >= 0) {
                    oldData.splice(index, 1);
                }
            }
        } else {
            let checkStatus = table.checkStatus('partExamTableId')
                , selectDataList = checkStatus.data;
            if (checkFlag) {
                // add
                $.each(selectDataList, function (index, item) {
                    let selectData = {
                        idMedCaseList : item.idMedCaseList,
                        idInspectItem : item.idInspectItem,
                        naItem : item.naItem
                    };
                    oldData.push(selectData);
                })
            } else {
                let selectDataList = table.cache["partExamTableId"];
                // del
                $.each(selectDataList, function (index, item) {
                    let indexItem = oldData.map(function (item1) { return item1.idMedCaseList; }).indexOf(item.idMedCaseList);
                    if (indexItem >= 0) {
                        oldData.splice(indexItem, 1);
                    }
                })
            }
        }
        oldData = uniq(oldData);
        table.reload('tempPartExamTableId', {
            data: oldData
        });
    });

    table.on('tool(tempPartExamTableFilter)', function (obj) {
        if (obj.event === 'del') {
            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
        }
    });


    //==============引入=====================
    $('#consImport').on('click', function () {
        let data = table.cache["tempPartConsTableId"];
        //console.log(data)
        if (data.length == 0) {
            common.toastTop("请先选择要引入的问题");
            return;
        }
        let names = new Array();
        $.each(data, function (index, item) {
            if (item && item.idInques) {
                let bizItemData = {
                    id : item.idInques,
                    name : item.desInques
                }
                names.push(bizItemData);
            }
        });
        //console.log(names)
        if (names.length == 0) {
            return;
        }
        fromCaseSave(1, names);
    });

    $('#checkImport').on('click', function () {
        let data = table.cache["tempPartCheckTableId"];
        //console.log(data)
        if (data.length == 0) {
            common.toastTop("请先选择要引入的问题");
            return;
        }
        let names = new Array();
        $.each(data, function (index, item) {
            if (item && item.idBody) {
                let bizItemData = {
                    id : item.idBody,
                    name : item.desBody
                }
                names.push(bizItemData);
            }
        });
        fromCaseSave(2, names);
    });

    $('#examImport').on('click', function () {
        let data = table.cache["tempPartExamTableId"];
        //console.log(data)
        if (data.length == 0) {
            common.toastTop("请先选择要引入的问题");
            return;
        }
        let names = new Array();
        $.each(data, function (index, item) {
            if (item && item.idInspectItem) {
                let bizItemData = {
                    id : item.idInspectItem,
                    name : item.naItem
                }
                names.push(bizItemData);
            }
        });
        fromCaseSave(3, names);
    });

    function fromCaseSave(sdEva, names) {
        let bizData = {
            module: module,
            idEvaCase: idEvaCase,
            cdEvaAsse: cdEvaAsse,
            tagFlag: tagFlag,
            sdEva: sdEva,
            names: names,
            fromCaseFlag: 1
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

