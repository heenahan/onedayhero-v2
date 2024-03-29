package com.sixheroes.onedayherocore.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

@Slf4j
public final class SliceResultConverter {

    private SliceResultConverter() {

    }

    public static <T> Slice<T> consume(List<T> content, Pageable pageable) {
        boolean hasNext = false;

        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
