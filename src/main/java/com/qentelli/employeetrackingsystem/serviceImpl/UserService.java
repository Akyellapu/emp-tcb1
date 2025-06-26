package com.qentelli.employeetrackingsystem.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qentelli.employeetrackingsystem.config.JwtUtil;
import com.qentelli.employeetrackingsystem.entity.User;
import com.qentelli.employeetrackingsystem.models.client.request.LoginUserRequest;
import com.qentelli.employeetrackingsystem.models.client.response.LoginUserResponse;
import com.qentelli.employeetrackingsystem.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByUserName(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	}

	public LoginUserResponse loginByEmail(LoginUserRequest loginUser) {
		try {
			String userName = loginUser.getUserName();
			String password = loginUser.getPassword();
			// 1. Authenticate
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			// 2. Set authenticated user in the context
			SecurityContextHolder.getContext().setAuthentication(authentication);
			// 3. Get authenticated User object
			User user = (User) authentication.getPrincipal();
			// 4. Generate JWT token
			String accessToken = jwtUtil.generateToken(userName);

			// 5. Prepare and return DTO (customize as needed)
			LoginUserResponse loginUserData = new LoginUserResponse();
			// password check
			user = userRepository.findByUserName(userName)
					.orElseThrow(() -> new UsernameNotFoundException("User not found wih userName " + userName));
			if (!passwordEncoder.matches(password, user.getPassword())) {
				throw new BadCredentialsException("Invalid user email or password");
			}
			
			loginUserData.setFirstName(user.getFirstName());
			loginUserData.setLastName(user.getLastName());
			loginUserData.setUserName(user.getUsername());
			loginUserData.setRole(user.getRoles().name());
			loginUserData.setAcessToken(accessToken);
			

			return loginUserData;
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid user email or password");
		}
	}

}
