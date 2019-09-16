layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    ckplayer: 'ckplayer/ckplayer'
}).use(['table', 'jquery', 'form', 'common', 'element'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common
        , element = layui.element;

    $(document).ready(function () {
        $("#treeDemo").css("min-height", 650);
        $("#treeDemo").css("max-height", 650);

        $('#rollBar').on('click', function () {

            let $rollBarText = $('#rollBarText');
            if ($rollBarText.text() == '收起') {
                $rollBarText.text('展开');
                $('#rollObject').removeClass().addClass('shrink');
                let $rollQuestion = $('#rollQuestion');
                $rollQuestion.css("padding-left", 0).css("margin-left", 0);
                let width = $("th[data-field='desInques']").width() + 190;
                $("th[data-field='desInques']").css("width", width + 5);
                $("td[data-field='desInques']").css("width", width + 5);
            } else {
                $rollBarText.text('收起');
                $('#rollObject').removeClass().addClass('expend');
                $('#rollQuestion').css("padding-left", 5).css(  "margin-left", 190);

                let width = $("th[data-field='desInques']").width() - 190 - 160;
                setTimeout(function () {
                    $("th[data-field='desInques']").css("width", width - 5);
                }, 1000)
            }


        })
    });

    //********************zTree***********************
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
            dblClickExpand: false
        },
        callback: {
            onClick: zTreeOnClick
        }
    };

    var zTree;
    $(document).ready(function () {
        layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/inquisition/question/classify/tree',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    var zNodes = data.data;
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                    zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    });

    function zTreeOnClick(event, treeId, treeNode) {
        _tableReload(treeNode.id, null);
    };

    //执行渲染
    function rebderTable() {
        table.render({
            elem: '#partConsTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'partConsTableId'
            , height: '600' //容器高度
            , cols: [[
                {type: 'numbers',width: 50, title: 'R'},
                {field: 'desInques', title: '问题'},
                {field: 'qa', width: 60, title: '提问', fixed: 'right', align: 'center', templet: '#qaTpl'}
            ]] //设置表头
            , url: basePath + '/pf/p/waiting/room/test/cons/list'
            , where: {
                idMedicalrec: idMedicalrec,
                cdMedAsse: cdMedAsse,
                idTestexecResult: idTestexecResult
            }
            , limit: 20
            , page: {//支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next'] //自定义分页布局
                , groups: 1 //只显示 1 个连续页码
                , first: false //不显示首页
                , last: false //不显示尾页
                , limits: [20, 30, 50]
            }
        });
    }

    //监听行双击事件
    table.on('rowDouble(partConsTableFilter)', function (obj) {
        let checkInput = $('#cons' + obj.data.idMedCaseList);

        if (!checkInput[0].checked) {
            var data = {
                idTestexecResult: idTestexecResult,
                idInques: obj.data.idInques,
                idMedCaseList: obj.data.idMedCaseList,
                fgValid: '0'
            };
            var url = basePath + '/pf/r/waiting/room/cons/qa/save';

            common.commonPost(url, data, null, null, queryQa, false);
            checkInput.attr("disabled", true);

            let checkCell = obj.tr.find(".layui-form-checkbox");
            if (!obj.tr.hasClass('layui-table-click')) {
                obj.tr.addClass('layui-table-click');
                checkCell.addClass('layui-form-checked');
                checkCell.addClass('layui-checkbox-disbaled');
            }
        }
    });

    function _tableReload(extItemId, keyword) {
        table.reload('partConsTableId', {
            where: {
                idMedicalrec: idMedicalrec,
                cdMedAsse: cdMedAsse,
                idTestexecResult: idTestexecResult,
                extItemId: extItemId,
                keyword: keyword
            }
            , height: '604'
        });
    }

    $('#queryBtn').on('click', function () {
        _tableReload(null, $('#keyword').val())
    });

    $('.layui-col-md4').on('click', function () {
        $('#key-tab').hide();
        rebderTable();
        $('#table').show();
        $('#k').text(($(this).text().trim()));
    });

    $('#back').on('click', function () {
        $('#table').hide();
        $('#key-tab').show();
    });

    $('#keyword').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            _tableReload(null, $('#keyword').val())
            return false;
        }
    });

    form.on('checkbox(qaConsFilter)', function (obj) {
        var qaValue = this.value;
        var qaArr = qaValue.split("-");
        var data = {
            idTestexecResult: idTestexecResult,
            idInques: qaArr[0],
            idMedCaseList: qaArr[1],
            fgValid: obj.elem.checked ? '0' : '1'
        }
        var url = basePath + '/pf/r/waiting/room/cons/qa/save';
        common.commonPost(url, data, null, null, queryQa, false);
        if (obj.elem.checked) {
            $('#cons' + qaArr[1]).attr("disabled", true);
        }

    });

    element.on('tab(docDemoTabBrief)', function(data){
    });

    // 页面加载完成查询问答
    $(document).ready(function () {
        queryQa();
    });

    $('#refreshRight').on('click', function () {
        layer.load(2);
        queryQa();
        return false;
    });

    // 显示专家解读
    var showExpertFlag = executingShowExpert == 'Y' ? true : false;
    if (showExpertFlag == false) {
        showExpertFlag = sdTestexec == '2' && completedShowExpert == 'Y' ? true : false;
    }

    function queryQa() {
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/cons/qa/list',
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
                    qaHtml(data.data, showExpertFlag);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("查询问答失败");
                return false;
            }
        });
    }

    function qaHtml(data, showExpertFlag) {
        var result = '<li style="padding-top: 1px;">\n' +
            ' <div class=\'chat\' >\n' +
            '  <div style=\'overflow: hidden\'>\n' +
            '   <div style=\'float: left \'>\n' +
            '    <img src=' + basePath + '/public/layui/build/images/p3.png alt="">\n' +
            '   </div>\n' +
            '   <div style=\'float: left;  width:70%\'>\n' +
            '    <span>&nbsp  开始接诊</span>\n' +
            '   </div>\n' +
            '  </div>\n' +
            '\n' +
            ' </div >\n' +
            ' </div >\n' +
            '</li >'+
            '<li>\n' +
            ' <div class=\'chat\' >\n' +
            '  <div style=\'overflow: hidden\'>\n' +
            '   <div style=\'float: left \'>\n' +
            '    <img src=' + basePath + '/public/layui/build/images/p2.png alt="">\n' +
            '   </div>\n' +
            '   <div style=\'float: left;  width:70%\'>\n' +
            '    <span>&nbsp  '+ name + "，"+ sex + "，" + age + "岁" +'</span>\n' +
            '   </div>\n' +
            '  </div>\n' +
            '\n' +
            ' </div >\n' +
            ' </div >\n' +
            '</li >'+
            '<li>\n' +
            ' <div class=\'chat\' >\n' +
            '  <div style=\'overflow: hidden\'>\n' +
            '   <div style=\'float: left; \'>\n' +
            '    <img src=' + basePath + '/public/layui/build/images/p1.png alt="">\n' +
            '   </div>\n' +
            '   <div style=\'float: left; margin-left: 8px;  width:70%\'>\n' +
            '    <span>'+ complaint +'</span>\n' +
            '   </div>\n' +
            '  </div>\n' +
            '\n' +
            ' </div >\n' +
            ' </div >\n' +
            '</li >';
        for (var i = 0; i < data.length; i++) {
            if (showExpertFlag == false) {
                result += appendQaNormalHtml(data[i]);
            } else {
                result += appendQaShowExpertHtml(data[i]);
            }
        }

        $('#serviceBox').empty();
        $('#serviceBox').append(result);

        var objDiv = document.getElementById("autoScroll");
        objDiv.scrollTop = objDiv.scrollHeight;
    }

    function appendQaNormalHtml(data) {
        //1 图片 2 音频 3 视频 4 其它

        let patientVar = null;
        switch (data.sdType) {
            case '1':
                patientVar = '<span>' + data.desAnswer + '</span>\n'+
                    '         <img class="response-img" id="patientImg' + data.idAnswer + '"  src="' + data.path + '" alt="" style="max-width: 350px; height: 200px;cursor: pointer;"' +
                    '                      onerror="onError(this)"' +
                    '                      onclick="openMedia(' + data.sdType + ',' + data.idAnswer + ')">\n';
                break;
            case '2':
                patientVar = '<span>' + data.desAnswer + '</span>\n'+
                    '           <p class="voice-box">\n' +
                    '               <audio class="patient-voice" id="patientVoice' + data.idAnswer + '" src="' + data.path + '"></audio>\n' +
                    '               <button class="sound-icon" style="cursor: pointer;" onclick="control(' + data.idAnswer + ')"><img src=' + basePath + '/public/layui/build/images/horn.png alt=""></button>\n' +
                    '           </p>\n';
                break;
            case '3':
                patientVar = '  <span class="time">12"</span>\n' +
                    '           <p class="voice-box">\n' +
                    '               <audio class="patient-voice" id="patientVideo' + data.idAnswer + '" src="' + data.path + '"></audio>\n' +
                    '               <button class="sound-icon" style="cursor: pointer;" onclick="openMedia(' + data.sdType + ',' + data.idAnswer + ')"><i class="iconfont icon-11"></i></button>\n' +
                    '           </p>\n';
                break;
            default:
                var begin = '<span>';
                if (data.isMasculine == "1") {
                    begin = '<span class="label-danger">';
                }
                patientVar = begin + data.desAnswer + '</span>\n';
        }

        var html = "                <li>\n" +
            "                    <div class='chat'>\n" +
            "                        <div style='overflow: hidden'>\n" +
            "                            <div style='float: left '>\n" +
            '                           <img src=' + basePath + '/public/layui/build/images/chat.png alt="">\n' +
            "                                <span>【医生】</span>\n" +
            "                            </div>\n" +
            "                            <div style='float: left;  width:70%'>\n" +
            '                               <span>' + data.desInques + '</span>\n' +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div style='margin-left: 41px; margin-top: 7px; overflow: hidden'>\n" +
            "                            <div style='float: left'>\n" +
            "                                <span>【患者】</span>\n" +
            "                            </div>\n" +
            "                            <div style='float: left; width: 70%'>\n" +
            patientVar
            +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </li>";


        return html;
    }

    function appendQaShowExpertHtml(data) {
        if (!data.desExpert) {
            data.desExpert = '';
        }
        var html = '<li class="other-side">\n' +
            '           <div class="doctor-details">\n' +
            '               <input class="details-select" type="checkbox" name="复选框" id="">\n' +
            '               <img src="' + basePath + '/public/biz/img/exam/doctor-avatar.png" alt="" class="doctor-avatar" style="width: 40px; height: 40px;">\n' +
            '               <p class="doctor-response">' + data.desInques + '</p>\n' +
            '            </div>\n' +
            '       </li>\n';

        if (!data.sdType) {
            html += '<li class="patient">\n' +
                '       <div class="patient-details">\n' +
                '           <p class="patient-response">' + data.desAnswer + '</p>\n' +
                '           <img class="patient-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </div>\n';
            if (data.desExpert) {
                html += '   <div class="official-details">\n' +
                    '           <span class="details-text">专家解读</span>\n' +
                    '           <p class="official-text">' + data.desExpert + '</p>\n' +
                    '       </div>\n';
            }
            html += '   </li>';
        } else if (data.sdType == '1') {
            html += '<li class="patient-img-response">\n' +
                '       <p class="text">' + data.desAnswer + '</p>\n' +
                '       <p class="img-box">\n' +
                '           <img class="response-img" id="patientImg' + data.idAnswer + '"  src="' + data.path + '" alt="" style="max-width: 350px; height: 200px;cursor: pointer;"' +
                '               onerror="onError(this)"' +
                '               onclick="openMedia(' + data.sdType + ',' + data.idAnswer + ')">\n' +
                '           <img class="patient-img-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </p>\n';
            if (data.desExpert) {
                html += '   <div class="official-details">\n' +
                    '           <span class="details-text">专家解读</span>\n' +
                    '           <p class="official-text">' + data.desExpert + '</p>\n' +
                    '       </div>\n';
            }
            html += '   </li>';
        } else if (data.sdType == '2') {
            html += '<li class="patient">\n' +
                '       <p class="text">' + data.desAnswer + '</p>\n' +
                '       <div class="patient-details">\n' +
                '           <span class="time">12"</span>\n' +
                '           <p class="voice-box">\n' +
                '               <audio class="patient-voice" id="patientVoice' + data.idAnswer + '" src="' + data.path + '"></audio>\n' +
                '               <button class="sound-icon" style="cursor: pointer;" onclick="control(' + data.idAnswer + ')"><i class="iconfont icon-shengyin"></i></button>\n' +
                '           </p>\n' +
                '           <img class="patient-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </div>\n';
            if (data.desExpert) {
                html += '   <div class="official-details">\n' +
                    '           <span class="details-text">专家解读</span>\n' +
                    '           <p class="official-text">' + data.desExpert + '</p>\n' +
                    '       </div>\n';
            }
            html += '   </li>';
        } else if (data.sdType == '3') {
            html += '<li class="patient">\n' +
                '       <p class="text">' + data.desAnswer + '</p>\n' +
                '       <div class="patient-details">\n' +
                '           <span class="time">12"</span>\n' +
                '           <p class="voice-box">\n' +
                '               <audio class="patient-voice" id="patientVideo' + data.idAnswer + '" src="' + data.path + '"></audio>\n' +
                '               <button class="sound-icon" style="cursor: pointer;" onclick="openMedia(' + data.sdType + ',' + data.idAnswer + ')"><i class="iconfont icon-11"></i></button>\n' +
                '           </p>\n' +
                '           <img class="patient-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </div>\n';
            if (data.desExpert) {
                html += '   <div class="official-details">\n' +
                    '           <span class="details-text">专家解读</span>\n' +
                    '           <p class="official-text">' + data.desExpert + '</p>\n' +
                    '       </div>\n';
            }
            html += '   </li>';
        }

        return html;
    }

});


