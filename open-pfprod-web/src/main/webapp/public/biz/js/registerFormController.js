/**
 * 注册表单
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common;

    $("#vercodeImage").click(function () {
        var src = basePath + "/login/verificationCode";
        $(this).attr("src", src + "?d=" + new Date().getTime());
    });

    $('#getEmailCode').on('click', function () {
        var v_email = $('#email').val();
        var v_photoVercode = $('#photoVercode').val();
        if (!v_email) {
            $('#email').focus();
            layer.tips('请输入邮箱', '#email', {tips: 1});
            return;
        }
        if (!v_email.match(/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/)) {
            $('#email').focus();
            layer.tips('邮箱格式不正确', '#email', {tips: 1});
            return;
        }
        if (!v_photoVercode) {
            $('#photoVercode').focus();
            layer.tips('请输入图片验证码', '#photoVercode', {tips: 1});
            return;
        }

        var url = basePath + '/pf/r/user/register/sendEmailVcode';
        var bizData = {};
        bizData.email = v_email;
        bizData.photoVercode = v_photoVercode;

        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                if (data.code == 'photoVcodeError') {
                    $('#vercodeImage').attr("src", basePath + "/login/verificationCode" + "?d=" + new Date().getTime());
                    $('#photoVercode').focus();
                    layer.tips('请输入正确的图片验证码', '#photoVercode', {tips: 1});
                } else if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    layer.msg('邮件已发送，15分钟内有效，请注意查收！', {
                        time: 5000, //5s后自动关闭
                        btn: ['知道了']
                    });
                    return true;
                }
            },
            error: function () {
                common.errorMsg("邮件发送失败！请联系管理员");
                return false;
            }
        });
        return false;
    });

    //自定义验证规则
    form.verify({
        orgName: function (value) {
            if (value.length > 64) {
                return '机构名称最多64个字';
            }
        },
        username: [/^[A-Za-z0-9_]{3,32}$/, '必须由字母、数字、下划线组成且至少3个字符'],
        pass: [/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[\s\S]{8,16}$/, '至少包含1个大写字母，1个小写字母和1个数字的8-16位密码'],
        phone: [/^((0\d{2,3}-\d{7,8})|(1[3456789]\d{9}))$/, "联系方式格式不正确"],
        photoVercode: [/^[A-Za-z0-9]{4}$/, "验证码为数字和字母组成的4个字符"],
        emailVercode: [/^[0-9]{6}$/, "验证码不正确"],
    });

    form.on('submit(user-register-submit)', function (obj) {
        var field = obj.field;
        //确认密码
        if (field.clearPassword !== field.clearPassword2) {
            return layer.msg('两次密码输入不一致');
        }
        //是否同意用户协议
        if (!field.agreement) {
            return layer.msg('你必须同意用户协议才能注册');
        }
        field.clearPassword = '';
        field.clearPassword2 = '';

        var url = basePath + '/pf/r/user/register/add';

        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(field),
            success: function (data) {
                if (data.code == 'emailVcodeExpired') {
                    layer.tips(data.msg, '#emailVercode', {tips: 1});
                } else if (data.code == 'orgEmailError') {
                    layer.tips(data.msg, '#email', {tips: 1});
                } else if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    layer.confirm('注册成功，我们将尽快审核并联系您，请您耐心等待！', {
                        btn: ['知道了'] //按钮
                    }, function () {
                        if (window != top) {
                            top.location.href = basePath + "/login";
                        } else {
                            window.location.href = basePath + "/login";
                        }
                    });
                    return true;
                }
            },
            error: function () {
                common.errorMsg("注册失败！请联系管理员");
                return false;
            }
        });
        return false;
    });


});

