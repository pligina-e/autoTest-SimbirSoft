package data;

public class Student {
    //атрибуты класса
    public String name, surname, email, mobile, pictureName, picturePath, address, state, city;
    public int gender, day, month, year, subject, hobby;
    //конструктор
    public Student(String name,
                String surname,
                String email,
                int gender,
                String mobile,
                int day, int month, int year,
                int subject,
                int hobby,
                String pictureName,
                String picturePath,
                String address,
                String state,
                String city) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
        this.mobile = mobile;
        this.day = day;
        this.month = month;
        this.year = year;
        this.subject = subject;
        this.hobby = hobby;
        this.pictureName = pictureName;
        this.picturePath = picturePath;
        this.address = address;
        this.state = state;
        this.city = city;
    }
    //геттеры
    public String getFullName() { //получить имя и фамилию студента в виде одной строки
        return name + " " + surname;
    }
    public String getStateAndCity() { //получить штат и город студента в виде одной строки
        return state + " " + city;
    }
    public String getBirth() { //возвращаем дату рождения в виде строки
        final String[] aMouth = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        year+= 1900;
        if (day < 10) return "0" + day + " " + aMouth[month] + "," + year;
        else return day + " " + aMouth[month] + "," + year;
    }
    public String getGender() { //возвращаем гендер как строковое значение
        final String[] aGender = {"Male", "Female", "Other"};
        return aGender[gender-1];
    }
    public String getHobby() { //возвращаем хобби как строковое значение
        final String[] aHobby = {"Sports", "Reading", "Music"};
        return aHobby[hobby-1];
    }
    public String getSubject() { //возвращаем предмет как строковое значение
        final String[] aSubject = {"Hindi", "English", "Physics", "Chemistry",
                                   "Biology", "Computer Science", "Accounting",
                                   "Economics", "Social Studies", "History", "Civics"};
        return aSubject[subject];
    }
    //грубо говоря, чтобы сохранить логику кода, геттеры были оставлены в данном классе
    //т.к. класс Student отвечает за "выходные" данные, а класс GenerateData за "входные" данные
}
