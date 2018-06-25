# SelectWangyiyunClass

用于抓取网易云课堂上相关课程的时长信息

```
public static void main(String[] args) throws IOException, InterruptedException, Excel4JException {
		getInfoToExcel("http://study.163.com/course/courseMain.htm?courseId=1004561002",
				"/Users/yuxiao/Desktop/汇编从0开始.xlsx");
		
		//getInfoToExcel("http://study.163.com/course/courseMain.htm?courseId=1004561002#/courseDetail?tab=1","/Users/yuxiao/Desktop/汇编从0开始.xlsx");
	}
```

![](http://7qn7pi.com1.z0.glb.clouddn.com/2018-06-25-071838.png)

参考库：

- Excel4J:https://github.com/Crab2died/Excel4J

- PhantomJs
