package pharmacy.config.auth.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import pharmacy.service.oauth2.CustomOAuth2User;
import pharmacy.service.oauth2.CustomOAuth2UserService;
import pharmacy.service.oauth2.CustomOidcUser;

@Configuration
public class OAuth2Config {

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> customOidcUserService(
            CustomOAuth2UserService customOAuth2UserService) {

        return userRequest -> {
            OAuth2User oauth2User = customOAuth2UserService.loadUser(
                    new OAuth2UserRequest(
                            userRequest.getClientRegistration(),
                            userRequest.getAccessToken(),
                            userRequest.getAdditionalParameters()
                    )
            );

            if (oauth2User instanceof CustomOAuth2User customUser) {
                return new CustomOidcUser(customUser, userRequest.getIdToken());
            }

            return new OidcUserService().loadUser(userRequest);
        };
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }
}