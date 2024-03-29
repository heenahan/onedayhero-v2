package com.sixheroes.onedayherocore.user.application;

import com.sixheroes.onedayherocore.user.application.response.UserAuthResponse;
import com.sixheroes.onedayherocore.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserLoginService {

    private final UserRepository userRepository;
    private final UserSignUpService signUpService;

    @Transactional(readOnly = true)
    public UserAuthResponse login(
            String userSocialType,
            String email
    ) {
        return userRepository.findByEmail_Email(email)
                .map(UserAuthResponse::loginResponse)
                .orElseGet(() -> signUpService.signUp(userSocialType, email));
    }
}
