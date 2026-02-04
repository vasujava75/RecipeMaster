package com.api.recipe.recipemasterapi.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeExceptionHandlerTest {

    @InjectMocks
    private RecipeExceptionHandler exceptionHandler;

    @Test
    void shouldReturnFieldErrorsWhenMethodArgumentNotValidExceptionOccurs() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("recipe", "name", "Name is required");
        FieldError fieldError2 = new FieldError("recipe", "description", "Description cannot be blank");

        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidation(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).containsEntry("name", "Name is required");
        assertThat(response.getBody()).containsEntry("description", "Description cannot be blank");
    }

    @Test
    void shouldReturnEmptyMapWhenMethodArgumentNotValidExceptionHasNoFieldErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.emptyList());

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidation(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEmpty();
    }


    @Test
    void shouldReturnGenericErrorWhenUnhandledExceptionOccurs() {
        RuntimeException exception = new RuntimeException("Something went wrong");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleAll(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()).containsEntry("error", "RuntimeException: Something went wrong");
    }

    @Test
    void shouldHandleExceptionWithNullMessage() {
        NullPointerException exception = new NullPointerException();

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleAll(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("error", "NullPointerException: null");
    }

    @Test
    void shouldHandleCustomExceptionTypes() {
        IllegalStateException exception = new IllegalStateException("Invalid state");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleAll(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("error", "IllegalStateException: Invalid state");
    }

    @Test
    void shouldHandleHttpMessageNotReadableExceptionWithNullCause() {
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);

        when(exception.getMostSpecificCause()).thenReturn(null);
        when(exception.getMessage()).thenReturn("JSON parse error");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleJsonParse(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsKey("error");
        assertThat(response.getBody().get("error")).startsWith("Invalid JSON:");
    }

    @Test
    void shouldReturnJsonParseErrorWhenHttpMessageNotReadableExceptionOccurs() {
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);
        IllegalArgumentException rootCause = new IllegalArgumentException("Cannot parse date");

        when(exception.getMostSpecificCause()).thenReturn(rootCause);

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleJsonParse(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()).containsKey("error");
        assertThat(response.getBody().get("error")).contains("Invalid JSON: Cannot parse date");
    }



}
