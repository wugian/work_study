package com.penley.utils;

public class TypeConversionUtils {
	public Object str2Other(String type, String value) {
		Object obj = null;
		switch (type) {
		case "int":
			obj = Integer.parseInt(value);
			break;
		case "boolean":
			if (value.equals("true") || value.equals("1")) {
				obj = true;
			} else {
				obj = false;
			}
			break;
		case "double":
			obj = Double.parseDouble(value);
			break;
		case "long":
			obj = Long.parseLong(value);
			break;
		case "float":
			obj = Float.parseFloat(value);
			break;
		default:
			obj = value;
			break;
		}
		return obj;
	}
}
