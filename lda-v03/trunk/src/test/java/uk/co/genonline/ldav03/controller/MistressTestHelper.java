package uk.co.genonline.ldav03.controller;

import org.junit.Assert;
import uk.co.genonline.ldav03.model.Mistress.Mistress;

/**
 * Created by thomassecondary on 03/08/2014.
 */
public class MistressTestHelper {

    public void checkMistressRecord(Mistress mistressData, String name, String shortName, String longName) {
        Assert.assertEquals("Unexpected name", name, mistressData.getMistressName());
        Assert.assertEquals("Unexpected Short Name", shortName, mistressData.getMistressShortName());
        Assert.assertEquals("Unexpected Long Name", longName, mistressData.getMistressLongName());
    }
}
