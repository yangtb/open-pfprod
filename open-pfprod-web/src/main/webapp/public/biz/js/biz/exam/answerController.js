layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    ckplayer: 'ckplayer/ckplayer'
}).use(['table', 'form', 'upload', 'jquery', 'element', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , common = layui.common
        , element = layui.element;

    //执行渲染
    table.render({
        elem: '#answerTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'answerTableId'
        , height: 'full-30' //容器高度
        , cols: [[
            {type: 'radio'},
            {field: 'fgActive', minWidth: 73, title: '状态', templet: '#fgActiveTpl'},
            {field: 'valResult', minWidth: 90, title: '结果值'},
            {field: 'desResult', minWidth: 110, title: '说明'},
            {fixed: 'right', title: '操作', align: 'center', toolbar: '#examAnswer'}
        ]] //设置表头
        , url: basePath + '/pf/p/exam/question/answer/list'
        , where: {
            idInspectItem: idInspectItem
        }
        , page: false
    });

    form.verify({
        desResult: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        },
        vDecimal: function (value) {
            if (value == null || value == '') {
                return;
            }
            if (!value.match(/(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/)) {
                return '精确到小数点后2位';
            }
        },
    });


    // 文件上传
    var timer;
    upload.render({
        elem: '#test3'
        , url: basePath + '/upload'
        , field: 'file'
        , accept: 'file' //普通文件
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
            }, 500);
        }
        , done: function (res) {
            $('#path').show();
            $('#uploadProgress').hide();
            clearInterval(timer);
            $('#path').val(res.data.path);
            $('#idMedia').val(res.data.idMedia);
            $('#sdType').val(res.data.sdType);
            clearPercent();
        }
        , error: function () {
            $('#path').show();
            $('#uploadProgress').hide();
            $('#sdType').val('');
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
        $('#reset').click();
        $('#save').click();
    });

    form.on('submit(saveAnswer)', function (data) {
        if (!data.field.fgActive) {
            data.field.fgActive = '0';
        }
        data.field.fgReason = data.field.fgReason ? '1' : '0';
        data.field.fgBack = data.field.fgBack ? '1' : '0';
        data.field.fgTag = data.field.fgTag ? '1' : '0';
        data.field.fgShow = data.field.fgShow ? '1' : '0';
        data.field.fgDefault = data.field.fgDefault ? '1' : '0';

        data.field.idInspectItem = idInspectItem;
        common.commonPost(basePath + '/pf/r/exam/question/answer/save', data.field, '保存', '', _callBack);
        return false;
    });

    var _callBack = function (data) {
        _tableReload();
        $('#idResult').val(data.data);
    }

    //监听工具条
    table.on('tool(answerTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            _delAnswer(data);
        }
    });

    var _delAnswer = function (currentData) {
        var url = basePath + '/pf/r/exam/question/answer/del';
        var reqData = new Array();
        var name = '【' + currentData.valResult + '】';
        reqData.push(currentData.idResult);
        var data = {};
        data.list = reqData;
        data.status = '1';
        data.extId = currentData.idInspectItem;

        layer.confirm('真的要删除结果值：' + name + '么？', {
            title: '删除结果内容提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            common.commonPost(url, data, '删除', '', _tableReload);
            $('#reset').click();
            layer.closeAll(index);
        })
    };

    var _tableReload = function () {
        table.reload('answerTableId', {
            where: {
                idInspectItem: idInspectItem
            },
            height: 'full-30'
        });
    };

    //单击行选中radio
    table.on('row(answerTableFilter)', function (obj) {
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');//选中行样式
        obj.tr.find('input[lay-type="layTableRadio"]').prop("checked", true);
        form.render('radio');
        rowClick(obj)
    });

    function rowClick(obj) {
        if (!obj.data.desExpert) {
            obj.data.desExpert = "";
        }

        $('#reset').click();
        $("#answerForm").autofill(obj.data);
        layui.use('form', function () {
            layui.form.render();
        });
    };

});

