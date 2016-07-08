package unittests.support;

import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensManager;
import co.uk.genonline.simpleweb.model.bean.ScreensManagerNonCaching;
import co.uk.genonline.simpleweb.model.bean.ScreensSortType;
import org.hibernate.SessionFactory;
import org.junit.Assert;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sets up a lot of screens in the database which can then be used to drive the tests.  The intentions is that every
 * test will use the same test screens although may add or delete screens as part of the test.
 *
 * At the time of writing the intention is to structure the screens as follows:
 *
 * - Total number of screens: 50
 * - Number of enabled screens: 40 (therefore 10 disabled screens)
 * - Number of screens of various Screen Types:
 *   - Mistress    : 12
 *   - Lucina      : 10
 *   - Chambers    : 8
 *   - Gallery     : 7
 *   - Testimonial : 9
 *   - Blank       : 1 (Not done yet)
 *   - Null        : 1 (Not done yet)
 *   - Invalid     : 2
 * - Number of Galleries: 25 (therefore 25 with no gallery)
 * - Number with no Short Name or Long Name: 10
 * - Number with Short Name but no Long Name: 13
 * - Number with Long Name but no Short Name: 16
 * - Number with Long Name and Short Name: 11
 * - Number with ScreenDisplayType "mistress-01" : 5
 * - Number with ScreenDisplayType "mistress-02" : 6
 * - Number with ScreenDisplayType "mistress-03" : 7
 * - Number with ScreenDisplayType "mistress-04" : 8
 * - Number with ScreenDisplayType "mistress-05" : 9
 * - Number with ScreenDisplayType "dummy-01" : 5
 * - Number with ScreenDisplayType "" : 5
 * - Number with ScreenDisplayType null : 5
 *
 * (To Be Added - Null field test cases)
 * - Number of screens with parent_id = null: 1
 * - Number of screens with name = null: 1
 * - Number of screens with screenTitleLong = null: 1
 * - Number of screens with screenTitleShort = null: 1
 * - Number of screens with screenContents = null: 1
 * - Number of screens with metaDescription = null: 1
 * - Number of screens with enabledFlag = null: 1
 * - Number of screens with created = null: 1
 * - Number of screens with modified = null: 1
 * - Number of screens with sortKey = null: 1
 *   (NOTE: Null values for screenType and screenDisplayType already covered above)
 */
public class ScreensUnitTestData {
    private static SessionFactory factory = TestSupportSessionFactory.getSessionFactory();
    private static ScreensManager screensManager = new ScreensManagerNonCaching(factory);
    private static Integer[] disabledCases = {2,3,5,7,9,11,13,15,17,19};
    private static Integer[] nullEnabledFlagCases = {23,25,35};

    private static Integer[] galleryEnabledCases = {49,34,23};
    private static Integer[] nullGalleryFlagCases = {8,14,27};

    private static Integer[] mistressScreenType = {1,6,11,16,21,26,31,36,41,46,2,7};
    private static Integer[] lucinaScreenType = {12,17,22,27,32,37,42,47,3,8};
    private static Integer[] chambersScreenType = {13,18,23,28,33,38,43,48};
    private static Integer[] galleryScreenType = {4,9,14,19,24,29,34};
    private static Integer[] testimonialScreenType = {39,44,49,5,10,15,20,25,30};
    private static Integer[] blankScreenType = {40};
    private static Integer[] nullScreenType = {50};
    private static Integer[] invalidScreenType = {35,45};

    private static Integer[] noShortNoLong = {1,8,15,22,29,36,43,50,7,14};
    private static Integer[] yesShortNoLong = {21,28,35,42,49,6,13,20,27,34,41,48,5};
    private static Integer[] noShortYesLong = {12,19,26,33,40,47,4,11,18,25,32,39,46,3,10,17};
    private static Integer[] yesShortYesLong = {24,31,38,45,2,9,16,23,30,37,44};

