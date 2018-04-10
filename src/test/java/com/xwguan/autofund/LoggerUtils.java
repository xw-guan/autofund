package com.xwguan.autofund;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xwguan.autofund.dto.common.Page;

public class LoggerUtils {
    @SuppressWarnings("unchecked")
    public static void logPagedList(Page page, Object target, String methodName, Object... notPageArgs)
        throws Exception {

        if (!ObjectUtils.allNotNull(page, target, methodName, notPageArgs)) {
            throw new IOException("Params contain null");
        }
        // get params
        Class<?> targetClass = target.getClass();
        Object[] args = new Object[notPageArgs.length + 1];
        for (int j = 0; j < notPageArgs.length; j++) {
            args[j] = notPageArgs[j];
        }
        args[args.length - 1] = page;
        Class<?>[] parameterTypes = new Class[args.length];
        for (int j = 0; j < args.length; j++) {
            parameterTypes[j] = args[j].getClass();
        }
        Method method = targetClass.getMethod(methodName, parameterTypes);
        page.calculateFields();
        // invoke
        Map<Page, List<Object>> pageResultMap = new HashMap<>();
        for (int i = 1; i <= page.getTotalPage(); page.setTargetPage(++i)) {
            Object result = method.invoke(target, args);
            pageResultMap.put(page.clone(), (List<Object>) result);
        }
        // do logging
        Logger logger = LoggerFactory.getLogger(targetClass);
        if (MapUtils.isNotEmpty(pageResultMap)) {
            Set<Page> keySet = pageResultMap.keySet();
            logger.info("Method={}", method);
            logger.info("Args={}", Arrays.asList(args));
            for (Page p : keySet) {
                logger.info("page: {}", p.getTargetPage());
                for (Object o : pageResultMap.get(p)) {
                    logger.info("{}", o);
                }
            }
        }
    }
}
