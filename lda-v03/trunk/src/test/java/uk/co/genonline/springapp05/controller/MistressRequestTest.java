package uk.co.genonline.springapp05.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import uk.co.genonline.springapp05.model.Mistress;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/web/WEB-INF/dispatcher-servlet.xml", "file:src/main/resources/META-INF/spring/applicationContext.xml"})
public class MistressRequestTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testValidRequests   () throws Exception {
        MistressTestHelper testHelper = new MistressTestHelper();
        Mistress mistressData;
        MvcResult result;

        result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("mistress"))
                .andReturn();

        mistressData = (Mistress)result.getModelAndView().getModel().get("mistressData");
        testHelper.checkMistressRecord(mistressData, "lucina", "Princess Lucina", "Princess Lucina");

        result = mockMvc.perform(get("/mistress/lucina"))
                .andExpect(status().isOk())
                .andExpect(view().name("mistress"))
                .andReturn();

        mistressData = (Mistress)result.getModelAndView().getModel().get("mistressData");
        testHelper.checkMistressRecord(mistressData, "lucina", "Princess Lucina", "Princess Lucina");

        result = mockMvc.perform(get("/mistress/xlucina"))
                .andExpect(status().isOk())
                .andExpect(view().name("404"))
                .andReturn();

    }
}