function control(idAnswer) {
    var audio = document.querySelector('#patientVoice' + idAnswer);
    if (audio !== null) {
        //检测播放是否已暂停.audio.paused 在播放器播放时返回false.
        if (audio.paused) {
            audio.play();//audio.play();// 这个就是播放
        } else {
            audio.pause();// 这个就是暂停
        }
    }
}

/**
 * 打开多媒体
 */
function openMedia(sdType, idAnswer) {
    layui.use('common', function () {
        if (sdType == '1') {
            var path = $('#patientImg' + idAnswer).attr('src');
            layui.common.openSinglePhoto(path);
        } else if (sdType == '3') {
            var path = $('#patientVideo' + idAnswer).attr('src');
            layui.common.openTopVideo(basePath + '/video/form?path=' + path, 890, 504);
        }
    });
}

function checkQa(obj) {
    var reqData = new Array();
    reqData.push(obj.value);

    var data = {};
    data.list = reqData;

    data.status = obj.checked ? '1' : '0';
    $.ajax({
        url: basePath + '/pf/r/waiting/room/cons/qa/status',
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (data) {
            layer.closeAll('loading');
            if (data.code != 0) {
                layui.use('common', function () {
                    layui.common.errorMsg(data.msg);
                });
                return false;
            }
        },
        error: function () {
            layer.closeAll('loading');
            layui.use('common', function () {
                layui.common.errorMsg("线索标记失败");
            });
            return false;
        }
    });
}

function onError(obj) {
    obj.src = basePath + '/public/biz/img/tupianjiazaishibai.png';
}