    private static Integer[] mistress01ScreenDisplayType = {11,22,33,44,5};
    private static Integer[] mistress02ScreenDisplayType = {27,38,49,1,12,23};
    private static Integer[] mistress03ScreenDisplayType = {45,6,17,28,39,50,21};
    private static Integer[] mistress04ScreenDisplayType = {43,4,15,26,37,48,9,20};
    private static Integer[] mistress05ScreenDisplayType = {42,3,14,25,36,47,8,19,30};
    private static Integer[] mistressDummyScreenDisplayType = {2,13,24,35,46};
    private static Integer[] mistressBlankScreenDisplayType = {7,18,29,40,10};
    private static Integer[] mistressNullScreenDisplayType = {16,34,32,31,41};

    protected static Map<Integer, ScreensEntity> testScreenData;

    static void initData() {
        testScreenData = new HashMap<Integer, ScreensEntity>();
        // Set up 50 base records using a loop, then modify individual test cases to test different scenarios
        // Use i for sort key which is a little hack to allow the code to know which original test case a db record is from
        for (int i = 1; i <= 50; i++) {
            testScreenData.put(i, createScreen(-1, -1, "test-case-" + i, "", "", "", "Metadescription for test case " + i,
                    true, false, new Timestamp(0), new Timestamp(0), "", i, ""));

            // Set up disabled screens
            if (Arrays.asList(disabledCases).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setEnabledFlag(false);
            }

            // Set up screens with null enabled flag.  Note these will fail to add to database but
            // can be used for unit testing (e.g.) ViewScreen with synthetic test data.
            if (Arrays.asList(nullEnabledFlagCases).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setEnabledFlag(null);
            }

            // Set up gallery enabled cases
            if (Arrays.asList(galleryEnabledCases).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setGalleryFlag(true);
            }

            // Set up gallery null cases
            if (Arrays.asList(nullGalleryFlagCases).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setGalleryFlag(null);
            }

            // Set up ScreenType = "Mistress"
            if (Arrays.asList(mistressScreenType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenType("Mistress");
            }

            // Set up ScreenType = "Lucina"
            if (Arrays.asList(lucinaScreenType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenType("Lucina");
            }

            // Set up ScreenType = "Chambers"
            if (Arrays.asList(chambersScreenType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenType("Chambers");
            }

            // Set up ScreenType = "Gallery"
            if (Arrays.asList(galleryScreenType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenType("Gallery");
            }

            // Set up ScreenType = "Testimonial"
            if (Arrays.asList(testimonialScreenType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenType("Testimonial");
            }

            // Set up ScreenType = blank
            if (Arrays.asList(blankScreenType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenType("");
            }

            // Set up ScreenType = null
            if (Arrays.asList(nullScreenType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenType(null);
            }

            // Set up ScreenType = Invalid ScreenType
            if (Arrays.asList(invalidScreenType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenType("Invalid-01");
            }

            // Set up screenTitleShort and screenTitleLong
            if (Arrays.asList(noShortNoLong).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenTitleShort("");
                screen.setScreenTitleLong("");
            }

            if (Arrays.asList(yesShortNoLong).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenTitleShort("Test Case" + i);
                screen.setScreenTitleLong("");
            }

            if (Arrays.asList(noShortYesLong).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenTitleShort("");
                screen.setScreenTitleLong("This Is Test Case" + i);
            }

            if (Arrays.asList(yesShortYesLong).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenTitleShort("Test Case" + i);
                screen.setScreenTitleLong("This is Test Case" + i);
            }

            if (Arrays.asList(mistress01ScreenDisplayType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenDisplayType("mistress-01");
            }

            if (Arrays.asList(mistress02ScreenDisplayType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenDisplayType("mistress-02");
            }

            if (Arrays.asList(mistress03ScreenDisplayType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenDisplayType("mistress-03");
            }

            if (Arrays.asList(mistress04ScreenDisplayType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenDisplayType("mistress-04");
            }

            if (Arrays.asList(mistress05ScreenDisplayType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenDisplayType("mistress-05");
            }

            if (Arrays.asList(mistressBlankScreenDisplayType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenDisplayType("");
            }

            if (Arrays.asList(mistressDummyScreenDisplayType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenDisplayType("dummy-01");
            }

            if (Arrays.asList(mistressNullScreenDisplayType).contains(i)) {
                ScreensEntity screen = testScreenData.get(i);
                screen.setScreenDisplayType(null);
            }
        }

        // Now add to database

        for (int i = 1; i <= 50; i++) {
            boolean status = screensManager.addScreen(testScreenData.get(i));
            if (ScreensUnitTestData.testCaseShouldBeInDatabase(i)) {
                Assert.assertTrue(String.format("Failure to add test record <%d>", i), status);
            } else {
                Assert.assertFalse(String.format("Test record should have failed but didn't <%d>", i), status);
            }
        }
    }

    /**
     * Tests whether test data has been set up as intended.  Just check number of each field type according to note
     * above in comment (e.g. check there are 10 disabled screens
     */

    public static void initialiseAndCheckTestData() {
        int nullEnabledFlagTarget = 0; // There are some null cases but ScreensManager cleans them up before passing them back
        int enabledTarget = 39 - nullEnabledFlagCases.length; // Note that this mustn't include null screen types (50)

        int mistressTypeTarget = 12;
        int lucinaTypeTarget = 10;
        int chamberTypeTarget = 8;
        int galleryTypeTarget = 7;
        int testimonialTypeTarget = 9;
        int blankTypeTarget = 1;
        int nullTypeTarget = 0; // Note this will always be zero as null cases fail to be added
        int invalidTypeTarget = 2;

        int nullGalleryFlagTarget = 0; // Note these have been set up but ScreensManager cleans them up
        int enabledGalleryFlagTarget = 3;

        int noShortNoLongTarget = 10 - 1; // Includes one null screen type so reduce as this won't be in the database
        int yesShortNoLongTarget = 13;
        int noShortYesLongTarget = 16;
        int yesShortYesLongTarget = 11;
        int screenDisplayMistress01Target = 5;
        int screenDisplayMistress02Target = 6;
        int screenDisplayMistress03Target = 7 - 1; // Includes a null screen type so reduce by one.
        int screenDisplayMistress04Target = 8;
        int screenDisplayMistress05Target = 9;
        int screenDisplayDummy01Target = 5;
        int screenDisplayEmptyTarget = 5;
        int screenDisplayNullTarget = 5;

        int expectedNumberScreens = 50 - nullScreenType.length; // null screen type cases won't be added to database so remove from count

        int enabledCount = 0;

        int mistressTypeCount = 0;
        int lucinaTypeCount = 0;
        int chamberTypeCount = 0;
        int galleryTypeCount = 0;
        int testimonialTypeCount = 0;
        int blankTypeCount = 0;
        int nullTypeCount = 0;
        int invalidTypeCount = 0;

        int nullEnabledFlagCount = 0;
        int nullGalleryFlagCount = 0;
        int enabledGalleryFlagCount = 0;

        int noShortnoLongCount = 0;
        int yesShortNoLongCount = 0;
        int noShortYesLongCount = 0;
        int yesShortYesLongCount = 0;
        int screenDisplayMistress01Count = 0;
        int screenDisplayMistress02Count = 0;
        int screenDisplayMistress03Count = 0;
        int screenDisplayMistress04Count = 0;
        int screenDisplayMistress05Count = 0;
        int screenDisplayDummy01Count = 0;
        int screenDisplayEmptyCount = 0;
        int screenDisplayNullCount = 0;

        initData();

        List<ScreensEntity> screens = screensManager.getAllScreens(ScreensSortType.ENTRY_ORDER, true);

        Assert.assertEquals(expectedNumberScreens, screens.size());

        // Go through all Screens and count each 'thing' then check totals
        for (ScreensEntity screen : screens) {
            if (screen.getEnabledFlag() != null && screen.getEnabledFlag()) enabledCount++;

            if (screen.getScreenType() == null) nullTypeCount++;
            else if (screen.getScreenType().equals("Mistress")) mistressTypeCount++;
            else if (screen.getScreenType().equals("Lucina")) lucinaTypeCount++;
            else if (screen.getScreenType().equals("Chambers")) chamberTypeCount++;
            else if (screen.getScreenType().equals("Gallery")) galleryTypeCount++;
            else if (screen.getScreenType().equals("Testimonial")) testimonialTypeCount++;
            else if (screen.getScreenType().equals("")) blankTypeCount++;
            else invalidTypeCount++;

            if (screen.getEnabledFlag() == null) nullEnabledFlagCount++;
            if (screen.getGalleryFlag() == null) nullGalleryFlagCount++;
            if (screen.getGalleryFlag() != null && screen.getGalleryFlag()) enabledGalleryFlagCount++;
            
            if (screen.getScreenTitleShort() != null && screen.getScreenTitleShort().equals("") 
                    && screen.getScreenTitleLong() != null && screen.getScreenTitleLong().equals("")) noShortnoLongCount++;
            if (screen.getScreenTitleShort() != null && !screen.getScreenTitleShort().equals("")
                    && screen.getScreenTitleLong() != null && screen.getScreenTitleLong().equals("")) yesShortNoLongCount++;
            if (screen.getScreenTitleShort() != null && screen.getScreenTitleShort().equals("")
                    && screen.getScreenTitleLong() != null && !screen.getScreenTitleLong().equals("")) noShortYesLongCount++;
            if (screen.getScreenTitleShort() != null && !screen.getScreenTitleShort().equals("")
                    && screen.getScreenTitleLong() != null && !screen.getScreenTitleLong().equals("")) yesShortYesLongCount++;
            
            if (screen.getScreenDisplayType() != null && screen.getScreenDisplayType().equals("mistress-01")) screenDisplayMistress01Count++;
            if (screen.getScreenDisplayType() != null && screen.getScreenDisplayType().equals("mistress-02")) screenDisplayMistress02Count++;
            if (screen.getScreenDisplayType() != null && screen.getScreenDisplayType().equals("mistress-03")) screenDisplayMistress03Count++;
            if (screen.getScreenDisplayType() != null && screen.getScreenDisplayType().equals("mistress-04")) screenDisplayMistress04Count++;
            if (screen.getScreenDisplayType() != null && screen.getScreenDisplayType().equals("mistress-05")) screenDisplayMistress05Count++;
            if (screen.getScreenDisplayType() != null && screen.getScreenDisplayType().equals("dummy-01")) screenDisplayDummy01Count++;
            if (screen.getScreenDisplayType() != null && screen.getScreenDisplayType().equals("")) screenDisplayEmptyCount++;
            if (screen.getScreenDisplayType() == null ) screenDisplayNullCount++;
        }
        
        Assert.assertEquals(enabledTarget, enabledCount);

        Assert.assertEquals(mistressTypeTarget, mistressTypeCount);
        Assert.assertEquals(lucinaTypeTarget, lucinaTypeCount);
        Assert.assertEquals(chamberTypeTarget, chamberTypeCount);
        Assert.assertEquals(galleryTypeTarget, galleryTypeCount);
        Assert.assertEquals(testimonialTypeTarget, testimonialTypeCount);
        Assert.assertEquals(blankTypeTarget, blankTypeCount);
        Assert.assertEquals(nullTypeTarget, nullTypeCount);
        Assert.assertEquals(invalidTypeTarget, invalidTypeCount);

        Assert.assertEquals(nullEnabledFlagTarget, nullEnabledFlagCount);
        Assert.assertEquals(nullGalleryFlagTarget, nullGalleryFlagCount);
        Assert.assertEquals(enabledGalleryFlagTarget, enabledGalleryFlagCount);

        Assert.assertEquals(noShortNoLongTarget, noShortnoLongCount);
        Assert.assertEquals(yesShortNoLongTarget, yesShortNoLongCount);
        Assert.assertEquals(noShortYesLongTarget, noShortYesLongCount);
        Assert.assertEquals(yesShortYesLongTarget, yesShortYesLongCount);

        Assert.assertEquals(screenDisplayMistress01Target, screenDisplayMistress01Count);
        Assert.assertEquals(screenDisplayMistress02Target, screenDisplayMistress02Count);
        Assert.assertEquals(screenDisplayMistress03Target, screenDisplayMistress03Count);
        Assert.assertEquals(screenDisplayMistress04Target, screenDisplayMistress04Count);
        Assert.assertEquals(screenDisplayMistress05Target, screenDisplayMistress05Count);
        Assert.assertEquals(screenDisplayDummy01Target, screenDisplayDummy01Count);
        Assert.assertEquals(screenDisplayEmptyTarget, screenDisplayEmptyCount);
        Assert.assertEquals(screenDisplayNullTarget, screenDisplayNullCount);

    }

    public static ScreensEntity createScreen(
        int id,
        int parentId,
        String name,
        String screenTitleLong,
        String screenTitleShort,
        String screenContents,
        String metaDescription,
        Boolean enabledFlag,
        Boolean galleryFlag,
        Timestamp created,
        Timestamp modified,
        String screenType,
        Integer sortKey,
        String screenDisplayType) {

        ScreensEntity screen = new ScreensEntity();

        screen.setId(id);
        screen.setName(name);
        screen.setParentId(parentId);
        screen.setScreenTitleLong(screenTitleLong);
        screen.setScreenTitleShort(screenTitleShort);
        screen.setScreenContents(screenContents);
        screen.setMetaDescription(metaDescription);
        screen.setEnabledFlag(enabledFlag);
        screen.setGalleryFlag(galleryFlag);
        screen.setCreated(created);
        screen.setModified(modified);
        screen.setScreenType(screenType);
        screen.setSortKey(sortKey);
        screen.setScreenDisplayType(screenDisplayType);

        return screen;
    }

    /**
     *
     * @param option String which indicates which variation of field values is required.
     * @param screenName screenName to use in the ScreenEntity object
     * @return Populated ScreensEntity object for use in testing
     */
    public static ScreensEntity getTestScreensEntity(String option, String screenName) {
        boolean validOption = true;

        ScreensEntity screen = null;
        if (option.equals("general-disabled")) {
            screen = createScreen(
                    -1,
                    0,
                    screenName,
                    "",
                    "",
                    "",
                    "",
                    false,
                    false,
                    new Timestamp(0),
                    new Timestamp(0),
                    "",
                    0,
                    "");
        } else if (option.equals("general-enabled")) {
            screen = createScreen(
                    -1,
                    0,
                    screenName,
                    "",
                    "",
                    "",
                    "",
                    true,
                    false,
                    new Timestamp(0),
                    new Timestamp(0),
                    "",
                    0,
                    "");
        } else {
            validOption = false;
        }
        if (validOption) {
            // Check that screen was correctly instantiated.
            Assert.assertEquals(screenName, screen.getName());
            return screen;
        } else {
            return null;
        }
    }

    /**
     * Creates chosen test case (hard coded) and optionally saves to database
     * @param caseNumber
     * @return
     */
    public static ScreensEntity getTestCase(int caseNumber) {
        return testScreenData.get(caseNumber);
    }

    public static int getNumberOfTestCases() {
        return testScreenData.size();
    }

    /**
     * Some of the test cases will have failed to be added to the database (correctly) because of problems
     * with fields being null.  There is no point trying to test these cases through the unit tests (or
     * at least if we do we need to know that there won't be a record) so we need to be able to identify them.
     *
     * Not sure whether this is starting to get a bit over-complex but let's keep going for now!
     *
     * @return
     */
    public static boolean testCaseShouldBeInDatabase(int i) {
        // At time of writing (my belief is that) only cases with null ScreenType will fail

        if (Arrays.asList(nullScreenType).contains(i)) {
            return false;
        } else {
            return true;
        }
    }
}
