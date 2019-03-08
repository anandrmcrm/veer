package com.vanancrm.Task;

public class SplitNameFromUrl {
	
	public static void main(String args[]) {
		
		String url = "http://vananvoiceover.com/Dubbing-Services.php";
		String name = url.substring(url.indexOf("/")+2, url.indexOf(".")) + "_" + (url.substring(url.lastIndexOf("/") +1, url.lastIndexOf(".")));
		System.out.println(name);
	}
}
