package ua.annalonskaya.sandbox;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Admin on 05.03.2017.
 */
public class Collections {

  public static void main (String[] args) {
//    Представление колекции в виде массива:

//    String[] langs = new String[4];  // объявлена переменная типа "массив строк"
//    langs[0] = "Java";
//    langs[1] = "C#";
//    langs[2] = "Python";
//    langs[3] = "PHP";
//    или :
    String[] langs = {"Java", "C#", "Python", "PHP"};  // Масив элементов

//    for (int i = 0; i < langs.length; i++) {
//      System.out.println("Я хочу выучить " + langs[i]);
//    }
//    или:
    for (String l : langs) {  // l - ссылка на элемент массива.
      System.out.println("Я хочу выучить " + l);  // Переменная l последовательно указывает на все элементы коллекции
    }

//    Представление колекции в виде списка:
//    List<String> languages = new ArrayList<>();  // Список элементов. Слева указан интерфейс (String - тип элемента), а справа - конкретный класс, который реализует этот интерфейс
//    languages.add("Java");  // теперь размер списка равен 1
//    languages.add("C#");
//    languages.add("Python");
//    languages.add("PHP");
    // или:
    List<String> languages = Arrays.asList("Java", "C#", "Python", "PHP");

    for (String l : languages) {
      System.out.println("Я хочу выучить " + l);  // Выводятся на печать все элементы списка
    }
  }

}
