layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['layer', 'table', 'form', 'jquery', 'common'], function () {
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


    var v_idInquesCa;

    function zTreeOnClick(event, treeId, treeNode) {
        v_idInquesCa = treeNode.id
        _tableReload(v_idInquesCa);
    };


    var zTree;
    $(document).ready(function () {
        layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/inquisition/question/classify/tree',
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
        elem: '#inquisitionQuestionTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'inquisitionQuestionTableId'
        , height: 'full-60' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            {field: 'desInques', title: '内容'},
        ]] //设置表头
        //, url: basePath + '/pf/p/inquisition/question/list'
        , limit: 30
        , limits: [30, 50]
        , page: true
    });

    var _tableReload = function (idInquesCa, desInques) {
        table.reload('inquisitionQuestionTableId', {
            url: basePath + '/pf/p/inquisition/question/list'
            , where: {
                idInquesCa: idInquesCa,
                desInques: desInques
            }
            , height: 'full-60'
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    };


    $('#add').on('click', function () {
        var checkStatus = table.checkStatus('inquisitionQuestionTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#add', {tips: 1});
            return;
        }

        var reqData = new Array();
        $.each(data, function (index, content) {
            reqData.push(content.idInques);
        });
        var url = basePath + '/pf/r/kb/part/cons/bach/add';
        var bizData = {
            list: reqData,
            extId: idMedCase
        }
        common.commonPost(url, bizData, '添加', 'add', successBachAddCallback)
    });

    function successBachAddCallback() {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
        $(window.parent.document).find("iframe")[0].contentWindow.location.reload(true);
    }

});

