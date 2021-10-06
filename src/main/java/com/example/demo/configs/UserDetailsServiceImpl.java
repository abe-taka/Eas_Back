package com.example.demo.configs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.LoginEntity;
import com.example.demo.repositories.LoginRepository;

//ユーザー認証を行うための情報を返すクラス
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private LoginRepository loginRepository;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

		//ユーザー検索
		LoginEntity loginEntity = loginRepository.Login(name);
		//ユーザーが存在しない場合
		if (loginEntity == null) {
			throw new UsernameNotFoundException("ユーザー名" + name + "は登録されていません");
		}

		// 仮で権限「USER」を付与
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority("USER");
		grantList.add(authority);
		UserDetails userDetails = (UserDetails) new User(loginEntity.getName(),loginEntity.getPassword(), grantList);
		//ユーザー名、パスワード、権限をreturnする
		return userDetails;
	}
}
