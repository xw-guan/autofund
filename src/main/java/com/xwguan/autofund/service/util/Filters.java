package com.xwguan.autofund.service.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Filters {

    /**
     * 只留下大于0的id
     * 
     * @param ids not null
     * @return
     */
    public static List<Long> filterValidId(List<Long> ids) {
        return ids.parallelStream()
            .filter(Predicates::greaterThanZero)
            .collect(Collectors.toList());
    }
    
    /**
     * 只留下大于0的id
     * 
     * @param ids not null
     * @return
     */
    public static Optional<Long> filterValidId(Long id) {
        return Optional.of(id).filter(Predicates::greaterThanZero);
    }

}
