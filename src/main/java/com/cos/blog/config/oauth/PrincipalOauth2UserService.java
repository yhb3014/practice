package com.cos.blog.config.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.oauth.provider.FacebookUserInfo;
import com.cos.blog.config.oauth.provider.GoogleUserInfo;
import com.cos.blog.config.oauth.provider.NaverUserInfo;
import com.cos.blog.config.oauth.provider.OAuth2UserInfo;
import com.cos.blog.model.User;
import com.cos.blog.repisitory.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;	
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		System.out.println("userRequest : "+userRequest.getClientRegistration());
		System.out.println("userRequest : "+userRequest.getAccessToken());
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		System.out.println("userRequest : "+oauth2User.getAttributes());
		
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
		
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			System.out.println("페이스북 로그인 요청");
			oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
		
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
		}else {
			System.out.println("로그인을 이미 한적이 있습니다.");
		}		
		String provider = oAuth2UserInfo.getProvider();
		String providerId = oAuth2UserInfo.getProviderId();
		String username = provider+"_"+providerId;
		//String password = bCryptPasswordEncoder.encode("HB");
		String password = "$2a$10$8P1nlCF6b4mhAy4SKGxvY.GPEl5T5KuKOEto0m4tP0JbWiCOb7R1S";
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		if(userEntity == null) {
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.providerId(providerId)
					.provider(provider)
					.build();
			userRepository.save(userEntity);
		} else {
			userEntity.setEmail(oAuth2UserInfo.getEmail());
			userRepository.save(userEntity);
		}
		
		
		
		return new PrincipalDetail(userEntity, oauth2User.getAttributes());
	}
}
