package com.movie.booking.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer numberOfElements;
    private Long totalElements;
    private Integer totalPages;
    private Boolean lastPage;
    private String sortBy;
    private String sortOrder;
}
