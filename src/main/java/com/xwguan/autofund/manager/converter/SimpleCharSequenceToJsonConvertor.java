package com.xwguan.autofund.manager.converter;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.annotation.Unimplement;
import com.xwguan.autofund.util.StringReader;

/**
 * Json抽取器, 直接获取一对{}中的内容
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-22
 */
@Component
public class SimpleCharSequenceToJsonConvertor implements CharSequenceToJsonConvertor {

    public static Pattern SIMPLE_JSON_EXTRACT_PATTERN = Pattern.compile("\\{[^}]*\\}");

    @Autowired
    private StringReader stringReader;

    @Override
    public String convert(Reader reader)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        StringBuilder sb = stringReader.readAsStringBuilder(reader);
        return convert(sb);
    }

    @Unimplement
    @Deprecated
    @Override
    public String convert(Reader reader, List<String> discardVars)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        return convert(reader);
    }

    @Override
    public String convert(CharSequence str) {
        Matcher matcher = SIMPLE_JSON_EXTRACT_PATTERN.matcher(str);
        if (matcher.find()) {
            System.out.println(matcher.group());
            return matcher.group();
        }
        return "";
    }

    @Unimplement
    @Deprecated
    @Override
    public String convert(CharSequence str, List<String> discardVars) {
        return convert(str);
    }

}
