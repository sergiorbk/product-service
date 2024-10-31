package com.sergosoft.productservice.web;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import com.sergosoft.productservice.service.exception.OrderItemNotFoundException;
import com.sergosoft.productservice.service.exception.category.CategoryNotFoundException;
import com.sergosoft.productservice.service.exception.category.ParentCategoryNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator {

    @ExceptionHandler(CategoryNotFoundException.class)
    ProblemDetail handleCategoryNotFoundException(CategoryNotFoundException ex) {
        log.info("Category Not Found exception raised.");
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("category-not-found"));
        problemDetail.setTitle("Category Not Found");
        return problemDetail;
    }

    @ExceptionHandler(ParentCategoryNotFoundException.class)
    ProblemDetail handleParentCategoryNotFoundException(ParentCategoryNotFoundException ex) {
        log.info("Parent Category Not Found exception raised.");
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("parent-category-not-found"));
        problemDetail.setTitle("Parent Category Not Found");
        return problemDetail;
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    ProblemDetail handleOrderItemNotFoundException(OrderItemNotFoundException ex) {
        log.info("Order Item Not Found exception raised.");
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("order-item-not-found"));
        problemDetail.setTitle("Order Item Not Found");
        return problemDetail;
    }
}
