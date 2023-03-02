package com.yohwan.test.security;

import com.yohwan.test.domain.members.Members;
import com.yohwan.test.domain.members.MembersRepository;
import com.yohwan.test.security.auth.PrincipalDetails;
import com.yohwan.test.security.oauth.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final MembersRepository membersRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(CustomOauth2UserService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest : " + userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User : " + oAuth2User.getAttributes());

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
