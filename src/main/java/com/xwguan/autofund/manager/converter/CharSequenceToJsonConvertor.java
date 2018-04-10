package com.xwguan.autofund.manager.converter;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * 将字符串转为json格式字符串
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-22
 */
public interface CharSequenceToJsonConvertor {

    public String convert(Reader reader)
        throws IOException, InterruptedException, ExecutionException, TimeoutException;

    public String convert(Reader reader, List<String> discardVars)
        throws IOException, InterruptedException, ExecutionException, TimeoutException;

    public String convert(CharSequence str);

    public String convert(CharSequence str, List<String> discardVars);
}
