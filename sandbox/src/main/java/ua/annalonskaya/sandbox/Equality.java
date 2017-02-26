package ua.annalonskaya.sandbox;

/**
 * Created by Admin on 26.02.2017.
 */
public class Equality {

  public static void main(String[] args) {
    String s1 = "firefox";
    String s2 = new String(s1);

    System.out.println(s1 == s2); // сравниваются две ссылки на объект (физическое сравнение)  результат: False
    System.out.println(s1.equals(s2)); // сравнивается содержимое объектов (логическое сравнение)  результат: True
   }
}
