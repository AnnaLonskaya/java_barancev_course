package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.GroupData;


public class GroupCreationTests extends TestBase{

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().gotoGroupPage();
    app.getGroupHelper().createGroup(new GroupData("test1", null, null));
  }

}
