package com.sixheroes.onedayherocore.user.domain.repository;

import com.sixheroes.onedayherocore.user.domain.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    Optional<UserImage> findUserImageByUser_Id(Long userId);
}
