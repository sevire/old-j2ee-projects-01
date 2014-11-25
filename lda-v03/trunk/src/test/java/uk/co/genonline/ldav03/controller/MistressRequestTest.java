package uk.co.genonline.ldav03.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import uk.co.genonline.ldav03.model.Mistress.Mistress;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Tests all URL combinations including invalid to confirm that right result is obtained.  In particular:
 * - Checks that the status is as expected, particularly for Page Not Found etc.
 * - Checks that the view is as expected.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml", "file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class MistressRequestTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testRequests   () throws Exception {
        MistressTestHelper testHelper = new MistressTestHelper();
        Mistress mistressData;
        MvcResult result;
        ResultActions resultActions;

        result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andReturn();

/*
        mistressData = (Mistress)result.getModelAndView().getModel().get("mistressData");
        testHelper.checkMistressRecord(mistressData, "lucina", "Princess Lucina", "Princess Lucina");
*/

        result = mockMvc.perform(get("/mistress/view/lucina"))
                .andExpect(status().isOk())
                .andExpect(view().name("mistress-02-displaytype"))
                .andReturn();

/*
        mistressData = (Mistress)result.getModelAndView().getModel().get("mistressData");
        testHelper.checkMistressRecord(mistressData, "lucina", "Princess Lucina", "Princess Lucina");
*/

        result = mockMvc.perform(get("/mistress/view/xlucina"))
                .andExpect(status().isOk())
                .andExpect(view().name("pagenotfound"))
                .andReturn();

        resultActions = mockMvc.perform(get("/invalidurl"));
    }
}
