package deepclone.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Man {

    private String name;
    private int age;
    private double weight;
    private char sex;
    private Date birthday;
    private List<String> favoriteBooks;
    private String[] favoriteFilms;
    private Set<Integer> favoriteNumbers;

    public Man(String name, int age, List<String> favoriteBooks) {
        this.name = name;
        this.age = age;
        this.favoriteBooks = favoriteBooks;
    }

    public Man(String name, int age, double weight, char sex, Date birthday,
               List<String> favoriteBooks, String[] favoriteFilms, Set<Integer> favoriteNumbers) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.sex = sex;
        this.birthday = birthday;
        this.favoriteBooks = favoriteBooks;
        this.favoriteFilms = favoriteFilms;
        this.favoriteNumbers = favoriteNumbers;
    }

    public String getInfo() {
        return String.format("NAME: %s; AGE: %s; WEIGHT: %s; SEX: %s; BIRTHDAY: %s; BOOKS: %s; FILMS: %s, NUMBERS: %s",
                name, age, weight, sex, getBirthdayString(), getFavoriteBooksString(), getFavoriteFilmsString(),
                getFavoriteNumbersString());
    }

    public String getBirthdayString() {
        if (birthday != null)
            return new SimpleDateFormat("dd.MM.yyyy").format(birthday);
        return "";
    }

    public String getFavoriteBooksString() {
        if (favoriteBooks != null)
            return String.join(", ", favoriteBooks);
        return "";
    }

    public String getFavoriteNumbersString() {
        if (favoriteNumbers != null) {
            return favoriteNumbers.stream().map(Object::toString).collect(Collectors.joining(", "));
        }

        return "";
    }

    public String getFavoriteFilmsString() {
        if (favoriteFilms != null)
            return String.join(", ", favoriteFilms);
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<String> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<String> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public String[] getFavoriteFilms() {
        return favoriteFilms;
    }

    public void setFavoriteFilms(String[] favoriteFilms) {
        this.favoriteFilms = favoriteFilms;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Set<Integer> getFavoriteNumbers() {
        return favoriteNumbers;
    }

    public void setFavoriteNumbers(Set<Integer> favoriteNumbers) {
        this.favoriteNumbers = favoriteNumbers;
    }
}
