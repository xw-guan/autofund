package com.xwguan.autofund.manager.converter;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.util.StringReader;

/**
 * js文件的变量转为json
 * <p>实际上是将var xxx = yyy;转为{xxx:yyy}, 但不做js的合法性校验. 如果变量的值是js对象, 则在js对象key的两端加上"".
 * <p>注: 考虑到获取的js变量值都是合法json, 字符串或js对象, 方便起见这里不做js合法性校验.
 * <p>TODO 是否有工具能直接读取js文件或转为json?
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-26
 * @since JDK 1.8
 */
@Component
public class JsVarToJsonConverter implements CharSequenceToJsonConvertor {

    @Autowired
    private StringReader reader;
    
    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 匹配js变量名和值
     */
    private final Pattern jsVarPattern = Pattern.compile("var\\s*+(\\w+)\\s*=\\s*([^;]+)\\s*;?");

    /**
     * 匹配js对象的key, 但会匹配到字符串中的:前面的字类字符.
     * 暂时还没想到一次匹配的解决方法, 故采用占位字符占位的方式替换不应该被匹配到的字符
     */
    private final Pattern jsKeyPattern = Pattern.compile("(\\w+)\\s*(?=:)"); // TODO

    /**
     * 匹配jsKeyPattern也能匹配到的字符串中的:
     * Java正则表达式在零宽断言中使用+或*报错look-behind group does not have obvious maximum length error. 解決方法是改成{m, n}限制最大长度,
     * n应取一个较大的数, 但n越大效率越低. 这是Java正则的bug?
     * 参看https://stackoverflow.com/questions/24874404/java-regex-look-behind-group-does-not-have-obvious-maximum-length-error
     */
    private final Pattern colonInQuotesPattern = Pattern.compile("(?<=\"[\\w]{1,10}\\s{0,5})(:)");

    /**
     * 用于替换encodePattern匹配到的:的占位字符串
     */
    private final String placeholder = "@#%";

    /**
     * 匹配占位字符串
     */
    private final Pattern placeholderPattern = Pattern.compile(placeholder);

    @Override
    public String convert(Reader jsReader)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        StringBuilder jsString = reader.readAsStringBuilder(jsReader);
        return doConvert(jsString, null);
    }

    @Override
    public String convert(Reader jsReader, List<String> discardVars)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        StringBuilder jsString = reader.readAsStringBuilder(jsReader);
        return doConvert(jsString, discardVars);
    }

    @Override
    public String convert(CharSequence jsString) {
        return doConvert(jsString, null);
    }

    @Override
    public String convert(CharSequence jsString, List<String> discardVars) {
        return doConvert(jsString, discardVars);
    }

    private String doConvert(CharSequence jsString, List<String> discardVars) {
        Matcher varMatcher = jsVarPattern.matcher(jsString);
        StringBuilder jsonSb = new StringBuilder();
        jsonSb.append("{");
        int varCnt = 0;
        while (varMatcher.find()) {
            String var = varMatcher.group(1);
            String value = varMatcher.group(2);
            if (CollectionUtils.isNotEmpty(discardVars) && discardVars.contains(var)) {
                continue;
            }
            String json = jsObjectToJson(value);
            jsonSb.append("\"").append(var).append("\"").append(":").append(json).append(",");
            varCnt++;
        }
        if (varCnt > 0) {
            jsonSb.deleteCharAt(jsonSb.length() - 1);
        }
        jsonSb.append("}");
        String json = jsonSb.toString();
        // logger.debug("\nConverted from: {}\n => To json: {}\nDiscard: {}", jsString, json, discardVars);
        return json;
    }

    private String jsObjectToJson(String jsObject) {
        String placeHeld = colonInQuotesPattern.matcher(jsObject).replaceAll(placeholder);
        // logger.debug("Finished place holding");
        String quotesAdded = jsKeyPattern.matcher(placeHeld).replaceAll("\"$1\"");
        // logger.debug("Finished quote adding");
        String placeholderReplaced = placeholderPattern.matcher(quotesAdded).replaceAll(":");
        // logger.debug("Finished replacing");
        return placeholderReplaced;
    }
}
