package de.amazon.utilities;

import de.amazon.stepDefinitions.ScenarioName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

public class BrowserUtils {

    public static String getScenarioNumber() {
        String scenario = new ScenarioName().getScenario();
        // System.out.println("scenario = " + scenario);
        String[] s = scenario.split(" ");
        return s[0];
    }

    public static Object getValueOfScenarioNumAddedKey(String key) {
        ScenarioName scenarioName = new ScenarioName();
        String scenario = scenarioName.getScenario();
        String[] s = scenario.split(" ");
        return ThreadStateHandler.getValue(key + "_" + s[0]);
    }

    public static void deleteKey(String key) {
        String s1 = key + "_" + getScenarioNumber();
        Object value = getValueOfScenarioNumAddedKey(s1);
        ThreadStateHandler.removeKey(s1);
        System.out.println("deleted key= " + s1 + " and value= " + value);
    }

    public static void setKeyAndValueWithScenarioNumber(String key, Object value) {
        String k = key + "_" + getScenarioNumber();
        ThreadStateHandler.setValue(k, value);
        UniqueArrayListForThread.getArray().add(k);//adds key in array to delete at the end of scenario in @After method in
        // Hooks
    }

    /**
     * Performs a pause
     *
     * @param seconds
     */
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void selectDropDownElementByValue(String value, String productNumber, String valueOfID) throws MalformedURLException {
        WebElement dropDownElement = Driver.get().findElement(By.xpath("(//*[@id='"+valueOfID+"'])["+ productNumber+"]"));
        Select dropDownElements = new Select(dropDownElement);
        dropDownElements.selectByValue(value);
    }

}