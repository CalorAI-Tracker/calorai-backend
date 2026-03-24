package ru.calorai.common.mapper;

import ru.calorai.common.model.PageResult;

import java.util.List;
import java.util.function.Function;

public class PageResultMapper {
    public static <T, R> PageResult<R> map(PageResult<T> source, Function<T, R> mapper) {
        List<R> content = source.getContent().stream().map(mapper).toList();

        return new PageResult<>(content, source.getPage(), source.getSize(), source.getTotalElements(),
                source.getTotalPages());
    }

    private PageResultMapper() {}
}
