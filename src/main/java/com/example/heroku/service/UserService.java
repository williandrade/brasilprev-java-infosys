package com.example.heroku.service;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.heroku.entity.User;
import com.example.heroku.exception.PasswordNotMatchException;
import com.example.heroku.exception.TokenTimeOutException;
import com.example.heroku.exception.UnauthorizedException;
import com.example.heroku.exception.UserNotFoundException;
import com.example.heroku.repository.UserRepository;
import com.example.heroku.util.HelperUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User logIn(String email, String password) {
		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserNotFoundException();
		}

		if (!user.getPassword().equals(HelperUtil.toMD5(password))) {
			throw new PasswordNotMatchException();
		}

		Date date = new Date();
		String unsafeToken = user.getEmail() + " " + date.getTime();
		user.setToken(HelperUtil.toMD5(unsafeToken));

		LocalDateTime tokenDuoDateTime = HelperUtil.getNowDateTime().plusHours(4);
		user.setTokenDuoDate(HelperUtil.toDate(tokenDuoDateTime));

		user = userRepository.saveAndFlush(user);

		return user;
	}

	public void authToken(String token) {
		User user = userRepository.findByToken(token);

		if (user == null) {
			throw new UnauthorizedException();
		}

		LocalDateTime expireAt = HelperUtil.toLocalDateTime(user.getTokenDuoDate());
		LocalDateTime now = HelperUtil.getNowDateTime();

		if (expireAt.isBefore(now)) {
			throw new TokenTimeOutException();
		}
	}
}
