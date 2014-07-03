package com.penley.model;

public class CopyOfXlSysInfo {
	private int tag;
	private boolean isNetOk;
	private String bindAcktiveKey;
	private int vipLevel;

	@Override
	public String toString() {
		return "XlSysInfo [tag=" + tag + ", isNetOk=" + isNetOk
				+ ", bindAcktiveKey=" + bindAcktiveKey + ", vipLevel="
				+ vipLevel + "]";
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

}
