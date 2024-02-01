package com.sixheroes.onedayheroapi.global.s3;

import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.BusinessException;
import com.sixheroes.onedayherocore.global.s3.dto.request.S3ImageUploadServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public final class MultipartFileMapper {

    public static List<S3ImageUploadServiceRequest> mapToServiceRequests(
            List<MultipartFile> multipartFiles
    ) {
        if (multipartFiles == null) {
            return Collections.emptyList();
        }

        return multipartFiles.stream()
                .map(multipartFile -> {
                    try {
                        return mapToServiceRequest(multipartFile);
                    } catch (IOException e) {
                        log.error("이미지 업로드 과정에서 에러가 발생했습니다.");
                        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
                    }
                }).toList();
    }

    private static S3ImageUploadServiceRequest mapToServiceRequest(
            MultipartFile multipartFile
    )
            throws IOException {
        return new S3ImageUploadServiceRequest(
                multipartFile.getInputStream(),
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getSize()
        );
    }
}
