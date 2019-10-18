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
        $('#key-tab').hide();
        rebderTable();
        $('#table').show();
        _tableReload(treeNode.id, null);
    };

    //执行渲染
    function rebderTable(sdInquesLabel) {
        table.render({
            elem: '#partConsTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'partConsTableId'
            , height: '580' //容器高度
            , cols: [[
                {type: 'numbers',width: 50, title: 'R'},
                {field: 'desInques', title: '问题'},
                {field: 'qa', width: 60, title: '提问', fixed: 'right', align: 'center', templet: '#qaTpl'}
            ]] //设置表头
            , url: basePath + '/pf/p/waiting/room/test/cons/list'
            , where: {
                idMedicalrec: idMedicalrec,
                cdMedAsse: cdMedAsse,
                idTestexecResult: idTestexecResult,
                sdInquesLabel : sdInquesLabel
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
                keyword: keyword,
                sdInquesLabel : null
            }
        });
    }

    $('#queryBtn').on('click', function () {
        _tableReload(null, $('#keyword').val())
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
        var bizData = {
            idTestexecResult: idTestexecResult,
            idInques: qaArr[0],
            idMedCaseList: qaArr[1],
            fgValid: obj.elem.checked ? '0' : '1'
        }

        $.ajax({
            url: basePath + '/pf/r/waiting/room/cons/qa/save',
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
                    queryResult(bizData.idInques)
                    if (obj.elem.checked) {
                        $('#cons' + qaArr[1]).attr("disabled", true);
                    }
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("网络异常");
                return false;
            }
        });
    });

    form.on('checkbox(qaPreConsFilter)', function (obj) {
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
            $('#consPre' + qaArr[1]).attr("disabled", true);
        }
    });

    element.on('tab(docDemoTabBrief)', function(data){
    });

    // 页面加载完成查询问答
    $(document).ready(function () {
        queryQa();
        // 加载问诊标签
        loadConsLabel();
    });

    function loadConsLabel() {
        $.ajax({
            url: basePath + '/pf/r/inquisition/question/classify/label',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                layer.closeAll('loading');

                $('#labelTab').empty();
                $('#labelContent').empty();

                if (data) {
                    $.each(data, function (index, context) {
                        //console.log(context);
                        var styleTab = index == 0 ? 'layui-this' : '';
                        $('#labelTab').append('<li class="' + styleTab + '">' + context.name + '</li>');
                        if (context.children) {
                            //console.log("------------------");
                            var styleContent = index == 0 ? 'layui-show' : '';
                            var attrContent = index == 0 ? 'id="key"' : '';

                            var html = ' <div class="layui-tab-item ' + styleContent + '" ' + attrContent + '>\n';
                            $.each(context.children, function (index, context) {
                                //console.log(context);
                                html += '<div class="layui-col-md4" data-key="' + context.id + '">\n' +
                                    '        <div>' + context.name + '</div>\n' +
                                    '    </div>\n';

                            });
                            html += '    </div>';
                            $('#labelContent').append(html);
                            //console.log("------------------");
                        } else {
                            $('#labelContent').append('<div class="layui-tab-item"</div>');
                        }
                    });
                }

                $('.layui-col-md4').on('click', function () {
                    var sdInquesLabel = $(this).attr("data-key");
                    $('#key-tab').hide();
                    rebderTable(sdInquesLabel);
                    $('#table').show();
                    $('#k').text(($(this).text().trim()));
                });

                return true;
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("查询问诊标签失败");
                return false;
            }
        });
    }

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

    function queryResult(idInques) {
        // 问诊结果
        queryQa();
        // 查询是否有前置条件
        queryInquesPre(idInques);
    }
    
    function queryInquesPre(idInques) {
        var bizData = {
            idMedicalrec: idMedicalrec,
            cdMedAsse: cdMedAsse,
            idTestexecResult: idTestexecResult,
            inquesPreFlag : 1,
            idInques : idInques
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/test/cons/list',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');

                if (!data || data.length == 0) {
                    return true;
                }

                var elem = $('#queryBtn');
                var t = elem.offset().top + elem.outerHeight() + "px";
                var l = (elem.offset().left + 60) + "px";

                var html =
                    '<div id="div-pop-cons-pre" class="layui-anim layui-anim-upbit" style="left:' + l + ';top:' + t + ';border: 1px solid #d2d2d2;background-color: #fff;box-shadow: 0 2px 4px rgba(0,0,0,.12);padding:0px 0px 0 0px;position: absolute;z-index:666;margin: 5px 0;border-radius: 2px;width:450px; height: 350px;">' +
                    '    <table id="partConsPreTable" lay-filter="partConsPreTableFilter">\n' +
                    '    </table>' +
                    '</div>';

                var formBox = $(html);
                $('body').append(formBox);

                renderConsPreTable(data);

                return true;

            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("网络异常");
                return false;
            }
        });
    }

    function renderConsPreTable(data) {
        table.render({
            elem: '#partConsPreTable' //指定原始表格元素选择器（推荐id选择器）
            , id: 'partConsPreTableId'
            , height: '350' //容器高度
            , cols: [[
                {type: 'numbers',width: 50, title: 'R'},
                {field: 'desInques', title: '问题'},
                {field: 'qa', width: 60, title: '提问', fixed: 'right', align: 'center', templet: '#qaPreTpl'}
            ]] //设置表头
            , data : data
            , page: false
        });
    }

    //点击其他区域关闭
    $(document).mouseup(function (e) {
        var userSet_con = $('#div-pop-cons-pre');
        if (!userSet_con.is(e.target) && userSet_con.has(e.target).length === 0) {
            $('#div-pop-cons-pre').remove();
        }
    });

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

        //添加按钮点击事件
        $('.cons-reason').on('click', function () {
            var idTestexecResultInques = this.getAttribute("data-index");
            layer.prompt({title: '<strong>检查理由</strong>', formType: 2}, function(text, index){
                layer.close(index);
                editExmMedResultInques(idTestexecResultInques, text, '');
            });
        })

        $('.cons-reply').on('click', function () {
            var idTestexecResultInques = this.getAttribute("data-index");
            var desExpert = this.getAttribute("data-expert");

            layer.prompt({title: '<strong>解释患者回复</strong>', formType: 2}, function(text, index){
                layer.close(index);
                editExmMedResultInques(idTestexecResultInques, '', text, desExpert);
            });
        })

        registerexpertLinkEvent();
    }
    function editExmMedResultInques(id, desReason, desReply, desExpert) {
        var bizData = {
            id : id,
            desReason : desReason,
            desReply : desReply,
            type : 1
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/qa/edit',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg);
                    return false;
                } else {
                    if (desReason) {
                        $('#reason-' + id).empty();

                        var html =
                            "                           <div style='background-color: #F2F2F2; padding: 10px; margin: 5px 5px 5px 0;'>\n" +
                            "                               <div style='font-weight: bold; padding: 2px;'>● 问诊理由</div>\n" +
                            "                               <div>" + desReason + "</div>\n" +
                            "                           </div>\n";
                        $('#reason-' + id).append(html);
                    }
                    if (desReply) {
                        $('#reply-' + id).empty();

                        var html =
                            "                           <div style='background-color: #F2F2F2; padding: 10px; margin: 5px 5px 5px 0;'>\n" +
                            "                               <div style='font-weight: bold; padding: 2px;'>● 解释患者的回复</div>\n" +
                            "                               <div>" + desReply + "</div>\n" +
                            "                           </div>\n";
                        if (desExpert) {
                            html +=
                                '                           <div style="margin-top: 10px;"><a class="expertLink" data-index="' + id + '" href="javascript:;" style="color: #009688; text-decoration: underline;">专家解读？</a></div>\n';
                            html +=
                                "                           <div id='expert-div-" + id + "' style='background-color: #F2F2F2; display: none; padding: 10px; margin: 5px 5px 5px 0;'>\n" +
                                "                               <div style='font-weight: bold; padding: 2px;'>● 专家解读</div>\n" +
                                "                               <div>" + desExpert + "</div>\n" +
                                "                           </div>\n";
                        }
                        $('#reply-' + id).append(html);

                        if (desExpert) {
                            registerexpertLinkEvent();
                        }
                    }
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

    function registerexpertLinkEvent() {
        $('.expertLink').on('click', function () {
            var idTestexecResultInques = this.getAttribute("data-index");
            //alert(idTestexecResultInques)

            if ($("#reply-btn-" + idTestexecResultInques).length > 0) {
                $('#reply-btn-' + idTestexecResultInques).addClass("layui-btn-disabled");
                $('#reply-btn-' + idTestexecResultInques).attr("disabled", "disabled");
            }
            $(this).hide();
            $('#expert-div-' + idTestexecResultInques).css("display","block");
        })
    }
    
    function appendMediahtml(data) {
        let patientVar = '';
        var begin = '<span>';
        if (data.isMasculine == "1") {
            begin = '<span class="label-danger">';
        }
        patientVar += begin + data.desAnswer + '</span>\n';

        let picHtml = '', voiceHtml = '', videoHtml = '';
        if (data.mediaList && data.mediaList.length > 0) {
            $.each(data.mediaList, function (index, item) {
                if (item.sdType == '1') {
                    picHtml += '         <img id="patientImg' + item.idMedia + '"  src="' + item.path + '" alt="" style="width: 50px; height: 50px;cursor: pointer;"' +
                        '                      onerror="onError(this)"' +
                        '                      onclick="openMedia(' + item.sdType + ',' + item.idMedia + ')">\n';
                } else if (item.sdType == '2') {
                    voiceHtml += '  <p class="voice-box" style="margin-right: 0px;">\n' +
                        '               <audio class="patient-voice" id="patientVoice' + item.idMedia + '" src="' + item.path + '"></audio>\n' +
                        '               <button class="sound-icon" style="cursor: pointer;" onclick="openMedia(' + item.sdType + ', ' + item.idMedia + ')"><img src=' + basePath + '/public/layui/build/images/horn.png alt=""></button>\n' +
                        '           </p>\n' ;
                } else if (item.sdType == '3') {
                    videoHtml +=   '<p class="voice-box">\n' +
                        '               <audio class="patient-voice" id="patientVideo' + item.idMedia + '" src="' + item.path + '"></audio>\n' +
                        '               <button class="sound-icon" style="cursor: pointer;" onclick="openMedia(' + item.sdType + ',' + item.idMedia + ')"><i class="iconfont icon-11"></i></button>\n' +
                        '           </p>\n';
                }
            })
        }

        patientVar += '<div>' + picHtml + '</div>';
        patientVar += '<div style="padding-top: 5px;">' + voiceHtml + '</div>';
        patientVar += '<div style="padding-top: 5px;">' + videoHtml + '</div>';

        return patientVar;
    }

    function appendQaNormalHtml(data) {
        //1 图片 2 音频 3 视频 4 其它

        let patientVar = appendMediahtml(data);
        /*switch (data.sdType) {
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
        }*/


        console.log(data)
        var html = "           <li>\n" +
            "                    <div class='chat'>\n" +
            "                        <div style='overflow: hidden'>\n" +
            "                            <div style='float: left '>\n" +
            '                            <img src=' + basePath + '/public/layui/build/images/chat.png alt="">\n' +
            "                                <span>【医生】</span>\n" +
            "                            </div>\n" +
            "                            <div style='float: left;  width:70%'>\n" +
            '                               <span>' + data.desInques + '</span>\n' +
            "                            </div>\n" +
            "                        </div>\n";

        var reasonStyle = data.fgReason == '1' || data.desReason ? 'padding-top: 10px;' : '';
        html +=
            "                        <div id='reason-" + data.idTestexecResultInques + "' style='padding-left: 45px; " + reasonStyle + "'>\n";
        if (data.fgReason == '1' && !data.desReason) {
            html +=
            '                           <button data-index="' + data.idTestexecResultInques + '" class="layui-btn layui-btn-xs layui-btn-radius cons-reason" style="background-color: #999999">请输入问诊理由</button>\n';
        }
        if (data.desReason) {
            html +=
            "                           <div style='background-color: #F2F2F2; padding: 10px; margin: 5px 5px 5px 0;'>\n" +
            "                               <div style='font-weight: bold; padding: 2px;'>● 问诊理由</div>\n" +
            "                               <div>" + data.desReason + "</div>\n" +
            "                           </div>\n";
        }

        html +=
            "                        </div>\n";

        html += 
            "                        <div style='margin-left: 41px; margin-top: 7px; overflow: hidden'>\n" +
            "                            <div style='float: left'>\n" +
            "                                <span>【患者】</span>\n" +
            "                            </div>\n" +
            "                            <div style='float: left; width: 70%'>\n" +
            patientVar +
            "                            </div>\n" +
            "                        </div>\n";


        var expertFlag = data.desExpert ? '1' : '0';
        var replyStyle = data.fgBack == '1' || data.desReply ? 'padding-top: 10px;' : '';
        html +=
            "                        <div  id='reply-" + data.idTestexecResultInques + "' style='padding-left: 45px; " + replyStyle + "'>\n";
        if (data.fgBack == '1' && !data.desReply) {
            html +=
                '                            <button id="reply-btn-' + data.idTestexecResultInques + '" data-index="' + data.idTestexecResultInques + '" data-expert="' + data.desExpert + '" class="layui-btn layui-btn-xs layui-btn-radius cons-reply" style="background-color: #999999">解释患者的回复</button>\n';
        }
        if (data.desReply) {
            html +=
                "                           <div style='background-color: #F2F2F2; padding: 10px; margin: 5px 5px 5px 0;'>\n" +
                "                               <div style='font-weight: bold; padding: 2px;'>● 解释患者的回复</div>\n" +
                "                               <div>" + data.desReply + "</div>\n" +
                "                           </div>\n";
        }
        if (expertFlag == '1') {
            html +=
                '                           <div style="margin-top: 10px;"><a class="expertLink" data-index="' + data.idTestexecResultInques + '" href="javascript:;" style="color: #009688; text-decoration: underline;">专家解读？</a></div>\n';
            html +=
                "                           <div id='expert-div-" + data.idTestexecResultInques + "' style='background-color: #F2F2F2; display: none; padding: 10px; margin: 5px 5px 5px 0;'>\n" +
                "                               <div style='font-weight: bold; padding: 2px;'>● 专家解读</div>\n" +
                "                               <div>" + data.desExpert + "</div>\n" +
                "                           </div>\n";
        }

        html +=
            "                        </div>\n";

        html +=  
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


    $("#gooey-v").gooeymenu({
        bgColor: "#009688",
        contentColor: "white",
        style: "vertical",
        horizontal: {
            menuItemPosition: "glue"
        },
        vertical: {
            menuItemPosition: "spaced",
            direction: "up"
        },
        circle: {
            radius: 90
        },
        margin: "small",
        size: 50,
        bounce: true,
        bounceLength: "small",
        transitionStep: 100,
        hover: "#44AAA0"
    });

    $('.gooey-menu-item').on('click', function () {
        var type = this.getAttribute("data-type");
        if (type == 1) {
            // 拟诊
            parent.openNzPage();
        }
        if (type == 2) {
            // 病情描述
            var bizData = {
                idTestexecResult: idTestexecResult
            };
            $.ajax({
                url: basePath + '/pf/r/waiting/room/summary/select',
                type: 'post',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(bizData),
                success: function (data) {
                    if (data.code != 0) {
                        layer.msg(data.msg);
                        return false;
                    } else {
                        layer.prompt({
                                title: '<strong>病情描述</strong>',
                                formType: 2,
                                anim: 2,
                                value: data.data && data.data.desConditionHpi ? data.data.desConditionHpi : '' ,
                                offset: [$(window).height() - 300, 100]
                            },
                            function (text, index) {
                                layer.close(index);

                                var bizData = {
                                    idTestexecResult: idTestexecResult,
                                    desConditionHpi: text
                                }
                                common.commonPost(basePath + '/pf/r/waiting/room/summary/save', bizData, '保存', null, null);
                            }
                        );
                    }
                },
                error: function () {
                    layer.msg("网络异常");
                    return false;
                }
            });
        }
    });

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
        } else if (sdType == '2') {
            var path = $('#patientVoice' + idAnswer).attr('src');
            console.log(path)
            layui.common.openAudio(path);
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
