layui.config({
    base: basePath + '/public/layui/plugins/'
}).extend({
    index: 'lib/index' //主入口模块
}).use(['table', 'form', 'jquery', 'index', 'common', 'view'], function () {
    var $ = layui.$,
        table = layui.table,
        form = layui.form,
        common = layui.common
        , view = layui.view;

    //执行渲染
    table.render({
        elem: '#roomTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'roomTableId'
        , title: '候诊室'
        //, toolbar: '#toolbarDemo1'
        //, defaultToolbar: []
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            {type: 'numbers', title: 'R'},
            {field: 'status', width: 90, title: '接诊状态', templet: '#statusTpl'},
            {field: 'patName', width: 90, title: '患者姓名'},
            {field: 'patSex', width: 70, title: '性别', templet: '#sexTpl'},
            {field: 'distributeDoc', width: 120, title: '分配医师'},
            {field: 'naTestplan', minWidth: 160, title: '测试计划'},
            {field: 'naTestpaper', minWidth: 160, title: '试卷名称', templet: '#naTestpaperTpl'}
        ]] //设置表头
        , url: basePath + '/pf/p/waiting/room/list'
        , limit: 20
        , even: true
        , limits: [20, 30, 50]
        , page: true
        /*, page: {//支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            , groups: 1 //只显示 1 个连续页码
            , first: false //不显示首页
            , last: false //不显示尾页
            , limits: [15, 30, 100]
        }*/
    });

    /*//执行渲染
    table.render({
        elem: '#enumTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'enumTableId'
        , title: '接诊列表'
        , toolbar: '#toolbarDemo2'
        , defaultToolbar: []
        , height: 'full-68' //容器高度
        , cols: [[
            //{checkbox: true, fixed: true},
            {type: 'numbers', title: 'R'},
            {field: 'status', width: 90, title: '接诊状态', templet: '#assessStatusTpl'},
            {field: 'receiveDate', width: 170, title: '接诊时间'},
            {field: 'receiveConsumingTime', width: 120, align: 'right', title: '接诊耗时(m)'},
            {field: 'patName', width: 100, title: '患者姓名'},
            {field: 'patSex', width: 150, title: '性别', templet: '#sexTpl'},
            {field: 'age', width: 80, sort: true, align: 'right', title: '年龄'},
            {field: 'receiveDoc', width: 120, title: '病例'},
            {field: 'medicalrecName', width: 120, title: '接诊医师'},
            {field: 'naTestplan', width: 170, title: '测试计划'},
            {field: 'naTestpaper', width: 170, title: '试卷'},
            {field: 'score', width: 120, title: '评分'},
            {field: 'ch', width: 120, title: '称号', templet: '#chTpl'},
            {field: 'AssessTeacher', width: 120, title: '评估老师'},
            {field: 'AssessDate', width: 170, title: '评估日期'}
        ]] //设置表头
        , url: basePath + '/pf/p/waiting/room/receive/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });*/

    //监听提交
    form.on('submit(enumSearchFilter)', function (data) {
        var queryType = data.field.queryType;
        if (queryType == 1) {

        }
        table.reload('roomTableId', {
            where: {
                medicalrecName: data.field.queryType == '1' ? data.field.keyword : '',
                naTestplan: data.field.queryType == '2' ? data.field.keyword : '',
                naTestpaper: data.field.queryType == '3' ? data.field.keyword : ''
            },
            height: 'full-68'
        });
    });

    //监听行双击事件
    table.on('rowDouble(roomTableFilter)', function (obj) {
        setAttr(obj.data)
    });

    $('#receivePat').on('click', function () {
        var currentData = _getEnumCheckData();
        if (currentData.length == 0) {
            layer.tips('请先选中一行记录', '#receivePat', {tips: 1});
            return;
        }
        if (currentData.length > 1) {
            layer.tips('请选中一行记录进行接诊', '#receivePat', {tips: 1});
            return;
        }
        setAttr(currentData[0])
    });
    // 获取编辑行数据
    var _getEnumCheckData = function () {
        var checkStatus = table.checkStatus('roomTableId')
            , data = checkStatus.data;
        return data;
    }

    function setAttr(data) {
        $('#test').attr('lay-href',
            basePath + '/pf/p/waiting/room/exam/page?idTestplanDetail=' + data.idTestplanDetail
            + '&idDemo=' + data.idDemo + '&idTestplan=' + data.idTestplan + '&idStudent=' + data.idStudent
            + '&idTestpaper=' + data.idTestpaper + '&idMedicalrec=' + data.idMedicalrec);
        $('#patName').text(data.patName + '患者');
        $('#test').click();
    }

    $('#exam').on('click', function () {
        layer.tips('暂未开放', '#exam', {tips: 1});
    });

    $('#zf').on('click', function () {
        var currentData = _getEnumCheckData();
        if (currentData.length == 0) {
            layer.tips('至少选中一行记录', '#zf', {tips: 1});
            return;
        }
        _delPlanDetail(currentData);
    });

    var _delPlanDetail = function (currentData) {
        var reqData = new Array();
        var messageTitle = '';
        var dontDelPlan = '';
        $.each(currentData, function (index, content) {
            var distributeDoc;
            if (content.distributeDoc) {
                distributeDoc = content.distributeDoc;
            } else {
                distributeDoc = "***";
            }
            if (messageTitle) {
                messageTitle += ', ';
            }
            if (content.status != '0') {
                if (dontDelPlan) {
                    dontDelPlan += ', ';
                }
                dontDelPlan += '【' + distributeDoc + '-' + content.naTestplan + '】';
            } else {
                messageTitle += '【' + distributeDoc + '-' + content.naTestplan + '】';
                reqData.push(content.idTestplanDetail);
            }
        });
        var title = dontDelPlan + '<span style="color: #5FB878; font-weight: bold;">正在接诊</span>，<span style="color: red; font-weight: bold;">不允许删除</span>！';
        if (reqData.length == 0) {
            layer.alert('<span style="color: red; font-weight: bold;">您选中的记录不可作废</span>');
            return false;
        } else {
            title += '<br><br><span style="color: #1E9FFF; font-weight: bold;">继续执行将作废:</span>' + messageTitle;
        }

        var data = {
            list: reqData,
            status: '1'
        };
        layer.confirm(title + '<br><br>确定作废么？', {
            title: '删除候诊室记录提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            var url = basePath + '/pf/r/waiting/room/cancel';
            _commonAjax(index, url, data, "作废");
        })
    }

    var _commonAjax = function (index, url, reqData, msg) {
        layer.load(2);
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(reqData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    if (data.code == 'planStatusChange') {
                        layer.alert('<span style="color: red; font-weight: bold;">' + data.msg + '</span>');
                    } else {
                        layer.msg(data.msg, {icon: 5});
                    }
                    return false;
                } else {
                    layer.msg(msg + "成功");
                    if (index) {
                        layer.close(index);
                    }
                    zfCallback();
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                layer.msg(msg + "失败", {icon: 5});
                return false;
            }
        });
    }

    function zfCallback() {
        table.reload('roomTableId', {
            height: 'full-68'
        });
    }

});

