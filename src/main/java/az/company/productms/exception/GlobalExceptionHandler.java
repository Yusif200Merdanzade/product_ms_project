package az.company.productms.exception;

import az.company.productms.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends DefaultErrorAttributes {

    private final MessageSource messageSource;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handle(ApplicationException ex, WebRequest request) {
        log.error("ApplicationException Exception Handler exception -> {}", ex.getMessage());

        return MessageResponse.response(ex.getMessage(), null, ofType(request, ex.getErrorResponse().getHttpStatus(), ex), ex.getErrorResponse().getHttpStatus());
    }


    @ExceptionHandler(
            {
                    org.hibernate.exception.ConstraintViolationException.class,
                    DataIntegrityViolationException.class
            })
    public ResponseEntity<?> handleConstraintViolation(Exception ex, WebRequest request) {
        log.error("HandleConstraintViolation Exception Handler exception -> {}", ex.getMessage());

        String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(HttpResponseConstants.STATUS, HttpStatus.BAD_REQUEST.value());
        attributes.put(HttpResponseConstants.ERROR, HttpStatus.BAD_REQUEST);
        attributes.put(HttpResponseConstants.MESSAGE, message);
        attributes.put(HttpResponseConstants.TIMESTAMP, new Date());
        attributes.put(HttpResponseConstants.ERROR_KEY, "CONSTRAINT_VIOLATION");
        attributes.put(HttpResponseConstants.PATH, ((ServletWebRequest) request).getRequest().getRequestURL());


        return MessageResponse.response(ex.getMessage(), null, new ResponseEntity<>(attributes, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMANVE(MethodArgumentNotValidException ex, WebRequest request) {
        List<ConstraintsViolationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ConstraintsViolationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        log.error("MethodArgumentNotValidException Exception Handler exception -> {}", ex.getMessage());
        return MessageResponse.response("daxil edilən məlumatlar yanlışdır"/*getLocdMesStatus("404.message")*/, null, ofType(request, HttpStatus.BAD_REQUEST, "daxil edilən məlumatlar yanlışdır", null, validationErrors), HttpStatus.BAD_REQUEST);
    }


    protected ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, ApplicationException ex) {
        return ofType(request, status, ex.getLocalizedMessage(LocaleContextHolder.getLocale(), messageSource),
                ex.getErrorResponse().getKey(), Collections.EMPTY_LIST);
    }


    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message,
                                                       String key, List validationErrors) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(HttpResponseConstants.STATUS, status.value());
        attributes.put(HttpResponseConstants.ERROR, status);
        attributes.put(HttpResponseConstants.MESSAGE, message);
        attributes.put(HttpResponseConstants.ERRORS, validationErrors);
        attributes.put(HttpResponseConstants.ERROR_KEY, key);
        attributes.put(HttpResponseConstants.PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(attributes, status);
    }

}

