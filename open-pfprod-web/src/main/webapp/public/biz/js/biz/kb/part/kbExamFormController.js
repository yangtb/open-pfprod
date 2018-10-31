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

    var formIdArr = new Array('searchAnswer', 'naItem', 'desStand', 'naShort', 'idResult', 'desResult', 'costMoney', 'costTime', 'fgShow', 'fgReason', 'fgBack', 'desExpert', 'test3');
    var initFormData = {};
    tableSelect.render({
        elem: '#searchAnswer',
        checkedKey: 'searchAnswerId',
        searchKey: 'keywords',
        searchPlaceholder: '项目分类和项目名称',
        table: {
            url: basePath + '/pf/p/kb/part/exam/search',
            cols: [[
                {type: 'radio'},
                {field: 'idInspectText', title: '项目分类'},
                {field: 'naItem', minWidth: '250', title: '项目名称'}
            ]]
            , limits: [10, 20, 50]
            , page: true
        },
        done: function (elem, data) {
            initFormData = data.data[0];

            var examList = data.data[0].examList;
            $("#idResult").empty();
            $('#idResult').append("<option value=\"\">请选择结果值</option>");
            for (var i = 0; i < examList.length; i++) {
                $('#idResult').append("<option value='" + examList[i].idResult + "'>" + examList[i].valResult + "</option>");
            }
            // 结果值唯一，直接填充
            if (examList.length == 1) {
                fillResult(examList[0]);
            } else if (examList.length > 1) {
                form.val("examFormFilter", data.data[0]);
            }
            form.render();
        }
    });

    function fillResult(data) {
        $.extend(true, data, initFormData);
        $('#reset').click();
        $("#examForm").autofill(data);
        form.render();
    }

    //监听工具条
    form.on('select(idResultSelectFilter)', function (data) {
        var selectedIndex = $("#idResult").get(0).selectedIndex;
        fillResult(initFormData.examList[selectedIndex - 1]);
        form.render();
    });

    //执行渲染
    table.render({
        elem: '#partExamTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'partExamTableId'
        , height: '465' //容器高度
        , cols: [[
            {type: 'radio'},
            {field: 'naItem', minWidth: 150, title: '项目'},
            {field: 'valResult', minWidth: 110, title: '结果'},
            {fixed: 'right', title: '操作', minWidth: 110, align: 'left', toolbar: '#partExamBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/kb/part/exam/list'
        , where: {
            idMedCase: idMedCase
        }
        , page: false
    });

    form.verify({
        naItem: function (value) {
            if (value.length > 64) {
                return '长度不能超过64个字';
            }
        },
        desStand: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        },
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

    form.on('submit(saveExam)', function (data) {
        data.field.fgReason = data.field.fgReason ? '1' : '0';
        data.field.fgBack = data.field.fgBack ? '1' : '0';
        data.field.fgShow = data.field.fgShow ? '1' : '0';
        data.field.idMedCase = idMedCase;
        data.field.valResult = $("#idResult option:selected").text();
        if (!data.field.fgCarried) {
            data.field.fgCarried = '0';
        }
        common.commonPost(basePath + '/pf/r/kb/part/exam/save', data.field, '保存', '', _callBack);
        return false;
    });

    var _callBack = function (data) {
        _tableReload();
        $('#idMedCaseList').val(data.data);
    }

    //监听工具条
    table.on('tool(partExamTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            _delExam(data);
        } else if (obj.event === 'reset') {
            _resetExam(obj);
        } else if (obj.event === 'edit') {
            _editExam(obj);
        }
    });

    var _resetExam = function (obj) {
        var url = basePath + '/pf/r/kb/part/exam/reset';
        layer.confirm('真的要将项目：【' + obj.data.naItem + '】恢复默认么？', {
            title: '项目恢复默认提示',
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

    var _editExam = function (obj) {
        var checkStatus = table.checkStatus('partExamTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中当前行");
            return;
        }
        if (obj.data.idMedCaseList != data[0].idMedCaseList) {
            common.toastTop("请先点击单选按钮选中当前行");
            return;
        }
        var url = basePath + '/pf/r/kb/part/exam/custom';
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

    var _delExam = function (currentData) {
        var url = basePath + '/pf/r/kb/part/exam/del';
        var reqData = new Array();
        var name = '【' + currentData.naItem + '】';
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
        table.reload('partExamTableId', {
            where: {
                idMedCase: idMedCase
            }
            , height: '465'
        });
    };

    table.on('radio(partExamTableFilter)', function (obj) {
        fillForm(obj.data);
    });

    function fillForm(data) {
        $('#reset').click();

        $("#idResult").empty();
        $('#idResult').append("<option value='" + data.idResult + "'>" + data.valResult + "</option>");

        $("#examForm").autofill(data);
        common.setFormStatus(data.fgCarried, formIdArr);
        form.render();
    };

});
