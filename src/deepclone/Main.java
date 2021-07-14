package deepclone;

import deepclone.entity.Employee;
import deepclone.entity.Man;
import deepclone.utils.CopyUtils;

import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Копирование объекта Man");
        Man man = createMan();
        Man copyMan = (Man) CopyUtils.deepCopy(man);
        System.out.println("Оригинал - " + man.getInfo());
        System.out.println("Копия - " + copyMan.getInfo());

        System.out.println("\nПроверка, что оригинал и копия ссылаются на разные списки книг");
        man.getFavoriteBooks().add("New book");
        System.out.println("Оригинал - BOOKS: " + man.getFavoriteBooksString());
        System.out.println("Копия - BOOKS: " + copyMan.getFavoriteBooksString());

        System.out.println("\nПроверка, что оригинал и копия ссылаются на разные массивы с фильмами");
        man.getFavoriteFilms()[0] = ("New film");
        System.out.println("Оригинал - FILMS: " + man.getFavoriteFilmsString());
        System.out.println("Копия - FILMS: " + copyMan.getFavoriteFilmsString());

        System.out.println("\nКопирование объекта Employee");
        Employee emp = createEmployee(man);
        emp.setChief(emp);    // self-reference
        Employee copyEmp = (Employee) CopyUtils.deepCopy(emp);
        System.out.println("Оригинал - " + emp.getFullInfo());
        System.out.println("Копия - " + copyEmp.getFullInfo());

        System.out.println("\nПроверка, что оригинал и копия ссылаются на разные объекты Man");
        man.setName("Alex");
        System.out.println("Оригинал - MAN: " + emp.getMan().getName());
        System.out.println("Копия - MAN: " + copyEmp.getMan().getName());

        System.out.println("\nПроверка, что оригинал и копия ссылаются на разные списки массивов");
        emp.getWorkDays().get(0)[0] = "Tuesday";
        System.out.println("Оригинал - WORKDAYS: " + emp.getWorkDaysString());
        System.out.println("Копия - WORKDAYS:" + copyEmp.getWorkDaysString());
    }

    private static Man createMan() {
        List<String> books = new ArrayList<>();
        books.add("Alice in wonderland");
        books.add("War and Peace");

        String[] films = {"Lion King", "The Matrix"};

        Set<Integer> numbers = new HashSet<>();
        numbers.add(5);
        numbers.add(7);

        return new Man("Ivan", 22, 70.7, 'M', new Date(), books, films, numbers);
    }

    private static Employee createEmployee(Man man) {
        String[] workDays1 = {"Monday", "Wednesday", "Friday"};
        String[] workDays2 = {"Saturday", "Sunday"};
        List<String[]> workDaysList = new ArrayList<>();
        workDaysList.add(workDays1);
        workDaysList.add(workDays2);

        return new Employee(man, new BigDecimal(50000), workDaysList, true);
    }
}
