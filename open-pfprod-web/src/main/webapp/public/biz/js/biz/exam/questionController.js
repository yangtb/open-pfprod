layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['layer', 'table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common;

    if (!common.readLocalStorage('examQuestionTips')) {
        layer.msg('左侧区域鼠标点击右键可新增分类', {
            time: 20000, //20s后自动关闭
            btnAlign: 'c', //按钮居中
            btn: ['知道了']
        }, function () {
            common.writeLocalStorage("examQuestionTips", true);
        });
    }

    $(document).ready(function () {
        var bodyHeight = $(this).height() - 32;
        $("#treeDemo").css("min-height", bodyHeight);
        $("#treeDemo").css("max-height", bodyHeight);
    });

    //********************zTree***********************
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
            dblClickExpand: false
        },
        callback: {
            onRightClick: OnRightClick,
            onClick: zTreeOnClick,
            onRename: onRename
        }
    };

    function OnRightClick(event, treeId, treeNode) {
        if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
            zTree.cancelSelectedNode();
            showRMenu("root", event.clientX, event.clientY);
        } else if (treeNode && !treeNode.noR) {
            zTree.selectNode(treeNode);
            showRMenu("node", event.clientX, event.clientY);
        }
    }

    var v_idInspect;

    function zTreeOnClick(event, treeId, treeNode) {
        v_idInspect = treeNode.id
        _tableReload(v_idInspect);
    };

    function showRMenu(type, x, y) {
        $("#rMenu").show();
        if (type == "root") {
            $("#m_del").hide();
            $("#m_edit").hide();
        } else {
            $("#m_del").show();
            $("#m_edit").show();
        }

        y += document.body.scrollTop;
        x += document.body.scrollLeft;
        rMenu.css({"top": y + "px", "left": x + "px", "visibility": "visible"});

        $("body").bind("mousedown", onBodyMouseDown);
    }

    function hideRMenu() {
        if (rMenu) rMenu.css({"visibility": "hidden"});
        $("body").unbind("mousedown", onBodyMouseDown);
    }

    function onBodyMouseDown(event) {
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
            rMenu.css({"visibility": "hidden"});
        }
    }

    function addTreeNode(name) {
        hideRMenu();
        var newNode = {name: name};
        var selectedNode = zTree.getSelectedNodes()[0];

        // ajax add
        layer.load(2);
        var url = basePath + '/pf/r/exam/question/classify/add';
        var bizData = {};
        bizData.idPar = selectedNode ? selectedNode.id : null;
        bizData.name = name;
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
                    newNode.id = data.data;
                    if (selectedNode) {
                        newNode.pId = bizData.idPar;
                        newNode.checked = selectedNode.checked;
                        zTree.addNodes(selectedNode, newNode);
                    } else {
                        zTree.addNodes(null, newNode);
                    }
                    common.sucChildMsg("添加成功");
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("添加失败");
                return false;
            }
        });
    }

    var oldValue, newValue;

    function editTreeNode() {
        hideRMenu();
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getSelectedNodes(),
            treeNode = nodes[0];
        if (nodes.length == 0) {
            alert("请先选择一个节点");
            return;
        }
        oldValue = treeNode.name;
        zTree.editName(treeNode);
    }

    function onRename(e, treeId, treeNode, isCancel) {
        newValue = treeNode.name;
        if (newValue == oldValue) {
            return false;
        }
        var url = basePath + '/pf/r/exam/question/classify/edit';
        var bizData = {};
        bizData.idInspect = treeNode.id;
        bizData.name = treeNode.name;
        return common.commonPost(url, bizData, "修改");
    }

    function removeTreeNode() {
        hideRMenu();
        var nodes = zTree.getSelectedNodes();
        if (nodes && nodes.length > 0) {
            var msg;
            if (nodes[0].children && nodes[0].children.length > 0) {
                msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。请确认！";
                /*if (confirm(msg) == true) {
                    zTree.removeNode(nodes[0]);
                }*/
            } else {
                msg = "您确定要删除该节点吗？";
                //zTree.removeNode(nodes[0])
            }
            layer.confirm(msg, {
                title: '删除问诊分类提示',
                resize: false,
                btn: ['确定', '取消'],
                btnAlign: 'c',
                icon: 3
            }, function (index) {
                var url = basePath + '/pf/r/exam/question/classify/del';
                var bizData = {};
                var idStrArr = '[' + getAllChildrenNodes(nodes[0], nodes[0].id) + ']'
                var idStrArrs = eval("(" + idStrArr + ")");
                bizData.list = idStrArrs;
                layer.load(2);
                $.ajax({
                    url: url,
                    type: 'post',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(bizData),
                    success: function (data) {
                        layer.closeAll();
                        if (data.code != 0) {
                            common.errorMsg(data.msg);
                            return false;
                        } else {
                            zTree.removeNode(nodes[0]);
                            common.sucMsg("删除成功");
                            return true;
                        }
                    },
                    error: function () {
                        layer.closeAll('loading');
                        common.errorMsg("删除失败");
                        return false;
                    }
                });
                return false;
            })
        }
    }

    function getAllChildrenNodes(treeNode, result) {
        if (treeNode.isParent) {
            var childrenNodes = treeNode.children;
            if (childrenNodes) {
                for (var i = 0; i < childrenNodes.length; i++) {
                    result += ',' + childrenNodes[i].id;
                    result = getAllChildrenNodes(childrenNodes[i], result);
                }
            }
        }
        return result;
    };

    var zTree, rMenu;
    $(document).ready(function () {
        layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/exam/question/classify/tree',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    var zNodes = [
                        {id: "0", name: "分类", open: true}
                    ];
                    zNodes = data.data;
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                    zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    rMenu = $("#rMenu");
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });

    });

    $("#m_add").on('click', function () {
        layer.prompt({
                title: "添加分类",
                offset: 'l'
            },
            function (val, index) {
                addTreeNode(val)
                layer.close(index);
            }
        );
    });

    $("#m_edit").on('click', function () {
        editTreeNode()
    });

    $("#m_del").on('click', function () {
        removeTreeNode()
    });

    //执行渲染
    table.render({
        elem: '#questionTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'questionTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            {field: 'fgActive', width: 100, title: '状态', templet: '#fgActiveTpl'},
            {field: 'naItem', width: 160, title: '项目'},
            {field: 'naShort', width: 120, title: '缩写'},
            {field: 'desStand', width: 140, title: '参考范围'},
            {field: 'gmtCreate', width: 170, sort: true, title: '创建时间'},
            {field: 'operator', width: 100, title: '创建人'},
            {fixed: 'right', width: 180, title: '操作', align: 'center', toolbar: '#question'}
        ]] //设置表头
        //, url: basePath + '/pf/p/exam/question/list'
        , limit: 15
        //, even: true
        , limits: [15, 30, 100]
        , page: true
    });

    var _tableReload = function (idInspect, naItem) {
        table.reload('questionTableId', {
            url: basePath + '/pf/p/exam/question/list'
            , where: {
                idInspect: idInspect,
                naItem: naItem
            }
            , height: 'full-68'
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    };


    $('#add').on('click', function () {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getSelectedNodes(),
            treeNode = nodes[0];
        if (nodes.length == 0) {
            layer.tips('请先在左侧选择分类', '#add', {tips: 1});
            return;
        }
        var bizData = {};
        bizData.idInspect = treeNode.id;
        bizData.idInspectText = treeNode.name;
        _addOrEdit("add", bizData);
    });

    $('#edit').on('click', function () {
        var checkStatus = table.checkStatus('questionTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#edit', {tips: 1});
            return;
        }
        if (data.length > 1) {
            layer.tips('请选中一行记录进行编辑', '#edit', {tips: 1});
            return;
        }
        var currentEditData = data[0];
        _addOrEdit("edit", currentEditData);
    });

    var _addOrEdit = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增项目', basePath + '/pf/p/exam/question/form?formType=' + formType, 360, 432, _successFunction(currentEditData));
        } else {
            common.open('编辑项目', basePath + '/pf/p/exam/question/form?formType=' + formType, 360, 432, _successFunction(currentEditData));
        }
    };

    var _successFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    };

    $("#del").on('click', function () {
        var checkStatus = table.checkStatus('questionTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#del', {tips: 1});
            return;
        }
        _delQuestion(data);
    });

    var _delQuestion = function (currentData) {
        var url = basePath + '/pf/r/exam/question/del';
        var reqData = new Array();
        var messageTitle = '';
        $.each(currentData, function (index, content) {
            if (messageTitle) {
                messageTitle += ', ';
            }
            messageTitle += '【' + content.naItem + '】';
            reqData.push(content.idInspectItem);
        });
        var data = {};
        data.list = reqData;
        data.status = '1';
        layer.confirm('确定删除项目' + messageTitle + '么？', {
            title: '删除提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            layer.load(2);
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function (data) {
                    layer.closeAll('loading');
                    if (data.code != 0) {
                        common.errorMsg(data.msg);
                        return false;
                    } else {
                        common.sucChildMsg("删除成功");
                        if (index) {
                            layer.close(index);
                        }
                        _tableReload(currentData[0].idInspectItemCa);
                        return true;
                    }
                },
                error: function () {
                    layer.closeAll('loading');
                    common.errorMsg("删除失败");
                    return false;
                }
            });
        })
    };

    //监听行双击事件
    table.on('rowDouble(questionTableFilter)', function (obj) {
        _addOrEdit("edit", obj.data);
    });

    //监听工具条
    table.on('tool(questionTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        }
        if (obj.event === 'editAnswer') {
            _editAnswer(data.naItem, data.idInspectItem);
        }
    });

    $('#editAnswer').on('click', function () {
        var checkStatus = table.checkStatus('questionTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#editAnswer', {tips: 1});
            return;
        }
        _editAnswer(data[0].naItem, data[0].idInspectItem);
    });

    var _editAnswer = function (title, id) {
        var index = common.open('编辑项目 - ' + '<span style="color: red">' + title + '</span>',
            basePath + '/pf/p/exam/question/answer/form?idInspectItem=' + id, 900, 460);
        layer.full(index);
    };

    //监听提交
    form.on('submit(questionSearchFilter)', function (data) {
        _tableReload(v_idInspect, data.field.naItem);
    });

});

