package com.sixheroes.onedayherocore.global.s3.dto.response;

import lombok.Builder;

@Builder
public record S3ImageUploadServiceResponse(
        String originalName,
        String uniqueName,
        String path
) {
}
