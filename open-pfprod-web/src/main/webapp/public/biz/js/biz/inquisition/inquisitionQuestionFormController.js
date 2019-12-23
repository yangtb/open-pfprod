
layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    xmSelect: 'xm-select'
}).use(['form', 'layer', 'jquery', 'common', 'xmSelect', 'table'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common
        , xmSelect = layui.xmSelect
        , table = layui.table;



    if (formType == 'add') {
        $.ajax({
            url: basePath + '/pf/r/inquisition/question/classify/label/xmSelect',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            //data: JSON.stringify(data.field),
            success: function (data) {
                demo1 = xmSelect.render({
                    el: '#sdInquesLabel',
                    autoRow: true,
                    filterable: true,
                    tree: {
                        //是否显示树状结构
                        show: true,
                        //是否展示三角图标
                        showFolderIcon: true,
                        //是否显示虚线
                        showLine: true,
                        //间距
                        indent: 20,
                        //默认展开节点的数组, 为 true 时, 展开所有节点
                        expandedKeys: true,
                        //是否严格遵守父子模式
                        strict: true,
                    },
                    filterable: true,
                    //height: 'auto',
                    data: data
                })

                return true;
            },
            error: function () {
                common.errorMsg("网络异常");
                return false;
            }
        });
        //渲染多选
        /*treeSelect.render({
            elem: '#sdInquesLabel',
            data: basePath + '/pf/r/inquisition/question/classify/label',
            type: 'post',
            placeholder: '请选择问题标签',
            search: true,
            click: function (d) {
                $("#sdInquesLabel").val(d.current.id);
            }
        });*/
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
        let selectArr = demo1.getValue();
        let sdInquesLabelStr = '';
        if (selectArr.length > 0) {
            $.each(selectArr, function (index, item) {
                if (index < selectArr.length - 1) {
                    sdInquesLabelStr += item.value + ','
                } else {
                    sdInquesLabelStr += item.value
                }

            });
        }
        data.field.sdInquesLabel = sdInquesLabelStr;
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


function fullForm(bizData) {
    $(document).ready(function () {
        $("#questionForm").autofill(bizData);
        layui.use(['form' , 'xmSelect', 'table'], function () {

            var  table = layui.table
                , xmSelect = layui.xmSelect

            if(formType == 'edit') {
                $.ajax({
                    url: basePath + '/pf/r/inquisition/question/classify/label/xmSelect',
                    type: 'post',
                    dataType: 'json',
                    contentType: "application/json",
                    //data: JSON.stringify(data.field),
                    success: function (data) {
                         demo1 = xmSelect.render({
                            el: '#sdInquesLabel',
                            autoRow: true,
                            filterable: true,
                            tree: {
                                //是否显示树状结构
                                show: true,
                                //是否展示三角图标
                                showFolderIcon: true,
                                //是否显示虚线
                                showLine: true,
                                //间距
                                indent: 20,
                                //默认展开节点的数组, 为 true 时, 展开所有节点
                                expandedKeys: true,
                                //是否严格遵守父子模式
                                strict: true,
                            },
                            filterable: true,
                            //height: 'auto',
                            data: data
                        })

                        if (bizData.sdInquesLabel) {
                            demo1.setValue(bizData.sdInquesLabel.split(','))
                        }
                        return true;
                    },
                    error: function () {
                        return false;
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
                    url: basePath + '/pf/p/inquisition/question/pre/list?idInques=' + bizData.idInques,
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



