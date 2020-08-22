package com.example.reimbursementonlinebackend.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public final class PaginationUtil {

    private PaginationUtil() {
    }

    public static Map<String, Object> constructMapReturn(List<?> data, long totalElements, int totalPages, Pageable pageable) {
        Map<String, Object> map = new HashMap<>();

        map.put("data", data);
        map.put("total", totalElements);
        map.put("limit", pageable.getPageSize());
        map.put("current_page", pageable.getPageNumber() + 1);
        map.put("total_pages", totalPages);

        return map;
    }

    public static Pageable constructPageable(Integer page, Integer limit, String dir, String sortColumn) {
        Pageable pageable;
        if (StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(dir)) {
            Sort sort = Sort.by((dir.equals("ascend")) ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
            pageable = PageRequest.of(page - 1, limit, sort);
        } else {
            pageable = PageRequest.of(page - 1, limit);
        }

        return pageable;
    }
}
