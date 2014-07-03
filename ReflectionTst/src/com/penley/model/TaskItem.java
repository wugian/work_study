package com.penley.model;

/**
 * Copyright (C) 2014 极米科技有限公司, Inc
 * 
 * 任务详情
 * 
 * @author PanLi <478760490@qq.com>
 * @Maintainer PanLi <478760490@qq.com>
 */
public class TaskItem {
	// TASK: [task_id, stat, type, “file_name”, “file_path”, “file_size”,
	// “download_data_size”, start_time, finished_time, fail_code, dl_speed,
	// ul_speed, “url”]
	// 6, 2, 0,
	// "%E7%89%B9%E5%B7%A5%E7%9A%84%E7%89%B9%E5%88%AB%E4%BB%BB%E5%8A%A1-%E5%85%A8%E9%9B%86%EF%BC%88720P%E9%AB%98%E6%B8%85%EF%BC%89.xv",
	// "%2Fdata%2Fthunder%2Ftmp%2Fvolumes%2FC%3A%2FTDDOWNLOAD%2F", "731130192",
	// "65191936", 1403148989, 1403149079, 0, 0, 0,
	// "http%3A%2F%2Fpubnet.sandai.net%3A8080%2F20%2F992cbffc6d5c8e0da342a1dcd21d5b9a8b7e8e44%2F055c05a6ea1306745eaec3cf0743f133c8cfb280%2F2b942950%2F200000%2F0%2Ff54c5%2F0%2F0%2F2b942950%2F0%2Ff0b3eec14452a7fb3e55fa3a5ce49971%2F4d44b7604b273c452aeccbb5f7449ac9%2F055c05a6ea1306745eaec3cf0743f133c8cfb280_1_IBOdownload_.flv.xv%3Ftype%3Dvod%26movieid%3D3981%26subid%3D6856%26ext%3D.xv"
	private int taskId;
	private int stat;
	private int type;// done or not done
	private String fileName;
	private String filePath;
	private String fileSize;
	private int dlDataSize;
	private String startTime;
	private String finishedTime;
	private int failCode;
	private int dlSpeed;
	private int ulSpeed;
	private String url;// download url

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public int getDlDataSize() {
		return dlDataSize;
	}

	public void setDlDataSize(int dlDataSize) {
		this.dlDataSize = dlDataSize;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(String finishedTime) {
		this.finishedTime = finishedTime;
	}

	public int getFailCode() {
		return failCode;
	}

	public void setFailCode(int failCode) {
		this.failCode = failCode;
	}

	public int getDlSpeed() {
		return dlSpeed;
	}

	public void setDlSpeed(int dlSpeed) {
		this.dlSpeed = dlSpeed;
	}

	public int getUlSpeed() {
		return ulSpeed;
	}

	public void setUlSpeed(int ulSpeed) {
		this.ulSpeed = ulSpeed;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "TaskItem [taskId=" + taskId + ", stat=" + stat + ", type="
				+ type + ", fileName=" + fileName + ", fileSize=" + fileSize
				+ ", dlDataSize=" + dlDataSize + ", startTime=" + startTime
				+ ", finishedTime=" + finishedTime + ", failCode=" + failCode
				+ ", dlSpeed=" + dlSpeed + ", ulSpeed=" + ulSpeed + ", url="
				+ url + "]";
	}

}
