package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withLname("Sunny").withFname("Irina").withCompany("Incom").withAddress("Street").withEmail("1@mail.ru").withHomePhone("123456789")
              .withDay(6).withMonth(10).withYear("2000").withGroup("[none]"), true);
    }
  }

  @Test
  public void testContactPhones() {
    app.goTo().gotoHomePage();
    ContactData contact = app.contact().all().iterator().next();  // загружаем множество контактов и выбираем контакт случайным образом
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
//    assertThat(contact.getWorkPhone(), equalTo(cleaned(contactInfoFromEditForm.getWorkPhone())));
  }

  // Сначала отфильтровываем пустые строки, потом ко всем телефонам применяем ф-цию очистки. Из получившихся очищенных телефонов собираем одну большую
  // строчку при помощи колектора joining и получившийся рез-т сравниваем с тем, к-ый был загружен с главной страницы приложения. Это метод обратных проверок.
  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone()) // формируем из телефонов коллекцию
            .stream().filter((s) -> !s.equals(""))  // .stream() - превращаем список в поток, затем фильтруем поток (выбрасываем те, к-ые равны пустой строке)
            .map(ContactPhoneTests::cleaned)  // map() - применить ко все эл-ам потока какую-то ф-цию и вернуть поток, состоящий из рез-ов этой ф-ции.
            .collect(Collectors.joining("\n"));  // коллектор, к-ый склеивает все эл-ты потока в одну строку. В качестве параметра передается
  }                                                        // разделитель "\n", это та строчка, к-ая будет вставляться между склеиваемыми фрагментами

  public static String cleaned (String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");  // replaceAll() - заменить все вхождения чего-то на что-то
  }

}

// "\\s" - пробельный символ (пробел, табуляцияб перевод строки).  "[a-z]" - заменить любую букву от a до z
// "[-az]" - заменить "-", букву а и букву z,    ("[-()]" - заменить "-", "(" и ")"