/**
 * 用户
 * @constructor
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['layer', 'form', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common;

    form.verify({
        commonLength: function (value) {
            if (value.length > 64) {
                return '长度不能超过64个字';
            }
        },
        sort: function (value) {
            if (value != null || value != '') {
                return;
            }
            if (!value.match(/^[1-9]\d*$/)) {
                $('#sort').focus();
                layer.tips('排序必须是正整数', '#sort', {tips: 1});
                return;
            }
        }
    });

    // ===============  ztree =========================//
    var setting = {
        view: {
            dblClickExpand: false,
            showLine: true
        },
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick,
            onCheck: onCheck
        }
    };

    var zNodes = [];
    $(document).ready(function () {
        layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/disease/catalogue/tree',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                layer.closeAll('loading');
                zNodes = data;
                $.fn.zTree.init($("#diseaseCatalogueTree"), setting, zNodes);
                //初始化模糊搜索方法
                fuzzySearch('diseaseCatalogueTree', '#key', null, true);
                return true;
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
        var bodyHeight = $(this).height() - $("#treeTitle").height() - 20;
        $("#treeDiv").css("min-height", bodyHeight);
        $("#treeDiv").css("max-height", bodyHeight);
    });

    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("diseaseCatalogueTree");
        zTree.expandNode(treeNode);
    };

    form.on('checkbox(allFilter)', function (data) {
        if (data.elem.checked) {
            $("#yes").attr('checked', false);
            $("#no").attr('checked', false);
            form.render('checkbox');
        }
    });
    form.on('checkbox(yesFilter)', function (data) {
        if (data.elem.checked) {
            $("#all").attr('checked', false);
            $("#no").attr('checked', false);
            form.render('checkbox');
        }
    });
    form.on('checkbox(noFilter)', function (data) {
        if (data.elem.checked) {
            $("#yes").attr('checked', false);
            $("#all").attr('checked', false);
            form.render('checkbox');
        }
    });

    // 左侧tree选中全局参数
    var idDieclass,
        cd,
        pId,
        idStr,
        name,
        selectedTreeNode;

    // tree query
    form.on('submit(diseaseSearchFilter)', function (data) {
        layer.load(2);

        var fgActive;
        if (data.field.all) {
            fgActive = '-1';
        }
        if (data.field.yes) {
            fgActive = '1';
        }
        if (data.field.no) {
            fgActive = '0';
        }
        var filterData = [];
        if (fgActive == '-1') {
            filterData = zNodes;
        } else {
            for (var i = 0; i < zNodes.length; i++) {
                if (fgActive == zNodes[i].fgActive) {
                    filterData.push(zNodes[i]);
                }
            }
        }
        $.fn.zTree.init($("#diseaseCatalogueTree"), setting, filterData);

        var treeObj = $.fn.zTree.getZTreeObj("diseaseCatalogueTree");
        var treenode = treeObj.getNodeByParam("id", cd, null);
        treeObj.expandNode(treenode, true, true, true);
        treeObj.selectNode(treenode);
        layer.closeAll('loading');
    });

    /**
     * 递归获取所有节点中值
     * @param treeNode
     * @param result
     * @returns {*}
     */
    function getAllChildrenNodes(treeNode, result) {
        if (treeNode.isParent) {
            var childrenNodes = treeNode.children;
            if (childrenNodes) {
                for (var i = 0; i < childrenNodes.length; i++) {
                    result += ',' + childrenNodes[i].idDieclass;
                    result = getAllChildrenNodes(childrenNodes[i], result);
                }
            }
        }
        return result;
    };

    // 选中tree填充表单
    function onCheck(e, treeId, treeNode) {
        //获取选中节点的值
        idStr = getAllChildrenNodes(treeNode, treeNode.idDieclass);
        selectedTreeNode = treeNode;

        var bizData = {};
        idDieclass = treeNode.idDieclass;
        cd = treeNode.id;
        pId = treeNode.pId;
        name = treeNode.name;
        bizData.idDieclass = idDieclass;
        layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/disease/catalogue/detail',
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
                    $("#diseaseCatalogueForm").autofill(data.data);
                    layui.use('form', function () {
                        layui.form.render();
                    });
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("获取疾病目录信息失败");
                return false;
            }
        });
    };


    $('#addCatalogue').on('click', function () {
        $('#reset').trigger('click');
        $('#save').trigger('click');
    });

    $('#addChildCatalogue').on('click', function () {
        $('#reset').trigger('click');
        if (!idDieclass) {
            layer.tips('请在下方点击圈圈选择上级目录', '#addChildCatalogue', {tips: 1});
            return;
        }
        $("#cdPar").val(cd);
        $('#save').trigger('click');
    });

    $('#delCatalogue').on('click', function () {
        if (!idDieclass) {
            layer.tips('请先选择要删除的疾病目录', '#delCatalogue', {tips: 1});
            return;
        }

        layer.confirm('您确定要删除疾病目录【' + name + '】及其子目录？', {
            title: '删除疾病目录提示',
            resize: false,
            btn: ['删除', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            var bizData = {};
            bizData.idStr = idStr;
            common.commonPost(basePath + '/pf/r/disease/catalogue/del', bizData, '删除目录');
            if (index) {
                layer.close(index);
            }

            // 重置node数据
            var refreshNodes = [];
            var idStrArr = '[' + idStr + ']'
            var idStrArrs = eval("(" + idStrArr + ")");
            for (var i = 0; i < zNodes.length; i++) {
                if ($.inArray(zNodes[i].idDieclass, idStrArrs) == -1) {
                    refreshNodes.push(zNodes[i]);
                }
            }
            zNodes = refreshNodes;
            $("#queryBtn").click();
        });
    });


    form.on('submit(addDiseaseCatalogue)', function (data) {
        var formType = data.field.idDieclass ? 'edit' : 'add';
        if (!data.field.fgActive) {
            data.field.fgActive = '0';
        }

        var refreshData = {};
        refreshData.idDieclass = data.field.idDieclass;
        refreshData.id = data.field.cd;
        refreshData.pId = data.field.cdPar;
        refreshData.name = data.field.name;
        refreshData.fgActive = data.field.fgActive;

        layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/disease/catalogue/save',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(data.field),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    if (!refreshData.idDieclass) {
                        refreshData.idDieclass = data.data.toString();
                    }
                    common.sucChildMsg("保存成功");

                    if (formType == 'edit') {
                        var refreshNodes = [];
                        for (var i = 0; i < zNodes.length; i++) {
                            if (cd == zNodes[i].id) {
                                refreshData.checked = true;
                                refreshNodes.push(refreshData);
                            } else {
                                zNodes[i].checked = false;
                                refreshNodes.push(zNodes[i]);
                            }
                        }
                        zNodes = refreshNodes;
                        $("#queryBtn").click();
                        return false;
                    } else {
                        var refreshNodes = [];
                        for (var i = 0; i < zNodes.length; i++) {
                            if (cd == zNodes[i].id) {
                                zNodes[i].checked = true;
                                zNodes[i].open = true;
                            } else {
                                zNodes[i].checked = false;
                            }
                            refreshNodes.push(zNodes[i]);
                        }
                        refreshNodes.push(refreshData);
                        zNodes = refreshNodes;
                        $("#queryBtn").click();
                        return false;
                    }
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("保存失败");
                return false;
            }
        });
        return false;
    });


    $("#addDiseaseInfo").on('click', function () {
        if (!$("#cd").val()) {
            layer.tips('请先在左侧点击圈圈选择疾病目录', '#addDiseaseInfo', {tips: 1});
            return false;
        }
        var currentEditData = {};
        currentEditData.cdDieclass = $("#cd").val();
        currentEditData.cdDieclassText = $("#name").val();
        common.open('新增疾病信息', basePath + '/pf/p/disease/info/form?formType=third', 512, 380, _successFunction(currentEditData));
        return false;
    });

    var _successFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    };

});

