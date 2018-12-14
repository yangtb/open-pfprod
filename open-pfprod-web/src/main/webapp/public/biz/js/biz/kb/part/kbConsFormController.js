layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    ckplayer: 'ckplayer/ckplayer'
    , Magnifier: 'js/Magnifier'
    , Event: 'js/Event'
}).use(['table', 'form', 'upload', 'jquery', 'element', 'tableSelect', 'common', 'layer'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , common = layui.common
        , element = layui.element
        , layer = layui.layer
        , tableSelect = layui.tableSelect;

    var formIdArr = new Array('searchAnswer', 'desInques', 'idAnswer', 'desAnswer', 'fgReason', 'fgBack', 'desExpert', 'test3');

    $(document).ready(function () {
        if (previewFlag == '1') {
            var formBtnArr = new Array('save');
            common.setFormStatus('0', formBtnArr);
            common.setFormStatus('0', formIdArr);
            layui.form.render('select');
        }
    });

    var initFormData = {};
    tableSelect.render({
        elem: '#searchAnswer',
        checkedKey: 'searchAnswerId',
        searchKey: 'keywords',
        searchPlaceholder: '目录名称和问题内容',
        table: {
            url: basePath + '/pf/p/kb/part/cons/search',
            cols: [[
                {type: 'radio'},
                {field: 'idInquesCaText', title: '目录名称'},
                {field: 'desInques', minWidth: '250', title: '问题内容'}
            ]]
            , limits: [10, 20, 50]
            , page: true
        },
        done: function (elem, data) {
            initFormData = data.data[0];

            var answerList = initFormData.answerList;
            $("#idAnswer").empty();
            for (var i = 0; i < answerList.length; i++) {
                $('#idAnswer').append("<option value='" + answerList[i].idAnswer + "'>" + answerList[i].desAnswer + "</option>");
            }
            // 结果值唯一，直接填充
            if (answerList.length == 1) {
                fillResult(answerList[0]);
            } else if (answerList.length > 1) {
                form.val("consFormFilter", data.data[0]);
                form.render();
            }
        }
    });

    function fillResult(data) {
        $.extend(true, data, initFormData);
        $('#reset').click();
        $("#consForm").autofill(data);
        form.render();
    }

    //监听工具条
    form.on('select(idAnswerSelectFilter)', function (data) {
        form.render();
        var selectedIndex = $("#idAnswer").get(0).selectedIndex;
        if (initFormData.answerList) {
            fillResult(initFormData.answerList[selectedIndex]);
            form.render();
        }
    });

    init();

    function init() {
        if (tagFlag == '1') {
            // 查询idMedCase
            var medData = {
                idMedicalrec: idMedicalrec,
                idTag: idTag
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
                            idMedCase = data.data.idMedCase;
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
            elem: '#partConsTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'partConsTableId'
            , height: 'full-20' //容器高度
            , toolbar: '#toolbarCons'
            , defaultToolbar: []
            , cols: [[
                {type: 'radio'},
                {field: 'desInques', minWidth: 145, title: '问题'},
                {field: 'desAnswer', minWidth: 110, title: '答案'},
                {fixed: 'right', title: '操作', minWidth: 110, align: 'left', toolbar: '#partConsBar'}
            ]] //设置表头
            , url: basePath + '/pf/p/kb/part/cons/list'
            , where: {
                idMedCase: idMedCase
            }
            , limit: 15
            , page: {//支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                , groups: 1 //只显示 1 个连续页码
                , first: false //不显示首页
                , last: false //不显示尾页
                , limits: [15, 30, 50, 100]
            }
        });
    }

    form.verify({
        desAnswer: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        }
    });

    // 文件上传
    var timer;
    upload.render({
        elem: '#test3'
        , url: basePath + '/upload'
        , field: 'file'
        , accept: 'file' //普通文件
        //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
        , before: function (obj) {
            $('#path').hide();
            $('#uploadProgress').show();
            //模拟loading
            timer = setInterval(function () {
                $.ajax({
                    type: "POST",
                    contentType: false,
                    async: false,
                    cache: false,
                    url: basePath + '/selectUploadPercent',
                    dataType: "json",
                    success: function (data) {
                        var per = data.percent + "%";
                        element.progress('demo', per);
                    }, error: function (data) {
                        alert("ajax异常！！！");
                    }
                });
            }, 100);
        }
        , done: function (res) {
            $('#path').show();
            $('#uploadProgress').hide();
            if (res.code != '0') {
                layer.tips(res.msg, '#test3', {
                    tips: [1, '#FF5722'],
                    time: 5000
                });
                return;
            }
            clearInterval(timer);
            $('#path').val(res.data.path);
            $('#idMedia').val(res.data.idMedia);
            clearPercent();
        }
        , error: function () {
            $('#path').show();
            $('#uploadProgress').hide();
            clearInterval(timer);
            clearPercent();
        }
    });

    //清除进度数据
    function clearPercent() {
        $.ajax({
            type: "POST",
            contentType: false,
            async: false,
            cache: false,
            url: basePath + '/clearUploadPercent',
            dataType: "json",
            success: function (data) {
                element.progress('demo', 0);
            },
        });
    };

    //相册层
    $('#preview').on('click', function () {
        var path = $('#path').val();
        if (!path) {
            layer.tips("您还未上传文件", '#preview', {tips: 1});
            return false;
        }
        var sdType = $('#sdType').val();
        if (sdType == '1') {
            common.openSinglePhoto(path);
        } else if (sdType == '2') {
            common.openAudio(path.substring(0, path.lastIndexOf(".")));
        } else if (sdType == '3') {
            common.openTopVideo(basePath + '/video/form?path=' + path, 890, 504);
        } else {
            layer.tips("该文件类型暂不支持预览", '#preview', {tips: 1});
        }
        return false;
    });

    $('#add').on('click', function () {
        $("#idAnswer").empty();
        common.setFormStatus('1', formIdArr);
        $('#reset').click();
        $('#save').click();
    });

    $('#save').on('click', function () {
        if (!$('#idMedCaseList').val()) {
            layer.tips('请先在左侧选中一行记录，若无，请先添加', '#save', {tips: 1});
            return false;
        }
    })

    form.on('submit(saveCons)', function (data) {
        data.field.fgReason = data.field.fgReason ? '1' : '0';
        data.field.fgBack = data.field.fgBack ? '1' : '0';
        data.field.idMedCase = idMedCase;
        if (!data.field.fgCarried) {
            data.field.fgCarried = '0';
        }
        common.commonPost(basePath + '/pf/r/kb/part/cons/save', data.field, '保存', '', _callBack);
        return false;
    });

    var _callBack = function (data) {
        _tableReload();
        $('#idMedCaseList').val(data.data);
    }

    //监听工具条
    table.on('tool(partConsTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            _delCons(data);
        } else if (obj.event === 'reset') {
            _resetCons(obj);
        } else if (obj.event === 'edit') {
            _editCons(obj);
        }
    });

    var _resetCons = function (obj) {
        var url = basePath + '/pf/r/kb/part/cons/reset';
        layer.confirm('真的要将：【' + obj.data.desInques + '】恢复默认么？', {
            title: '问题恢复默认提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            common.commonPost(url, obj.data, '', '', resetCallback);
            layer.closeAll(index);
        })
    };

    function resetCallback(data) {
        fillForm(data.data);
        common.sucChildMsg("已恢复至默认数据");
        _tableReload();
    };

    var _editCons = function (obj) {
        /*var checkStatus = table.checkStatus('partConsTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中当前行");
            return;
        }
        if (obj.data.idMedCaseList != data[0].idMedCaseList) {
            common.toastTop("请先点击单选按钮选中当前行");
            return;
        }*/
        var url = basePath + '/pf/r/kb/part/cons/custom';
        var reqData = new Array();
        reqData.push(obj.data.idMedCaseList);
        var data = {};
        data.list = reqData;
        data.status = '1';

        common.commonPost(url, data);
        obj.update({
            fgCarried: data.status
        });
        common.setFormStatus(data.status, formIdArr);
        $('#fgCarried').val(data.status);
        form.render();
    };

    var _delCons = function (currentData) {
        var url = basePath + '/pf/r/kb/part/cons/del';
        var reqData = new Array();
        var name = '【' + currentData.desInques + '】';
        reqData.push(currentData.idMedCaseList);
        var data = {};
        data.list = reqData;
        data.status = '1';

        layer.confirm('真的要删除问题：' + name + '么？', {
            title: '删除问题提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            common.commonPost(url, data, '删除', '', _tableReload);
            layer.closeAll(index);
            $('#reset').click();
        })
    };

    var _tableReload = function () {
        table.reload('partConsTableId', {
            where: {
                idMedCase: idMedCase
            }
            , height: 'full-20'
        });
    };

    /*table.on('radio(partConsTableFilter)', function (obj) {
        fillForm(obj.data);
    });*/

    //单击行选中radio
    table.on('row(partConsTableFilter)', function (obj) {
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');//选中行样式
        obj.tr.find('input[lay-type="layTableRadio"]').prop("checked", true);
        form.render('radio');
        fillForm(obj.data);
    });

    function fillForm(data) {
        $('#reset').click();

        $("#idAnswer").empty();
        $('#idAnswer').append("<option value='" + data.idAnswer + "'>" + data.desAnswer + "</option>");

        $("#consForm").autofill(data);
        common.setFormStatus(data.fgCarried, formIdArr);
        form.render();
    };

    table.on('toolbar(partConsTableFilter)', function (obj) {
        switch (obj.event) {
            case 'bachAddConsAnswer':
                common.openParent('问诊选择',
                    basePath + '/pf/p/kb/part/define/cons/bach/add/page?idMedCase='
                    + idMedCase + '&tagFlag=' + tagFlag + '&idTag=' + idTag + '&idMedicalrec=' + idMedicalrec
                    + '&caseName=' + caseName,
                    800, 480);
                break;
            case 'allAddConsAnswer':
                var y = $(this).offset().top;
                var x = $(this).offset().left;
                addAll(x, y);
                break;
        }
    });

    function addAll(x, y) {
        layer.confirm('确定要全部引入么？', {
            title: '提示',
            offset: [y + 'px', x + 'px'],
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            var url = basePath + '/pf/r/kb/part/cons/bach/add';
            var bizData = {
                extId: idMedCase,
                extType: '1', // 全部引入
                tagFlag: tagFlag,
                idMedCase: idMedCase,
                idMedicalrec: idMedicalrec,
                idTag: idTag,
                caseName : caseName
            }
            layer.msg('正在执行，请稍后...', {icon: 16, shade: 0.01});
            common.commonPost(url, bizData, '全部引入', null, successAddAllCallback, false);
        })
    }

    function successAddAllCallback() {
        layer.closeAll('loading');
        window.location.reload();
    }

});

