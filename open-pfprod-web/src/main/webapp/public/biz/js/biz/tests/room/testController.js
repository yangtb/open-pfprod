layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common', 'layer', 'util'], function () {
    var $ = layui.$,
        table = layui.table,
        form = layui.form,
        common = layui.common
        , layer = layui.layer
        , util = layui.util;

    //固定块
    util.fixbar({
        bar1: '<i class="iconfont icon-zhenduan" id="nzImg" style="font-size: 30px;"></i>'
        , css: {right: 40, top: 110}
        , bgcolor: '#393D49'
        , click: function (type) {
            if (type === 'bar1') {
                common.open('拟诊', basePath + '/pf/p/waiting/room/test/referral/page'
                    + '?idMedicalrec=' + idMedicalrec + '&cdMedAsse=009'
                    + '&idTestexecResult=' + $('#idTestexecResult').val()
                    + '&sdTestexec=' + sdTestexec, 850, 450);
            }
        }
    });


    $(document).ready(function () {
        if (!$('#idTestexec').val()) {
            layer.confirm('请点击【开始】按钮答题', {
                closeBtn: 0, //不显示关闭按钮
                btn: ['开始'] //按钮
            }, function () {
                $('#startBtn').click();
            });
        }

        var execTags = document.querySelectorAll(".execTag");
        for (var i = 0; i < execTags.length; i++) {
            execTags[i].addEventListener('click', function () {
                $(this).addClass("active").siblings().removeClass("active");
                loadIframe(this.getAttribute('exec-ref'), this.getAttribute('exec-code'));
            });
        }
        execTags[0].click();
    });

    function loadIframe(execRef, execCode) {
        if (!$('#iframe' + execCode).attr("src")) {
            if (!execRef) {
                $('#iframe' + execCode).attr('src', basePath + '/empty/page');
            } else {
                $('#iframe' + execCode).attr('src', basePath + execRef
                    + '?idMedicalrec=' + idMedicalrec + '&cdMedAsse=' + execCode
                    + '&idTestexecResult=' + $('#idTestexecResult').val()
                    + '&sdTestexec=' + sdTestexec);
            }
        }
        $('#iframe' + execCode).removeClass("display-my").siblings().addClass("display-my");
        if (execCode == '004' || execCode == '005' || execCode == '006') {
            showfixBar();
            layer.tips('点击此处可添加拟诊', '#nzImg', {
                tips: [4, '#FF5722']
            });
        } else {
            hiddenFixBar()
        }
    }

    function showfixBar() {
        $(".layui-fixbar").show();
    }

    function hiddenFixBar() {
        $(".layui-fixbar").hide();
    }

    $('#startBtn').on('click', function () {
        if ($('#endTime').text()) {
            layer.msg('您已交卷', {icon: 1});
            return;
        }
        if ($('#idTestexec').val()) {
            layer.msg('请开始答题', {icon: 1});
            return;
        }
        var url = basePath + '/pf/r/waiting/room/start';
        var bizData = {
            idTestplanDetail: idTestplanDetail,
            idMedicalrec: idMedicalrec,
            sdTestexec: '0',
            idStudent: $('#studentId').val()
        };
        return common.commonPost(url, bizData, '', null, _starCallback);
    });

    function _starCallback(data) {
        $('#idTestexec').val(data.data.idTestexec);
        $('#idTestexecResult').val(data.data.idTestexecResult);
        $('#beginTime').text(moment(new Date()).format('YYYY-MM-DD HH:mm:ss'));
        layer.msg('请答题', {icon: 1});
    };

    $('#endBtn').on('click', function () {
        if ($('#endTime').text()) {
            layer.msg('您已交卷成功', {icon: 1});
            return;
        }

        layer.confirm('确定要交卷么？', {
            title: '交卷提示',
            resize: false,
            offset: 'rt',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            var url = basePath + '/pf/r/waiting/room/end';
            var bizData = {
                idTestexec: $('#idTestexec').val(),
                idTestplanDetail: idTestplanDetail,
                sdTestexec: '2'
            };
            common.commonPost(url, bizData, '', null, _endCallback);
            layer.close(index);
        })
    });

    function _endCallback(data) {
        $('#endTime').text(moment(new Date()).format('YYYY-MM-DD HH:mm:ss'));
        layer.msg('交卷成功', {icon: 1});
    };


});

