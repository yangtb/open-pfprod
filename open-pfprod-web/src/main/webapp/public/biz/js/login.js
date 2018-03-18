
$(function () {
    $.jCryption.crypt.setPublicKey(publicKey);
    $('#clearPassword').keyup(function () {
        _valueOfEncryptPassword($(this).val());
    }).change(function () {
        _valueOfEncryptPassword($(this).val());
    });

    $("#login-switch").click(function () {
        if ($("#scanLogin").hasClass("login-hidden")) {
            //切换为密码登录
            $("#passwordLogin").addClass("login-hidden");
            $("#scanLogin").removeClass("login-hidden");
            $("#passwordLoginBody").removeClass("login-hidden");
            $("#scanLoginBody").addClass("login-hidden");
        } else {
            //切换为扫码登录
            /*$("#passwordLogin").removeClass("login-hidden");
            $("#scanLogin").addClass("login-hidden");
            $("#passwordLoginBody").addClass("login-hidden");
            $("#scanLoginBody").removeClass("login-hidden");
            */
        }
    });
    $("#vcodeImage").click(function () {
        var src = basePath + "/login/verificationCode";
        $(this).attr("src", src + "?d=" + new Date().getTime());
    });
    /* 登录框enter事件 */
    $(document).keypress(function (e) {
        if (e.keyCode == "13") {
            if ($("#username").is(":focus")) {
                $("#encryptPassword").focus();
            } else if ($("#encryptPassword").is(":focus")) {
                $("#vcode").focus();
            } else if ($("#vcode").is(":focus")) {
                doLogin();
            }
        }
    });

});

var _valueOfEncryptPassword = function (value) {
    var encryptPassword = $.jCryption.crypt.encrypt(value);
    $('#encryptPassword').val(encryptPassword);
}

function checkForm() {
    var username = $.trim($("#username").val());
    var password = $.trim($("#encryptPassword").val());
    var vcode = $.trim($("#vcode").val());
    if (username.length == 0) {
        $("#loginwarning").html("请填写登录账号!");
        $("#doLoginBtn").button('reset');
        return false;
    }
    /*var phoneReg=/^((12[0-9])|(13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\d{8}$/;
    if (!phoneReg.test(username)) {
        $("#loginwarning").html("请输入有效手机号!");
        return false;
    }*/
    if (password.length == 0) {
        $("#loginwarning").html("请输入登录密码!");
        $("#doLoginBtn").button('reset');
        return false;
    }
    if (vcode.length == 0) {
        $("#loginwarning").html("请输入图片验证码!");
        $("#doLoginBtn").button('reset');
        return false;
    }
    /*if (password.length < 6 || password.length > 16) {
        $("#loginwarning").html("密码为6-16位!");
        return false;
    }*/
    /*var passwordReg = /^[0-9A-Za-z]+$/;
    if (!passwordReg.test(password)) {
        $("#loginwarning").html("密码为数字或字母!");
        return false;
    }*/
    if (vcode.length != 4) {
        $("#loginwarning").html("请输入4位图片验证码!");
        $("#doLoginBtn").button('reset');
        return false;
    }
    userName = $('#username').val();
    encryptPasswordStr = $('#encryptPassword').val();
    return true;
}


function submitForm() {
    $("#doLoginBtn").button('loading');
    return checkForm();
}
