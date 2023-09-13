package pages;

import data.Student;
import generator.GenerateData;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class StudentRegFormPage extends BasePage {
    //Конструктор
    public StudentRegFormPage(ChromeDriver driver) {
        super(driver);
    }
    String url = "https://demoqa.com/automation-practice-form"; //ссылка на страницу
    GenerateData generator = new GenerateData();
    Student student = generator.getDetails();
    //Элементы страницы
    private By firstNameField     = By.id("firstName"); //ищем элемент по id-селектору
    private By lastNameField      = By.id("lastName");
    private By emailField         = By.id("userEmail");
    //ищем элемент по css-селектору:
    private By genderRadioButton  = By.cssSelector(String.format("label[for='gender-radio-%s']", student.gender));
    private By mobileNumberField  = By.id("userNumber");
    private By birthdayDatePicker = By.id("dateOfBirthInput");
    //выпадающий список месяцев всплывающего календаря:
    private By monthList          = By.xpath("//*[@class=\"react-datepicker__month-select\"]");
    //выпадающий список годов всплывающего календаря:
    private By yearList           = By.xpath("//*[@class=\"react-datepicker__year-select\"]");
    private By subjectsList       = By.id("subjectsInput"); //поле для ввода названия предмета
    private By subjectListItems   = By.id(String.format("react-select-2-option-%s", student.subject)); //выпадающий список предметов
    private By hobbiesCheckBox    = By.cssSelector(String.format("label[for='hobbies-checkbox-%s']", student.hobby));
    private By pictureInput       = By.id("uploadPicture");
    private By addressField       = By.id("currentAddress");
    private By statesList         = By.id("react-select-3-input");
    private By citiesList         = By.id("react-select-4-input");
    private By submitButton       = By.id("submit");
    private By resultTable        = By.xpath("//div[@class=\"table-responsive\"]//td[2]"); //ищем элемент по xpath-селектору:
    private By dialogTitle        = By.id("example-modal-sizes-title-lg");
    //Методы
    public StudentRegFormPage openPage () { //открываем страницу по ссылке с помощью метода "get(...)"
        driver.get(url);
        return this;
    }
    public StudentRegFormPage checkTitle() { //сверяемся, находимся ли мы на нужной странице
        String sTitle = driver.getTitle();
        assertEqualsString("Вы находитесь не на той странице!", "DEMOQA", sTitle);
        return this;
    }
    private void setBirthday() { //метод для установки даты рождения
        int iDay= student.day, iMonth = student.month, iYear = student.year;
        String sDatepickerDay;
        click(birthdayDatePicker); //кликаем на элемент, чтобы открылся всплывающий календарь

        Select monthSelect = new Select(find(monthList)); //обращаемся к выпадающему списку
        monthSelect.selectByIndex(iMonth); //выбираем элемент выпад.списка по индексу, сгенерир-му в классе GenerateData

        Select yearSelect = new Select(find(yearList));
        yearSelect.selectByIndex(iYear);

        if (iDay < 10) sDatepickerDay = String.format("//div[contains(@class, 'react-datepicker__day--00%s')]", iDay);
        else if (iDay > 9 && iDay < 14) sDatepickerDay = String.format("//div[contains(@class, 'react-datepicker__day--0%s')]", iDay);
        else sDatepickerDay = String.format("(//div[contains(@class, 'react-datepicker__day--0%s')])[last()]", iDay);

        click(By.xpath(sDatepickerDay)); //кликаем день в всплывающем календаре
    }
    public void setSubject() { //метод для установки предмета
        writeText(subjectsList, "i"); //записываем в поле букву "i" для высвечивания выпадающего списка из 11 элементов
        click(subjectListItems); //нажимаем на рандомный элемент выпадающего списка
    }
    public void setStateAndCity() { //метод для установки штата и города
        writeTextAndPressEnter(statesList,student.state); //вписываем штат и нажимаем "Enter"
        writeTextAndPressEnter(citiesList,student.city); //вписываем город и нажимаем "Enter"
    }
    public void clickSubmitButton() { //метод для нажатия на кнопку "Submit"
        //Элемент присутствует, но имеет постоянное наложение, поэтому не сработает "driver.findElement(submitButton).click();"
        //=> используем JavascriptExecutor для отправки клика непосредственно на элемент
        JavascriptExecutor executor = driver;
        executor.executeScript("arguments[0].click();", driver.findElement(submitButton));
    }

    public ArrayList<String> resultTableList() { //запоминаем значения таблицы, находящиеся со 2-стр, 2-стб
        List<WebElement> list = driver.findElements(resultTable);
        ArrayList<String> listText = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            listText.add(list.get(i).getText());
        }
        return listText;
    }

    //Метод для заполнения страницы данными
    public StudentRegFormPage fillFields () {
        writeText(firstNameField, student.name); //заполняем поле First Name произвольной строкой
        writeText(lastNameField, student.surname); //заполняем поле Last Name произвольной строкой
        writeText(emailField, student.email); //заполняем поле Email Name строкой формата name@example.com
        writeText(mobileNumberField, student.mobile); //заполняем поле Mobile произвольнями 10 цифрами
        writeText(addressField, student.address); //заполняем поле Current Address произвольной строкой
        writeText(pictureInput, student.picturePath); //загружаем любое изображение в поле Picture
        click(genderRadioButton); //выбираем любое значение в Gender с помощью переключателя
        click(hobbiesCheckBox); //выбираем любое значение в Hobbies (сделала это, но в тест-кейсе не говорилось об этом)
        setStateAndCity(); //выбираем любое значение в Select State и в Select City с помощью выпадающего списка
        setSubject(); //заполняем поле Subjects произвольной строкой
        setBirthday(); //заполняем поле Date of birth произвольной датой с помощью всплывающего календаря
        clickSubmitButton(); //нажимаем кнопку Submit
        return this;
    }
    //Проверка данных таблицы с введенными ранее значениями
    public StudentRegFormPage verifyTableData () {
        //проверяем, появилось ли всплывающее окно с заголовком "Thanks for submitting the form"
        assertEqualsString("Текст заголовка всплывающего окна неверен!", "Thanks for submitting the form", find(dialogTitle).getText());

        //создаем переменную listTableData, хранящую значения второго столбца таблицы (начиная со второй строки)
        ArrayList<String> listTableData = resultTableList();
        //сверяем ранее введенные значения (хранящиеся в экз.класса Student) со значениями, получившемися в табл.
        assertEqualsString("Полное имя неверно!", student.getFullName(), listTableData.get(0));
        assertEqualsString("Эл.почта неверна!", student.email, listTableData.get(1));
        assertEqualsString("Гендер неверен!", student.getGender(), listTableData.get(2));
        assertEqualsString("Телефон неверен!", student.mobile, listTableData.get(3));
        assertEqualsString("Дата рождения неверна!", student.getBirth(), listTableData.get(4));
        assertEqualsString("Предмет неверен!", student.getSubject(), listTableData.get(5));
        assertEqualsString("Хобби неверно!", student.getHobby(), listTableData.get(6));
        assertEqualsString("Картинка неверна!", student.pictureName, listTableData.get(7));
        assertEqualsString("Адрес неверен!", student.address, listTableData.get(8));
        assertEqualsString("Штат/Город неверен!", student.getStateAndCity(), listTableData.get(9));

        //также можно было бы сверять значения, записанные в поля веб-элементов, со знач-ми таблицы.
        //но, думаю, логичнее проверять значения, сгенерированные в переменные кода,
        //а не записанные в поля элементов страницы. На всякий случай оставляю комментарий
        //assertEqualsText(genderRadioButton, listTableData.get(2));
        //assertEqualsAttribute(mobileNumberField, listTableData.get(3));
        return this;
    }
}


