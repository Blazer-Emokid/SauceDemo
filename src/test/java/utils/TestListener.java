package utils;

import org.jspecify.annotations.NonNull;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.DriverManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TestListener implements ITestListener {

    private static final String SCREENSHOTS_DIR = "test-output/screenshots/";

    @Override
    public void onTestStart(ITestResult result) {
        logToFile("ПОШЛА ЖАРА!", result, null);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logToFile("УСПЕХ", result, null);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logToFile("УПАЛ", result, result.getThrowable());
        takeScreenshot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logToFile("ПРОПУЩЕН", result, null);
    }


    @Override
    public void onStart(ITestContext context) {
        createScreenshotsDirectory();
        logToFile("SUITE_START", context.getName(), null);
    }

    @Override
    public void onFinish(@NonNull ITestContext context) {
        logToFile("ФИНИШ", context.getName(), null);
        printTestSummary(context);
    }

    private void logToFile(String status, @NonNull ITestResult result, Throwable throwable) {
        String testName = result.getName();
        long duration = getExecutionTime(result);
        String message = String.format("[%s] %s - %s (Duration: %d ms)",
                status, testName,
                getTestDescription(result),
                duration);

        if (throwable != null) {
            message += "\nError: " + throwable.getMessage();
        }

        writeToLogFile(message);
    }

    private void logToFile(String status, String name, Throwable throwable) {
        String message = String.format("[%s] %s", status, name);
        writeToLogFile(message);
    }

    private void writeToLogFile(String message) {
        System.out.println(message);
    }

    private String getTestDescription(@NonNull ITestResult result) {
        org.testng.annotations.Test test = result.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(org.testng.annotations.Test.class);
        if (test != null && !test.testName().isEmpty()) {
            return test.testName();
        }
        if (test != null && !test.description().isEmpty()) {
            return test.description();
        }
        return result.getName();
    }

    private long getExecutionTime(@NonNull ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }

    private void takeScreenshot(ITestResult result) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String fileName = String.format("%s_%s_screenshot.png",
                        result.getName(),
                        timestamp);
                Path destination = Paths.get(SCREENSHOTS_DIR + fileName);
                Files.createDirectories(destination.getParent());
                Files.copy(screenshot.toPath(), destination);
            }
        } catch (Exception e) {
            // Логируем ошибку, но не прерываем тест
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    private void createScreenshotsDirectory() {
        try {
            Files.createDirectories(Paths.get(SCREENSHOTS_DIR));
        } catch (IOException e) {
            System.err.println("Failed to create screenshots directory: " + e.getMessage());
        }
    }

    private void printTestSummary(@NonNull ITestContext context) {
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        int total = passed + failed + skipped;

        String summary = String.format(
                "Test Summary - Passed: %d, Failed: %d, Skipped: %d, Total: %d",
                passed, failed, skipped, total);

        writeToLogFile(summary);
    }
}