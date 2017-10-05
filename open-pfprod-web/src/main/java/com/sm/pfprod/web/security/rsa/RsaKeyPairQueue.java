package com.sm.pfprod.web.security.rsa;

import com.sm.open.care.core.utils.rsa.RsaKeyPair;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @ClassName: RsaKeyPairQueue
 * @Description: Rsa公私钥队列
 */
public class RsaKeyPairQueue {

	private final BlockingQueue<RsaKeyPair> queue = new ArrayBlockingQueue<RsaKeyPair>(100);
	
	public boolean offerQueue(RsaKeyPair keyPair){
		return queue.offer(keyPair);
	}
	
	public void putQueue(RsaKeyPair keyPair) throws InterruptedException{
		queue.put(keyPair);
	}
	
	public RsaKeyPair takeQueue() throws InterruptedException{
		return queue.take();
	}
	
	public RsaKeyPair takeQueue(HttpServletRequest request) throws InterruptedException{
		RsaKeyPair sessionKeyPair = RsaKeyPairCache.getRsaKeyPair(request);
		if(sessionKeyPair!=null){
			return sessionKeyPair;
		}
		RsaKeyPair keyPair = this.takeQueue();
		RsaKeyPairCache.putRsaKeyPair(request, keyPair);
		return keyPair;
	}

}
