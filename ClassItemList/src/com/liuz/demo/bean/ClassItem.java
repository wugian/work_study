package com.liuz.demo.bean;

import java.io.Serializable;

public class ClassItem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public int classId = 0;
	public String classIcon = "";
	public String className = "";
	public int partId = 0;
	public String partName = "";
	public boolean ifTop = false;
	
	public ClassItem() {
		super();
	}
	
	/**
	 * 
	 * @param classId 数据id
	 * @param className 数据name
	 * @param partId 栏目id
	 * @param partName 栏目name 
	 * @param classIcon 数据图片
	 */
	public ClassItem(int classId, String className, int partId,
			String partName, String classIcon) {
		super();
		this.classId = classId;
		this.className = className;
		this.partId = partId;
		this.partName = partName;
		this.classIcon = classIcon;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getPartId() {
		return partId;
	}
	public void setPartId(int partId) {
		this.partId = partId;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getClassIcon() {
		return classIcon;
	}
	public void setClassIcon(String classIcon) {
		this.classIcon = classIcon;
	}
	
	@Override
	public String toString() {
		return "ifTop:::" + ifTop + ":::className:::" + className + ":::partName:::" + partName;
	}
}
