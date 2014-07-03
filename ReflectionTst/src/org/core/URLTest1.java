package org.core;

import java.net.*;
import java.util.*;

public class URLTest1 {

	public static void main(String[] args) throws Exception {
		URL url = new URL("http://www.baidu.com");
		Scanner in = new Scanner(url.openStream());
		while (in.hasNextLine()) {
			String str = in.nextLine();
			System.out.println(str);
		}

		URI uri = new URI("http://blog.csdn.net/xiazdong");
		System.out.println(uri.getScheme());
		System.out.println(uri.getSchemeSpecificPart());
		System.out.println(uri.getAuthority());
		System.out.println(uri.getUserInfo());
		System.out.println(uri.getHost());
		System.out.println(uri.getPort());
		System.out.println(uri.getPath());
		System.out.println(uri.getQuery());
		System.out.println(uri.getFragment());

		String str = "/article/details/6705033";
		URI combined = uri.resolve(str);// 根据uri的路径把str变成绝对地址
		System.out.println(combined.getScheme()
				+ combined.getSchemeSpecificPart());

		URI relative = uri.relativize(new URI(str));
		System.out.println(relative.getSchemeSpecificPart());

	}

}
