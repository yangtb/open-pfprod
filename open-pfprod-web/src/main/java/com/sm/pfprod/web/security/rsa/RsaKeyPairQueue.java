package com.sm.pfprod.web.security.rsa;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @ClassName: RsaKeyPairQueue
 * @Description: Rsa公私钥队列
 * @Author yangtongbin
 * @Date 2017/10/11 22:10
 */
public class RsaKeyPairQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(RsaKeyPairQueue.class);

    private final BlockingQueue<RsaKeyPair> queue = new ArrayBlockingQueue<RsaKeyPair>(100);

    public boolean offerQueue(RsaKeyPair keyPair) {
        return queue.offer(keyPair);
    }

    public void putQueue(RsaKeyPair keyPair) throws InterruptedException {
        queue.put(keyPair);
    }

    public RsaKeyPair takeQueue() throws InterruptedException {
        return queue.take();
    }

    public RsaKeyPair takeQueue(HttpServletRequest request) throws InterruptedException {
        RsaKeyPair sessionKeyPair = RsaKeyPairCache.getRsaKeyPair(request);
        if (sessionKeyPair != null) {
            return sessionKeyPair;
        }
        RsaKeyPair keyPair = this.takeQueue();
        RsaKeyPairCache.putRsaKeyPair(request, keyPair);
        return keyPair;
    }

    public RsaKeyPair getRsaKeyQueue(HttpServletRequest request) {
        RsaKeyPair keyPair;
        try {
            keyPair = takeQueue(request);
        } catch (InterruptedException e) {
            LOGGER.error("【RsaKeyPairQueue-getRsaKeyQueue】获取rsa公私钥队列相关操作异常", e);
            throw new BizRuntimeException(ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
        }
        return keyPair;
    }


}
