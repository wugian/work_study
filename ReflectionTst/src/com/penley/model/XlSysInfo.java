package com.penley.model;

public class XlSysInfo {
	private int tag;
	private boolean isNetOk;
	private String bindAcktiveKey;
	private int vipLevel;
	private double x;
	private long y;

 

	@Override
	public String toString() {
		return "XlSysInfo [tag=" + tag + ", isNetOk=" + isNetOk
				+ ", bindAcktiveKey=" + bindAcktiveKey + ", vipLevel="
				+ vipLevel + ", x=" + x + ", y=" + y + "]";
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public boolean isNetOk() {
		return isNetOk;
	}

	public void setNetOk(boolean isNetOk) {
		this.isNetOk = isNetOk;
	}

	public String getBindAcktiveKey() {
		return bindAcktiveKey;
	}

	public void setBindAcktiveKey(String bindAcktiveKey) {
		this.bindAcktiveKey = bindAcktiveKey;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

}
