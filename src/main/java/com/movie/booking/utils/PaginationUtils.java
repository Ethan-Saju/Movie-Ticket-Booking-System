package com.movie.booking.utils;

import com.movie.booking.exceptions.APIException;


import com.movie.booking.payloads.PaginatedResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.function.Supplier;

public class PaginationUtils {



    public static <E,D> PaginatedResponse<D> buildPaginatedResponse(
            JpaRepository<E,?>  jpaRepository,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder,
            ModelMapper modelMapper,
            Class<E> entityClass,
            Class<D> dtoClass
    ) {

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<E> entityPage = jpaRepository.findAll(pageable);

        if (entityPage.isEmpty()){
            throw new APIException("Request Resource has no instances");
        }

        List<D> dtoList = entityPage.getContent()
                .stream()
                .map(entity-> modelMapper.map(entity,dtoClass))
                .toList();

        PaginatedResponse<D> response = new PaginatedResponse<>();

        response.setContent(dtoList);
        response.setPageNumber(entityPage.getNumber());
        response.setPageSize(entityPage.getSize());
        response.setTotalElements(entityPage.getTotalElements());
        response.setTotalPages(entityPage.getTotalPages());
        response.setNumberOfElements(entityPage.getNumberOfElements());
        response.setLastPage(entityPage.isLast());
        response.setSortBy(sortBy);
        response.setSortOrder(sortOrder);

        return response;
    }




}
