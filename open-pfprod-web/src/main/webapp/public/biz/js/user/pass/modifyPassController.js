/**
 * 修改密码
 * @constructor
 */
function ModifyPassController() {

    var _valueOfEncryptPassword = function (encryptName, value) {
        var encryptOldPassword = $.jCryption.crypt.encrypt(value);
        $('#' + encryptName).val(encryptOldPassword);
    }

    var _updatePsw = function (nameId, encryptName) {
        $('#' + nameId).keyup(function () {
            _valueOfEncryptPassword(encryptName, $(this).val());
        }).change(function () {
            _valueOfEncryptPassword(encryptName, $(this).val());
        });
    }

    var _initUpdatePsw = function (publicKey) {
        $.jCryption.crypt.setPublicKey(publicKey);
        // 用户登录前密码加密
        _updatePsw('clearOldPassword', 'encryptOldPassword');
        _updatePsw('clearNewPassword', 'encryptNewPassword');
    }

    this.init = function (publicKey) {
        _initUpdatePsw(publicKey);
    }
}