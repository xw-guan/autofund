package com.xwguan.autofund.util;

import java.io.Reader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class StringReader {

    // 线程池
    @Autowired
    private ThreadPoolTaskExecutor executor;

    public StringBuilder readAsStringBuilder(Reader reader)
        throws InterruptedException, ExecutionException, TimeoutException {
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

    public String readAsString(Reader reader)
        throws InterruptedException, ExecutionException, TimeoutException {
        return readAsStringBuilder(reader).toString();
    }
}
