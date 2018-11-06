layui.config({
    base: basePath + '/public/layui/plugins/'
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'element', 'jquery', 'common'], function () {

    var $ = layui.$
        , element = layui.element
        , common = layui.common;

    FrameWH();

    function FrameWH() {
        var ifm = document.getElementById("caseHistoryTag");
        ifm.height = document.documentElement.clientHeight;
    }

    $(window).resize(function () {
        FrameWH();
    });

    var tableSelect = layui.tableSelect;
    var caseTagList = eval('(' + tags + ')');
    var assessTagList = eval('(' + assessTags + ')');

    var currentMedIdTag = '',
        currentEvaIdTag = '',
        currentCdMedAsse = '',
        currentCdEvaAsse = '',
        currentIdMedCase = '',
        currentIdEvaCase = '',
        currentMedIndex = '',
        currentType = '';

    var caseli = document.querySelectorAll(".caseTag"),
        assessli = document.querySelectorAll(".assessTag");

    init();

    function init() {
        for (var i = 0; i < caseli.length; i++) {
            caseli[i].addEventListener('click', function () {
                $(this).addClass("active").siblings().removeClass("active");
                loadIframe(this.getAttribute('data-type'), this.getAttribute('data-index') - 1);
            });
        }

        for (var i = 0; i < assessli.length; i++) {
            assessli[i].addEventListener('click', function () {
                $(this).addClass("active").siblings().removeClass("active");
                loadIframe(this.getAttribute('data-type'), this.getAttribute('data-index') - 1);
            });
        }
        caseli[0].click();
    };

    element.on('tab(tagTabFilter)', function (data) {
        if (!$("#assessTag").attr("src")) {
            currentCdEvaAsse = assessTagList[0].cdEvaAsse;
            assessli[0].click();
            evaRender();
        }
    });


    function loadIframe(type, dataIndex) {
        currentType = type;
        currentMedIndex = dataIndex;

        if (type == 'med') {
            var dataUrl = basePath + caseTagList[dataIndex].script,
                cd = caseTagList[dataIndex].cdMedAsse,
                idTag = caseTagList[dataIndex].idTag,
                dataCase = caseTagList[dataIndex].idMedCase;
            if (!dataUrl) {
                return;
            }
            var medUrl = dataCase ? '?idMedCase=' + dataCase : '?idMedCase=';
            $('#caseName').val('');
            currentMedIdTag = idTag;
            currentIdMedCase = dataCase;
            currentCdMedAsse = cd;
            $('#caseHistoryTag').attr('src', dataUrl + medUrl);
            caseRender();
        }
        if (type == 'eva') {
            var dataUrl = basePath + assessTagList[dataIndex].script,
                cd = assessTagList[dataIndex].cdEvaAsse,
                idTag = assessTagList[dataIndex].idTag,
                dataCase = assessTagList[dataIndex].idEvaCase;
            if (!dataUrl) {
                return;
            }
            var evaUrl = dataCase ? '?cdEvaAsse=' + cd + '&idEvaCase=' + dataCase + '&showForm=0' : '?showForm=0';
            $('#assessName').val('');
            currentEvaIdTag = idTag;
            currentIdEvaCase = dataCase;
            currentCdEvaAsse = cd;
            $('#assessTag').attr('src', dataUrl + evaUrl);
            evaRender();
        }
    };

    function caseRender() {
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
                , where: {
                    cdMedAsse: currentCdMedAsse
                }
            },
            done: function (elem, data) {
                var selectData = data.data[0];
                currentIdMedCase = selectData.idMedCase;
                $('#caseName').val(selectData.name);
                if (!selectData.script) {
                    return;
                }
                $('#caseHistoryTag').attr('src', basePath + selectData.script + '?idMedCase=' + selectData.idMedCase);
            }
        });
    };

    function evaRender() {
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
                , where: {
                    cdEvaAsse: currentCdEvaAsse
                }
            },
            done: function (elem, data) {
                var selectData = data.data[0];
                currentIdEvaCase = selectData.idEvaCase;
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
    }


    $('#save').on('click', function () {
        var bizData = {},
            url = basePath;
        if (currentType == 'med') {
            if (!currentIdMedCase) {
                layer.tips('请选择用例', '#caseName', {tips: 1});
                return;
            }
            bizData = {
                idMedicalrec: idMedicalrec,
                idTag: currentMedIdTag,
                idMedCase: currentIdMedCase

            }
            url += '/pf/r/case/history/med/tag/save';
        } else if (currentType == 'eva') {
            if (!currentIdEvaCase) {
                layer.tips('请选择用例', '#assessName', {tips: 1});
                return;
            }
            bizData = {
                idMedicalrec: idMedicalrec,
                idTag: currentEvaIdTag,
                idEvaCase: currentIdEvaCase

            }
            url += '/pf/r/case/history/med/eva/save';
        }
        return _commonAjax(url, bizData, '保存', currentType);
    });

    var _commonAjax = function (url, reqData, msg, type) {
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
                    layer.msg(data.msg, {icon: 5});
                    return false;
                } else {
                    layer.msg(msg + "成功");
                    if (type == 'med') {
                        caseTagList[currentMedIndex].idMedCase = currentIdMedCase;
                        $('#med-dot-bg' + currentMedIdTag).removeClass('layui-bg-orange')
                            .addClass('layui-bg-green');
                    } else if (type == 'eva') {
                        assessTagList[currentMedIndex].idEvaCase = currentIdEvaCase;
                        $('#eva-dot-bg' + currentMedIdTag).removeClass('layui-bg-orange')
                            .addClass('layui-bg-green');
                    }
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                layer.msg(msg + "失败", {icon: 5});
                return false;
            }
        });
    };

    /*$('#editCase').on('click', function () {
        if (!medCurrentData) {
            layer.tips('请先选择用例', '#editCase', {tips: 1});
        }
        _editUseCase(medCurrentData);
    });

    var _editUseCase = function (data) {
        var index = layui.layer.open({
            title: '组件用例维护 【' + '<span style="color: red">' + data.name + '</span>】',
            type: 2,
            area: ['900px', '460px'],
            //anim: anim,
            fixed: false, //不固定
            maxmin: true,
            content: basePath + '/pf/p/kb/part/useCase/form?cdMedAsse=' + currentCdMedAsse + '&idMedCase=' + currentIdMedCase,
            shadeClose: true
        });
        layer.full(index);
    };


    $('#editAssess').on('click', function () {
        if (!currentIdEvaCase) {
            layer.tips('请先选择用例', '#editAssess', {tips: 1});
        }
        _editUseCaseAssess(currentIdEvaCase);
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
            content: url + '?cdEvaAsse=' + currentCdEvaAsse + '&idEvaCase=' + currentIdEvaCase + '&showForm=0',
            shadeClose: true
        });
        layer.full(index);
    };*/

});


