package com.demoqa;

import org.junit.Test;
import pages.StudentRegFormPage;

public class StudentRegFormTest extends BaseTest {
    @Test
    public void RegistrationTest () {
        //создаем экземпляр класса "StudentRegFormPage"
        StudentRegFormPage registerPage = new StudentRegFormPage(driver);
        //вызываем методы экземпляра класса "registerPage"
        registerPage.openPage(); //открываем страницу
        registerPage.checkTitle(); //проверяем title страницы
        registerPage.fillFields(); //заполняем страницу данными
        registerPage.verifyTableData(); //сверяем введенные ранее данные с полученным результатом
    }
}
