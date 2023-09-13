package generator;

import data.Student;
import net.datafaker.Faker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
public class GenerateData {
    //экземпляр класса для создания широкого спектра реалистичных данных
    Faker faker = new Faker();
    //экземпляр класса генератора псевдослучайных чисел
    Random random = new Random();
    //создаем случайные данные с помощью классов Faker и Random:
    String fakeFirstName = faker.name().firstName(); //генерируем случайное имя студента
    String fakeLastName = faker.name().lastName(); //фамилия студента
    String fakeEmailAddress = faker.internet().safeEmailAddress(fakeFirstName); //почта формата "name@example.com" или "name@example.org"
    //можно создать email формата "name.surname@yahoo.com"
    //т.е. с использованием известных почтовых индексов и случайным именем: faker.internet().emailAddress()
    int fakeGender = random.nextInt(3) + 1; //рандомно задаем индекс гендера
    String fakePhoneNumber = faker.phoneNumber().subscriberNumber(10); //номер телефона (10 цифр)
    int fakeMonth = random.nextInt(12); //[янв;дек] рандомно задаем порядковый номер месяца
    int fakeYear = random.nextInt(201); //[1900;2100] рандомно задаем индекс для дальнейшего выбора года рождения
    int fakeDay = getDay(); //задаем день
    int fakeSubject = random.nextInt(11); //рандомно задаем индекс предмета
    int fakeHobby = random.nextInt(3) + 1; //рандомно задаем индекс хобби
    String fakePictureName = pictureName(); //запоминаем название картинки
    String fakePicturePath = picturePath(); //запоминаем расположение (путь) картинки
    String fakeAddress = faker.address().fullAddress(); //место проживания
    String fakeState = getState(); //задаем штат
    String fakeCity = getCityByState(fakeState); //задаем город, опираясь на штат

    public Student getDetails() //сохраняем сгенерированные данные
    {
        return new Student(fakeFirstName,
                fakeLastName,
                fakeEmailAddress,
                fakeGender,
                fakePhoneNumber,
                fakeDay,
                fakeMonth,
                fakeYear,
                fakeSubject,
                fakeHobby,
                fakePictureName,
                fakePicturePath,
                fakeAddress,
                fakeState,
                fakeCity
        );
    }

    public int getDay() { //устанавливаем случайный день в зависимости от месяца (если февраль, то макс. день = 28)
        int iDay;
        if (fakeMonth == 1) iDay = random.nextInt(28) + 1; //[1;28] - диапазон, в пределах которого выпадает значение
        else iDay = random.nextInt(31) + 1; //[1;31]
        return iDay;
    }
    public String getState() { //случайным образом выбираем штат с помощью списка по рандомному индексу
        String[] aState = {"NCR", "Uttar Pradesh", "Haryana", "Rajasthan"};
        int iState = random.nextInt(aState.length);
        String state = aState[iState];
        return state;
    }
    public String getCityByState(String state) { //рандомно выбираем город в зависимости от штата
        String sCity = null;
        int iCity;

        switch (state) {
            case "NCR": //если штат равен "NCR"
                final String[] aCityNCR = {"Delhi", "Gurgaon", "Noida"};
                iCity = random.nextInt(aCityNCR.length); //то рандомно выбираем город из списка
                sCity = aCityNCR[iCity];
                break;

            case "Uttar Pradesh":
                final String[] aCityUttar = {"Agra", "Lucknow", "Merrut"};
                iCity = random.nextInt(aCityUttar.length);
                sCity = aCityUttar[iCity];
                break;

            case "Haryana":
                final String[] aCityHaryana = {"Karnal", "Panipat"};
                iCity = random.nextInt(aCityHaryana.length);
                sCity = aCityHaryana[iCity];
                break;

            case "Rajasthan":
                final String[] aCityRajasthan = {"Jaipur", "Jaiselmer"};
                iCity = random.nextInt(aCityRajasthan.length);
                sCity = aCityRajasthan[iCity];
                break;
        }
        return sCity; //возвращаем название выбранного города
    }

    //ниже написано 4 метода для работы с картинками.
    //идея в том, чтобы создавать рандомную пискельную картинку в папку "images", которая имеет уже несколько картинок,
    //а затем брать рандомно любую картинку из тех, которые уже были в "images" и плюс включая новую созданную картинку.
    //Причем картинка ищется рандомно с нахождением её абсолютного пути.
    //Таким образом выбирается рандомная картинка для загрузки (выберется либо сгенерированная рандомная картинка,
    //либо одна из тех картинок, что уже имеется среди остальных в папке (включая сгенерированную пиксельную)).
    private ArrayList<String> getImagesName() { //метод для вытягивания всех названий картинок в папке "images"
        ArrayList<String> fileNames = new ArrayList<>(); //запомним все названия картинок в папке "images"
        File directoryPictures = new File("src/test/images/"); //обратимся к папке "images"
        String sPath = directoryPictures.getAbsolutePath(); //запоминаем абсолютный путь до папки с картинками
        File filePath = new File(sPath);
        File aFile[] = filePath.listFiles();
        for (int i = 0; i < aFile.length; i++) {
            //сохраняем все имена картинок в ArrayList, чтобы потом можно было по индексу доставать
            fileNames.add(aFile[i].getName());
        }
        return fileNames;
    }
    public String pictureName() { //выбираем рандомную картинку
        //createPicture(); //создаем случайную картинку (можно закомментировать этот метод)
        ArrayList<String> imagesName;
        imagesName = getImagesName(); //берем все имена всех имеющихся картинок в папке
        String sPictureName = imagesName.get(random.nextInt(imagesName.size())); //берем рандомное имя картинки
        return sPictureName;
    }
    public String picturePath() { //ищем абсолютный путь картинки (чтобы не задавать однозначный, т.к.путь мб разный на разных устройствах)
        File file = new File("src/test/images/" + fakePictureName);
        return file.getAbsolutePath();
    }
    //метод для создания случайного пиксельного изображения
    private void createPicture() {
        //размер картинки
        int iWidth = 500; //ширина картинки
        int iHeight = 400; //высота картинки
        //создаем экземпляр класса BufferedImage
        BufferedImage img = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_ARGB);
        //экземпляр класса File (путь для сгенерированной картинки)
        File file = null;
        //создаем случайное изображение попиксельно
        for(int y = 0; y < iHeight; y++) {
            for(int x = 0; x < iWidth; x++) {
                int iAlpha = (int)(Math.random()*256); //альфа
                int iRed = (int)(Math.random()*256); //красный
                int iGreen = (int)(Math.random()*256); //зеленый
                int iBlue = (int)(Math.random()*256); //синий

                //TYPE_INT_ARGB представляет цвет в виде int(4 байта) с альфа-каналом в битах 24–31,
                //красными каналами в 16–23, зелеными в 8–15, синими в 0–7
                int iPixel = (iAlpha<<24) | (iRed<<16) | (iGreen<<8) | iBlue; //пиксель

                img.setRGB(x, y, iPixel); //устанавливаем пиксель на изображение
            }
        }
        //сохраняем картинку в папку проекта
        try{
            //название картинки также делаем случайным с помощью рандома
            //расширение изображения, например, сделаем ".png"
            file = new File("src/test/images/testPicture" + random.nextInt(100) + ".png");
            //картинка такого же названия может уже присутствовать в папке, но нам не важно, если она изменится
            ImageIO.write(img, "png", file);
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }
}
