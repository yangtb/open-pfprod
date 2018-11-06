layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['layer', 'table', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , common = layui.common;

    $(document).ready(function () {
        var bodyHeight = $(this).height() - 60;
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
        check: {
            enable: true
        },
        callback: {
            onClick: onClick,
            onCheck: onCheck
        }
    };

    var zTree;
    $(document).ready(function () {
        layer.load(2);

        var bizData = {
            extId: idTestplan
        };
        $.ajax({
            url: basePath + '/pf/r/test/plan/student/tree',
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
                    var zNodes = data.data;
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                    zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    count();
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });

    });

    function onClick(e, treeId, treeNode) {
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return true;
    };

    function onCheck(e, treeId, treeNode) {
        count();
    };


    function count() {
        //var checkCount = zTree.getCheckedNodes(true).length;
        //$("#checkCount").text(checkCount);
    };

    table.render({
        elem: '#questionTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'questionTableId'
        , height: 'full-48' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            {field: 'studentName', width: 160, title: '学生姓名', fixed: true},
            {field: 'phoneNo', width: 160, title: '联系方式', fixed: true},
            {field: 'planStatus', width: 120, title: '计划状态', templet: '#sdTestPlanTpl'},

            {fixed: 'right', width: 100, title: '操作', align: 'center', toolbar: '#question'}
        ]] //设置表头
        , url: basePath + '/pf/p/test/plan/student/list'
        , where: {
            idTestplan: idTestplan
        }
        , page: false
    });

    var _tableReload = function () {
        table.reload('questionTableId', {
            url: basePath + '/pf/p/test/plan/student/list'
            , where: {
                idTestplan: idTestplan
            }
            , height: 'full-48'
        });
    };

    $('#add').on('click', function () {
        var nodes = zTree.getCheckedNodes(true);
        if (nodes.length == 0) {
            layer.tips('请先在左侧选择病例', '#add', {tips: 1});
            return;
        }
        var data = {},
            reqData = new Array();
        for (var i = 0; i < nodes.length; i++) {
            var idStr = nodes[i].id;
            if (idStr.indexOf("-") != -1) {
                reqData.push(idStr.substring(idStr.indexOf("-") + 1, idStr.length));
            }
        }
        data.list = reqData;
        // 1.保存试卷关联病例id
        data.idTestplan = idTestplan;
        var url = basePath + '/pf/r/test/plan/student/add';
        common.commonPost(url, data, '添加', '', _callback);
    });

    function _callback() {
        // 2.reload table
        _tableReload();
    }

    //监听工具条
    table.on('tool(questionTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            var currentData = [];
            currentData.push(data)
            _delPaperItem(currentData);
        }
    });

    // 删除
    $('#batchDel').on('click', function () {
        var checkStatus = table.checkStatus('questionTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#batchDel', {tips: 1});
            return;
        }
        _delPaperItem(data);
    });

    var delList = new Array();
    var _delPaperItem = function (currentData) {
        var url = basePath + '/pf/r/test/plan/student/del';
        var reqData = new Array();
        var messageTitle = '';
        $.each(currentData, function (index, content) {
            if (messageTitle) {
                messageTitle += ', ';
            }
            messageTitle += '【' + content.studentName + '】';
            reqData.push(content.idTestplanStudent);
            delList.push(content.idStudent);
        });
        var data = {};
        data.list = reqData;
        layer.confirm('确定删除学生' + messageTitle + '么？', {
            title: '删除提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            common.commonPost(url, data, "删除", '', _delCallback);
        })
    };

    function _delCallback() {
        for (var i = 0; i < delList.length; i++) {
            var nodes = zTree.getNodesByParam("id", "user-" + delList[i], null);
            zTree.checkNode(nodes[0], false, false);
        }
        delList = [];
        count();
        _tableReload();
    };

    // 修改排序

    $('#refresh').on('click', function () {
        window.location.reload();
    });

});

