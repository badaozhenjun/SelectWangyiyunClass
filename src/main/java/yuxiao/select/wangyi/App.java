package yuxiao.select.wangyi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.github.crab2died.ExcelUtils;
import com.github.crab2died.exceptions.Excel4JException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException, InterruptedException, Excel4JException {
		getInfoToExcel("http://study.163.com/course/courseMain.htm?courseId=1004561002",
				"/Users/yuxiao/Desktop/汇编从0开始.xlsx");
		
		//getInfoToExcel("http://study.163.com/course/courseMain.htm?courseId=1004561002#/courseDetail?tab=1","/Users/yuxiao/Desktop/汇编从0开始.xlsx");
	}

	private static void getInfoToExcel(String url, String writeToFileName) {
		// 设置必要参数
		DesiredCapabilities dcaps = new DesiredCapabilities();
		// ssl证书支持
		dcaps.setCapability("acceptSslCerts", true);
		// 截屏支持
		dcaps.setCapability("takesScreenshot", true);
		// css搜索支持
		dcaps.setCapability("cssSelectorsEnabled", true);
		// js支持
		dcaps.setJavascriptEnabled(true);
		// 驱动支持（第二参数表明的是你的phantomjs引擎所在的路径）
		dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				"/Users/yuxiao/Documents/phantomjs-2.1.1-macosx/bin/phantomjs");
		// 创建无界面浏览器对象
		PhantomJSDriver driver = new PhantomJSDriver(dcaps);
		try {
			// 设置隐性等待（作用于全局）
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			// 打开页面
			driver.get(url);
			List<WebElement> sections = driver.findElementsByClassName("chapter");
			float totalTime = 0;
			ArrayList<TimeRecord> records = new ArrayList<TimeRecord>();
			for (WebElement section : sections) {
				TimeRecord tRecord = new TimeRecord();
				String chapterName = null;
				try {
					chapterName = section.findElement(By.className("chaptername")).getAttribute("innerHTML");
				} catch (Exception e) {
					// TODO: handle exception
				}
				 
				float totalChapterTime = 0;
				for (WebElement element : section.findElements(By.className("section"))) {
					TimeRecord tSubRecord = new TimeRecord();
					String name = element.findElement(By.className("ksname")).getAttribute("innerHTML");
					String time = element.findElement(By.className("kstime")).getAttribute("innerHTML");
					tSubRecord.setName(name);
					if(time.length()>0) {
						String[] times = time.split(":");
						int currentTime = Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
						totalChapterTime = totalChapterTime + currentTime;
						tSubRecord.setTime1(String.format("%.1f", currentTime / 60.0f));
					}
					records.add(tSubRecord);
				} 
				totalTime += totalChapterTime;
				if(chapterName==null) {
					continue;
				}
				tRecord.setName("本章:"+chapterName);
				tRecord.setTime2(String.format("%.1f", totalChapterTime / 3600.f) + "");
				records.add(tRecord);
				TimeRecord tBlank = new TimeRecord();
				records.add(tBlank);
			}
			TimeRecord totalRecord = new TimeRecord();
			totalRecord.setName("合计");
			totalRecord.setTime2(String.format("%.1f", totalTime / 3600) + "");
			records.add(totalRecord);
			driver.quit();
			ExcelUtils.getInstance().exportObjects2Excel(records, TimeRecord.class, true, "sheet1", true,
					writeToFileName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
		System.out.println("写入成功:" + writeToFileName);
	}

}
