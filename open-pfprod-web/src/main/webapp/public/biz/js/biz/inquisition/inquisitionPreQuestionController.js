layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['layer', 'form', 'table', 'jquery', 'common', 'transfer', 'util'], function () {
    var $ = layui.$
        , common = layui.common
        , layer = layui.layer
        , transfer = layui.transfer;

    transfer.render({
        elem: '#test4'
        , id: 'key123'
        //, data: data1
        , title: ['问题', '待添加问题']
        , showSearch: true
        , width: 285
        , height: 447
    })

    // ===============  ztree =========================//
    var setting = {
        view: {
            dblClickExpand: false,
            showLine: true
        },
        check: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick
        }
    };

    var zNodes = [], zTree;
    $(document).ready(function () {
        loadLeftTree()
    });

    function loadLeftTree() {
        layer.load(2);
        var bizData = {
            idGrade: ''
        }
        $.ajax({
            url: basePath + '/pf/r/inquisition/question/classify/tree',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                zNodes = data.data;
                $.fn.zTree.init($("#departTree"), setting, zNodes);
                zTree = $.fn.zTree.getZTreeObj("departTree");
                return true;
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
        var bodyHeight = $(this).height() - $("#treeTitle").height() - 60;
        $("#departTree").css("min-height", bodyHeight);
        $("#departTree").css("max-height", bodyHeight);
    }


    // 选中tree
    function onClick(e, treeId, treeNode) {
        layer.load(2);
        var bizData = {
            idInquesCa: treeNode.id
        }

        $.ajax({
            url: basePath + '/pf/r/inquisition/question/list',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                // console.log(data.data)
                var data1 = [];
                if (data.data) {

                    $.each(data.data, function (index, content) {
                        var stuData = {
                            "value": content.idInques,
                            "title": content.desInques
                        }
                        data1.push(stuData)
                    });
                }

                transfer.reload('key123', {

                title: ['[' + treeNode.name + '] 问题', '待添加问题']
                    , data: data1
                    , showSearch: true
                })
                return true;
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    };


    $('#closeMoveWind').on('click', function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });

    $('#moveStudent').on('click', function () {

        var getData = transfer.getData('key123'); //获取右侧数据
        if (!getData || getData.length == 0) {
            layer.msg("无待添加问题");
            return;
        }
        if (getData.length > 5) {
            layer.msg("添加关联问题最多5个");
            return;
        }

        var preList = new Array();
        $.each(getData, function (index, content) {
            var bizData = {
                idInques : content.value,
                desInques : content.title
            }
            preList.push(bizData);
        });

        //调用子页面的全局函数
        parent.addPreTable(preList);

        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });


});

