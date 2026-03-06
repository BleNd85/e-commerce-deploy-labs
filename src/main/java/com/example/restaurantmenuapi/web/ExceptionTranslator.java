package com.example.restaurantmenuapi.web;

import com.example.restaurantmenuapi.service.exception.MenuItemNotFoundException;
import com.example.restaurantmenuapi.service.exception.OrderEntryNotFoundException;
import com.example.restaurantmenuapi.service.exception.OrderNotFoundException;
import com.example.restaurantmenuapi.service.exception.ParamsViolationDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MenuItemNotFoundException.class)
    public ProblemDetail handleMenuItemNotFoundException(MenuItemNotFoundException ex) {
        log.info("MenuItemNotFoundException raised");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("menu-item-not-found"));
        problemDetail.setTitle("MenuItem Not Found");
        return problemDetail;
    }

    @ExceptionHandler(OrderEntryNotFoundException.class)
    public ProblemDetail handleOrderEntryNotFoundException(OrderEntryNotFoundException ex) {
        log.info("OrderEntryNotFoundException raised");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("order-entry-not-found"));
        problemDetail.setTitle("OrderEntry Not Found");
        return problemDetail;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ProblemDetail handleOrderNotFoundException(OrderNotFoundException ex) {
        log.info("OrderNotFoundException raised");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("order-not-found"));
        problemDetail.setTitle("Order Not Found");
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<ParamsViolationDetails> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(er -> ParamsViolationDetails.builder()
                        .fieldName(er.getField())
                        .reason(er.getDefaultMessage())
                        .build())
                .toList();

        String fields = errors.stream()
                .map(ParamsViolationDetails::getFieldName)
                .collect(Collectors.joining(", "));

        log.info("Input validation failed for fields: [{}]. Total errors: {}", fields, errors.size());

        return ResponseEntity.badRequest().body(errors);
    }
}