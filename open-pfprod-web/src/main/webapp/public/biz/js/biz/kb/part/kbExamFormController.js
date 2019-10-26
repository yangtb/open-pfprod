layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    ckplayer: 'ckplayer/ckplayer'
    , Magnifier: 'js/Magnifier'
    , Event: 'js/Event'
}).use(['table', 'form', 'upload', 'jquery', 'element', 'tableSelect', 'common', 'treeSelect'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , common = layui.common
        , element = layui.element
        , treeSelect = layui.treeSelect
        , tableSelect = layui.tableSelect;

    treeSelect.render({
        elem: '#idInspect',
        data: basePath + '/pf/r/exam/question/classify/tree/select',
        type: 'post',
        placeholder: '请选择',
        search: true,
        click: function (d) {
            $("#idInspect").val(d.current.id);
            reloadTable($('#keyword').val(), $('#idInspect').val())
        }
    });

    var formIdArr = new Array('searchAnswer', 'naItem', 'desStand', 'naShort', 'idResult', 'valResult' ,'desResult', 'costMoney', 'costTime', 'fgShow', 'fgReason', 'fgBack', 'desExpert', 'testList', 'testListAction', 'isMasculine');
    var initFormData = {};
    tableSelect.render({
        elem: '#searchAnswer',
        checkedKey: 'idInspectItem',
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
            //$('#idResult').append("<option value=\"\">请选择结果值</option>");
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
        if (initFormData.examList) {
            fillResult(initFormData.examList[selectedIndex]);
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
            elem: '#partExamTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'partExamTableId'
            , height: 'full-50' //容器高度
            //, toolbar: '#toolbarExam'
            //, defaultToolbar: []
            , cols: [[
                {type: 'radio'},
                {field: 'naItem', minWidth: 140, title: '项目'},
                {field: 'valResult', minWidth: 110, title: '结果'},
                {field: 'isMasculine', minWidth: 135, title: '是否阳性',style:'display:none;'},
                {fixed: 'right', title: '操作', minWidth: 110, align: 'left', toolbar: '#partExamBar'}
            ]] //设置表头
            , url: basePath + '/pf/p/kb/part/exam/list'
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
            },done: function () {
                $("[data-field='isMasculine']").css('display','none');
            }
        });
    }


    //监听提交
    form.on('submit(queryFilter)', function (data) {
        reloadTable(data.field.keyword)
    });

    $('#keyword').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            reloadTable($('#keyword').val(), $('#idInspect').val())
            return false;
        }
    });

    function reloadTable(keyword, idInspect) {
        if (idInspect == "0") {
            idInspect = null;
        }
        table.reload('partExamTableId', {
            where: {
                idMedCase: idMedCase,
                keyword: keyword,
                idInspect : idInspect
            }
        });
    }


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
        $("#idResult").empty();
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

    form.on('submit(saveExam)', function (data) {
        data.field.fgReason = data.field.fgReason ? '1' : '0';
        data.field.fgBack = data.field.fgBack ? '1' : '0';
        data.field.fgShow = data.field.fgShow ? '1' : '0';
        data.field.idMedCase = idMedCase;
        if (!data.field.fgCarried) {
            data.field.fgCarried = '0';
        }
        var idMedia = '';
        $(".media-value").each(function(index, item) {
            //console.log($(this).val())
            if (index == 0) {
                idMedia = $(this).val();
            } else {
                idMedia += ',' + $(this).val();
            }
        });
        data.field.idMedia = idMedia;
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
        /*var checkStatus = table.checkStatus('partExamTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中当前行");
            return;
        }
        if (obj.data.idMedCaseList != data[0].idMedCaseList) {
            common.toastTop("请先点击单选按钮选中当前行");
            return;
        }*/
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
        });
    };

    /*table.on('radio(partExamTableFilter)', function (obj) {
        fillForm(obj.data);
    });*/

    //单击行选中radio
    table.on('row(partExamTableFilter)', function (obj) {
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');//选中行样式
        obj.tr.find('input[lay-type="layTableRadio"]').prop("checked", true);
        form.render('radio');
        fillForm(obj.data);
    });

    function fillForm(data) {
        $('#reset').click();

        data.path = data.path ? data.path : '';
        data.costMoney = data.costMoney ? data.costMoney : '';
        data.costTime = data.costTime ? data.costTime : '';
        data.desResult = data.desResult ? data.desResult : '';
        $("#idResult").empty();
        $('#idResult').append("<option value='" + data.idResult + "'>" + data.valResult + "</option>");

        $("#examForm").autofill(data);
        fullMediaUrl(data.mediaList);
        common.setFormStatus(data.fgCarried, formIdArr);
        form.render();
        editSelect();
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


    $('#bachAddExamAnswer').on('click', function () {
        common.openParent('辅助检查选择',
            basePath + '/pf/p/kb/part/define/exam/bach/add/page?idMedCase='
            + idMedCase + '&tagFlag=' + tagFlag + '&idTag=' + idTag
            + '&idMedicalrec=' + idMedicalrec + '&caseName=' + caseName,
            800, 480);
    });

    $('#allAddExamAnswer').on('click', function () {
        var y = $(this).offset().top;
        var x = $(this).offset().left;
        addAll(x, y);
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
            var url = basePath + '/pf/r/kb/part/exam/bach/add';
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
    function editSelect() {
        $('.mySelect').find('input').attr("readonly", false)
    }

    $(document).ready(function () {
        if (previewFlag == '1') {
            var formBtnArr = new Array('save');
            common.setFormStatus('0', formBtnArr);
            common.setFormStatus('0', formIdArr);
            layui.form.render('select');
        }
    });

});

