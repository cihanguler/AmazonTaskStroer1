package de.amazon.stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.util.HashMap;

public class ScenarioName {
    private static HashMap<Integer, String> scenarios;

    public ScenarioName() { // or even inside of your singleton's getInstance();
        if (scenarios == null) scenarios = new HashMap<Integer, String>();
    }

    @Before
    public void scenarioClassHook(Scenario scenario) {
        addScenario(scenario.getName());
    }

    private void addScenario(String scenario) {
        Thread currentThread = Thread.currentThread();
        int threadID = currentThread.hashCode();
        scenarios.put(threadID, scenario);
    }

    public synchronized String getScenario() {
        Thread currentThread = Thread.currentThread();
        int threadID = currentThread.hashCode();
        return scenarios.get(threadID);
    }
}
