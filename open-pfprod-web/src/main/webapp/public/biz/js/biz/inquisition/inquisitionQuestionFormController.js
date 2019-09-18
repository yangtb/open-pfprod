
layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    formSelects: 'formSelects-v4'
}).use(['form', 'layer', 'jquery', 'common', 'formSelects', 'treeSelect', 'table'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common
        , treeSelect = layui.treeSelect
        , table = layui.table;

    if (formType == 'add') {
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
        form.render();
        table.render({
            elem: '#test'
            , id: 'preQuestionId'
            , height : 200
            , size : 'sm'
            , cols: [[
                {type: 'numbers', title: '#'}
                , {field:'desInques', minwidth: 200, title: '问题'}
                , {field:'idInquesCaText', width: 150, title: '目录'}
                , {fixed: 'right', width: 80, title: '', align: 'center', toolbar: '#barDemo'}
            ]]
            //, url: basePath + '/pf/p/inquisition/question/pre/list'
            , data: []
        });
    }

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

    form.verify({
        desInques: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        }
    });

    //监听提交
    form.on('submit(addQuestion)', function (data) {
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
        if (!data.field.fgActive) {
            data.field.fgActive = '0';
        }
        var url = basePath + '/pf/r/inquisition/question/';
        if (formType == 'add') {
            url += 'add';
        } else if (formType == 'edit') {
            url += 'edit';
        }
        return common.commonParentFormPost(url, data.field, formType, 'inquisitionQuestionTableId', '保存');
    });

    $('#addPreQuestion').on('click', function () {
        layui.layer.open({
            title: '<b>添加关联问题</b>',
            type: 2,
            area: ['900px', '550px'],
            fixed: false, //不固定
            maxmin: false,
            content: basePath + '/pf/p/inquisition/question/pre/page',
            shadeClose: true
        });
    });

});


function fullForm(data) {
    $(document).ready(function () {
        $("#questionForm").autofill(data);
        layui.use(['form', 'treeSelect', 'table'], function () {
            var treeSelect= layui.treeSelect
                , table = layui.table;
            if(formType == 'edit') {
                treeSelect.render({
                    elem: '#sdInquesLabel',
                    data: basePath + '/pf/r/inquisition/question/classify/label',
                    type: 'post',
                    placeholder: '请选择问题标签',
                    search: true,
                    // 加载完成后的回调函数
                    success: function (d) {
                        treeSelect.checkNode('sdInquesLabelTree', data.sdInquesLabel);
                    }
                    , click: function (d) {
                        $("#sdInquesLabel").val(d.current.id);
                    }
                });
            }
            layui.form.render();

            if(formType == 'edit') {
                table.render({
                    elem: '#test'
                    , id: 'preQuestionId'
                    , height : 200
                    , size : 'sm'
                    , cols: [[
                        {type: 'numbers', title: '#'}
                        , {field:'desInques', minwidth: 200, title: '问题'}
                        , {field:'idInquesCaText', width: 150, title: '目录'}
                        , {fixed: 'right', width: 80, title: '', align: 'center', toolbar: '#barDemo'}
                    ]]
                    , data: []
                });

                $.ajax({
                    url: basePath + '/pf/p/inquisition/question/pre/list?idInques=' + data.idInques,
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

        });
    });
}

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



