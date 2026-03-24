package ru.calorai.common.model;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private List<T> content;

    private int page;
    private int size;

    private long totalElements;
    private int totalPages;

    public static <T> PageResult<T> of(List<T> content, long totalElements, int page, int size) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return PageResult.<T>builder()
                .content(content)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

    public static <T> PageResult<T> empty(int page, int size) {
        return PageResult.<T>builder()
                .content(List.of())
                .page(page)
                .size(size)
                .totalElements(0)
                .totalPages(0)
                .build();
    }
}
