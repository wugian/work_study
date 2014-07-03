package com.penley.test;

import com.penley.model.TaskResult;
import com.penley.utils.ReflectionUtils;

public class MainTest {

	public static void main(String[] args) {
		String tstStr = "2,3,1,1,2,name,filepath,filesize,200,adfadsf,234234,2,3,5,http://sdf,1,1,2,name,filepath,filesize,200,adfadsf,234234,2,3,5,http://sdf";
		String[] parseStr = tstStr.split(",");
		ReflectionUtils ru = new ReflectionUtils();
		TaskResult xl = (TaskResult) ru.parseResult("com.penley.model.TaskResult",
				parseStr);
		System.out.println(xl.toString());

		System.out.println("***********divide*************");

		// XlSysInfo xlSysInfo = new XlSysInfo();
		// Class c = xlSysInfo.getClass();
		// Method[] method = c.getDeclaredMethods();
		// for (int i = 0; i < method.length; i++) {
		// Annotation[][] annotations = method[i].getParameterAnnotations();
		// System.out.println(method[i].getName() + ": "
		// + method[i].getDefaultValue());
		// }
		// System.out.println("constructor");
		// Constructor[] cs = c.getConstructors();
		// for (int i = 0; i < cs.length; i++) {
		// System.out.println(cs[i].getName() + ": ");
		// }
		// Field[] filed = c.getDeclaredFields();
		// for (int i = 0; i < filed.length; i++) {
		// System.out.println(filed[i].getName());
		//
		// }
	}
}
