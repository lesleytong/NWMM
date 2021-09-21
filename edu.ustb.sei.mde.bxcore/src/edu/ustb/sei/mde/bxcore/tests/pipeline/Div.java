package edu.ustb.sei.mde.bxcore.tests.pipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Div implements Runnable{
	public static BlockingQueue<Msg> bq = new LinkedBlockingDeque<>();
	@Override
	public void run() {
		while(true) {
			try {
				Msg msg = bq.take();
				msg.i = msg.i / 2;	// D=D/2
				System.out.println(msg.orgStr + "=" + msg.i);
			} catch (InterruptedException e) {
			}
		}
	}

}
