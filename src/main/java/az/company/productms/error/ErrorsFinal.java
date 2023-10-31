package az.company.productms.error;

import az.company.productms.exception.ErrorResponse;
import org.springframework.http.HttpStatus;


//TODO: yaml messages configuration
public enum ErrorsFinal implements ErrorResponse {
    DATE_EXISTS("DATA_EXISTS", HttpStatus.BAD_REQUEST, "məlumat bazada artıq mövcuddur!"),
    DATA_NOT_FOUND("DATA_NOT_FOUND", HttpStatus.NOT_FOUND, " id-si '{id}' olan məlumat tapılmadı"),
    STATUS_NOT_FOUND("STATUS_NOT_FOUND", HttpStatus.NOT_FOUND, " '{status}' statusu olan '{name}' məlumatı tapılmadı"),
    DATA_NOT_FOUND_LAST("DATA_NOT_FOUND_LAST", HttpStatus.NOT_FOUND, "{message}"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "daxili server xətası"),
    SERVICE_TYPE_ERROR("SERVICE_TYPE_ERROR", HttpStatus.BAD_REQUEST, "{message}"),


    UNIQUE_CONSTRAINT("23505", HttpStatus.BAD_REQUEST, "id-lər(unikallığa görə) təkrarlana bilməz"),
    FK_CONSTRAINT("23503", HttpStatus.BAD_REQUEST, "əlaqəli olduğu məlumat xətası"),
    NOT_EMPTY_CONSTRAINT("23502", HttpStatus.BAD_REQUEST, "{name} boş ola bilməz"),
    CHECK_VIOLATION("23514", HttpStatus.BAD_REQUEST, "check violation xətası"),


    DATA_IN_USE("DATA_IN_USE", HttpStatus.BAD_REQUEST, " {id} id-li universitet hazırda istifadə olunur"),


    CODE_DUPLICATE("CODE_ALREADY_EXIST", HttpStatus.CONFLICT, "Bazada bu id {id} ile aktiv kod mövcuddur!"),
    CODE_NOT_EQUALS("CODE_NOT_EQUALS", HttpStatus.BAD_REQUEST, "Daxil etdiyiniz mövcud kod '{code}' bazada olan mövcud kod ilə uzlaşmır!"),
    CODE_EQUALS("CODE_EQUALS", HttpStatus.CONFLICT, "Daxil etdiyiniz yeni kod '{code}' bazada mövcuddur, fərqli kod cəhd edin!"),

    ACCESS_DENIED("ACCESS_DENIED", HttpStatus.FORBIDDEN, "İcazə yoxdur"),
    EXPIRED_JWT_ERROR("EXPIRED_JWT_ERROR", HttpStatus.UNAUTHORIZED, "JWT token-in vaxtı keçib"),
    UNSUPPORTED_JWT_ERROR("UNSUPPORTED_JWT_ERROR", HttpStatus.UNAUTHORIZED, "Bu token formatı dəstəklənmir"),
    MALFORMED_JWT_ERROR("MALFORMED_JWT_ERROR", HttpStatus.UNAUTHORIZED, "Token düzgün formatda deyil"),
    SIGNATURE_JWT_ERROR("SIGNATURE_JWT_ERROR", HttpStatus.UNAUTHORIZED, "Token-in signature-i səhvdi"),
    INVALID_TOKEN("INVALID_TOKEN", HttpStatus.UNAUTHORIZED, "Token səhvdi"),
    USERNAME_NOT_FOUND( "USER_NOT_FOUND", HttpStatus.UNAUTHORIZED , "User with this username not found");


    final String key;
    final HttpStatus httpStatus;
    final String message;

    ErrorsFinal(String key, HttpStatus httpStatus, String message) {
        this.key = key;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}