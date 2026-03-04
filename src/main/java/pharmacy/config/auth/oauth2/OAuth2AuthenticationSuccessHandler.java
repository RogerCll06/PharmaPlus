package pharmacy.config.auth.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pharmacy.entity.UserEntity;
import pharmacy.service.oauth2.CustomOAuth2User;
import pharmacy.service.oauth2.CustomOidcUser;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        Object principal = authentication.getPrincipal();
        UserEntity user = null;

        if (principal instanceof CustomOidcUser customOidcUser) {
            user = customOidcUser.getUser();
        } else if (principal instanceof CustomOAuth2User customOAuth2User) {
            user = customOAuth2User.getUser();
        }

        if (user == null) {
            log.error("No se pudo obtener el usuario OAuth2");
            getRedirectStrategy().sendRedirect(request, response, "/login?error");
            return;
        }

        log.info("Login OAuth2 exitoso: {}", user.getEmail());

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, "/dashboard");
    }
}
