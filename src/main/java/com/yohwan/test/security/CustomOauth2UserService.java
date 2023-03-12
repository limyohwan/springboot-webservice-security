package com.yohwan.test.security;

import com.yohwan.test.domain.members.Members;
import com.yohwan.test.domain.members.MembersRepository;
import com.yohwan.test.security.auth.PrincipalDetails;
import com.yohwan.test.security.oauth.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final MembersRepository membersRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest : {}", userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User : {}", oAuth2User.getAttributes());

        OAuthAttributes attributes = OAuthAttributes.of(userRequest.getClientRegistration().getRegistrationId()
        ,userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName()
        , oAuth2User.getAttributes());

        Members member = membersRepository.findByUsername(attributes.getUsername());

        if(member == null){
            member = attributes.toEntity();
            String encPassword = passwordEncoder.encode(member.getPassword());
            member.changeEncPassword(encPassword);
            membersRepository.save(member);
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
