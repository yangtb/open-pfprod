layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    ckplayer: 'ckplayer/ckplayer'
    , Magnifier: 'js/Magnifier'
    , Event: 'js/Event'
}).use(['table', 'form', 'upload', 'jquery', 'element', 'tableSelect', 'common'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , common = layui.common
        , element = layui.element
        , tableSelect = layui.tableSelect;

    tableSelect.render({
        elem: '#desAnswer',
        checkedKey: 'id',
        table: {
            url: 'table.json',
            cols: [[
                {type: 'radio'},
                {field: 'username', title: '目录名称'},
                {field: 'sex', minWidth: '250', title: '问题内容'}
            ]]
        },
        done: function (elem, data) {
            var NEWJSON = []
            layui.each(data.data, function (index, item) {
                NEWJSON.push(item.username)
            })
            elem.val(NEWJSON.join(","))
        }
    })

    /*tableSelect.render({
        elem: '#desAnswer',	//定义输入框input对象 必填
        checkedKey: 'idy8789789798', //表格的唯一建值，非常重要，影响到选中状态 必填
        searchKey: 'keyword',	//搜索输入框的name值 默认keyword
        searchPlaceholder: '关键词搜索',	//搜索输入框的提示文字 默认关键词搜索
        table: {	//定义表格参数，与LAYUI的TABLE模块一致，只是无需再定义表格elem
            url:'',
            cols: [[]]
        },
        done: function (elem, data) {
            //选择完后的回调，包含2个返回值 elem:返回之前input对象；data:表格返回的选中的数据 []
            //拿到data[]后 就按照业务需求做想做的事情啦~比如加个隐藏域放ID...
        }
    })*/

    //执行渲染
    table.render({
        elem: '#partConsTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'partConsTableId'
        , height: 'full-10' //容器高度
        , cols: [[
            {type: 'radio'},
            {field: 'desInques', minWidth: 150, title: '问题'},
            {field: 'desAnswer', minWidth: 110, title: '答案'},
            {fixed: 'right', title: '操作', minWidth: 110, align: 'left', toolbar: '#partConsBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/kb/part/cons/list'
        , where: {
            idMedCase: idMedCase
        }
        , page : false
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
            clearInterval(timer);
            $('#path').val(res.data.path);
            $('#idMedia').val(res.data.idMedia);
            $('#sdType').val(res.data.sdType);
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
        data.field.idInques = idInques;
        common.commonPost(basePath + '/pf/r/inquisition/question/answer/save', data.field, '保存', '', _callBack);
        return false;
    });

    var _callBack = function (data) {
        _tableReload();
        $('#idAnswer').val(data.data);
    }

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
            common.commonPost(url, data, '删除', '', _tableReload);
            layer.closeAll(index);
            $('#reset').click();
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
        $('#reset').click();
        $("#answerForm").autofill(obj.data);
        layui.use('form', function () {
            layui.form.render();
        });
    });

});

