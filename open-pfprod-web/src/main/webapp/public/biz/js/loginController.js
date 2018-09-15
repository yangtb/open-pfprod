/**
 * login
 * @constructor
 */
function LoginController() {

    var _valueOfEncryptPassword = function (value) {
        var encryptPassword = $.jCryption.crypt.encrypt(value);
        $('#encryptPassword').val(encryptPassword);
    }

    var _initLogin = function (publicKey) {
        $.jCryption.crypt.setPublicKey(publicKey);
        // 用户登录前密码加密
        $('#clearPassword').keyup(function () {
            _valueOfEncryptPassword($(this).val());
        }).change(function () {
            _valueOfEncryptPassword($(this).val());
        });
    }

    this.init = function (publicKey) {
        _initLogin(publicKey);
    }
}