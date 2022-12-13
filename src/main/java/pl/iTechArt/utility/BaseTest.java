package pl.iTechArt.utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.Properties;

public class BaseTest {

    public WebDriver driver;

    public void initializeDriver() throws IOException {

        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "C:/Users/Adam_Molecki/IdeaProjects/RahulProject/src/main/resources/GlobalData.properties");
        properties.load(fis);
        String browserName = properties.getProperty("browser");

        if(browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if(browserName.equalsIgnoreCase("firefox")){
            //Provide the path for firefox
        }

        driver.manage().window().maximize();

    }
}
