
var publicKey ='MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCRZB2zGLnzgP9Kk919327AaBqzJyvsgVsfGD8jQYUAgPJ/Ro0akFKhDKb/Kp/qP5CxJcM6x/hOUR4M9W52B+uZZccGYqp+EMw/K5xVekPI+WLEKUk1EeYexFANTq/AoqgxjxqtfjK5y/eRmJVvk5jVrdgiFcOIjmi4Rc1Hd88zIwIDAQAB';
$.jCryption.crypt.setPublicKey(publicKey);                //注入公钥

var time=new Date().getTime();
var m = $.jCryption.crypt.encrypt(time+"");


/**
 * header头参数暂时写死测试
 * @type {string}
 */
var prdCode ='DCT-000001';
var unicode = 'a9a8bc8d37af45d39943d146f8295ff9'; //$.cookies.get('unicode');
var client_id = '444444@310000';//$.cookies.get('client_id');
var access_token = 'ec2b89ce-c744-4c42-951a-2b6340f5f1ed';//$.cookies.get('access_token');
