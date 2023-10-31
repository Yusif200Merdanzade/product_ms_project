//package az.company.productms.model.security;
//
//
//import az.company.productms.service.security.AuthService;
//import io.jsonwebtoken.Claims;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.GenericFilterBean;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class JwtFilter extends GenericFilterBean {
//
//    private static final String AUTHORIZATION = "Authorization";
//
//    private final AuthService authService;
//    private final HandlerExceptionResolver resolver;
//
//
//    @Autowired
//    public JwtFilter(AuthService authService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
//        this.authService = authService;
//        this.resolver = resolver;
//    }
//
//    @SneakyThrows
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse res = (HttpServletResponse) servletResponse;
//
//
//        final String token = getTokenFromRequest((HttpServletRequest) servletRequest);
//        try {
//            if (token != null && authService.validateToken(token)) {
//                final Claims claims = authService.getClaims(token);
//                final JwtAuthentication jwtInfoToken = generate(claims);
//                jwtInfoToken.setAuthenticated(true);
//                SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
//            }
//        } catch (Exception e) {
//            resolver.resolveException(req, res, null, e);
//            return;
//
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    private String getTokenFromRequest(HttpServletRequest request) {
//        final String bearer = request.getHeader(AUTHORIZATION);
//        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
//            return bearer.substring(7);
//        }
//        return null;
//    }
//
//    public JwtAuthentication generate(Claims claims) {
//        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
////        jwtInfoToken.setRoles(getPermissions(claims));
//        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
//        jwtInfoToken.setUsername(claims.getSubject());
//        return jwtInfoToken;
//    }
//
//}