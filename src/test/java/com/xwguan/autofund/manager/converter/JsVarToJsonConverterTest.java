package com.xwguan.autofund.manager.converter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xwguan.autofund.constant.AppConst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
@SuppressWarnings("unused")
public class JsVarToJsonConverterTest {
    @Autowired
    private JsVarToJsonConverter converter;

    // @Test
    public void testConvertString() throws JsonParseException, JsonMappingException, IOException {
        String[] testJsVar = { "var a = \"a\";", "var b=1;", "var c = [[\"c\" ,\"cValue\"],[\"c\" , \"cValue\"]];",
            "var d = [{\"d\":\"dValue\"},{\"d\":\"dValue\"}];", "var e = \"e\"; var e1 =\"\";",
            "var f = {\"f\" : \"f\"};", "var g=\"g\"" };
        String json = null;
        // for (String s : testJsVar) {
        // json = converter.convert(s, null);
        // ObjectMapper mapper = new ObjectMapper();
        // Object result = mapper.readValue(json, Map.class);
        // }
        for (String s : testJsVar) {
            json = converter.convert(s, Arrays.asList("a"));
            ObjectMapper mapper = new ObjectMapper();
            Object result = mapper.readValue(json, Map.class);
        }
    }

     @Test
    public void testConvertReader() {
        String[] urls = {
            "http://fund.eastmoney.com/Data/FundDataPortfolio_Interface.aspx?dt=14&mc=returnjson&ft=all&pn=50&pi=1&sc=abbname&st=asc" };
        for (String s : urls) {
            try {
                URL url = new URL(s);
                Reader reader = new InputStreamReader(url.openStream(), AppConst.CHARSET);
                converter.convert(reader, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testConvertJsObject() throws JsonParseException, JsonMappingException, IOException {
        String[] testJsObj = { "var managers= {data:[],record:1628,pages:-2147483648,curpage:1}",
            "var a = {\"a\":\"http://123\", b:\"http://456\"}"};
        //
        String json = null;
        for (String s : testJsObj) {
            json = converter.convert(s);
            ObjectMapper mapper = new ObjectMapper();
            Object result = mapper.readValue(json, Map.class);
        }
    }
    
//    @Test
    public void testt() {
        StringBuffer parsed = new StringBuffer();
        StringBuffer remainder = new StringBuffer("{data:[],record:1628,pic:\"http://123\"}");
        Pattern pattern = Pattern.compile("data|record|pic");
        Matcher matcher = pattern.matcher(remainder);
        while (matcher.find()) {
            matcher.appendReplacement(parsed, "\"$0\"");
        }
        matcher.appendTail(parsed);
        System.out.println(parsed.toString());
    }
}
