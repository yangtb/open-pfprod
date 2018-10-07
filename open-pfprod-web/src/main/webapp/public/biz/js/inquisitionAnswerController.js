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
            {field: 'desAnswer', minWidth: 150, title: '答案内容'},
            {fixed: 'right', title: '操作', align: 'center', toolbar: '#inquisitionAnswer'}
        ]] //设置表头
        , url: basePath + '/pf/p/inquisition/question/answer/list'
        , where: {
            idInques: idInques,
        }
        , page: false
    });

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
                        console.log(data);
                        var per = data.percent + "%";
                        element.progress('demo', per);
                    }, error: function (data) {
                        console.log(data);
                        alert("ajax异常！！！");
                    }
                });
            }, 100);
        }
        , done: function (res) {
            $('#path').show();
            $('#uploadProgress').hide();
            clearInterval(timer);
            $('#path').val(res.data.path);
            $('#idMedia').val(res.data.idMedia);
            clearPercent();
            console.log(res)
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
                console.log("ddd:" + JSON.stringify(data));
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
            var json = {
                //"title": "", //相册标题
                //"id": 123, //相册id
                "start": 0, //初始显示的图片序号，默认0
                "data": [   //相册包含的图片，数组格式
                    {
                        //"alt": "图片名",
                        //"pid": 666, //图片id
                        "src": path, //原图地址
                        "thumb": "" //缩略图地址
                    }
                ]
            };
            common.openPhoto(json)
        } else if (sdType == '2') {
            common.openAudio("http://hsknowledgebase.oss-cn-hangzhou.aliyuncs.com/mytest/music");
        } else if (sdType == '3') {
            common.openTopVideo(path, basePath + '/video/form', 890, 504);
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
        data.field.fgReason = data.field.radioGroup === 'fgReason' ? '1' : '0';
        data.field.fgBack = data.field.radioGroup === 'fgBack' ? '1' : '0';
        data.field.fgTag = data.field.radioGroup === 'fgTag' ? '1' : '0';
        data.field.idInques = idInques;
        common.commonPost(basePath + '/pf/r/inquisition/question/answer/save', data.field, '保存');
        $('#reset').click();
        _tableReload();
        return false;
    });

//监听工具条
    table.on('tool(answerTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            _delAnswer(data);
        }
    });

    var _delAnswer = function (currentData) {
        var url = basePath + '/pf/r/inquisition/question/answer/del';
        var reqData = new Array();
        var name = '【' + currentData.desAnswer + '】';
        reqData.push(currentData.idAnswer);
        var data = {};
        data.list = reqData;
        data.status = '1';

        layer.confirm('真的要删除答案内容：' + name + '么？', {
            title: '删除答案内容提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            common.commonPost(url, data, '删除');
            layer.closeAll(index);
            _tableReload();
        })
    };

    var _tableReload = function () {
        table.reload('answerTableId', {
            where: {
                idInques: idInques,
            },
            height: 'full-30'
        });
    };

    table.on('radio(answerTableFilter)', function (obj) {
        if (!obj.data.desExpert) {
            obj.data.desExpert = "";
        }
        if (obj.data.fgReason == '1') {
            obj.data.radioGroup = 'fgReason';
        } else if (obj.data.fgBack == '1') {
            obj.data.radioGroup = 'fgBack';
        } else if (obj.data.fgTag == '1') {
            obj.data.radioGroup = 'fgTag';
        }
        $('#reset').click();
        $("#answerForm").autofill(obj.data);
        layui.use('form', function () {
            layui.form.render();
        });
    });

});

