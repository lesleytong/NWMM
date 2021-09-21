package edu.ustb.sei.mde.bxcore.tests.pipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Plus implements Runnable{
	public static BlockingQueue<Msg> bq = new LinkedBlockingDeque<>();
	@Override
	public void run() {
		while(true) {
			try {
				Msg msg = bq.take();
				msg.j = msg.i + msg.j;	// A=B+C
				Multiply.bq.add(msg);
			} catch (InterruptedException e) {
			}
		}
	}
	
}
