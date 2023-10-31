package az.company.productms.config;


import az.company.productms.error.ErrorsFinal;
import az.company.productms.exception.ApplicationException;
import az.company.productms.model.security.AuthorizeRequest;
import az.company.productms.service.security.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomAuthorization {
    private final HttpServletRequest httpServletRequest;
    private final AuthService authService;
    private final String authUrl;
    private final RestTemplate restTemplate;

    public CustomAuthorization(HttpServletRequest httpServletRequest,
                               AuthService authService,
                               @Value("${auth.url}") String authUrl,
                               RestTemplate restTemplate) {
        this.httpServletRequest = httpServletRequest;
        this.authService = authService;
        this.restTemplate = restTemplate;
        this.authUrl = authUrl;
    }

    public Long getUserIdFromToken() {
        try {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");

            String accessToken = authorizationHeader.substring("Bearer ".length());
            Claims claims = authService.getClaims(accessToken);
            return Long.parseLong(claims.get("userId").toString());
        } catch (Exception e) {
            throw new IllegalStateException("Invalid Token");
        }
    }

    public boolean isValid() throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String accessToken = authorizationHeader.substring("Bearer ".length());
        headers.setBearerAuth(accessToken);

        AuthorizeRequest jwtRequest = new AuthorizeRequest(accessToken);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(jwtRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(
                authUrl,
                HttpMethod.GET,
                requestEntity,
                Boolean.class
        );

        boolean authorized = Boolean.TRUE.equals(response.getBody());
        if (!authorized) {
            throw new ApplicationException(ErrorsFinal.ACCESS_DENIED);
        }

        return authorized;

    }

}
