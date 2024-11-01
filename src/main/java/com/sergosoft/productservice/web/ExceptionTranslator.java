package com.sergosoft.productservice.web;

import java.net.URI;
import java.util.List;

import lombok.NonNull;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import com.sergosoft.productservice.service.exception.OrderNotFoundException;
import com.sergosoft.productservice.service.exception.ParamsViolationDetails;
import com.sergosoft.productservice.service.exception.ProductNotFoundException;
import com.sergosoft.productservice.service.exception.OrderItemNotFoundException;
import com.sergosoft.productservice.service.exception.category.CategoryNotFoundException;
import com.sergosoft.productservice.service.exception.category.ParentCategoryNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    public static ProblemDetail getValidationErrorsProblemDetail(List<ParamsViolationDetails> validationResponse) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");
        problemDetail.setType(URI.create("urn:problem-type:validation-error"));
        problemDetail.setTitle("Field Validation Exception");
        problemDetail.setProperty("invalidParams", validationResponse);
        return problemDetail;
    }

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

    @ExceptionHandler(OrderNotFoundException.class)
    ProblemDetail handleOrderNotFoundException(OrderNotFoundException ex) {
        log.info("Order Not Found exception raised.");
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("order-not-found"));
        problemDetail.setTitle("Order Not Found");
        return problemDetail;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    ProblemDetail handleProductNotFoundException(ProductNotFoundException ex) {
        log.info("Product Not Found exception raised.");
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("product-not-found"));
        problemDetail.setTitle("Product Not Found");
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<ParamsViolationDetails> validationResponse =
                errors.stream().map(err -> ParamsViolationDetails.builder().reason(err.getDefaultMessage()).fieldName(err.getField()).build()).toList();
        log.info("Input params validation failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getValidationErrorsProblemDetail(validationResponse));
    }
}
