package com.xwguan.autofund.manager.util;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * IO相关工具类
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-10
 */
@Component
public class IOUtils {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    public StringBuilder readAsString(Reader reader)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        if (reader == null) {
            return null;
        }
        Future<StringBuilder> future = executor.submit(() -> {
            char[] buf = new char[1024];
            int num = 0;
            StringBuilder sb = new StringBuilder();
            try (Reader r = reader) {
                while ((num = r.read(buf)) != -1) {
                    sb.append(buf, 0, num);
                }
                return sb;
            }
        });
        return future.get(120, TimeUnit.SECONDS);
    }
}
