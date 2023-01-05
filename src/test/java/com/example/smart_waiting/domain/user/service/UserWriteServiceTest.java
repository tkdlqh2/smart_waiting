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

import static com.example.smart_waiting.exception.UserErrorCode.EMAIL_ALREADY_EXIST;
import static com.example.smart_waiting.exception.UserErrorCode.PHONE_ALREADY_EXIST;
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
}
