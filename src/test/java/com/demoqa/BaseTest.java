package com.demoqa;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    public ChromeDriver driver;
    String driverPath = "D:\\chromedriver\\chromedriver.exe"; //напишите свой путь драйвера
    @Before //делаем перед тестом
    public void setup () {
        System.setProperty("webdriver.chrome.driver", driverPath);
        //WebDriverManager.chromedriver().setup();//можно исп-ть это, закоммент-вав строку выше и прописать в pom.xml зависимость
        driver = new ChromeDriver();
        //открываем окно страницы на весь экран
        driver.manage().window().maximize();
    }
    @After //делаем после завершения теста
    public void close() {
        driver.quit();
    }
}
