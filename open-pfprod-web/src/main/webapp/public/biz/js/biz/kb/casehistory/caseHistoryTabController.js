layui.config({
    base: basePath + '/public/layui/plugins/'
}).extend({
    index: 'lib/index' //主入口模块
}).use(['element', 'jquery', 'common', 'index'], function () {

    var $ = layui.$
        , element = layui.element
        , common = layui.common;

    /*FrameWH();

    function FrameWH() {
        var ifm = document.getElementById("caseHistoryTag");
        ifm.height = document.documentElement.clientHeight;
    }

    $(window).resize(function () {
        FrameWH();
    });*/

    var tableSelect = layui.tableSelect;
    var caseTagList = eval('(' + tags + ')');
    var assessTagList = eval('(' + assessTags + ')');

    var currentMedIdTag = '',
        currentEvaIdTag = '',
        currentCdMedAsse = '',
        currentCdEvaAsse = '',
        currentIdMedCase = '',
        currentIdEvaCase = '',
        currentIndex = '',
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
        currentType = data.index == 0 ? 'med' : 'eva';
        if (!$("#assessTag").attr("src")) {
            currentCdEvaAsse = assessTagList[0].cdEvaAsse;
            assessli[0].click();
            evaRender();
        }
    });


    function loadIframe(type, dataIndex) {
        currentType = type;
        currentIndex = dataIndex;

        if (type == 'med') {
            if (!caseTagList[dataIndex].script) {
                $('#caseHistoryTag').attr('src', basePath + '/empty/page');
                //$('#caseName').val("");
                return;
            }
            var dataUrl = basePath + caseTagList[dataIndex].script,
                cd = caseTagList[dataIndex].cdMedAsse,
                idTag = caseTagList[dataIndex].idTag,
                dataCase = caseTagList[dataIndex].idMedCase;
            if (!dataUrl) {
                return;
            }
            var medUrl = dataCase ? '?idMedCase=' + dataCase : '?idMedCase=';
            //$('#caseName').val(caseTagList[dataIndex].caseName);
            currentMedIdTag = idTag;
            currentIdMedCase = dataCase;
            currentCdMedAsse = cd;
            $('#caseHistoryTag').attr('src', dataUrl + medUrl + '&showBtn=1&tagFlag=1'
                + '&idMedicalrec=' + idMedicalrec + '&idTag=' + currentMedIdTag
                + '&caseName=' + caseName);
            caseRender();
        }
        if (type == 'eva') {
            if (!assessTagList[dataIndex].script) {
                $('#assessTag').attr('src', basePath + '/empty/page');
                $('#assessName').val("");
                return;
            }
            var dataUrl = basePath + assessTagList[dataIndex].script,
                cd = assessTagList[dataIndex].cdEvaAsse,
                idTag = assessTagList[dataIndex].idTag,
                dataCase = assessTagList[dataIndex].idEvaCase;
            if (!dataUrl) {
                return;
            }
            if (!dataCase) {
                dataCase = '';
            }
            var evaUrl = '?cdEvaAsse=' + cd + '&idEvaCase=' + dataCase + '&showForm=0';
            //$('#assessName').val(assessTagList[dataIndex].caseName);
            currentEvaIdTag = idTag;
            currentIdEvaCase = dataCase;
            currentCdEvaAsse = cd;
            evaUrl += '&idMedicalrec=' + idMedicalrec + '&idTag=' + currentEvaIdTag
                + '&caseName=' + caseName;
            $('#assessTag').attr('src', dataUrl + evaUrl + '&showBtn=1&tagFlag=1');
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
                if (!data) {
                    return;
                }
                var selectData = data.data[0];
                // 另存一份病例
                saveAsCase(selectData);
            }
        });
    };

    function saveAsCase(selectData) {
        var bizData = {
            idMedicalrec: idMedicalrec,
            idTag: currentMedIdTag,
            oldIdMedCase: selectData.idMedCase,
            tagFlag: '1',
            caseName: caseName + '-' + caseTagList[currentIndex].name,
            cdMedAsse: selectData.cdMedAsse
        };
        var url = basePath;
        if (selectData.cdMedAsse == '001') {
            url += '/pf/r/kb/part/text/save';
        }
        if (selectData.cdMedAsse == '002') {
            url += '/pf/r/kb/part/pic/save';
        }
        if (selectData.cdMedAsse == '003') {
            url += '/pf/r/kb/part/pat/save';
        }
        if (selectData.cdMedAsse == '004' || selectData.cdMedAsse == '005' || selectData.cdMedAsse == '006') {
            url += '/pf/r/case/history/save/as/med';
        }
        $.ajax({
            url: url,
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
                    saveMedAfter(selectData);
                    return true;
                }
            },
            error: function () {
                common.errorMsg("另存用例出错");
                return false;
            }
        });
    }

    function saveMedAfter(selectData) {
        if (currentCdMedAsse != selectData.cdMedAsse) {
            var idx = '';
            caseTagList.map(function (item, index) {
                if (item.cdMedAsse === selectData.cdMedAsse) idx = index;
            })
            $('#med-' + selectData.cdMedAsse).addClass("active").siblings().removeClass("active");
            currentMedIdTag = caseTagList[idx].idTag;
            currentCdMedAsse = caseTagList[idx].cdMedAsse;
            currentIndex = idx;
        }
        currentIdMedCase = selectData.idMedCase;
        //$('#caseName').val(selectData.name);
        if (!selectData.script) {
            return;
        }
        $('#caseHistoryTag').attr('src', basePath + selectData.script
            + '?idMedCase=' + selectData.idMedCase + '&showBtn=1&tagFlag=1'
            + '&idMedicalrec=' + idMedicalrec + '&idTag=' + currentMedIdTag
            + '&caseName=' + caseName);

    }

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
                if (!data) {
                    return;
                }

                var selectData = data.data[0];
                // 另存一份病例
                saveAsEva(selectData);
            }
        });
    }

    function saveAsEva(selectData) {
        var bizData = {
            idMedicalrec: idMedicalrec,
            idTag: currentEvaIdTag,
            oldIdEvaCase: selectData.idEvaCase,
            tagFlag: '1',
            caseName: caseName + '-' + assessTagList[currentIndex].name,
            cdEvaAsse: selectData.cdEvaAsse
        };
        $.ajax({
            url: basePath + '/pf/r/case/history/save/as/eva',
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
                    saveEvaAfter(selectData);
                    return true;
                }
            },
            error: function () {
                common.errorMsg("另存评估出错");
                return false;
            }
        });
    }

    function saveEvaAfter(selectData) {
        if (currentCdMedAsse != selectData.cdEvaAsse) {
            var idx = '';
            assessTagList.map(function (item, index) {
                if (item.cdEvaAsse === selectData.cdEvaAsse) idx = index;
            })
            $('#eva-' + selectData.cdEvaAsse).addClass("active").siblings().removeClass("active");
            currentEvaIdTag = assessTagList[idx].idTag;
            currentCdEvaAsse = assessTagList[idx].cdEvaAsse;
            currentIndex = idx;
        }
        currentIdEvaCase = selectData.idEvaCase;
        //$('#assessName').val(selectData.name);

        var url = basePath;
        if (!selectData.script) {
            url += '/pf/p/kb/assess/common/page';
        } else {
            url = url + selectData.script;
        }
        url += '?cdEvaAsse=' + selectData.cdEvaAsse
            + '&idEvaCase=' + selectData.idEvaCase
            + '&showForm=0&showBtn=1&tagFlag=1' + '&idMedicalrec=' + idMedicalrec + '&idTag=' + currentMedIdTag
            + '&caseName=' + caseName;
        $('#assessTag').attr('src', url);

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

    $('#saveAs').on('click', function () {
        layer.tips('正在开发。。。', '#saveAs', {tips: 1});
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
                        caseTagList[currentIndex].idMedCase = currentIdMedCase;
                        /*$('#med-dot-bg' + currentMedIdTag).removeClass('layui-bg-orange')
                            .addClass('layui-bg-green');*/
                    } else if (type == 'eva') {
                        assessTagList[currentIndex].idEvaCase = currentIdEvaCase;
                        /*$('#eva-dot-bg' + currentEvaIdTag).removeClass('layui-bg-orange')
                            .addClass('layui-bg-green');*/
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

    $('#editCase').on('click', function () {
        if (!currentIdMedCase && !currentCdMedAsse) {
            layer.tips('请先选择用例', '#editCase', {tips: 1});
        }
        _editUseCase();
    });

    var _editUseCase = function () {
        var url = basePath;
        if (!caseTagList[currentIndex].script) {
            return;
        } else {
            url = url + caseTagList[currentIndex].script;
        }
        var index = layui.layer.open({
            title: '组件用例维护 【' + '<span style="color: red">' + $('#caseName').val() + '</span>】',
            type: 2,
            area: ['900px', '460px'],
            //anim: anim,
            fixed: false, //不固定
            maxmin: true,
            content: url + '?&idMedCase=' + currentIdMedCase + '&showForm=0',
            shadeClose: true,
            zIndex: layer.zIndex, //重点1
            success: function (layero) {
                layer.setTop(layero); //重点2
            }
        });
        layer.full(index);
    };


    $('#editAssess').on('click', function () {
        if (!currentIdEvaCase && !currentCdEvaAsse) {
            layer.tips('请先选择用例', '#editAssess', {tips: 1});
        }
        _editUseCaseAssess();
    });

    var _editUseCaseAssess = function () {
        var url = basePath;
        if (!assessTagList[currentIndex].script) {
            url += '/pf/p/kb/assess/common/page';
        } else {
            url = url + assessTagList[currentIndex].script;
        }
        var index = layui.layer.open({
            title: '组件用例维护 【' + '<span style="color: red">' + $('#assessName').val() + '</span>】',
            type: 2,
            area: ['900px', '460px'],
            //anim: anim,
            fixed: false, //不固定
            maxmin: true,
            content: url + '?cdEvaAsse=' + currentCdEvaAsse + '&idEvaCase=' + currentIdEvaCase + '&showForm=0',
            shadeClose: true
        });
        layer.full(index);
    };

});


