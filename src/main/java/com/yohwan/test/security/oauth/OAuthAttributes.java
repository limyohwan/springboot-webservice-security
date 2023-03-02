package com.yohwan.test.security.oauth;

import com.yohwan.test.domain.members.Members;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String provider;
    private String providerId;
    private String username;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String provider, String providerId, String username) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
        this.providerId = providerId;
        this.username = username;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if("google".equals(registrationId)){
            System.out.println("구글입니다.");
        }else if("naver".equals(registrationId)){
            System.out.println("네이버입니다.");
        }
        return ofGoogle(userNameAttributeName, attributes, registrationId);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes, String registrationId){
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .provider(registrationId)
                .providerId((String) attributes.get("sub"))
                .username(registrationId + "_" + (String) attributes.get("sub"))
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Members toEntity(){
        return Members.builder()
                .username(username)
                .password(username)
                .email(email)
                .role("ROLE_USER")
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}
