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

    //--------------------文件上传begin-----------------------

    //多文件列表示例
    var demoListView = $('#demoList')
        ,uploadListIns = upload.render({
        elem: '#testList'
        ,url: basePath + '/upload'
        ,accept: 'file'
        ,multiple: true
        ,auto: false
        ,bindAction: '#testListAction'
        ,choose: function(obj){
            var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
            //读取本地文件
            obj.preview(function(index, file, result){
                var tr = $(['<tr id="upload-'+ index +'">'
                    ,'<td>'+ file.name +'</td>'
                    //,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                    ,'<td>等待上传</td>'
                    ,'<td>'
                    ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                    ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                    ,'</td>'
                    ,'</tr>'].join(''));

                //单个重传
                tr.find('.demo-reload').on('click', function(){
                    obj.upload(index, file);
                });

                //删除
                tr.find('.demo-delete').on('click', function(){
                    delete files[index]; //删除对应的文件
                    tr.remove();
                    uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                });

                demoListView.append(tr);
            });
        }
        ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            layer.msg('正在上传，若文件过大，请耐心等待', {
                icon: 16,
                shade: 0.01,
                time: false
            });
        }
        ,done: function(res, index, upload){
            if(res.code == 0){ //上传成功
                var tr = demoListView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(1).html('<span style="color: #5FB878;">上传成功</span><input class="media-value" value="'+ res.data.idMedia +'" hidden>');
                //tds.eq(2).html(''); //清空操作
                layer.closeAll(); //关闭loading
                return delete this.files[index]; //删除文件队列已经上传成功的文件
            }
            this.error(index, upload);
        }
        ,error: function(index, upload) {
            var tr = demoListView.find('tr#upload-'+ index)
                ,tds = tr.children();
            tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
            tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            layer.closeAll(); //关闭loading
        }
    });
    //--------------------文件上传end-----------------------

    $('#add').on('click', function () {
        $('#demoList').empty();
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
        data.field.fgDefault = data.field.fgDefault ? '1' : '0';
        data.field.idInques = idInques;

        var idMedia = '';
        $(".media-value").each(function(index, item) {
            //console.log($(this).val())
            if (index == 0) {
                idMedia = $(this).val();
            } else {
                idMedia += ',' + $(this).val();
            }
        });
        //console.log(idMedia)
        data.field.idMedia = idMedia;
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
        data.extId = currentData.idInques;

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
        fullMediaUrl(obj.data.mediaList);
    };
    
    function fullMediaUrl(mediaList) {
        $('#demoList').empty();
        if (!mediaList) {
            return;
        }
        $.each(mediaList, function (index, item) {
            let tr = $(['<tr id="upload-' + Date.now() + '-' + index + '">'
                , '<td style="width: 50%;">' + item.des + '</td>'
                //,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                , '<td><span style="color: #5FB878;">上传成功</span><input class="media-value" value="'+ item.idMedia +'" hidden></td>'
                , '<td>'
                , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                , '<button class="layui-btn layui-btn-xs demo-preview" data-url="' + item.path + '" sd-type="' + item.sdType + '">预览</button>'
                , '</td>'
                , '</tr>'].join(''));
            // 删除
            tr.find('.demo-delete').on('click', function() {
                tr.remove();
                return false;
            });
            // 预览
            tr.find('.demo-preview').on('click', function() {
                previewMedia(this.getAttribute('data-url'), this.getAttribute('sd-type'));
                return false;
            });

            demoListView.append(tr);
        })
    }

    // 多媒体预览
    function previewMedia(path, sdType) {
        if (!path) {
            layer.msg("您还未上传文件");
            return false;
        }
        if (sdType == '1') {
            common.openSinglePhoto(path);
        } else if (sdType == '2') {
            common.openAudio(path);
        } else if (sdType == '3') {
            common.openTopVideo(basePath + '/video/form?path=' + path, 890, 504);
        } else {
            layer.msg("该文件类型暂不支持预览");
        }
    }


});

