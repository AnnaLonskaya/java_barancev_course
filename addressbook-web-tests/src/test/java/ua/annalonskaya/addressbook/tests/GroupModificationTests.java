package ua.annalonskaya.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.GroupData;

import java.util.List;

/**
 * Created by Admin on 21.02.2017.
 */
public class GroupModificationTests extends TestBase{

  @Test
  public void testGroupModification() {
    app.getNavigationHelper().gotoGroupPage();
    if (! app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().selectGroup(before.size() - 1);
    app.getGroupHelper().initGroupModification();
    app.getGroupHelper().fillGroupForm(new GroupData("test1", "test4", "test5"));
    app.getGroupHelper().submitGroupModification();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size());
  }

}
