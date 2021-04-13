package com.koreait.mango;

public enum MyKey {	// Const와 같은 방식
	MENUS("menus"),
	APP_KEY("appKey"),
	DATA("data"),
	LIST("list");
	
	private String val;
	
	private MyKey(String val) {
		this.val = val;
	}
	
	public String getVal() {
		return this.val;
	}
}
