package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.Test;

/**
 * Created by Admin on 21.02.2017.
 */
public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getContactHelper().initContactAction();
    app.getContactHelper().submitContactDeletion();
    app.getContactHelper().acceptAlert();
  }

}
