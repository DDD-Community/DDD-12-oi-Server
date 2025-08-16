package com.ddd.oi.auth.service;

import static com.ddd.oi.user.domain.RoleType.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ddd.oi.auth.dto.AuthResponse;
import com.ddd.oi.auth.dto.profile.GoogleDTO.GoogleProfile;
import com.ddd.oi.auth.dto.profile.KakaoDTO.KakaoProfile;
import com.ddd.oi.auth.dto.profile.NaverDTO.NaverProfile;
import com.ddd.oi.common.utils.GoogleUtil;
import com.ddd.oi.common.utils.JWTUtil;
import com.ddd.oi.common.utils.KakaoUtil;
import com.ddd.oi.common.utils.NaverUtil;
import com.ddd.oi.common.utils.RedisUtil;
import com.ddd.oi.user.domain.ProviderInfo;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private KakaoUtil kakaoUtil;
    @Mock private NaverUtil naverUtil;
    @Mock private GoogleUtil googleUtil;
    @Mock private UserRepository userRepository;
    @Mock private JWTUtil jwtUtil;
    @Mock private RedisUtil redisUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void 기존_사용자가_아닐시_회원가입_성공_카카오() {
        // given
        String oauthAccessToken = "testAccessToken";
        String email = "test@kakao.com";
        String nickname = "테스트유저";

        KakaoProfile kakaoProfileDto = KakaoProfile.builder()
                .id("1234567890")
                .email(email)
                .nickname(nickname)
                .build();

        given(kakaoUtil.requestProfileByAccessToken(oauthAccessToken))
                .willReturn(kakaoProfileDto);

        given(userRepository.findByEmail(email))
                .willReturn(Optional.empty());

        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        given(jwtUtil.createJwt(any(), anyString(), anyString(), anyLong()))
                .willReturn("testAccessToken")
                .willReturn("testRefreshToken");

        // when
        AuthResponse response = authService.oAuthLogin(ProviderInfo.KAKAO, oauthAccessToken, null);

        // then
        assertThat(response.user()).isNotNull();
        assertThat(response.user().getEmail()).isEqualTo(email);
        assertThat(response.user().getNickname()).isEqualTo(nickname);
        assertThat(response.accessToken()).isEqualTo("testAccessToken");
        assertThat(response.refreshToken()).isEqualTo("testRefreshToken");

        verify(kakaoUtil, times(1)).requestProfileByAccessToken(oauthAccessToken);
        verify(userRepository, times(1)).save(any(User.class));

        verify(redisUtil, times(1)).setDataExpire(
                eq("RT:" + email),
                eq("testRefreshToken"),
                anyLong()
        );
    }

    @Test
    void 기존_사용자가_아닐시_회원가입_성공_구글() {
        String oauthAccessToken = "testAccessToken";
        String email = "test@gmail.com";
        String nickname = "테스트유저";

        GoogleProfile googleProfile = GoogleProfile.builder()
                .id("1234567890")
                .email(email)
                .verified_email(true)
                .name(nickname)
                .family_name("테스트")
                .given_name("유저")
                .build();
        given(googleUtil.requestProfileByAccessToken(oauthAccessToken))
                .willReturn(googleProfile);

        given(userRepository.findByEmail(email))
                .willReturn(Optional.empty());

        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        given(jwtUtil.createJwt(any(), anyString(), anyString(), anyLong()))
                .willReturn("testAccessToken")
                .willReturn("testRefreshToken");
        // when
        AuthResponse response = authService.oAuthLogin(ProviderInfo.GOOGLE, oauthAccessToken, null);

        // then
        assertThat(response.user()).isNotNull();
        assertThat(response.user().getEmail()).isEqualTo(email);
        assertThat(response.user().getNickname()).isEqualTo(nickname);
        assertThat(response.accessToken()).isEqualTo("testAccessToken");
        assertThat(response.refreshToken()).isEqualTo("testRefreshToken");

        verify(googleUtil, times(1)).requestProfileByAccessToken(oauthAccessToken);
        verify(userRepository, times(1)).save(any(User.class));

        verify(redisUtil, times(1)).setDataExpire(
                eq("RT:" + email),
                eq("testRefreshToken"),
                anyLong()
        );
    }

    @Test
    void 기존_사용자가_아닐시_회원가입_성공_네이버() {
        String oauthAccessToken = "testAccessToken";
        String email = "test@naver.com";
        String nickname = "테스트유저";

        NaverProfile naverProfile = NaverProfile.builder()
                .message("success")
                .response(NaverProfile.Response.builder()
                        .id("1234567890")
                        .email("test@naver.com")
                        .name(nickname)
                        .nickname(nickname)
                        .build())
                .build();
        given(naverUtil.requestProfileByAccessToken(oauthAccessToken))
                .willReturn(naverProfile);

        given(userRepository.findByEmail(email))
                .willReturn(Optional.empty());

        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        given(jwtUtil.createJwt(any(), anyString(), anyString(), anyLong()))
                .willReturn("testAccessToken")
                .willReturn("testRefreshToken");
        // when
        AuthResponse response = authService.oAuthLogin(ProviderInfo.NAVER, oauthAccessToken, null);

        // then
        assertThat(response.user()).isNotNull();
        assertThat(response.user().getEmail()).isEqualTo(email);
        assertThat(response.user().getNickname()).isEqualTo(nickname);
        assertThat(response.accessToken()).isEqualTo("testAccessToken");
        assertThat(response.refreshToken()).isEqualTo("testRefreshToken");

        verify(naverUtil, times(1)).requestProfileByAccessToken(oauthAccessToken);
        verify(userRepository, times(1)).save(any(User.class));

        verify(redisUtil, times(1)).setDataExpire(
                eq("RT:" + email),
                eq("testRefreshToken"),
                anyLong()
        );
    }

    @Test
    void 리프레시토큰_발급_성공() {
        Long REFRESH_TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 14;
        User user = User.builder()
                .id(1L)
                .role(USER)
                .nickname("테스트유저")
                .providerInfo(ProviderInfo.KAKAO)
                .email("test@kakao.com")
                .build();
        // given
        String oldRefresh = "oldRefreshToken";
        String redisKey = "RT:" + user.getEmail();

        given(jwtUtil.getEmail(oldRefresh))
                .willReturn(user.getEmail());
        given(redisUtil.getData(redisKey))
                .willReturn(oldRefresh);
        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

        given(jwtUtil.createJwt(any(), anyString(), anyString(), anyLong()))
                .willReturn("testAccessToken")
                .willReturn("testRefreshToken");
        // when
        AuthResponse response = authService.reissueAccessToken(oldRefresh, null);

        //then
        assertThat(response.user()).isNotNull();
        assertThat(response.user().getEmail()).isEqualTo(user.getEmail());
        assertThat(response.user().getNickname()).isEqualTo(user.getNickname());
        assertThat(response.accessToken()).isEqualTo("testAccessToken");
        assertThat(response.refreshToken()).isEqualTo("testRefreshToken");

        verify(redisUtil).deleteData(redisKey);
        verify(redisUtil).setDataExpire(
                eq(redisKey),
                eq("testRefreshToken"),
                eq(REFRESH_TOKEN_VALIDITY));
    }

}
