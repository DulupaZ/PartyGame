package com.dulupa.scum.util;

public class Debug {
	public final static String TAG_DEBUG="[DEBUG]:";
	public static void log(Object msg) {
		System.out.println(TAG_DEBUG+msg.toString());
	}
}
