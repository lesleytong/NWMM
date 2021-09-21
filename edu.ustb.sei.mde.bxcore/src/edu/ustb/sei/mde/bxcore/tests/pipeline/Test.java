package edu.ustb.sei.mde.bxcore.tests.pipeline;

import java.util.HashSet;
import java.util.Set;

public class Test {
	public static void main(String[] args) {

//		new Thread(new Plus()).start();
//		new Thread(new Multiply()).start();
//		new Thread(new Div()).start();
//		
//		for(int i=0; i<10; i++) {
//			for(int j=0; j<10; j++) {
//				Msg msg = new Msg();
//				msg.i = i;
//				msg.j = j;
//				msg.orgStr = "(("+i+" + "+j+")*"+i+")/2";
//				Plus.bq.add(msg);
//			}
//		}
		
		Set<Object> hashSet1 = new HashSet<>();
		Set<Object> hashSet2 = new HashSet<>();
		
		hashSet1.add(1);
		hashSet1.add(2);
		
		hashSet2.addAll(hashSet1);
		
		System.out.println(hashSet1.hashCode());
		System.out.println(hashSet2.hashCode());
		
	}
}

