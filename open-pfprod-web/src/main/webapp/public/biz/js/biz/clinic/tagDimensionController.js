layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'jquery', 'slider', 'common'], function () {
    var $ = layui.$
        , form = layui.form
        , common = layui.common
        , slider = layui.slider;

    //默认滑块
    var ins1 = slider.render({
        elem: '#weightSlider'
        , input: true //输入框
        , change: function (value) {
            $('#weight').val(value);//动态获取滑块数值
        }
    });

    form.on('radio(fgSystemAlgorithmFilter)', function (data) {
        displayForm(data.value);
    });

    function displayForm(value) {
        if (value == 1) {
            $("#sysAlgorithm").css("display", "block");
            $("#subjective1").css("display", "none");
            $("#subjective2").css("display", "none");
        } else {
            $("#sysAlgorithm").css("display", "none");
            $("#subjective1").css("display", "block");
            $("#subjective2").css("display", "block");
        }
    };

    $(document).ready(function () {
        var bodyHeight = $(this).height() - 25;
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
            addDiyDom: addDiyDom
        },
        callback: {
            onClick: zTreeOnClick
        }
    };

    function addDiyDom(treeId, treeNode) {
        var aObj = $("#" + treeNode.tId + "_a");
        var editStr = "<span id='diyWeight_" + treeNode.id + "' style='color: #009688'>  " + treeNode.ext + "</span>";
        aObj.after(editStr);
    };

    var zTree;
    $(document).ready(function () {
        layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/clinic/template/tag/dimension/classify/tree',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    var zNodes = data.data;
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                    zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });

    });

    var parentName,
        parentId,
        currentNodeId,
        currentNodeName,
        addType;

    function zTreeOnClick(event, treeId, treeNode) {
        var pNode = treeNode.getParentNode();
        parentName = pNode != null ? pNode.name : null;
        parentId = pNode != null ? pNode.id : null;
        currentNodeId = treeNode.id;
        currentNodeName = treeNode.name;
        var bizData = {};
        bizData.idDimemsion = treeNode.id;
        layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/clinic/select/tag/dimensionInfo',
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
                    $('#reset').click();
                    displayForm(data.data.fgSystemAlgorithm);
                    data.data.parDimemsionText = parentName;
                    $("#tagForm").autofill(data.data);
                    layui.use('form', function () {
                        layui.form.render();
                    });
                    //改变指定滑块实例的数值
                    ins1.setValue(data.data.weight);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("获取评估维度信息失败");
                return false;
            }
        });
    };

    function addTreeNode(id, pId, addType) {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var node = treeObj.getNodeByParam("id", id, null);
        if (node) {
            editTreeNode($('#name').val(), $('#weight').val());
            return;
        }
        var newNode = {
            id: id,
            name: $('#name').val(),
            ext: $('#weight').val()
        };
        var selectedNode = zTree.getSelectedNodes()[0];
        if (selectedNode && addType == 'child') {
            newNode.pId = pId;
            newNode.checked = selectedNode.checked;
            zTree.addNodes(selectedNode, newNode);
        } else {
            zTree.addNodes(null, newNode);
        }
    }

    function editTreeNode(name, ext) {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = treeObj.getSelectedNodes(),
            treeNode = nodes[0];
        if (nodes.length == 0) {
            alert("请先选择一个节点");
            return;
        }
        treeNode.name = name;
        treeNode.ext = ext;
        treeObj.updateNode(treeNode);
        $('#diyWeight_' + treeNode.id).html(ext);
    }

    function removeTreeNode() {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getSelectedNodes();
        treeObj.removeNode(nodes[0]);
        if (nodes && nodes.length > 0) {
            treeObj.removeChildNodes(nodes[0]);
        }
    }

    form.verify({
        name: function (value) {
            if (value.length > 64) {
                return '长度不能超过64个字';
            }
        },
        sort: [/^[1-9]\d*$/, '排序必须是正整数'],
        // 页签嵌入，则嵌入代码必填
        idAlgorithm: function (value) {
            var val = $('input:radio[name="fgSystemAlgorithm"]:checked').val();
            if (val == 1 && !value) {
                return '必填项不能为空';
            }
        },
        score: function (value) {
            var val = $('input:radio[name="fgSystemAlgorithm"]:checked').val();
            if (val == 2) {
                if (!value) {
                    return '必填项不能为空';
                }
                if (!value.match(/^(\-|\+?)\d+(\.\d+)?$/)) {
                    return '分值必须是数字';
                }
            }
        }
    });

    $('#addParent').on('click', function () {
        resetFrom();
        addType = 'parent';
        $('#parDimemsion').val('');
        $('#parDimemsionText').val('');
    });

    $('#addChild').on('click', function () {
        resetFrom();
        addType = 'child';
        $('#parDimemsion').val(currentNodeId);
        $('#parDimemsionText').val(currentNodeName);
    });

    function resetFrom() {
        $('#reset').click();
        $('#save').click();
        displayForm(1);
        ins1.setValue(0);
    }

    form.on('submit(saveTag)', function (data) {
        if (!data.field.fgActive) {
            data.field.fgActive = '0';
        }
        data.field.idDemo = idDemo;
        common.commonPost(basePath + '/pf/r/clinic/template/tag/dimension/save', data.field, '保存', '', _callBack);
        return false;
    });

    var _callBack = function (data) {
        $('#idDimemsion').val(data.data);
        addTreeNode(data.data, currentNodeId, addType);
    };

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

    $('#del').on('click', function () {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = treeObj.getSelectedNodes();
        if (!nodes[0]) {
            layer.tips('请在左侧选择要删除的目录', '#del', {tips: 1});
            return;
        }
        var treeNode = nodes[0];

        var idStr = getAllChildrenNodes(treeNode, treeNode.id);
        var idStrArr = '[' + idStr + ']'
        var idStrArrs = eval("(" + idStrArr + ")");

        _delDimemsion(idStrArrs, treeNode.name, treeNode.isParent);
    });

    var _delDimemsion = function (ids, name, isParent) {
        var url = basePath + '/pf/r/clinic/template/tag/dimension/del';
        var data = {};
        data.list = ids;
        data.status = '1';
        var msg;
        if (isParent == true) {
            msg = '您确定要删除药品目录【' + name + '】及其子目录？';
        } else {
            msg = '真的要删除评估维度：【' + name + '】么？';
        }

        layer.confirm(msg, {
            title: '删除评估维度提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            common.commonPost(url, data, '删除', '', null);
            removeTreeNode();
            $('#reset').click();
            layer.closeAll(index);
        })
    };


});

