package uk.co.genonline.ldav03.controller;

import io.wcm.testing.junit.rules.parameterized.Generator;
import io.wcm.testing.junit.rules.parameterized.ListGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import uk.co.genonline.ldav03.model.PageData;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by thomassecondary on 18/11/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml", "file:src/main/webapp/WEB-INF/applicationContext.xml"})

public class UrlTest {
    UrlTestData[] data = {
            (new UrlTestData("/index", HttpStatus.OK, "index", null, null)),
            (new UrlTestData("/", HttpStatus.MOVED_PERMANENTLY, null, null, null)),
            (new UrlTestData("/xxx", HttpStatus.OK, "pagenotfound", null, null)),

            (new UrlTestData("/mistress", HttpStatus.OK, "mistress-02-displaytype", "data", "lucina")),
            (new UrlTestData("/mistress/view", HttpStatus.OK, "mistress-02-displaytype", "data", "lucina")),
            (new UrlTestData("/mistress/xxx", HttpStatus.OK, "pagenotfound", null, null)),
            (new UrlTestData("/mistress/view/lucina", HttpStatus.OK, "mistress-02-displaytype", "data", "lucina")),
            (new UrlTestData("/mistress/view/xxx", HttpStatus.OK, "pagenotfound", null, null)),

            (new UrlTestData("/chamber", HttpStatus.OK, "chamber-01-displaytype", "data", "facilities")),
            (new UrlTestData("/chamber/view", HttpStatus.OK, "chamber-01-displaytype", "data", "facilities")),
            (new UrlTestData("/chamber/xxx", HttpStatus.OK, "pagenotfound", null, null)),
            (new UrlTestData("/chamber/view/etiquette", HttpStatus.OK, "chamber-01-displaytype", "data", "etiquette")),
            (new UrlTestData("/chamber/view/xxx", HttpStatus.OK, "pagenotfound", null, null)),

            (new UrlTestData("/testimonial", HttpStatus.OK, "pagenotfound", null, null)),
            (new UrlTestData("/testimonial/xxx", HttpStatus.OK, "pagenotfound", null, null)),
            (new UrlTestData("/testimonial/view/xxx", HttpStatus.OK, "pagenotfound", null, null)),
            (new UrlTestData("/testimonial/view/thomas-01", HttpStatus.OK, "testimonial-01-displaytype", "data", "thomas-01")),
    };

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Rule
    public Generator<UrlTestData> params =
            new ListGenerator(Arrays.asList(data));

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    public UrlTest() {
    }

    @Test
    public void testRequest() throws Exception {
        UrlTestData testData = params.value();
        MvcResult returnedData = mockMvc.perform(get(testData.getUrl()))
                .andDo(print())
                .andExpect(status().is(testData.getStatus().value()))
                .andExpect(view().name(testData.getViewName()))
                .andReturn();
        if (!(testData.getAttributeName() == null | testData.getRecordName() == null)) {
            PageData data = (PageData) returnedData.getModelAndView().getModel().get(testData.getAttributeName());
            Assert.assertEquals(testData.toString(), testData.getRecordName(), data.getLinkData().getName());
        }
    }
}

