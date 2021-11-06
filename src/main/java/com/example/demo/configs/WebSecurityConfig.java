package com.example.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// SpringSecurity設定クラス
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//静的ファイルのアクセス制御をなくす
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/image/**", "/css/**", "/javascript/**","/bootstrap/**","/webjars/**");
	}

	//URLのセキュリティ
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//アクセス許可
		http.authorizeRequests()
				.antMatchers("/").permitAll() //記述したパスのみログイン認証無しでもアクセス可
				.anyRequest().authenticated(); //他のパスは全て認証必要
		//Googleログイン認証
		http.oauth2Login()
				.loginPage("/")
				.defaultSuccessUrl("/home/identification")
				.failureUrl("/loginFailure");
		//ログアウト
		http.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID")// ログアウト後、Cookieに保存されているセッションIDを削除。
				.invalidateHttpSession(true);// ログアウト後セッション無効
		//不正アクセスハンドリング
		http.exceptionHandling()
				.accessDeniedPage("/access-denied/access-denied");
	}
}