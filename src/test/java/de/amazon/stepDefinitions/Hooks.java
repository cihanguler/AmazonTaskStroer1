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
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Hooks {
    Logger logger = LoggerFactory.getLogger(Hooks.class);
    String browser = ConfigurationReader.get("browser");

    @Before
    public void setUp(Scenario scenario) {
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
        // remove all objects created by using StateHandler Class to use execution of a scenario
        ThreadStateArray.removeThreadArray();
        Driver.closeDriver();
    }
}
