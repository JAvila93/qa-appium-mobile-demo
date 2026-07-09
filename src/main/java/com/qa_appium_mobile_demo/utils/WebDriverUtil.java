package com.qa_appium_mobile_demo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.qa_appium_mobile_demo.base.BaseTest;
import com.qa_appium_mobile_demo.base.SeleniumWrapper;

public class WebDriverUtil {

    public static String takeScreenshot(SeleniumWrapper driver, String timeTaken, String failedMethodAndClass, String failedTestClassName) throws IOException {
        String fileName;
        String reportFileName;

        fileName = "test-output-" + BaseTest.getSuiteId() + "/screenshots/" + timeTaken + "-" + failedMethodAndClass + ".jpg";
        reportFileName = "screenshots/" + timeTaken + "-"+failedMethodAndClass+".jpg";

        File srcFile = ((TakesScreenshot) driver.getWebDriver()).getScreenshotAs(OutputType.FILE);
        File destFile = new File(fileName);
        FileUtils.copyFile(srcFile, destFile);
        return reportFileName;
    }

    public static void savePageSourceToFile(SeleniumWrapper driver, String timeTaken, String failedMethodAndClass, String failedTestClassName) throws IOException {
        String fileName;

        fileName = "test-output-" + BaseTest.getSuiteId() + "/sources/" + timeTaken + "-" + failedMethodAndClass + ".txt";
        String pageSource = driver.getCurrentPageTitle();
        File destFile = new File(fileName);
        FileUtils.writeStringToFile(destFile, pageSource, "UTF-8");
    }

    public static String encodeFileToBase64(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new IllegalStateException("could not read file " + file, e);
        }
    }

    public static void createScreenshotsZipFolder() {
        String fileName = "test-output-" + BaseTest.getSuiteId() + "/screenshots.zip";
        String sourcePath = "test-output-" + BaseTest.getSuiteId() + "/screenshots";
        final Path sourceDir = Paths.get(sourcePath);

        try {
            final ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(fileName));
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                    try {
                        Path targetFile = sourceDir.relativize(file);
                        outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
                        byte[] bytes = Files.readAllBytes(file);
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getScreenshotsZipFolderPath() {
        return "test-output-" + BaseTest.getSuiteId() + "/screenshots.zip";
    }

    public static void createLogsZipFolder() {
        String fileName = "test-output-" + BaseTest.getSuiteId() + "/logs.zip";
        String sourcePath = "test-output-" + BaseTest.getSuiteId() + "/logs";
        final Path sourceDir = Paths.get(sourcePath);

        try {
            final ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(fileName));
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                    try {
                        Path targetFile = sourceDir.relativize(file);
                        outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
                        byte[] bytes = Files.readAllBytes(file);
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLogsZipFolderPath() {
        return "test-output-" + BaseTest.getSuiteId() + "/logs.zip";
    }

    public static void cleanDirectory() {
        String basePath = "test-output-" + BaseTest.getSuiteId();
        String screenshotsPath = "/screenshots";
        String sourcesPath = "/sources";
        String logsPath = "/logs";

        File screenshotsDir = new File(basePath + screenshotsPath);
        File sourcesDir = new File(basePath + sourcesPath);
        File baseDir = new File(basePath);
        File logsDir = new File(basePath + logsPath);

        TestUtilities.log("Cleaning directory..." + basePath);

        try {
            FileUtils.cleanDirectory(screenshotsDir);
        } catch (NullPointerException | IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }

        try {
            FileUtils.cleanDirectory(sourcesDir);
        } catch (NullPointerException | IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }

        try {
            FileUtils.cleanDirectory(logsDir);
        } catch (NullPointerException | IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }

        try {
            FileUtils.cleanDirectory(baseDir);
        } catch (NullPointerException | IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }

        TestUtilities.log("Cleaned directory..." + basePath);
    }
}
