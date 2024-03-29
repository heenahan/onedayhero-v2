package com.sixheroes.onedayheroapi.review;

import com.sixheroes.onedayheroapi.docs.RestDocsSupport;
import com.sixheroes.onedayheroapi.review.request.ReviewCreateRequest;
import com.sixheroes.onedayheroapi.review.request.ReviewUpdateRequest;
import com.sixheroes.onedayherocommon.converter.DateTimeConverter;
import com.sixheroes.onedayherocore.review.application.ReviewService;
import com.sixheroes.onedayherocore.review.application.reqeust.ReviewCreateServiceRequest;
import com.sixheroes.onedayherocore.review.application.reqeust.ReviewUpdateServiceRequest;
import com.sixheroes.onedayherocore.review.application.response.ReceivedReviewResponse;
import com.sixheroes.onedayherocore.review.application.response.ReviewDetailResponse;
import com.sixheroes.onedayherocore.review.application.response.ReviewImageResponse;
import com.sixheroes.onedayherocore.review.application.response.ReviewResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static com.sixheroes.onedayheroapi.docs.DocumentFormatGenerator.getDateTimeFormat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest extends RestDocsSupport {

    @MockBean
    private ReviewService reviewService;

    @Override
    protected Object setController() {
        return new ReviewController(reviewService);
    }

    @DisplayName("리뷰 상세 조회를 할 수 있다")
    @Test
    void findReviewDetail() throws Exception {
        // given
        var senderId = 1L;
        var senderNickname = "슈퍼 히어로 토끼";
        var senderProfileImage = "슈퍼 히어로 토끼 프로필 주소";
        var receiverId = 2L;
        var receiverNickname = "리시버 슈퍼 히어로 토끼";
        var missionTitle = "서빙 구함";
        var content = "리뷰 내용";
        var starScore = 5;
        var createdAt = LocalDateTime.now();

        var response = createReviewDetailResponse(
                senderId,
                senderNickname,
                senderProfileImage,
                receiverId,
                receiverNickname,
                missionTitle,
                content,
                starScore,
                createdAt
        );
        given(reviewService.viewReviewDetail(anyLong())).willReturn(response);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/reviews/{reviewId}", response.id())
                        .header(HttpHeaders.AUTHORIZATION, getAccessToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(response.id()))
                .andExpect(jsonPath("$.data.senderId").value(response.senderId()))
                .andExpect(jsonPath("$.data.senderNickname").value(response.senderNickname()))
                .andExpect(jsonPath("$.data.senderProfileImage").isArray())
                .andExpect(jsonPath("$.data.receiverId").value(response.receiverId()))
                .andExpect(jsonPath("$.data.receiverNickname").value(response.receiverNickname()))
                .andExpect(jsonPath("$.data.categoryId").value(response.categoryId()))
                .andExpect(jsonPath("$.data.categoryCode").value(response.categoryCode()))
                .andExpect(jsonPath("$.data.categoryName").value(response.categoryName()))
                .andExpect(jsonPath("$.data.missionTitle").value(response.missionTitle()))
                .andExpect(jsonPath("$.data.content").value(response.content()))
                .andExpect(jsonPath("$.data.starScore").value(response.starScore()))
                .andExpect(jsonPath("$.data.reviewImageResponses.size()").value(response.reviewImageResponses().size()))
                .andDo(print())
                .andDo(document("review-detail",
                        pathParameters(
                                parameterWithName("reviewId")
                                        .description("리뷰 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER)
                                        .description("HTTP 응답 코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                        .description("조회한 리뷰 아이디"),
                                fieldWithPath("data.senderId").type(JsonFieldType.NUMBER)
                                        .description("리뷰 작성 유저 아이디"),
                                fieldWithPath("data.senderNickname").type(JsonFieldType.STRING)
                                        .description("리뷰 작성 유저 닉네임"),
                                fieldWithPath("data.senderProfileImage[]").type(JsonFieldType.ARRAY)
                                        .description("리뷰 작성 유저 프로필 사진")
                                        .optional(),
                                fieldWithPath("data.receiverId").type(JsonFieldType.NUMBER)
                                        .description("리뷰 대상 유저 아이디"),
                                fieldWithPath("data.receiverNickname").type(JsonFieldType.STRING)
                                        .description("리뷰 대상 유저 닉네임"),
                                fieldWithPath("data.categoryId").type(JsonFieldType.NUMBER)
                                        .description("미션 카테고리 아이디"),
                                fieldWithPath("data.categoryCode").type(JsonFieldType.STRING)
                                        .description("미션 카테고리 코드"),
                                fieldWithPath("data.categoryName").type(JsonFieldType.STRING)
                                        .description("미션 카테고리 내용 ex) 청소"),
                                fieldWithPath("data.missionTitle").type(JsonFieldType.STRING)
                                        .description("리뷰가 발생된 미션 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING)
                                        .description("리뷰 내용"),
                                fieldWithPath("data.starScore").type(JsonFieldType.NUMBER)
                                        .description("별점"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING)
                                        .attributes(getDateTimeFormat())
                                        .description("리뷰 생성 시간"),
                                fieldWithPath("data.reviewImageResponses[]").type(JsonFieldType.ARRAY)
                                        .description("리뷰 이미지 응답 데이터 배열"),
                                fieldWithPath("data.reviewImageResponses[].id").type(JsonFieldType.NUMBER)
                                        .description("리뷰 이미지 아이디"),
                                fieldWithPath("data.reviewImageResponses[].originalName").type(JsonFieldType.STRING)
                                        .description("리뷰 이미지 오리지널 네임"),
                                fieldWithPath("data.reviewImageResponses[].uniqueName").type(JsonFieldType.STRING)
                                        .description("리뷰 이미지 유니크 네임"),
                                fieldWithPath("data.reviewImageResponses[].path").type(JsonFieldType.STRING)
                                        .description("리뷰 이미지 S3 주소"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING)
                                        .attributes(getDateTimeFormat())
                                        .description("서버 응답 시간")
                        )
                ));
    }

    @DisplayName("리뷰를 생성할 수 있다.")
    @Test
    void createReview() throws Exception {
        // given
        var senderId = 1L;
        var receiverId = 2L;
        var missionId = 10L;
        var missionTitle = "서빙 구함";
        var content = "리뷰 내용";
        var starScore = 5;

        var request = createReviewCreateRequest(
                senderId,
                receiverId,
                missionId,
                missionTitle,
                content,
                starScore
        );
        var reviewCreateRequest = createReviewCreateRequestToMultipartFile(objectMapper.writeValueAsString(request));
        var imageA = createImageA();
        var imageB = createImageB();

        var response = createReviewResponse();
        given(reviewService.create(any(ReviewCreateServiceRequest.class), anyList())).willReturn(response);

        // when & then
        mockMvc.perform(
                        multipart(HttpMethod.POST, "/api/v1/reviews")
                                .file(reviewCreateRequest)
                                .file(imageA)
                                .file(imageB)
                                .header(HttpHeaders.AUTHORIZATION, getAccessToken())
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/reviews/" + response.id()))
                .andExpect(jsonPath("$.data.id").value(response.id()))
                .andDo(print())
                .andDo(document("review-create",
                        requestPartFields("reviewCreateRequest",
                                fieldWithPath("senderId").type(JsonFieldType.NUMBER)
                                        .description("리뷰 작성 유저 아이디"),
                                fieldWithPath("receiverId").type(JsonFieldType.NUMBER)
                                        .description("리뷰 대상 유저 아이디"),
                                fieldWithPath("missionId").type(JsonFieldType.NUMBER)
                                        .description("리뷰가 생성된 미션 아이디"),
                                fieldWithPath("categoryId").type(JsonFieldType.NUMBER)
                                        .description("미션 카테고리 아이디"),
                                fieldWithPath("missionTitle").type(JsonFieldType.STRING)
                                        .description("미션 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING)
                                        .description("리뷰 내용"),
                                fieldWithPath("starScore").type(JsonFieldType.NUMBER)
                                        .description("별점")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER)
                                        .description("HTTP 응답 코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                        .description("생성된 리뷰 아이디"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING)
                                        .attributes(getDateTimeFormat())
                                        .description("서버 응답 시간")
                        )
                ));
    }

    @DisplayName("리뷰를 수정할 수 있다.")
    @Test
    void updateReview() throws Exception {
        // given
        var content = "리뷰 내용";
        var starScore = 5;
        var request = createReviewUpdateRequest(
                content,
                starScore
        );

        var reviewUpdateRequest = createReviewUpdateRequestToMultipartFile(objectMapper.writeValueAsString(request));
        var imageA = createImageA();

        var response = createReviewResponse();
        given(reviewService.update(anyLong(), any(ReviewUpdateServiceRequest.class), anyList())).willReturn(response);

        // when & then
        mockMvc.perform(
                        multipart(HttpMethod.POST, "/api/v1/reviews/{reviewId}", 1L)
                                .file(reviewUpdateRequest)
                                .file(imageA)
                                .header(HttpHeaders.AUTHORIZATION, getAccessToken())
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(response.id()))
                .andDo(print())
                .andDo(document("review-update",
                        requestPartFields("reviewUpdateRequest",
                                fieldWithPath("content").type(JsonFieldType.STRING)
                                        .description("리뷰 내용"),
                                fieldWithPath("starScore").type(JsonFieldType.NUMBER)
                                        .description("별점")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER)
                                        .description("HTTP 응답 코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                        .description("수정된 리뷰 아이디"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING)
                                        .attributes(getDateTimeFormat())
                                        .description("서버 응답 시간")
                        )
                ));
    }

    @DisplayName("리뷰를 제거할 수 있다.")
    @Test
    void deleteReview() throws Exception {
        var reviewId = 1L;
        willDoNothing().given(reviewService).delete(anyLong());

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/reviews/{reviewId}", reviewId)
                        .header(HttpHeaders.AUTHORIZATION, getAccessToken())
                )
                .andDo(document("review-delete",
                        pathParameters(
                                parameterWithName("reviewId")
                                        .description("리뷰 아이디")
                        )));
    }

    @DisplayName("특정 유저가 받은 리뷰를 확인할 수 있다.")
    @Test
    void viewReceivedReviews() throws Exception {
        // given
        var reviewReceiver = 1L;
        var receivedReviewA = createReceivedReviewA();
        var receivedReviewB = createReceivedReviewB();

        var receivedReviewResponses = new SliceImpl<ReceivedReviewResponse>(
                List.of(receivedReviewA, receivedReviewB),
                PageRequest.of(0, 5),
                true
        );

        given(reviewService.viewReceivedReviews(any(Pageable.class), anyLong())).willReturn(receivedReviewResponses);

        mockMvc.perform(get("/api/v1/reviews/users/{userId}/receive", reviewReceiver)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].reviewId").value(receivedReviewA.reviewId()))
                .andExpect(jsonPath("$.data.content[0].senderId").value(receivedReviewA.senderId()))
                .andExpect(jsonPath("$.data.content[0].senderNickname").value(receivedReviewA.senderNickname()))
                .andExpect(jsonPath("$.data.content[0].profileImage").isArray())
                .andExpect(jsonPath("$.data.content[0].categoryName").value(receivedReviewA.categoryName()))
                .andExpect(jsonPath("$.data.content[0].missionTitle").value(receivedReviewA.missionTitle()))
                .andExpect(jsonPath("$.data.content[0].starScore").value(receivedReviewA.starScore()))
                .andExpect(jsonPath("$.data.content[0].createdAt").value(DateTimeConverter.convertLocalDateTimeToString(receivedReviewA.createdAt())))
                .andExpect(jsonPath("$.data.content[1].reviewId").value(receivedReviewB.reviewId()))
                .andExpect(jsonPath("$.data.content[1].senderId").value(receivedReviewB.senderId()))
                .andExpect(jsonPath("$.data.content[1].senderNickname").value(receivedReviewB.senderNickname()))
                .andExpect(jsonPath("$.data.content[1].profileImage").isArray())
                .andExpect(jsonPath("$.data.content[1].categoryName").value(receivedReviewB.categoryName()))
                .andExpect(jsonPath("$.data.content[1].missionTitle").value(receivedReviewB.missionTitle()))
                .andExpect(jsonPath("$.data.content[1].starScore").value(receivedReviewB.starScore()))
                .andExpect(jsonPath("$.data.content[1].createdAt").value(DateTimeConverter.convertLocalDateTimeToString(receivedReviewB.createdAt())))
                .andDo(document("specific-user-received-reviews",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Authorization: Bearer 액세스토큰")
                        ),
                        pathParameters(
                                parameterWithName("userId")
                                        .description("특정 유저 아이디")
                        ),
                        queryParameters(
                                parameterWithName("page").optional()
                                        .description("페이지 번호"),
                                parameterWithName("size").optional()
                                        .description("데이터 크기"),
                                parameterWithName("sort").optional()
                                        .description("정렬 기준 필드")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER)
                                        .description("HTTP 응답 코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY)
                                        .description("특정 유저가 받은 리뷰 목록 배열"),
                                fieldWithPath("data.content[].reviewId").type(JsonFieldType.NUMBER)
                                        .description("특정 유저가 받은 리뷰의 아이디"),
                                fieldWithPath("data.content[].senderId").type(JsonFieldType.NUMBER)
                                        .description("특정 유저가 받은 리뷰를 작성한 유저의 아이디"),
                                fieldWithPath("data.content[].senderNickname").type(JsonFieldType.STRING)
                                        .description("특정 유저가 받은 리뷰를 작성한 유저의 닉네임"),
                                fieldWithPath("data.content[].profileImage").type(JsonFieldType.ARRAY)
                                        .description("특정 유저가 받은 리뷰를 작성한 유저의 프로필 이미지 경로")
                                        .optional(),
                                fieldWithPath("data.content[].categoryName").type(JsonFieldType.STRING)
                                        .description("툭정 유저가 받은 리뷰의 미션 카테고리 이름"),
                                fieldWithPath("data.content[].missionTitle").type(JsonFieldType.STRING)
                                        .description("특정 유저가 받은 리뷰의 미션 제목"),
                                fieldWithPath("data.content[].starScore").type(JsonFieldType.NUMBER)
                                        .description("특정 유저가 받은 별점"),
                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING)
                                        .description("특정 유저가 리뷰를 받은 시간"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER)
                                        .description("현재 페이지 번호"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER)
                                        .description("페이지 크기"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT)
                                        .description("정렬 상태 객체"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                                        .description("정렬 정보가 비어있는지 여부"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                                        .description("정렬 정보가 있는지 여부"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                                        .description("정렬 정보가 정렬되지 않은지 여부"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER)
                                        .description("페이지 번호"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN)
                                        .description("페이징이 되어 있는지 여부"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN)
                                        .description("페이징이 되어 있지 않은지 여부"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                                        .description("현재 페이지 조회에서 가져온 리뷰 개수"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER)
                                        .description("현재 페이지 번호"),
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT)
                                        .description("정렬 정보 객체"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN)
                                        .description("정렬 정보가 비어있는지 여부"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN)
                                        .description("정렬 정보가 있는지 여부"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN)
                                        .description("정렬 정보가 정렬되지 않은지 여부"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER)
                                        .description("현재 페이지의 요소 수"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN)
                                        .description("첫 번째 페이지인지 여부"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN)
                                        .description("마지막 페이지인지 여부"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN)
                                        .description("비어있는지 여부"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING)
                                        .attributes(getDateTimeFormat())
                                        .description("서버 응답 시간")
                        )));
    }


    private ReviewCreateRequest createReviewCreateRequest(
            Long senderId,
            Long receiverId,
            Long missionId,
            String missionTitle,
            String content,
            Integer starScore
    ) {
        return ReviewCreateRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .missionId(missionId)
                .categoryId(1L)
                .missionTitle(missionTitle)
                .content(content)
                .starScore(starScore)
                .build();
    }

    private ReviewUpdateRequest createReviewUpdateRequest(
            String content,
            Integer starScore
    ) {
        return ReviewUpdateRequest.builder()
                .content(content)
                .starScore(starScore)
                .build();
    }


    private MockMultipartFile createReviewCreateRequestToMultipartFile(
            String json
    ) {
        return new MockMultipartFile(
                "reviewCreateRequest",
                "json",
                MediaType.APPLICATION_JSON.toString(),
                json.getBytes(StandardCharsets.UTF_8)
        );
    }

    private MockMultipartFile createReviewUpdateRequestToMultipartFile(
            String json
    ) {
        return new MockMultipartFile(
                "reviewUpdateRequest",
                "json",
                MediaType.APPLICATION_JSON.toString(),
                json.getBytes(StandardCharsets.UTF_8)
        );
    }

    private MockMultipartFile createImageA() {
        return new MockMultipartFile(
                "images",
                "imageA.jpeg",
                "image/jpeg",
                "<<jpeg data>>".getBytes()
        );
    }

    private MockMultipartFile createImageB() {
        return new MockMultipartFile(
                "images",
                "imageB.jpeg",
                "image/jpeg",
                "<<jpeg data>>".getBytes()
        );
    }

    private ReviewDetailResponse createReviewDetailResponse(
            Long senderId,
            String senderNickname,
            String senderProfileImage,
            Long receiverId,
            String receiverNickname,
            String missionTitle,
            String content,
            Integer starScore,
            LocalDateTime createdAt
    ) {
        var savedImageA = ReviewImageResponse.builder()
                .id(1L)
                .originalName("A 원본 이미지 이름")
                .uniqueName("A")
                .path("S3 이미지 주소A")
                .build();
        var savedImageB = ReviewImageResponse.builder()
                .id(2L)
                .originalName("B 원본 이미지 이름")
                .uniqueName("B")
                .path("S3 이미지 주소B")
                .build();

        return ReviewDetailResponse.builder()
                .id(1L)
                .categoryId(1L)
                .categoryName("서빙")
                .categoryCode("MC_001")
                .senderId(senderId)
                .senderNickname(senderNickname)
                .senderProfileImage(List.of(senderProfileImage))
                .receiverId(receiverId)
                .receiverNickname(receiverNickname)
                .missionTitle(missionTitle)
                .content(content)
                .starScore(starScore)
                .reviewImageResponses(List.of(savedImageA, savedImageB))
                .createdAt(createdAt)
                .build();
    }

    private ReviewResponse createReviewResponse(
    ) {
        return ReviewResponse.builder()
                .id(1L)
                .build();
    }

    private ReceivedReviewResponse createReceivedReviewA() {
        return ReceivedReviewResponse.builder()
                .reviewId(1L)
                .senderId(5L)
                .senderNickname("nickname A")
                .profileImage(List.of("s3 프로필 이미지 주소"))
                .categoryName("청소")
                .missionTitle("청소 미션")
                .starScore(4)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private ReceivedReviewResponse createReceivedReviewB() {
        return ReceivedReviewResponse.builder()
                .reviewId(2L)
                .senderId(8L)
                .senderNickname("nickname B")
                .profileImage(List.of("s3 프로필 이미지 주소"))
                .categoryName("심부름")
                .missionTitle("심부름 미션")
                .starScore(3)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
