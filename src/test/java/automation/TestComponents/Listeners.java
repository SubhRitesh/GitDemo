package automation.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import automation.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{

	ExtentReports extent = ExtentReporterNG.getReportObject();
	ExtentTest test;
	ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<ExtentTest>(); // thread safe
	
	@Override
	public void onTestStart(ITestResult result) {
//		// TODO Auto-generated method stub
//		ITestListener.super.onTestStart(result);
		test = extent.createTest(result.getMethod().getMethodName());
		threadLocal.set(test); //unique thread id
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
//		ITestListener.super.onTestSuccess(result);
//		test.log(Status.PASS, "The Test is successfully Passed");
		threadLocal.get().log(Status.PASS, "The Test is successfully Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
//		ITestListener.super.onTestFailure(result);
		threadLocal.get().fail(result.getThrowable());
		threadLocal.get().log(Status.FAIL, result.getThrowable());
		
		// Screenshot, Attach to report
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadLocal.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
//		ITestListener.super.onFinish(context);
		extent.flush();
	}

	
	
}
