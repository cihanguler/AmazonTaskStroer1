package de.amazon.runners;


import io.cucumber.core.cli.Main;

public class TestRunner {

    public static void main (String[] arg){
        SecurityManager manager = new IgnoreExitCall();
        System.setSecurityManager(manager);
        try {
            Main.main(
                  "classpath:features",
                    "-t", "@wip",
                    "-g", "de.amazon.stepDefinitions",
                    "-p", "pretty",
                    "-p", "json:target/cucumber-reports/Cucumber.json",
                    "-p", "html:target/myReports/default-html-reports",
                    "-m"
            );
        } catch (SecurityException securityException) {
            System.out.println("Ignore exit");
        }

    }

}
