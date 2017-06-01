package com.sm.pfprod.web.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.log4j.Logger;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
//import java.lang.reflect.Method;
//import org.springframework.util.ReflectionUtils;
//import com.yuntai.med.api.beans.common.ResultVo;
//import com.yuntai.med.support.util.HsMedDES;

/**
 * @Description: fastjson自定义消息转换器
 */
public class MyJsonDESMessageConverter extends AbstractHttpMessageConverter<Object>  {
    private final Logger logger =  Logger.getLogger(getClass());
    
    public final static Charset UTF8     = Charset.forName("UTF-8");

    private Charset             charset  = UTF8;

    private SerializerFeature[] features = new SerializerFeature[0];
    
    private String secretKey;

    public MyJsonDESMessageConverter(){
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
    }
    
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public SerializerFeature[] getFeatures() {
        return features;
    }

    public void setFeatures(SerializerFeature... features) {
        this.features = features;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException,
                                                                                               HttpMessageNotReadableException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        InputStream in = inputMessage.getBody();

        byte[] buf = new byte[1024];
        for (;;) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }

            if (len > 0) {
                baos.write(buf, 0, len);
            }
        }

        byte[] bytes = baos.toByteArray();
        return JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz);
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException{
        OutputStream out = outputMessage.getBody();
        long now = System.currentTimeMillis();
        try {
//            if(obj instanceof ResultVo){
//                Method m = ReflectionUtils.findMethod(obj.getClass(), "getData");
//                Method mSet = ReflectionUtils.findMethod(obj.getClass(), "setData",Object.class);
//                if(m!=null&&mSet!=null){
//                    Object data = ReflectionUtils.invokeMethod(m, obj);
//                    if(data!=null){
//                        String dataStr = JSON.toJSONString(data, features);
//                        if(secretKey!=null&&secretKey.length()!=0&&!secretKey.equals("${DES.key}")){
//                            ReflectionUtils.invokeMethod(mSet, obj, HsMedDES.encrypt(dataStr, secretKey));
//                        }else{
//                            ReflectionUtils.invokeMethod(mSet, obj, HsMedDES.encrypt(dataStr));
//                        }
//                    }
//                }
//            }
        } catch (Exception e) {
            logger.error("FastjsonMessageConverter error",e);
        } finally{
            String text = JSON.toJSONString(obj, features);
//            String text = JSON.toJSONString(obj, SerializerFeature.SortField);
            byte[] bytes = text.getBytes(charset);
            out.write(bytes);
            // 2017-05-14 21:09:45,884 INFO  qtp587444708-78                  - (/medicalsvprod/sv/r/my_home/get_homes,Y,151ms)(-)
            logger.debug(String.format("FastjsonMessageConverter cost %d millis",System.currentTimeMillis() - now));
        }
    }
}
