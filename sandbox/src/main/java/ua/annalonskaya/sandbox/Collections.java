package ua.annalonskaya.sandbox;

/**
 * Created by Admin on 05.03.2017.
 */
public class Collections {

  public static void main (String[] args) {
//    String[] langs = new String[4];  // объявлена переменная типа "массив строк"
//    langs[0] = "Java";
//    langs[1] = "C#";
//    langs[2] = "Python";
//    langs[3] = "PHP";
//    или :
    String[] langs = {"Java", "C#", "Python", "PHP"};

//    for (int i = 0; i < langs.length; i++) {
//      System.out.println("Я хочу выучить " + langs[i]);
//    }
//    или:
    for (String l : langs) {  // l - ссылка на элемент массива.
      System.out.println("Я хочу выучить " + l);  // Переменная l последовательно указывает на все элементы коллекции
    }
  }
}
