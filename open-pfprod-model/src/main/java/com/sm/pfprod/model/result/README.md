1. 存放rest接口的出参，注意使用@JSONField注解包装成下划线格式输出

2. 另外，facade RPC接口的入参出参也用param和result来包装。但是不放在此model下面，而是单独放置facade里。

