layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['layer', 'table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common;

    $(document).ready(function () {
        var bodyHeight = $(this).height() - 80;
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
            extId: idTestpaper
        };
        $.ajax({
            url: basePath + '/pf/r/tests/paper/tree',
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
        var checkCount = zTree.getCheckedNodes(true).length;
        $("#checkCount").text(checkCount);
    };

    table.render({
        elem: '#questionTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'questionTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            {field: 'name', width: 160, title: '病例', fixed: true},
            {field: 'sdLevel', width: 100, title: '病例级别', templet: "#sdLevelTpl"},
            {
                field: 'sort',
                width: 120,
                title: '排序(<span style="color: red;font-weight: bold">可编辑</span>)',
                sort: true,
                edit: 'text',
                style: 'text-align:right'
            },
            {field: 'sdUse', width: 100, title: '病例用途', templet: "#sdUseTpl"},
            {field: 'orgName', width: 170, title: '归属机构'},
            {
                field: 'count', width: 100, title: '使用次数',
                style: 'background-color: #5FB878; color: #fff;text-align:right'
            },
            {field: 'gmtCreate', width: 170, sort: true, title: '病例创建时间'},
            {field: 'creator', width: 100, title: '病例创建人'},
            {fixed: 'right', width: 100, title: '操作', align: 'center', toolbar: '#question'}
        ]] //设置表头
        , url: basePath + '/pf/p/tests/paper/item/list'
        , where: {
            idTestpaper: idTestpaper
        }
        , page: false
    });

    var _tableReload = function () {
        table.reload('questionTableId', {
            url: basePath + '/pf/p/tests/paper/item/list'
            , where: {
                idTestpaper: idTestpaper
            }
            , height: 'full-68'
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
            reqData.push(idStr.substring(idStr.indexOf("-") + 1, idStr.length));
        }
        data.list = reqData;
        // 1.保存试卷关联病例id
        data.idTestpaper = idTestpaper;
        var url = basePath + '/pf/r/tests/paper/item/add';
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

    //监听单元格编辑
    table.on('edit(questionTableFilter)', function (obj) {
        var value = obj.value //得到修改后的值
            , data = obj.data //得到所在行所有键值
            , field = obj.field; //得到字段

        if (!value) {
            layer.msg('排序字段不能为空');
            return;
        }
        if (!value.match(/^[1-9]\d*$/)) {
            layer.msg('排序必须是正整数');
            return;
        }
        var bizData = {
            idTestpaperMedicalrec: data.idTestpaperMedicalrec,
            sort: value
        };
        var url = basePath + '/pf/r/tests/paper/item/update/sort';
        common.commonPost(url, bizData, '修改排序', '', '');
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
        var url = basePath + '/pf/r/tests/paper/item/del';
        var reqData = new Array();
        var messageTitle = '';
        $.each(currentData, function (index, content) {
            if (messageTitle) {
                messageTitle += ', ';
            }
            messageTitle += '【' + content.name + '】';
            reqData.push(content.idTestpaperMedicalrec);
            delList.push(content.idMedicalrec)
        });
        var data = {};
        data.list = reqData;
        layer.confirm('确定删除试题' + messageTitle + '么？', {
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
            var nodes = zTree.getNodesByParam("id", "case-" + delList[i], null);
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

