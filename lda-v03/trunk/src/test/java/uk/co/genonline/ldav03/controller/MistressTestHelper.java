package uk.co.genonline.ldav03.controller;

import org.junit.Assert;
import uk.co.genonline.ldav03.model.Mistressxxx.Mistress;

/**
 * Created by thomassecondary on 03/08/2014.
 */
public class MistressTestHelper {

    public void checkMistressRecord(Mistress mistressData, String name, String shortName, String longName) {
        Assert.assertEquals("Unexpected name", name, mistressData.getMistressName());
        Assert.assertEquals("Unexpected Short Name", shortName, mistressData.getLinkData().getShortName());
        Assert.assertEquals("Unexpected Long Name", longName, mistressData.getLinkData().getLongName());
    }
}
