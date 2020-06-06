layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'layer', 'element', 'tableSelect', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common
        , tableSelect = layui.tableSelect;

    init();

    $(document).ready(function () {
        if (previewFlag == '1') {
            var formIdArr = new Array('add', 'save', 'itemName', 'scoreEva', 'sdEvaType');
            common.setFormStatus('0', formIdArr);
            layui.form.render('select');
        }
    });

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
                /*{field: 'sdEva', width: 90, title: '评估阶段', templet: '#sdEvaTpl'},*/
                {field: 'itemName', minWidth: 90, title: '评估项'},
                {field: 'scoreEva', width: 60, title: '分值'},
                {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#kbBar'}
            ]] //设置表头
            , url: basePath + '/pf/p/kb/assess/referral/list'
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

    function rowClick (obj) {
        $('#reset').click();
        $("#kbForm").autofill(obj.data);
        form.render();

        var bizData = {idEvaCaseItem: obj.data.idEvaCaseItem}
        _postReload(bizData);
    };

    var _postReload = function (bizData) {
        //layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/kb/assess/referral/list',
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
        , height: '327' //容器高度
        //, toolbar: '#toolbarDemo'
        , defaultToolbar: []
        , cols: [[
            {type: 'numbers'},
            {field: 'idDieText', minWidth: 90, title: '等效答案'},
            {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#answerBar'}
        ]] //设置表头
        //, url: basePath + '/pf/p/kb/assess/referral/list'
        , where: {
            idEvaCase: idEvaCase
        }
        , page: false
        , data: []
    });

    /*table.on('toolbar(answerTableFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        if (layEvent === 'addAnswer') { //查看
            var sdEva = $('#sdEvaType').val();
            var idEvaCaseItem = $('#idEvaCaseItem').val();

            if (!sdEva) {
                common.toastTop('请先选择评估阶段')
                return;
            }
            common.open('添加等效答案', basePath + '/pf/p/kb/assess/referral/answer/page?idEvaCaseItem=' + idEvaCaseItem + '&sdEva=' + sdEva, 600, 350);
        }
    });*/

    //监听工具条
    table.on('tool(answerTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            var tableData = table.cache["answerTableId"];
            if (tableData.length == 1) {
                layer.tips("等效答案至少一个，请先添加后再删除", obj.othis);
                return false;
            }
            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
            if (obj.data.idEvaCaseItemList != null) {
                layer.confirm('真的要删除【' + data.idDieText + '】么？', function (index) {
                    layer.close(index);
                    //向服务端发送删除指令
                    if (obj.data.idEvaCaseItemList != null) {
                        var url = basePath + '/pf/r/kb/assess/referral/del';
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

    /*tableSelect.render({
        elem: '#addAnswerBtn',
        searchKey: 'keywords',
        checkedKey: 'idDie',
        searchPlaceholder: '疾病名称/ICD/拼音码',
        table: {
            url: basePath + '/pf/p/disease/info/list',
            cols: [[
                {type: 'checkbox'},
                {field: 'name', title: '疾病名称'},
                {field: 'cdDieclassText', title: '疾病目录'},
                {field: 'icd', title: 'ICD编码'},
            ]]
            , limits: [10, 20, 50]
            , page: true
        },
        done: function (elem, data) {
            var dieList = data.data;
            var oldData = table.cache["answerTableId"];
            if (!oldData) {
                //oldData = [];
            }
            for (var i = 0; i < dieList.length; i++) {
                var selectData = {};
                selectData.idEvaCaseItem = $('#idEvaCaseItem').val();
                selectData.sdEvaReferral = $('#sdEvaType').val();
                selectData.idDie = dieList[i].idDie;
                selectData.idDieText = dieList[i].name;
                oldData.push(selectData)
            }
            table.reload('answerTableId', {
                data: oldData
            });
        }
    });*/


    var setting = {
        check: {
            enable: true
            , chkStyle: 'checkbox'
            , chkboxType:{ "Y": "", "N": "" }
        },

        data: {
            simpleData: {
                enable: true
            }
        } ,
        callback: {
            onCheck: zTreeOnCheck
        }
    };

    function zTreeOnCheck(event, treeId, treeNode) {
        console.log(treeNode)

        var oldData = table.cache["answerTableId"];
        if (!oldData) {
            //oldData = [];
        }

        var fgDieClass = treeNode.idDieclass ? '1' : '2' ,
            idDie = treeNode.idDieclass ? treeNode.idDieclass : treeNode.idDie,
            name = treeNode.name;
        var selectData = {
            idEvaCaseItem : $('#idEvaCaseItem').val(),
            sdEvaReferral : $('#sdEvaType').val(),
            idDie : idDie,
            idDieText : name,
            fgDieClass : fgDieClass
        };

        if (treeNode.checked) {
            oldData.push(selectData);
            table.reload('answerTableId', {
                data: oldData
            });
        } else {
            var oldDataDel = new Array();
            $.each(oldData, function (index, context) {
                if (context.fgDieClass == selectData.fgDieClass && context.idDie == selectData.idDie ) {

                } else {
                    oldDataDel.push(context);
                }
            })
            table.reload('answerTableId', {
                data: oldDataDel
            });
        }
    };



    $('#addAnswerBtn').on('click', function () {
        popSelectDie();
    });

    function popSelectDie() {
        var elem = $('#addAnswerBtn');
        var t = elem.offset().top + elem.outerHeight() - 162 + "px";
        var l = (elem.offset().left - 270) + "px";

        var html = '<div id="div-select-die" class="layui-anim layui-anim-upbit" style="left:' + l + ';top:' + t + ';border: 1px solid #d2d2d2;background-color: #fff;box-shadow: 0 2px 4px rgba(0,0,0,.12);padding:10px 10px 0 0px;position: absolute;z-index:666;margin: 5px 0;border-radius: 2px;width:250px; height: 545px;">' +
            '           <div id="treeDiv" style="overflow:auto;">\n' +
            '               <ul id="roleTree" class="ztree"></ul>\n' +
            '           </div>' +
            ' </div>';

        var formBox = $(html);
        $('body').append(formBox);

        var reqData = {
            includeDie : 1
        }
        $.ajax({
            url: basePath + '/pf/r/disease/catalogue/tree',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(reqData),
            success: function (data) {
                var zNodes = data;
                $.fn.zTree.init($("#roleTree"), setting, zNodes);
                return true;
            },
            error: function () {
                return false;
            }
        });
        $("#treeDiv").css("max-height", 545);

    }

    //点击其他区域关闭
    $(document).mouseup(function (e) {
        var userSet_con = $('#div-select-die');
        if (!userSet_con.is(e.target) && userSet_con.has(e.target).length === 0) {
            $('#div-select-die').remove();
        }
    });

    //监听提交
    form.on('submit(saveAnswer)', function (data) {

        var tableData = table.cache["answerTableId"];
        if (tableData.length == 0) {
            layer.tips('请添加等效答案', '#addAnswerBtn', {tips: 1});
            return false;
        }

        $.each(tableData, function (index, item) {
            item.fgCrs = data.field.fgCrs ? data.field.fgCrs : '0'
        });

        data.field.sdEva = 1;
        data.field.idEvaCase = idEvaCase;
        data.field.cdEvaAsse = cdEvaAsse;
        data.field.list = tableData;

        data.field.tagFlag = tagFlag;
        if (tagFlag == '1') {
            data.field.caseName = caseName;
            data.field.idMedicalrec = idMedicalrec;
            data.field.idTag = idTag;
        }

        var url = basePath + '/pf/r/kb/assess/referral/save';
        return common.commonPost(url, data.field, '保存', '', _callBack);
    });

    var _callBack = function (data) {
        if (idEvaCase) {
            _postReload({idEvaCaseItem: data.data});
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
                data.extType = 'referral';
                common.commonPost(url, data, '删除');
                obj.del();
            });

        }
    });



    $('#saveAs').on('click', function () {
        layer.tips('重载成功', '#saveAs', {tips: 1});
    });





});

