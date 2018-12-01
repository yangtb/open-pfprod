layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['layer', 'table','jquery', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , common = layui.common;

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
            onClick: zTreeOnClick
        }
    };


    var v_idInspect;

    function zTreeOnClick(event, treeId, treeNode) {
        v_idInspect = treeNode.id
        _tableReload(v_idInspect);
    };

    var zTree;
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
                    var zNodes = data.data;
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                    zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    var node = zTree.getNodes()[0];
                    zTree.selectNode(node);
                    setting.callback.onClick(null, zTree.setting.treeId, node);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });

    });


    //执行渲染
    table.render({
        elem: '#questionTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'questionTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            {field: 'naItem', minWidth: 200, title: '项目'},
            {field: 'naShort', width: 160, title: '缩写'},
        ]] //设置表头
        //, url: basePath + '/pf/p/exam/question/list'
        , limit: 30
        //, even: true
        , limits: [30, 50]
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
        var checkStatus = table.checkStatus('questionTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#add', {tips: 1});
            return;
        }

        var reqData = new Array();
        $.each(data, function (index, content) {
            reqData.push(content.idInspectItem);
        });
        var url = basePath + '/pf/r/kb/part/exam/bach/add';
        var bizData = {
            list: reqData,
            extId: idMedCase,
            extType : '0'
        }
        common.commonPost(url, bizData, '添加', 'add', successBachAddCallback)
    });

    function successBachAddCallback() {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
        $(window.parent.document).find("iframe")[0].contentWindow.location.reload(true);
    }

});

