package com.sm.pfprod.web.security.rsa;

import com.sm.open.care.core.utils.rsa.RSAEncrypt;
import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName: RsaKeyPairQueueInit
 * @Description: Rsa公私钥队列初始化
 * @author 王勇琳
 * @date 2017年7月12日 上午10:26:17
 */
public class RsaKeyPairQueueInit {
	private final static Logger logger = LoggerFactory.getLogger(RsaKeyPairQueueInit.class);
	
	private RsaKeyPairQueue keyPairQueue;
	
	private final AtomicBoolean isExcute = new AtomicBoolean(false);
	
	private final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
	
	/**
	 * 初始化rsa公私钥队列
	 * @author 王勇琳
	 * @date 2017年7月11日 下午6:48:28
	 */
	public void init(){
		if(!isExcute.compareAndSet(false, true)){
			return;
		}
		scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				RsaKeyPair keyPair = RSAEncrypt.genKeyPair();
				try {
					keyPairQueue.putQueue(keyPair);
					logger.debug("rsa公私钥队列put：{}", keyPair);
				} catch (InterruptedException e1) {
					logger.error("rsa公私钥队列put 线程阻塞异常", e1);
				} catch (Exception e2) {
					logger.error("rsa公私钥队列put异常", e2);
				}
			}
		}, 100, 1, TimeUnit.MILLISECONDS);
	}
	
	public RsaKeyPairQueue getKeyPairQueue() {
		return keyPairQueue;
	}

	public void setKeyPairQueue(RsaKeyPairQueue keyPairQueue) {
		this.keyPairQueue = keyPairQueue;
	}
	
}