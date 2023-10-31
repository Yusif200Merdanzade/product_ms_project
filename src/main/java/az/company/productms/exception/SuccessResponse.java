package az.company.productms.exception;

import org.springframework.http.HttpStatus;



public interface SuccessResponse {

    String getKey();

    String getMessage();

    HttpStatus getHttpStatus();
}
