package com.shuogesha.platform.template;


public class CmsModuleGenerator {
	private static String packName = "com.shuogesha.platform.template";
	private static String fileName = "shuogesha.properties";

	public static void main(String[] args) {
		new ModuleGenerator(packName, fileName).generate();
	}
}
