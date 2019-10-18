layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    ckplayer: 'ckplayer/ckplayer'
    , Magnifier: 'js/Magnifier'
    , Event: 'js/Event'
}).use(['table', 'form', 'upload', 'jquery', 'element', 'tableSelect', 'common', 'layer', 'treeSelect'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , common = layui.common
        , element = layui.element
        , layer = layui.layer
        , treeSelect = layui.treeSelect
        , tableSelect = layui.tableSelect;


    treeSelect.render({
        elem: '#sdInquesLabel',
        data: basePath + '/pf/r/inquisition/question/classify/label',
        type: 'post',
        placeholder: '请选择问题标签',
        search: true,
        click: function (d) {
            $("#sdInquesLabel").val(d.current.id);
        }
    });

    treeSelect.render({
        elem: '#idInquesCa',
        data: basePath + '/pf/r/inquisition/question/classify/tree/select',
        type: 'post',
        placeholder: '请选择',
        search: true,
        click: function (d) {
            $("#idInquesCa").val(d.current.id);
            reloadTable($('#keyword').val(), $('#idInquesCa').val())
        }
    });



    // form.render();
    table.render({
        elem: '#test'
        , id: 'preQuestionId'
        , height : 200
        , size : 'sm'
        , cols: [[
            {type: 'numbers', title: '#'}
            , {field:'desInques', minwidth: 150, title: '问题'}
            , {field:'idInquesCaText', width: 150, title: '目录'}
            , {fixed: 'right', width: 80, title: '', align: 'center', toolbar: '#barDemo'}
        ]]
        //, url: basePath + '/pf/p/inquisition/question/pre/list'
        , data: []
    });

    //监听工具条
    table.on('tool(test)', function(obj){
        var layEvent = obj.event;
        if(layEvent === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
            });
        }
    });

    var formIdArr = new Array('searchAnswer', 'desInques', 'idAnswer', 'desAnswer', 'fgReason', 'fgBack', 'desExpert', 'testList', 'testListAction', 'addPreQuestion');

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
        checkedKey: 'idInques',
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
            , height: 'full-50' //容器高度
            //, toolbar: '#toolbarCons'
            //, defaultToolbar: []
            , cols: [[
                {type: 'radio'},
                {field: 'desInques', minWidth: 200, title: '问题'},
                {field: 'isMasculine', minWidth: 135, title: '是否阳性',style:'display:none;'},
                {field: 'desAnswer', minWidth: 180, title: '答案'},
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
            },done: function () {
                $("[data-field='isMasculine']").css('display','none');
            }
        });
    }

    //监听提交
    form.on('submit(queryFilter)', function (data) {
        reloadTable(data.field.keyword, data.field.idInquesCa)
    });

    $('#keyword').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            reloadTable($('#keyword').val(), $('#idInquesCa').val())
            return false;
        }
    });

    function reloadTable(keyword, idInquesCa) {
        table.reload('partConsTableId', {
            where: {
                idMedCase: idMedCase,
                keyword: keyword,
                idInquesCa : idInquesCa
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
        data.field.sdInquesLabel = $("#sdInquesLabel").val();
        var tableData = table.cache["preQuestionId"];
        if (tableData.length > 0) {
            for (var i = 0; i < tableData.length; i++) {
                if (i == 0) {
                    data.field.idInquesPre = tableData[i].idInques;
                } else {
                    data.field["idInquesPre" + (i+1)] = tableData[i].idInques;
                }
            }
        }
        data.field.fgReason = data.field.fgReason ? '1' : '0';
        data.field.fgBack = data.field.fgBack ? '1' : '0';
        data.field.isMasculine = data.field.isMasculine ? '1' : '0';
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

        data.path = data.path ? data.path : '';
        $("#idAnswer").empty();
        $('#idAnswer').append("<option value='" + data.idAnswer + "'>" + data.desAnswer + "</option>");

        $("#consForm").autofill(data);
        common.setFormStatus(data.fgCarried, formIdArr);

        // 撤销选中的节点
        treeSelect.revokeNode('sdInquesLabelTree');
        // 填充treeSelect
        if (data.sdInquesLabel) {
            treeSelect.checkNode('sdInquesLabelTree', data.sdInquesLabel);
        } else {
            $("#sdInquesLabel").val('');
        }
        form.render();

        // 关联触发问题
        fullPreQuestionTable(data.idMedCaseList);
        fullMediaUrl(data.mediaList);
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


    function fullPreQuestionTable(idMedCaseList) {
        $.ajax({
            url: basePath + '/pf/p/kb/part/question/pre/list?idMedCaseList=' + idMedCaseList,
            type: 'get',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg);
                    return false;
                } else {
                    table.reload('preQuestionId', {
                        data : data.data
                    });
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                layer.msg("网络异常");
                return false;
            }
        });
    }

    $('#bachAddConsAnswer').on('click', function () {
        common.openParent('问诊选择',
            basePath + '/pf/p/kb/part/define/cons/bach/add/page?idMedCase='
            + idMedCase + '&tagFlag=' + tagFlag + '&idTag=' + idTag + '&idMedicalrec=' + idMedicalrec
            + '&caseName=' + caseName,
            800, 480);
    });

    $('#allAddConsAnswer').on('click', function () {
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

    $('#addPreQuestion').on('click', function () {
        layui.layer.open({
            title: '<b>添加关联问题</b>',
            type: 2,
            area: ['900px', '550px'],
            fixed: false, //不固定
            maxmin: false,
            content: basePath + '/pf/p/kb/part/question/pre/page?idMedCase=' + idMedCase,
            shadeClose: true
        });
    });

});




function addPreTable(data) {
    console.log(data)
    layui.use(['table'], function () {
        var table = layui.table;
        var oldData = table.cache["preQuestionId"];
        var newArr = oldData.concat(data);
        table.reload('preQuestionId', {
            data : uniq(newArr)
        });
    });
}


function uniq(array) {
    //console.log(array);

    var result = [];
    var obj = {};

    for (var i = 0; i < array.length; i++) {
        console.log("-------->" + result.length)
        if (result.length == 5) {
            break;
        }
        //console.log(array[i])
        if(array[i] && array[i] != null && array[i].idInques) {
            if (!obj[array[i].idInques]) {
                result.push(array[i]);
                obj[array[i].idInques] = true;
            }
        }

    }
    //console.log("---------------");
    //console.log(result);

    return result;
}