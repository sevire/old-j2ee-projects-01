package unittests.model.bean;

import co.uk.genonline.simpleweb.model.bean.*;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import support.TestSupportLogger;
import unittests.support.ScreensUnitTestData;
import unittests.support.TestSupport;
import unittests.support.TestSupportSessionFactory;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class ScreensManagerNonCachingTest {
    private SessionFactory factory;
    ScreensManager manager;

    @Before
    public void setUp() throws Exception {
        TestSupportLogger.initLogger();
        this.factory = TestSupportSessionFactory.getSessionFactory();
        TestSupport.clearDatabase(factory);
        manager = new ScreensManagerNonCaching(factory);
    }

    @After
    public void tearDown() throws Exception {
        TestSupport.clearDatabase(factory);
    }

    @Test
    public void testGetScreen() throws Exception {
        ScreensEntity screen = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGS-01");

        // Get screen before it exists to check get right result
        ScreensEntity screen1b = manager.getScreen("testGS-01", false);
        Assert.assertNull(screen1b);

        ScreensEntity screen1c = manager.getScreen("testGS-01", true);
        Assert.assertNull(screen1c);

        // Add screen to database.  Should also add to cache
        boolean status = manager.addScreen(screen);
        Assert.assertTrue(status);

        // Test scenarios

        // Get screen without override so should fail as test object has enabled flag set to false
        ScreensEntity screen1 = manager.getScreen("testGS-01", false);
        Assert.assertNull(screen1);

        // Get screen with override so will always retrieve regardless of enabledFlag
        ScreensEntity screen2 = manager.getScreen("testGS-01", true);
        Assert.assertNotNull(screen2);
        Assert.assertEquals("testGS-01", screen2.getName());

        // Get non-existent screen (should fail)
        ScreensEntity screen3 = manager.getScreen("XXXXX", true);
        Assert.assertNull(screen3);

        // Call with null screen (should fail)
        ScreensEntity screen4 = manager.getScreen(null, true);
        Assert.assertNull(screen4);
    }

    @Test
    public void testAddScreen() throws Exception {
        ScreensEntity screen1 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testAS-01");

        boolean status;
        long timestampErrorMargin = 60000; // 60 Sec. This allows a check whether the created or modified timestamps have been updated

        // --- Scenarios ---

        // --- Scenario 1: Successful add screen

        // Record time we are adding so we can see whether created time in database is similar to confirm correctly added
        Timestamp created1 = new Timestamp(Calendar.getInstance().getTime().getTime());
        status = manager.addScreen(screen1);
        Assert.assertTrue(status);

        ScreensEntity screen2 = manager.getScreen("testAS-01", false);

        // Check that Created Timestamp has been updated to a time "close" to the time recorded before adding screen
        Timestamp created2 = screen2.getCreated();

        long timeDiffMilliseconds = StrictMath.abs(created1.getTime() - created2.getTime());

        Assert.assertTrue(String.format("Created time not updated: addTime <%d>, getTime <%d>",
                created1.getTime(), created2.getTime()), timeDiffMilliseconds < timestampErrorMargin);

        // Check that retrieved screen is the same as the one added.
        Assert.assertEquals(screen1, screen2);

        // Check again but refresh cache first to force read from database (check that saved version is same as in cache)
        ScreensEntity screen3 = manager.getScreen("testAS-01", false);
        System.out.println(screen1);
        System.out.println(screen3);

        Assert.assertEquals(screen1, screen3);

        // --- Scenario 2: Add with invalid or null name

        // First with empty name
        ScreensEntity screen4 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "");
        status = manager.addScreen(screen4);
        Assert.assertFalse(status);

        // Shouldn't need to clean up as database entry shouldn't be created.

        // Then with null name
        ScreensEntity screen5 = ScreensUnitTestData.getTestScreensEntity("general-enabled", null);
        status = manager.addScreen(screen5);
        Assert.assertFalse(status);

        // Shouldn't need to clean up as database entry shouldn't be created.

        // --- Scenario 3: Add with screenName too long (20 max)

        ScreensEntity screen6 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "012345678901234567890");
        status = manager.addScreen(screen6);
        Assert.assertFalse(status);

        // Shouldn't need to clean up as database entry shouldn't be created.

        // --- Scenario 4: Add duplicate screenName

        // First add record successfully
        ScreensEntity screen7 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testAS-02");
        status = manager.addScreen(screen7);
        Assert.assertTrue(status);

        // Then add again - should fail
        status = manager.addScreen(screen7);
        Assert.assertFalse(status);

        // --- Scenario 5: Add with duplicate id
        // Note - removed this because this isn't possible while auto-increment is set.
    }

    @Test
    public void testUpdateScreen() throws Exception {
        ScreensEntity screen1 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testUS-01");

        boolean status;

        // --- Scenarios ---

        // --- Scenario 1: Successful update screen

        // Add screen

        status = manager.addScreen(screen1);
        Assert.assertTrue(status);

        // Update screen changing screenTitleLong

        screen1.setScreenTitleLong("Update Screen Title");

        Timestamp created1 = new Timestamp(Calendar.getInstance().getTime().getTime());
        status = manager.updateScreen(screen1);
        Assert.assertTrue(status);

        // Shouldn't be able to retrieve screen without override as it is disabled
        ScreensEntity screen1a = manager.getScreen("test-01", false);
        Assert.assertNull(screen1a);

        ScreensEntity screen2 = manager.getScreen("testUS-01", true);
        Assert.assertEquals("Update Screen Title", screen2.getScreenTitleLong());

        // Check that Modified Timestamp has been updated to a time "close" to the time recorded before adding screen
        Timestamp created2 = screen2.getCreated();

        long timeDiffMilliseconds = StrictMath.abs(created1.getTime() - created2.getTime());

        Assert.assertTrue(String.format("Created time not updated: addTime <%d>, getTime <%d>",
                created1.getTime(), created2.getTime()), timeDiffMilliseconds < 60000);


        // Update screen changing enabledFlag

        screen1.setEnabledFlag(true);
        status = manager.updateScreen(screen1);
        Assert.assertTrue(status);

        // Should be able to read screen without enabled override now
        ScreensEntity screen4 = manager.getScreen("testUS-01", false);
        Assert.assertTrue(screen4.getEnabledFlag());

        // --- Scenario 2: Invalid updates

        // Update with different primary key, should fail

        screen1.setId(9999);
        status = manager.updateScreen(screen1);
        Assert.assertFalse(status);

        // Update with null or blank screenName

        // First with empty name

        screen1.setName("");
        status = manager.updateScreen(screen1);
        Assert.assertFalse(status);

        // Shouldn't need to clean up as database entry shouldn't be created.

        // Then with null name
        screen1.setName(null);
        status = manager.updateScreen(screen1);
        Assert.assertFalse(status);

        // Shouldn't need to clean up as database entry shouldn't be created.

        // --- Scenario 3: Update with screenName too long (20 max)

        screen1.setName("012345678901234567890");
        status = manager.updateScreen(screen1);
        Assert.assertFalse(status);

        // Shouldn't need to clean up as database entry shouldn't be created.

        // --- Scenario 4: Add duplicate screenName

        // First add second record, then update first record to same screenName

        ScreensEntity screen6 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testUS-02");
        status = manager.addScreen(screen6);
        Assert.assertTrue(status);

        screen1.setName("testUS-02");
        status = manager.updateScreen(screen1);
        Assert.assertFalse(status);
    }

    @Test
    public void testGetScreensByType() throws Exception {

        boolean status;

        // -- Add several records and set ScreenType --

        // Create records...

        ScreensEntity screen01 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGSBT-01");
        ScreensEntity screen02 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGSBT-02");
        ScreensEntity screen03 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGSBT-03");
        ScreensEntity screen04 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGSBT-04");
        ScreensEntity screen05 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGSBT-05");
        ScreensEntity screen06 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGSBT-06");
        ScreensEntity screen07 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGSBT-07");
        ScreensEntity screen08 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGSBT-08");
        ScreensEntity screen09 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGSBT-09");
        ScreensEntity screen10 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGSBT-10");

        // Set Screen Type for each record

        screen01.setScreenType("Category-01");
        screen02.setScreenType("Category-02");
        screen03.setScreenType("Category-03");
        screen04.setScreenType("Category-02");
        screen05.setScreenType("Category-03");
        screen06.setScreenType("Category-01");
        screen07.setScreenType("Category-03");
        screen08.setScreenType("Category-02");
        screen09.setScreenType("Category-01");
        screen10.setScreenType("");

        // Add records

        Assert.assertTrue(manager.addScreen(screen01));
        Assert.assertTrue(manager.addScreen(screen02));
        Assert.assertTrue(manager.addScreen(screen03));
        Assert.assertTrue(manager.addScreen(screen04));
        Assert.assertTrue(manager.addScreen(screen05));
        Assert.assertTrue(manager.addScreen(screen06));
        Assert.assertTrue(manager.addScreen(screen07));
        Assert.assertTrue(manager.addScreen(screen08));
        Assert.assertTrue(manager.addScreen(screen09));
        Assert.assertTrue(manager.addScreen(screen10));

        // Check records added...

        Assert.assertTrue(manager.isScreenExist("testGSBT-01"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-02"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-03"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-04"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-05"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-06"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-07"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-08"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-09"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-10"));

        // Check that method returns the right screens

        List<ScreensEntity> screens1 = manager.getScreensByType("Category-01", true);
        Assert.assertEquals(3, screens1.size());

        for (ScreensEntity screen : screens1) {
            Assert.assertTrue(screen.getName().equals("testGSBT-01") || screen.getName().equals("testGSBT-06") || screen.getName().equals("testGSBT-09"));
        }

        // --- Test when no screens of given category.  Should return null
        List<ScreensEntity> screens2 = manager.getScreensByType("XXXX", true);
        Assert.assertNull(screens2);

        // --- Test when no category too long (>45).  Should return null
        List<ScreensEntity> screens3 =
                manager.getScreensByType("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", true);
        Assert.assertNull(screens3);

        // --- Test null category.  Should return null
        List<ScreensEntity> screens4 = manager.getScreensByType(null, true);
        Assert.assertNull(screens4);
    }

    @Test
    public void testGetScreensByType1() throws Exception {

        boolean status;

        // -- Add several records and set ScreenType.  Also need sortKey, enabledFlag and name to determine sort order --

        // Create records...

        ScreensEntity screen01 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGSBT-01");
        ScreensEntity screen02 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGSBT-02");
        ScreensEntity screen03 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGSBT-03");
        ScreensEntity screen04 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGSBT-04");
        ScreensEntity screen05 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGSBT-05");
        ScreensEntity screen06 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGSBT-06");
        ScreensEntity screen07 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGSBT-07");
        ScreensEntity screen08 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGSBT-08");
        ScreensEntity screen09 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGSBT-09");
        ScreensEntity screen10 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGSBT-10");

        // Set Screen Type for each record

        screen01.setScreenType("Category-01");screen01.setSortKey(50);
        screen02.setScreenType("Category-02");screen02.setSortKey(20);
        screen03.setScreenType("Category-03");screen03.setSortKey(70);
        screen04.setScreenType("Category-02");screen04.setSortKey(80);
        screen05.setScreenType("Category-03");screen05.setSortKey(100);
        screen06.setScreenType("Category-01");screen06.setSortKey(30);
        screen07.setScreenType("Category-03");screen07.setSortKey(20);
        screen08.setScreenType("Category-02");screen08.setSortKey(90);
        screen09.setScreenType("Category-01");screen09.setSortKey(10);
        screen10.setScreenType("");

        // Add records

        Assert.assertTrue(manager.addScreen(screen01));
        Assert.assertTrue(manager.addScreen(screen02));
        Assert.assertTrue(manager.addScreen(screen03));
        Assert.assertTrue(manager.addScreen(screen04));
        Assert.assertTrue(manager.addScreen(screen05));
        Assert.assertTrue(manager.addScreen(screen06));
        Assert.assertTrue(manager.addScreen(screen07));
        Assert.assertTrue(manager.addScreen(screen08));
        Assert.assertTrue(manager.addScreen(screen09));
        Assert.assertTrue(manager.addScreen(screen10));

        // Check records added...

        Assert.assertTrue(manager.isScreenExist("testGSBT-01"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-02"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-03"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-04"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-05"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-06"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-07"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-08"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-09"));
        Assert.assertTrue(manager.isScreenExist("testGSBT-10"));

        // Check that method returns the right screens

        List<ScreensEntity> screens1 = manager.getScreensByType("Category-01", ScreensSortType.ADMIN_SCREEN, true);
        Assert.assertEquals(3, screens1.size());

        System.out.println((new ScreensEntityDecorator(screens1.get(0)).toString()));
        System.out.println((new ScreensEntityDecorator(screens1.get(1)).toString()));
        System.out.println((new ScreensEntityDecorator(screens1.get(2)).toString()));

        Assert.assertEquals(("testGSBT-09"), screens1.get(0).getName());
        Assert.assertEquals(("testGSBT-06"), screens1.get(1).getName());
        Assert.assertEquals(("testGSBT-01"), screens1.get(2).getName());

        List<ScreensEntity> screens2 = manager.getScreensByType("Category-02", ScreensSortType.ADMIN_SCREEN,true );
        Assert.assertEquals(3, screens2.size());

        System.out.println((new ScreensEntityDecorator(screens2.get(0)).toString()));
        System.out.println((new ScreensEntityDecorator(screens2.get(1)).toString()));
        System.out.println((new ScreensEntityDecorator(screens2.get(2)).toString()));

        Assert.assertEquals(("testGSBT-08"), screens2.get(0).getName());
        Assert.assertEquals(("testGSBT-02"), screens2.get(1).getName());
        Assert.assertEquals(("testGSBT-04"), screens2.get(2).getName());

        // --- Test when no screens of given category.  Should return null
        List<ScreensEntity> screens3 = manager.getScreensByType("XXXX", true);
        Assert.assertNull(screens3);

        // --- Test when no category too long (>45).  Should return null
        List<ScreensEntity> screens4 =
                manager.getScreensByType("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", true);
        Assert.assertNull(screens4);

        // --- Test null category.  Should return null
        List<ScreensEntity> screens5 = manager.getScreensByType(null, true);
        Assert.assertNull(screens5);
    }

    @Test
    public void testGetAllScreens() throws Exception {

        boolean status;

        // -- Add several records and set ScreenType.  Also need sortKey, enabledFlag and name to determine sort order --

        // Create records...

        ScreensEntity screen01 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGAS-01");
        ScreensEntity screen02 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGAS-02");
        ScreensEntity screen03 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGAS-03");
        ScreensEntity screen04 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGAS-04");
        ScreensEntity screen05 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGAS-05");
        ScreensEntity screen06 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGAS-06");
        ScreensEntity screen07 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGAS-07");
        ScreensEntity screen08 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGAS-08");
        ScreensEntity screen09 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGAS-09");
        ScreensEntity screen10 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGAS-10");

        // Set Screen Type for each record

        screen01.setScreenType("Category-01");screen01.setSortKey(50);
        screen02.setScreenType("Category-02");screen02.setSortKey(20);
        screen03.setScreenType("Category-03");screen03.setSortKey(70);
        screen04.setScreenType("Category-02");screen04.setSortKey(80);
        screen05.setScreenType("Category-03");screen05.setSortKey(100);
        screen06.setScreenType("Category-01");screen06.setSortKey(30);
        screen07.setScreenType("Category-03");screen07.setSortKey(20);
        screen08.setScreenType("Category-02");screen08.setSortKey(90);
        screen09.setScreenType("Category-01");screen09.setSortKey(10);
        screen10.setScreenType("");

        // Add records

        Assert.assertTrue(manager.addScreen(screen01));
        Assert.assertTrue(manager.addScreen(screen02));
        Assert.assertTrue(manager.addScreen(screen03));
        Assert.assertTrue(manager.addScreen(screen04));
        Assert.assertTrue(manager.addScreen(screen05));
        Assert.assertTrue(manager.addScreen(screen06));
        Assert.assertTrue(manager.addScreen(screen07));
        Assert.assertTrue(manager.addScreen(screen08));
        Assert.assertTrue(manager.addScreen(screen09));
        Assert.assertTrue(manager.addScreen(screen10));

        // Check records added...

        Assert.assertTrue(manager.isScreenExist("testGAS-01"));
        Assert.assertTrue(manager.isScreenExist("testGAS-02"));
        Assert.assertTrue(manager.isScreenExist("testGAS-03"));
        Assert.assertTrue(manager.isScreenExist("testGAS-04"));
        Assert.assertTrue(manager.isScreenExist("testGAS-05"));
        Assert.assertTrue(manager.isScreenExist("testGAS-06"));
        Assert.assertTrue(manager.isScreenExist("testGAS-07"));
        Assert.assertTrue(manager.isScreenExist("testGAS-08"));
        Assert.assertTrue(manager.isScreenExist("testGAS-09"));
        Assert.assertTrue(manager.isScreenExist("testGAS-10"));

        // --- Check can retrieve screens in right order with or without disabled records ---

/*      Correct Order is...

        Enabled First
        screen10 ("");
        screen09 ("Category-01") SortKey(10); Enabled
        screen06 ("Category-01") SortKey(30); Enabled
        screen08 ("Category-02") SortKey(90); Enabled
        screen07 ("Category-03") SortKey(20); Enabled
        screen05 ("Category-03") SortKey(100); Enabled

        Disabled next (if enabledOverride is true
        screen01 ("Category-01") SortKey(50); Disabled
        screen02 ("Category-02") SortKey(20); Disabled
        screen04 ("Category-02") SortKey(80); Disabled
        screen03 ("Category-03") SortKey(70); Disabled

*/
        // ADMIN_SCREEN order, enabledFlag desc, screenType, sortKey, name

        // First without disabled record (i.e. enabledOveride is false)
        List<ScreensEntity> screens1 = manager.getAllScreens(ScreensSortType.ADMIN_SCREEN, false);
        Assert.assertEquals(6, screens1.size());

        // Check the right screens are retrieved
        Assert.assertEquals("testGAS-10", screens1.get(0).getName()); Assert.assertEquals(screen10, screens1.get(0));
        Assert.assertEquals("testGAS-09", screens1.get(1).getName()); Assert.assertEquals(screen09, screens1.get(1));
        Assert.assertEquals("testGAS-06", screens1.get(2).getName()); Assert.assertEquals(screen06, screens1.get(2));
        Assert.assertEquals("testGAS-08", screens1.get(3).getName()); Assert.assertEquals(screen08, screens1.get(3));
        Assert.assertEquals("testGAS-07", screens1.get(4).getName()); Assert.assertEquals(screen07, screens1.get(4));
        Assert.assertEquals("testGAS-05", screens1.get(5).getName()); Assert.assertEquals(screen05, screens1.get(5));

        // Now with disabled records
        List<ScreensEntity> screens2 = manager.getAllScreens(ScreensSortType.ADMIN_SCREEN, true);
        Assert.assertEquals(10, screens2.size());

        // Check the right screens are retrieved
        Assert.assertEquals("testGAS-10", screens2.get(0).getName()); Assert.assertEquals(screen10, screens2.get(0));
        Assert.assertEquals("testGAS-09", screens2.get(1).getName()); Assert.assertEquals(screen09, screens2.get(1));
        Assert.assertEquals("testGAS-06", screens2.get(2).getName()); Assert.assertEquals(screen06, screens2.get(2));
        Assert.assertEquals("testGAS-08", screens2.get(3).getName()); Assert.assertEquals(screen08, screens2.get(3));
        Assert.assertEquals("testGAS-07", screens2.get(4).getName()); Assert.assertEquals(screen07, screens2.get(4));
        Assert.assertEquals("testGAS-05", screens2.get(5).getName()); Assert.assertEquals(screen05, screens2.get(5));
        Assert.assertEquals("testGAS-01", screens2.get(6).getName()); Assert.assertEquals(screen01, screens2.get(6));
        Assert.assertEquals("testGAS-02", screens2.get(7).getName()); Assert.assertEquals(screen02, screens2.get(7));
        Assert.assertEquals("testGAS-04", screens2.get(8).getName()); Assert.assertEquals(screen04, screens2.get(8));
        Assert.assertEquals("testGAS-03", screens2.get(9).getName()); Assert.assertEquals(screen03, screens2.get(9));
    }

    @Test
    public void testGetAllScreenNames() throws Exception {

        // -- Add several records and set ScreenType.  Also need sortKey, enabledFlag and name to determine sort order --

        // Create records...

        ScreensEntity screen01 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGASN-01");
        ScreensEntity screen02 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGASN-02");
        ScreensEntity screen03 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGASN-03");
        ScreensEntity screen04 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testGASN-04");
        ScreensEntity screen05 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGASN-05");
        ScreensEntity screen06 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGASN-06");
        ScreensEntity screen07 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGASN-07");
        ScreensEntity screen08 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGASN-08");
        ScreensEntity screen09 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGASN-09");
        ScreensEntity screen10 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testGASN-10");

        // Set Screen Type for each record

        screen01.setScreenType("Category-01");screen01.setSortKey(50);
        screen02.setScreenType("Category-02");screen02.setSortKey(20);
        screen03.setScreenType("Category-03");screen03.setSortKey(70);
        screen04.setScreenType("Category-02");screen04.setSortKey(80);
        screen05.setScreenType("Category-03");screen05.setSortKey(100);
        screen06.setScreenType("Category-01");screen06.setSortKey(30);
        screen07.setScreenType("Category-03");screen07.setSortKey(20);
        screen08.setScreenType("Category-02");screen08.setSortKey(90);
        screen09.setScreenType("Category-01");screen09.setSortKey(10);
        screen10.setScreenType("");

        // Add records

        Assert.assertTrue(manager.addScreen(screen01));
        Assert.assertTrue(manager.addScreen(screen02));
        Assert.assertTrue(manager.addScreen(screen03));
        Assert.assertTrue(manager.addScreen(screen04));
        Assert.assertTrue(manager.addScreen(screen05));
        Assert.assertTrue(manager.addScreen(screen06));
        Assert.assertTrue(manager.addScreen(screen07));
        Assert.assertTrue(manager.addScreen(screen08));
        Assert.assertTrue(manager.addScreen(screen09));
        Assert.assertTrue(manager.addScreen(screen10));

        // Check records added...

        Assert.assertTrue(manager.isScreenExist("testGASN-01"));
        Assert.assertTrue(manager.isScreenExist("testGASN-02"));
        Assert.assertTrue(manager.isScreenExist("testGASN-03"));
        Assert.assertTrue(manager.isScreenExist("testGASN-04"));
        Assert.assertTrue(manager.isScreenExist("testGASN-05"));
        Assert.assertTrue(manager.isScreenExist("testGASN-06"));
        Assert.assertTrue(manager.isScreenExist("testGASN-07"));
        Assert.assertTrue(manager.isScreenExist("testGASN-08"));
        Assert.assertTrue(manager.isScreenExist("testGASN-09"));
        Assert.assertTrue(manager.isScreenExist("testGASN-10"));

        List<String> names1 = manager.getAllScreenNames(ScreensSortType.NAME_ORDER, false, false);
        Assert.assertNotNull(names1);
        Assert.assertEquals(6, names1.size());

        // Check that have the right names (enabled but not disabled)
        Assert.assertFalse(names1.contains("testGASN-01"));
        Assert.assertFalse(names1.contains("testGASN-02"));
        Assert.assertFalse(names1.contains("testGASN-03"));
        Assert.assertFalse(names1.contains("testGASN-04"));
        Assert.assertTrue(names1.contains("testGASN-05"));
        Assert.assertTrue(names1.contains("testGASN-06"));
        Assert.assertTrue(names1.contains("testGASN-07"));
        Assert.assertTrue(names1.contains("testGASN-08"));
        Assert.assertTrue(names1.contains("testGASN-09"));

        List<String> names2 = manager.getAllScreenNames(ScreensSortType.NAME_ORDER, true, false);
        Assert.assertNotNull(names2);
        Assert.assertEquals(10, names2.size());

        // Check that have the right names (all names whether enabled or not)
        Assert.assertTrue(names2.contains("testGASN-01"));
        Assert.assertTrue(names2.contains("testGASN-02"));
        Assert.assertTrue(names2.contains("testGASN-03"));
        Assert.assertTrue(names2.contains("testGASN-04"));
        Assert.assertTrue(names2.contains("testGASN-05"));
        Assert.assertTrue(names2.contains("testGASN-06"));
        Assert.assertTrue(names2.contains("testGASN-07"));
        Assert.assertTrue(names2.contains("testGASN-08"));
        Assert.assertTrue(names2.contains("testGASN-09"));

        // Delete some rows and check still correct
        manager.deleteScreen("testGASN-04");
        manager.deleteScreen("testGASN-09");
        manager.deleteScreen("testGASN-02");

        List<String> names3 = manager.getAllScreenNames(ScreensSortType.NAME_ORDER, false, false);
        Assert.assertNotNull(names3);
        Assert.assertEquals(5, names3.size());

        // Check that have the right names (enabled but not disabled)
        Assert.assertFalse(names3.contains("testGASN-01"));
        Assert.assertFalse(names3.contains("testGASN-02"));
        Assert.assertFalse(names3.contains("testGASN-03"));
        Assert.assertFalse(names3.contains("testGASN-04"));
        Assert.assertTrue(names3.contains("testGASN-05"));
        Assert.assertTrue(names3.contains("testGASN-06"));
        Assert.assertTrue(names3.contains("testGASN-07"));
        Assert.assertTrue(names3.contains("testGASN-08"));
        Assert.assertFalse(names3.contains("testGASN-09"));

        List<String> names4 = manager.getAllScreenNames(ScreensSortType.NAME_ORDER, true, false);
        Assert.assertNotNull(names4);
        Assert.assertEquals(7, names4.size());

        // Check that have the right names (all names whether enabled or not)
        Assert.assertTrue(names4.contains("testGASN-01"));
        Assert.assertFalse(names4.contains("testGASN-02"));
        Assert.assertTrue(names4.contains("testGASN-03"));
        Assert.assertFalse(names4.contains("testGASN-04"));
        Assert.assertTrue(names4.contains("testGASN-05"));
        Assert.assertTrue(names4.contains("testGASN-06"));
        Assert.assertTrue(names4.contains("testGASN-07"));
        Assert.assertTrue(names4.contains("testGASN-08"));
        Assert.assertFalse(names4.contains("testGASN-09"));
    }

    @Test
    public void testDeleteScreen() throws Exception {
        ScreensEntity screen1 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testDS-01");
        // Check that screen was correctly instantiated.
        Assert.assertEquals("testDS-01", screen1.getName());

        // Add screen to database.  Should also add to cache
        boolean status = manager.addScreen(screen1);
        Assert.assertTrue(status);

        // Check that screen is in database
        ScreensEntity screen2 = manager.getScreen("testDS-01", true);
        Assert.assertNotNull(screen2);

        // Remove record
        status = manager.deleteScreen(screen2);
        Assert.assertTrue(status);

        // Check record not in database
        ScreensEntity screen3 = manager.getScreen("testDS-01", true);
        Assert.assertNull(screen3);

        // Now try to delete again and check that get error status
        // Re-use screen2 as should still have valid data.
        status = manager.deleteScreen(screen2);
        Assert.assertFalse(status);
    }

    @Test
    public void testDeleteScreen1() throws Exception {
        // --- Add a load of screens first

        // Create records...

        ScreensEntity screen01 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testDS-01");
        ScreensEntity screen02 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testDS-02");
        ScreensEntity screen03 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testDS-03");
        ScreensEntity screen04 = ScreensUnitTestData.getTestScreensEntity("general-disabled", "testDS-04");
        ScreensEntity screen05 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testDS-05");
        ScreensEntity screen06 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testDS-06");
        ScreensEntity screen07 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testDS-07");
        ScreensEntity screen08 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testDS-08");
        ScreensEntity screen09 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testDS-09");
        ScreensEntity screen10 = ScreensUnitTestData.getTestScreensEntity("general-enabled", "testDS-10");

        // Set Screen Type and sortKeyfor each record

        screen01.setScreenType("Category-02");screen01.setSortKey(90);
        screen02.setScreenType("Category-01");screen02.setSortKey(30);
        screen03.setScreenType("Category-03");screen03.setSortKey(20);
        screen04.setScreenType("Category-03");screen04.setSortKey(30);
        screen05.setScreenType("Category-01");screen05.setSortKey(10000);
        screen06.setScreenType("Category-03");screen06.setSortKey(1);
        screen07.setScreenType("Category-02");screen07.setSortKey(0);
        screen08.setScreenType("Category-01");screen08.setSortKey(50);
        screen09.setScreenType("Category-03");screen09.setSortKey(25);
        screen10.setScreenType("Category-02");screen10.setSortKey(45);

        // Add records

        Assert.assertTrue(manager.addScreen(screen01));
        Assert.assertTrue(manager.addScreen(screen02));
        Assert.assertTrue(manager.addScreen(screen03));
        Assert.assertTrue(manager.addScreen(screen04));
        Assert.assertTrue(manager.addScreen(screen05));
        Assert.assertTrue(manager.addScreen(screen06));
        Assert.assertTrue(manager.addScreen(screen07));
        Assert.assertTrue(manager.addScreen(screen08));
        Assert.assertTrue(manager.addScreen(screen09));
        Assert.assertTrue(manager.addScreen(screen10));

        // Check records added...

        Assert.assertTrue(manager.isScreenExist("testDS-01"));
        Assert.assertTrue(manager.isScreenExist("testDS-02"));
        Assert.assertTrue(manager.isScreenExist("testDS-03"));
        Assert.assertTrue(manager.isScreenExist("testDS-04"));
        Assert.assertTrue(manager.isScreenExist("testDS-05"));
        Assert.assertTrue(manager.isScreenExist("testDS-06"));
        Assert.assertTrue(manager.isScreenExist("testDS-07"));
        Assert.assertTrue(manager.isScreenExist("testDS-08"));
        Assert.assertTrue(manager.isScreenExist("testDS-09"));
        Assert.assertTrue(manager.isScreenExist("testDS-10"));

        // --- Delete some screens in different ways and check a few things are still correct

        List<String> names1 = manager.getAllScreenNames(ScreensSortType.DEFAULT, true, false);
        Assert.assertEquals(10, names1.size());
        Assert.assertTrue(names1.contains("testDS-01"));

        boolean status;

        status = manager.deleteScreen(screen01);
        Assert.assertTrue(status);

        List<String> names2 = manager.getAllScreenNames(ScreensSortType.DEFAULT, true, false);
        Assert.assertEquals(9, names2.size());
        Assert.assertFalse(names2.contains("testDS-01"));

        status = manager.deleteScreen("testDS-10");
        Assert.assertTrue(status);

        List<String> names3 = manager.getAllScreenNames(ScreensSortType.DEFAULT, true, false);
        Assert.assertEquals(8, names3.size());
        Assert.assertFalse(names3.contains("testDS-10"));

        // --- Delete all records and check things still ok ---
        manager.deleteScreen("testDS-02");
        manager.deleteScreen("testDS-03");
        manager.deleteScreen("testDS-04");
        manager.deleteScreen("testDS-05");
        manager.deleteScreen("testDS-06");
        manager.deleteScreen("testDS-07");
        manager.deleteScreen("testDS-08");
        manager.deleteScreen("testDS-09");

        List<String> names4 = manager.getAllScreenNames(ScreensSortType.DEFAULT, true, false);
        Assert.assertNull(names4);

        // --- Some invalid conditions

        Assert.assertFalse(manager.deleteScreen("XXXXX"));
    }

    @Test
    public void testDeleteScreen2() throws Exception {

    }

    @Test
    public void testGetScreenCreatedTimestamp() throws Exception {

    }

    @Test
    public void testSetScreenModifiedTimestamp() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }
}