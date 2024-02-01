package com.sixheroes.onedayherocore.user.application;

import com.sixheroes.onedayherocore.global.s3.S3ImageDeleteService;
import com.sixheroes.onedayherocore.global.s3.S3ImageDirectoryProperties;
import com.sixheroes.onedayherocore.global.s3.S3ImageUploadService;
import com.sixheroes.onedayherocore.global.s3.dto.request.S3ImageDeleteServiceRequest;
import com.sixheroes.onedayherocore.global.s3.dto.request.S3ImageUploadServiceRequest;
import com.sixheroes.onedayherocore.global.s3.dto.response.S3ImageUploadServiceResponse;
import com.sixheroes.onedayherocore.region.application.RegionReader;
import com.sixheroes.onedayherocore.region.domain.Region;
import com.sixheroes.onedayherocore.user.application.reader.UserImageReader;
import com.sixheroes.onedayherocore.user.application.reader.UserReader;
import com.sixheroes.onedayherocore.user.application.reader.UserRegionReader;
import com.sixheroes.onedayherocore.user.application.request.UserServiceUpdateRequest;
import com.sixheroes.onedayherocore.user.application.response.UserResponse;
import com.sixheroes.onedayherocore.user.application.response.UserUpdateResponse;
import com.sixheroes.onedayherocore.user.application.validation.UserValidation;
import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.EntityNotFoundException;
import com.sixheroes.onedayherocore.user.domain.User;
import com.sixheroes.onedayherocore.user.domain.UserImage;
import com.sixheroes.onedayherocore.user.domain.repository.UserImageRepository;
import com.sixheroes.onedayherocore.user.domain.repository.UserRegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserValidation userValidation;

    private final UserReader userReader;
    private final UserRegionReader userRegionReader;
    private final RegionReader regionReader;
    private final UserImageReader userImageReader;

    private final UserRegionRepository userRegionRepository;
    private final UserImageRepository userImageRepository;

    private final S3ImageDirectoryProperties properties;
    private final S3ImageUploadService s3ImageUploadService;
    private final S3ImageDeleteService s3ImageDeleteService;

    private static final BiFunction<User, S3ImageUploadServiceResponse, UserImage> userImageMapper = (user, uploadServiceResponse) ->
            UserImage.createUserImage(
                    user,
                    uploadServiceResponse.originalName(),
                    uploadServiceResponse.uniqueName(),
                    uploadServiceResponse.path()
            );

    private static final Function<UserImage, S3ImageDeleteServiceRequest> s3ImageDeleteServiceRequestMapper = userImage ->
            S3ImageDeleteServiceRequest.builder()
                    .imageId(userImage.getId())
                    .uniqueName(userImage.getUniqueName())
                    .build();

    public UserResponse findUser(
            Long userId
    ) {
        var user = userReader.findOne(userId);
        var regions = regionReader.findByUser(user);

        return UserResponse.from(user, regions);
    }

    @Transactional
    public UserUpdateResponse updateUser(
            Long userId,
            UserServiceUpdateRequest userServiceUpdateRequest,
            List<S3ImageUploadServiceRequest> s3ImageUploadServiceRequests
    ) {
        var user = userReader.findOne(userId);
        userValidation.validUserNickname(userId, userServiceUpdateRequest.userBasicInfo().nickname());

        var userBasicInfo = userServiceUpdateRequest.toUserBasicInfo();
        var userFavoriteWorkingDay = userServiceUpdateRequest.toUserFavoriteWorkingDay();

        user.updateUser(userBasicInfo, userFavoriteWorkingDay);

        // 기존의 유저 선호 지역 모두 삭제
        deleteUserRegions(user);

        // 변경된 유저 선호 지역 모두 넣기
        insertUserRegions(user, userServiceUpdateRequest);

        // 새로운 이미지 업로드
        uploadUserImage(s3ImageUploadServiceRequests, user);

        return UserUpdateResponse.from(user);
    }

    @Transactional
    public void deleteUserImage(
        Long userId,
        Long userImageId
    ) {
        var userImage = userImageReader.findOne(userImageId);

        userImage.validOwner(userId);

        deleteUserImage(userImage);
    }

    @Transactional
    public UserUpdateResponse turnOnHeroMode(
            Long userId
    ) {
        var user = userReader.findOne(userId);
        user.changeHeroModeOn();

        return UserUpdateResponse.from(user);
    }

    @Transactional
    public UserUpdateResponse turnOffHeroMode(
            Long userId
    ) {
        var user = userReader.findOne(userId);
        user.changeHeroModeOff();

        return UserUpdateResponse.from(user);
    }

    private void deleteUserRegions(
            User user
    ) {
        var findUserRegions = userRegionReader.findAll(user);
        userRegionRepository.deleteAllInBatch(findUserRegions);
    }

    private void insertUserRegions(
            User user,
            UserServiceUpdateRequest userServiceUpdateRequest
    ) {
        var regionIds = userServiceUpdateRequest.userFavoriteRegions();
        if (Objects.nonNull(regionIds) && regionIds.isEmpty()) {
            return;
        }

        var regions = regionReader.findAll(regionIds);
        validRegions(userServiceUpdateRequest.userFavoriteRegions(), regions);

        var userRegions = userServiceUpdateRequest.toUserRegions(user);
        userRegionRepository.saveAll(userRegions);
    }

    private void deleteUserImage(
        UserImage userImage
    ) {
        var s3ImageDeleteServiceRequest = s3ImageDeleteServiceRequestMapper.apply(userImage);
        s3ImageDeleteService.deleteImages(List.of(s3ImageDeleteServiceRequest));
        userImageRepository.delete(userImage);
    }

    private void uploadUserImage(
            List<S3ImageUploadServiceRequest> s3ImageUploadServiceRequests,
            User user
    ) {
        var s3ImageUploadServiceResponses = s3ImageUploadService.uploadImages(s3ImageUploadServiceRequests, properties.getProfileDir());
        var userImages = s3ImageUploadServiceResponses.stream()
                .map(res -> userImageMapper.apply(user, res))
                .toList();
        userImageRepository.saveAll(userImages);
    }

    private void validRegions(
            List<Long> regionIds,
            List<Region> regions
    ) {
        var notExistRegionIds = regionIds.stream()
                .filter(id ->
                        regions.stream()
                                .map(Region::getId)
                                .noneMatch(regionId -> Objects.equals(regionId, id))
                ).toList();

        if (!notExistRegionIds.isEmpty()) {
            log.warn("존재하지 않는 지역 아이디입니다. regionIds = {}", notExistRegionIds);
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND_REGION);
        }
    }
}
