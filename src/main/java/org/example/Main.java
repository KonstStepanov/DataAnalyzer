package org.example;

import java.util.*;

/**
 * класс генерации жителей и поиск результата запросов
 */
public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000L; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        //Количество несовершеннолетних
        long minorCount = persons.stream()
                .filter(x -> x.getAge() < 18)
                .count();
        System.out.println("Minors found: " + minorCount);
        //Список фамилий призывников
        List<String> conscriptsList = persons.stream()
                .filter(x -> x.getSex() == Sex.MAN)
                .filter(y -> y.getAge() >= 18)
                .filter(z -> z.getAge() <= 27)
                .map(Person::getFamily).toList();
        System.out.println("Conscript man list with surname: " + conscriptsList);
        //Отсортированный по фамилии список потенциально работоспособных людей с высшим образованием
        List<String> workClassList = persons.stream()
                .filter(x -> (x.getEducation() == Education.HIGHER))
                .filter(y -> (y.getAge() >= 18))
                .filter(z -> (z.getSex() == Sex.MAN && z.getAge() <= 65)
                        || (z.getSex() == Sex.WOMAN && z.getAge() <= 60))
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::getFamily).toList();
        System.out.println("List of alphabetically sorted surname working class: " + workClassList);
    }
}