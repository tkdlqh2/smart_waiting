package com.example.smart_waiting.domain.user.service;

import com.example.smart_waiting.domain.user.dto.UserInput;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.user.repository.UserRepository;
import com.example.smart_waiting.exception.NoErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.smart_waiting.domain.user.type.UserStatus.APPROVED;
import static com.example.smart_waiting.exception.UserErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserWriteServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserWriteService userWriteService;

    @Test
    void createUserSuccess(){
        //given
        UserInput userInput = UserInput.builder()
                .email("yhj7124@naver.com")
                .password("1111")
                .name("유형진")
                .phone("010-1111-2222")
                .build();

        given(passwordEncoder.encode("1111")).willReturn("2222");
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        //when
        userWriteService.createUser(userInput);

        //then
        verify(userRepository,times(1)).save(captor.capture());
        var capturedUser = captor.getValue();
        assertEquals("yhj7124@naver.com",capturedUser.getEmail());
        assertEquals("2222",capturedUser.getPassword());
        assertEquals("유형진",capturedUser.getName());
        assertEquals("010-1111-2222",capturedUser.getPhone());
    }

    @Test
    void createUserFail_emailAlreadyExist(){
        //given
        UserInput userInput = UserInput.builder()
                .email("yhj7124@naver.com")
                .password("1111")
                .name("유형진")
                .phone("010-1111-2222")
                .build();

        given(userRepository.existsByEmail("yhj7124@naver.com")).willReturn(true);

        //when
        //then
        try{
            userWriteService.createUser(userInput);
            throw new NoErrorException();
        } catch(Exception e){
            assertEquals(EMAIL_ALREADY_EXIST.getMessage(),e.getMessage());
        }
    }

    @Test
    void createUserFail_phoneAlreadyExist(){
        //given
        UserInput userInput = UserInput.builder()
                .email("yhj7124@naver.com")
                .password("1111")
                .name("유형진")
                .phone("010-1111-2222")
                .build();

        given(userRepository.existsByEmail("yhj7124@naver.com")).willReturn(false);
        given(userRepository.existsByPhone("010-1111-2222")).willReturn(true);

        //when
        //then
        try{
            userWriteService.createUser(userInput);
            throw new NoErrorException();
        } catch(Exception e){
            assertEquals(PHONE_ALREADY_EXIST.getMessage(),e.getMessage());
        }
    }

    @Test
    void emailAuthSuccess(){
        //given
        given(userRepository.findById(1L))
                .willReturn(Optional.of(User.builder()
                        .id(1L)
                        .authKey("인증키~")
                        .expireDateTime(LocalDateTime.now().plusDays(1))
                        .build()));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        //when
        userWriteService.emailAuth(1L,"인증키~");

        //then
        verify(userRepository,times(1)).save(userCaptor.capture());
        User UserCaptorValue = userCaptor.getValue();
        assertEquals(APPROVED,UserCaptorValue.getUserStatus());
    }

    @Test
    void emailAuthFail_userNotFound(){
        //given
        given(userRepository.findById(1L))
                .willReturn(Optional.empty());

        //when
        //then
        try{
            userWriteService.emailAuth(1L,"인증키~");
        } catch (Exception e){
            assertEquals(USER_NOT_FOUND.getMessage(),e.getMessage());
        }
    }

    @Test
    void emailAuthFail_codeAlreadyExpired(){
        //given
        given(userRepository.findById(1L))
                .willReturn(Optional.of(User.builder()
                        .id(1L)
                        .authKey("인증키~")
                        .expireDateTime(LocalDateTime.now().minusDays(1))
                        .build()));

        //when
        //then
        try{
            userWriteService.emailAuth(1L,"인증키~");
        } catch (Exception e){
            assertEquals(CODE_ALREADY_EXPIRED.getMessage(),e.getMessage());
        }
    }

    @Test
    void emailAuthFail_codeMismatch(){
        //given
        given(userRepository.findById(1L))
                .willReturn(Optional.of(User.builder()
                        .id(1L)
                        .authKey("잘못된 인증키")
                        .expireDateTime(LocalDateTime.now().plusDays(1))
                        .build()));

        //when
        //then
        try{
            userWriteService.emailAuth(1L,"인증키~");
        } catch (Exception e){
            assertEquals(CODE_MISMATCH.getMessage(),e.getMessage());
        }
    }
}
