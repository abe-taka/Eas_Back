package com.example.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// SpringSecurity設定クラス
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	//静的ファイルのアクセス制御をなくす
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/image/**", "/css/**", "/javascript/**","/bootstrap/**","/webjars/**");
	}

	//URLのセキュリティ
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//アクセス許可に関する設定
		http.authorizeRequests()
				.antMatchers("/","/signup","/signupprocess").permitAll() //記述したパスのみログイン認証無しでもアクセス可
				//.and()
				//.authorizeRequests().mvcMatchers("/admin").hasAuthority("admin") //管理者画面　※とりあえず放置
				.anyRequest().authenticated() //他のパスは全て認証必要
				.and()
			//ログイン認証に関する設定
			.formLogin()
				.loginPage("/")
				.loginProcessingUrl("/home")
				.usernameParameter("name")
				.passwordParameter("password")
				.successForwardUrl("/home")
				.failureUrl("/")
				.and()
			//ログアウトに関する設定
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID")// ログアウト後、Cookieに保存されているセッションIDを削除。
				.invalidateHttpSession(true)// ログアウト後セッション無効
				.and()
				.rememberMe()//Remember-Meの有効化
				.and()
				.exceptionHandling() //不正アクセスのハンドリング
				.accessDeniedPage("/access-denied/access-denied");
	}

	//認証処理
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}