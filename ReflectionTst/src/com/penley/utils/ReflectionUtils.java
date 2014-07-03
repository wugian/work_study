package com.penley.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {

	public Object parseResult(String clazz, String[] parseStr) {
		Class<?> class1 = null;
		Object obj = null;
		TypeConversionUtils tcu = new TypeConversionUtils();
		try {
			class1 = Class.forName(clazz);
			obj = class1.newInstance();
			Field[] fileds = class1.getDeclaredFields();
			for (int i = 0; i < fileds.length; i++) {
				Field filed = class1.getDeclaredField(fileds[i].getName());
				System.out.println(filed.getType().toString());
				filed.setAccessible(true);
				filed.set(
						obj,
						tcu.str2Other(filed.getType().toString(),
								parseStr[i].trim()));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
