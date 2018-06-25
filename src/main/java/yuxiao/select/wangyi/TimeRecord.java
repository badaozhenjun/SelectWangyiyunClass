package yuxiao.select.wangyi;

import com.github.crab2died.annotation.ExcelField;

public class TimeRecord {
	@ExcelField(title="名称",order=0)
	private String name;
	@ExcelField(title="时间(分钟)",order=1)
	private String time1;
	@ExcelField(title="总时间(小时)",order=2)
	private String time2;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name.replaceAll("&nbsp;", " ");
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
}
