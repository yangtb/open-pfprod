layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common', 'layer'], function () {
    var $ = layui.$,
        table = layui.table,
        form = layui.form,
        common = layui.common
        , layer = layui.layer;

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
            $('#iframe' + execCode).attr('src', basePath + execRef
                + '?idMedicalrec=' + idMedicalrec + '&cdMedAsse=' + execCode + '&idTestexecResult=' + $('#idTestexecResult').val());
        }
        $('#iframe' + execCode).removeClass("display-my").siblings().addClass("display-my");
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
            sdTestexec: '0'
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

