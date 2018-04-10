package com.xwguan.autofund.manager.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO
@Deprecated
public class DataExtractUtils {

    private static Pattern stringInLabelTdPattern = Pattern.compile("(?<=<td[\\s\\S]{0,1000}>)[^\\s]+(?=<)");
    
    public static List<String> extractStringInLabelTd(String htmlString) {
        List<String> strList = new ArrayList<>();
        Matcher matcher = stringInLabelTdPattern.matcher(htmlString);
        if (matcher.find()) {
            do {
                strList.add(matcher.group());
            } while (matcher.find());
        }
        return strList;
    }
    
}
