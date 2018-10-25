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

    init();

    function init() {
        if (!idMedCase) {
            return;
        }
        var selectUrl = basePath + '/pf/r/kb/part/check/pic/select';
        var bizData = {};
        bizData.idMedCase = idMedCase;
        layer.load(2);
        $.ajax({
            url: selectUrl,
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
                    form.val("uploadFormFilter", data.data);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("查询失败");
                return false;
            }
        });
    }

    var formIdArr = new Array('searchAnswer', 'desBody', 'sdBody', 'cdCheck', 'idResult', 'fgReason', 'fgBack', 'desExpert', 'test3');
    var initFormData = {};
    tableSelect.render({
        elem: '#searchAnswer',
        checkedKey: 'searchAnswerId',
        searchKey: 'keywords',
        searchPlaceholder: '检查分类和部位描述\n',
        table: {
            url: basePath + '/pf/p/kb/part/check/search',
            cols: [[
                {type: 'radio'},
                {field: 'idBodyText', title: '检查分类'},
                {field: 'desBody', minWidth: 250, title: '部位描述'},
            ]]
            , limits: [10, 20, 50]
            , page: true
        },
        done: function (elem, data) {
            initFormData = data.data[0];
            var checkList = data.data[0].checkList;
            $("#idResult").empty();
            $('#idResult').append("<option value=\"\">请选择结果内容</option>");
            for (var i = 0; i < checkList.length; i++) {
                $('#idResult').append("<option value='" + checkList[i].idResult + "'>" + checkList[i].desResult + "</option>");
            }
            // 结果值唯一，直接填充
            if (checkList.length == 1) {
                fillResult(checkList[0]);
            } else if (checkList.length > 1) {
                form.val("checkFormFilter", data.data[0]);
            }
            form.render();
        }
    });

    function fillResult(data) {
        $.extend(true, data, initFormData);
        $('#reset').click();
        $("#checkForm").autofill(data);
        form.render();
    }

    //监听工具条
    form.on('select(idBodySelectFilter)', function (data) {
        var selectedIndex = $("#idResult").get(0).selectedIndex;
        fillResult(initFormData.checkList[selectedIndex - 1]);
    });

    //执行渲染
    table.render({
        elem: '#partCheckTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'partCheckTableId'
        , height: '335' //容器高度
        , cols: [[
            {type: 'radio'},
            {field: 'desBody', minWidth: 150, title: '部位描述'},
            {field: 'desResult', minWidth: 110, title: '检查结果'},
            {fixed: 'right', title: '操作', minWidth: 110, align: 'left', toolbar: '#partCheckBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/kb/part/check/list'
        , where: {
            idMedCase: idMedCase
        }
        , page: false
    });

    form.verify({
        desAnswer: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        },
        desBody: function (value) {
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
            clearPercent();
        }
        , error: function () {
            $('#path').show();
            $('#uploadProgress').hide();
            clearInterval(timer);
            clearPercent();
        }
    });

    upload.render({
        elem: '#frontPicUp'
        , url: basePath + '/upload'
        , field: 'file'
        , accept: 'images' //普通文件
        , exts: 'jpg|png|bmp|jpeg'
        , before: function (obj) {
            layer.msg('正在上传图片', {icon: 16, shade: 0.01});
        }
        , done: function (res) {
            $('#frontPath').val(res.data.path);
            $('#idMedia').val(res.data.idMedia);
            var dataPic = {};
            dataPic.idMedCase = idMedCase;
            dataPic.idMediaFront = res.data.idMedia;
            dataPic.frontPath = res.data.path
            common.commonPost(basePath + '/pf/r/kb/part/check/pic/save', dataPic, '上传');
            layer.closeAll('loading');
        }
        , error: function () {
            layer.closeAll('loading');
        }
    });

    upload.render({
        elem: '#backPicUp'
        , url: basePath + '/upload'
        , field: 'file'
        , accept: 'images' //普通文件
        , exts: 'jpg|png|bmp|jpeg'
        , before: function (obj) {
            layer.msg('正在上传图片', {icon: 16, shade: 0.01});
        }
        , done: function (res) {
            $('#backPath').val(res.data.path);
            $('#idMedia').val(res.data.idMedia);
            var dataPic = {};
            dataPic.idMedCase = idMedCase;
            dataPic.idMediaBack = res.data.idMedia;
            dataPic.backPath = res.data.path
            common.commonPost(basePath + '/pf/r/kb/part/check/pic/save', dataPic, '上传');
            layer.closeAll('loading');
        }
        , error: function () {
            layer.closeAll('loading');
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
        $("#idResult").empty();
        common.setFormStatus('1', formIdArr);
        $('#reset').click();
        $('#save').click();
    });

    form.on('submit(saveCheck)', function (data) {
        data.field.fgReason = data.field.fgReason ? '1' : '0';
        data.field.fgBack = data.field.fgBack ? '1' : '0';
        data.field.fgShow = data.field.fgShow ? '1' : '0';
        data.field.idMedCase = idMedCase;
        data.field.valResult = $("#idResult option:selected").text();
        if (!data.field.fgCarried) {
            data.field.fgCarried = '0';
        }
        common.commonPost(basePath + '/pf/r/kb/part/check/save', data.field, '保存', '', _callBack);
        return false;
    });

    var _callBack = function (data) {
        _tableReload();
        $('#idMedCaseList').val(data.data);
    }

    //监听工具条
    table.on('tool(partCheckTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            _delCheck(data);
        } else if (obj.event === 'reset') {
            _resetCheck(obj);
        } else if (obj.event === 'edit') {
            _editCheck(obj);
        }
    });

    var _resetCheck = function (obj) {
        var url = basePath + '/pf/r/kb/part/check/reset';
        layer.confirm('真的要将部位描述：【' + obj.data.desBody + '】恢复默认么？', {
            title: '部位描述恢复默认提示',
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

    var _editCheck = function (obj) {
        var checkStatus = table.checkStatus('partCheckTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中当前行");
            return;
        }
        if (obj.data.idMedCaseList != data[0].idMedCaseList) {
            common.toastTop("请先点击单选按钮选中当前行");
            return;
        }
        var url = basePath + '/pf/r/kb/part/check/custom';
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

    var _delCheck = function (currentData) {
        var url = basePath + '/pf/r/kb/part/check/del';
        var reqData = new Array();
        var name = '【' + currentData.desBody + '】';
        reqData.push(currentData.idMedCaseList);
        var data = {};
        data.list = reqData;
        data.status = '1';

        layer.confirm('真的要删除项目：' + name + '么？', {
            title: '删除项目提示',
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
        table.reload('partCheckTableId', {
            where: {
                idMedCase: idMedCase
            }
            , height: '335'
        });
    };

    table.on('radio(partCheckTableFilter)', function (obj) {
        fillForm(obj.data);
    });

    function fillForm(data) {
        $('#reset').click();

        $("#idResult").empty();
        $('#idResult').append("<option value='" + data.idResult + "'>" + data.desResult + "</option>");

        $("#checkForm").autofill(data);
        common.setFormStatus(data.fgCarried, formIdArr);
        form.render();
    };

});

