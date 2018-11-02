layui.config({
    base: basePath + '/public/layui/plugins/'
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'element', 'jquery', 'common'], function () {

    var $ = layui.$
        , element = layui.element
        , common = layui.common;

    init();

    function init() {
        var li = document.querySelectorAll("li");
        for (var i = 0; i < li.length; i++) {
            li[i].addEventListener('click', function () {
                setPartType(this.getAttribute('id'));
                loadIframe(this.getAttribute('data-url'), this.getAttribute('data-type'),  this.getAttribute('data-cd'));
            });
        }
    };

    var partType;

    function setPartType(type) {
        partType = type;
    }


    var medCurrentData;
    var tableSelect = layui.tableSelect;
    tableSelect.render({
        elem: '#searchCase',
        searchKey: 'name',
        checkedKey: 'addAnswerBtnId',
        searchPlaceholder: '请输入关键字',
        table: {
            url: basePath + '/pf/p/kb/part/list',
            cols: [[
                {type: 'radio'},
                {field: 'name', width: 130, title: '用例名称'},
                {field: 'descript', width: 160, title: '用例描述'},
                {field: 'orgName', width: 150, title: '归属机构'},
                {
                    field: 'count', width: 100, title: '使用次数',
                    style: 'background-color: #5FB878; color: #fff;text-align:right'
                }
            ]]
            , limits: [10, 20, 50]
            , page: true
        },
        done: function (elem, data) {
            var selectData = data.data[0];
            medCurrentData = selectData;
            $('#caseName').val(selectData.name);
            if (!selectData.script) {
                return;
            }
            $('#caseHistoryTag').attr('src', basePath + selectData.script + '?idMedCase=' + selectData.idMedCase);
        }
    });


    $('#editCase').on('click', function () {
        if (!medCurrentData) {
            layer.tips('请先选择用例', '#editCase', {tips: 1});
        }
        _editUseCase(medCurrentData);
    });

    var _editUseCase = function (data) {
        var index = layui.layer.open({
            title: '组件用例维护 -> ' + '<span style="color: red">' + data.name + '</span>',
            type: 2,
            area: ['900px', '460px'],
            //anim: anim,
            fixed: false, //不固定
            maxmin: true,
            content: basePath + '/pf/p/kb/part/useCase/form?cdMedAsse=' + data.cdMedAsse + '&idMedCase=' + data.idMedCase,
            shadeClose: true,
            success: _successFillUseCase(data)
        });
        layer.full(index);
    };

    var _successFillUseCase = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    };


    var evaCurrentData;
    tableSelect.render({
        elem: '#searchAssess',
        searchKey: 'name',
        checkedKey: 'searchAssessId',
        searchPlaceholder: '请输入关键字',
        table: {
            url: basePath + '/pf/p/kb/assess/list',
            cols: [[
                {type: 'radio'},
                {field: 'name', width: 190, title: '用例名称'},
                {field: 'descript', width: 160, title: '用例描述'},
                {field: 'orgName', width: 150, title: '归属机构'},
                {
                    field: 'count', width: 100, title: '使用次数',
                    style: 'background-color: #5FB878; color: #fff;text-align:right'
                },
            ]]
            , limits: [10, 20, 50]
            , page: true
        },
        done: function (elem, data) {
            var selectData = data.data[0];
            evaCurrentData = selectData;
            $('#assessName').val(selectData.name);

            var url = basePath;
            if (!selectData.script) {
                url += '/pf/p/kb/assess/common/page';
            } else {
                url = url + selectData.script;
            }
            url += '?cdEvaAsse=' + selectData.cdEvaAsse + '&idEvaCase=' + selectData.idEvaCase + '&showForm=0';
            $('#assessTag').attr('src', url);
        }
    });


    $('#editAssess').on('click', function () {
        if (!evaCurrentData) {
            layer.tips('请先选择用例', '#editAssess', {tips: 1});
        }
        _editUseCaseAssess(evaCurrentData);
    });

    var _editUseCaseAssess = function (data) {
        var url = basePath;
        if (!data.script) {
            url += '/pf/p/kb/assess/common/page';
        } else {
            url = url + data.script;
        }
        var index = layui.layer.open({
            title: '组件用例维护 【' + '<span style="color: red">' + data.name + '</span>】',
            type: 2,
            area: ['900px', '460px'],
            //anim: anim,
            fixed: false, //不固定
            maxmin: true,
            content: url + '?cdEvaAsse=' + data.cdEvaAsse + '&idEvaCase=' + data.idEvaCase + '&showForm=0',
            shadeClose: true,
            success: _successFillUseCaseAssess(data)
        });
        layer.full(index);
    };

    var _successFillUseCaseAssess = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    };


    function loadIframe(dataUrl, type, cd) {
        if (dataUrl === basePath || !dataUrl) {
            return;
        }
        var medUrl = medCurrentData ? '?cdMedAsse=' + cd + '&idMedCase=' + medCurrentData.idMedCase : '?idMedCase=';
        var evaUrl = evaCurrentData ? '?cdEvaAsse=' + cd + '&idEvaCase=' + evaCurrentData.idEvaCase + '&showForm=0' : '?showForm=0';
        if (type == 'med') {
            $('#caseHistoryTag').attr('src', dataUrl + medUrl);
        }
        if (type == 'eva') {
            $('#assessTag').attr('src', dataUrl + evaUrl);

        }
    };


    FrameWH();

    function FrameWH() {
        var ifm = document.getElementById("caseHistoryTag");
        ifm.height = document.documentElement.clientHeight;
    }

    $(window).resize(function () {
        FrameWH();
    });


});


