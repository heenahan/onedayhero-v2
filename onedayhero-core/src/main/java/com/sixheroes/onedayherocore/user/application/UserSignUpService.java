package com.sixheroes.onedayherocore.user.application;

import com.sixheroes.onedayherocore.user.application.response.UserAuthResponse;
import com.sixheroes.onedayherocore.user.domain.*;
import com.sixheroes.onedayherocore.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserSignUpService {

    private final UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserAuthResponse signUp(
            String userSocialType,
            String email
    ) {
        var createdEmail = Email.builder()
                .email(email)
                .build();

        var createdUser = User.signUp(
                createdEmail,
                UserSocialType.findByName(userSocialType),
                UserRole.MEMBER,
                UserBasicInfo.initStatus(DefaultNicknameGenerator.generate())
        );

        var user = userRepository.save(createdUser);

        return UserAuthResponse.signUpResponse(user);
    }
}
