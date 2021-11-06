package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.components.SessionManage;

//ログイン
@Controller
public class LoginController {
	@Autowired
	SessionManage session_manage;

	// セッションid
	private String session_id = null;

	private static String authorizationRequestBaseUri = "oauth2/authorization";
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@GetMapping("/")
	public String Get_Login(Model model) {
	    Iterable<ClientRegistration> clientRegistrations = null;
	    SessionManage session_manage = new SessionManage();
	    System.out.println("####クラスid####" + session_manage.getSession_classid());
	    ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
	      .as(Iterable.class);
	    if (type != ResolvableType.NONE &&
	      ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
	        clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
	    }

	    clientRegistrations.forEach(registration ->
	      oauth2AuthenticationUrls.put(registration.getClientName(),
	      authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
	    model.addAttribute("urls", oauth2AuthenticationUrls);

	    return "/login/login";
	}
}