package com.sixheroes.onedayherocore.review.application.reqeust;

import lombok.Builder;

@Builder
public record ReviewUpdateServiceRequest(
        String content,
        Integer starScore
){
}
