package de.amazon.stepDefinitions;

import de.amazon.utilities.ConfigurationReader;
import de.amazon.utilities.Driver;
import de.amazon.utilities.ThreadStateArray;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Hooks {
    Logger logger = LoggerFactory.getLogger(Hooks.class);
    String browser = ConfigurationReader.get("browser");

    //It tooks too many time for every scenario and does not work properly
    public static void generateAllureReport() throws IOException {

        String pathOfFramework= System.getProperty("user.dir");
        Runtime runtime = Runtime.getRuntime();
        String openCmdCommand = ConfigurationReader.get("openCmdCommand");
        String pathOfReportRunner= ConfigurationReader.get("pathOfReportRunner");
        String fullPathOfReportRunner=pathOfFramework + pathOfReportRunner;
        runtime.exec(openCmdCommand +" "+ fullPathOfReportRunner);
        String closeCmdCommand = ConfigurationReader.get("closeCmdCommand");
        String pathOfCloser= ConfigurationReader.get("pathOfCloser");
        String fullPathOfCloser=pathOfFramework + pathOfCloser;
        runtime.exec(openCmdCommand +" "+ fullPathOfCloser);

/*        String pattern = "dd-MM-yyyy_HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String reportFolder = "allure-report_" + simpleDateFormat.format(new Date());
        executeShellCmd("allure generate allure-results");
        executeShellCmd("mv allure-report " + reportFolder);
        executeShellCmd("cp -R /src/test/resources/config/allure-2.21.0 "+reportFolder);
        executeShellCmd("cp /src/test/resources/config/open_report_windows.bat "+reportFolder);*/
    }

    public static void executeShellCmd(String shellCmd) {
        try {
            Process process = Runtime.getRuntime().exec(shellCmd);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Executing the command " + shellCmd);
        }
    }


    public void setAllureEnvironment() {
        //this method provides info for Environment part of Allure-Report
        try (OutputStream output = Files.newOutputStream(Paths.get("allure-results/environment.properties"))) {

            Properties prop = new Properties();
            String tester = ConfigurationReader.get("tester");
            String environment = ConfigurationReader.get("env");
            String browser = ConfigurationReader.get("browser");

            // set the properties value
            prop.setProperty("Tester", tester);
            prop.setProperty("Environment", environment);
            prop.setProperty("Browser", browser);
            prop.setProperty("Project_Name", "Amazon");

            // save properties to project root folder
            prop.store(output, null);

            // System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Before
    public void setUp(Scenario scenario) throws MalformedURLException {
        System.out.println("******** NEW TEST SCENARIO ********");
        System.out.printf("SCENARIO : %s%n", scenario.getName());
        if (!browser.contains("mobile")) {
            Driver.get().manage().window().maximize();
        }
        Driver.get().manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        logger.info("===============================================================");
        logger.info("|          Environment..................: " + ConfigurationReader.get("env"));
        logger.info("|          Browser......................: " + ConfigurationReader.get("browser"));
        logger.info("|          Operating System.............: " + System.getProperty("os.name"));
        logger.info("|          Test.........................: " + ConfigurationReader.get("testName"));
        logger.info("|          Tester.......................: " + ConfigurationReader.get("tester"));
        logger.info("===============================================================\n");
        // create an array for parallel test to add all keys in one Thread (they will be removed from
        // memory in after method again)
        setAllureEnvironment();
        ThreadStateArray.getArray();

    }

    @After
    public void tearDown(Scenario scenario) throws IOException {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.get();
        ScenarioName scenarioName = new ScenarioName();
        String testName = scenarioName.getScenario();
        if (scenario.isFailed()) {
            logger.error("!!!!Test Failed! check the screenshot!!!!");
            final byte[] screenshot = ((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
            //sends test status and test name to browserstack if scenario fails
            if (browser.contains("browserstack")) {
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"\"}}");
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionName\", \"arguments\": {\"name\": \""+testName+"\"}}");
            }
        } else {
            //sends test status and test name to browserstack if scenario passes
            if (browser.contains("browserstack")) {
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"\"}}");
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionName\", \"arguments\": {\"name\": \""+testName+"\"}}");
            }
        }
        generateAllureReport();
        // remove all objects created by using StateHandler Class to use execution of a scenario
        ThreadStateArray.removeThreadArray();
        Driver.closeDriver();
    }
}
