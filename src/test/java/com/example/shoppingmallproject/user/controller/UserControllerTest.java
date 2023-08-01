//package com.example.shoppingmallproject.user.controller;
//
//import com.example.shoppingmallproject.user.dto.SignUpRequestDto;
//import com.example.shoppingmallproject.user.dto.UserResponseDto;
//import com.example.shoppingmallproject.user.entity.User;
//import com.example.shoppingmallproject.user.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//import com.google.gson.Gson;
//
//import static org.mockito.BDDMockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(UserController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    @MockBean
//    private UserController userController;
//
//    private String username = "병두";
//    private String email = "quden04@gmail.com";
//    private String password = "password";
//    private String phone ="1234";
//
//    @Test
//
//    void userSignUpTest() throws Exception {
//
//        //given
//        given(userController.signUp(SignUpRequestDto.builder()
//            .username(username)
//            .password(password)
//            .email(email)
//            .phone(phone)
//            .build())
//        )
//            .willReturn(
//                new ResponseEntity<UserResponseDto>(
//                    UserResponseDto.of(
//                                        User.builder()
//                                            .username(username)
//                                            .password(password)
//                                            .email(email)
//                                            .phone(phone)
//                                            .build()), HttpStatus.CREATED
//                                ));
//
//        //when
//        User user1 = User.builder()
//            .username(username)
//            .password(password)
//            .email(email)
//            .phone(phone)
//            .build();
//        UserResponseDto userResponseDto = UserResponseDto.of(user1);
//        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
//            .username(username)
//            .email(email)
//            .password(password)
//            .phone(phone)
//            .build();
//
//        Gson gson = new Gson();
//        String content = gson.toJson(userResponseDto);
//
//        mockMvc.perform(
//            post("/users/signup")
//                .content(content)
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isCreated())
//            .andExpect(jsonPath("$.username").exists())
//            .andExpect(jsonPath("$.email").exists())
//            .andExpect(jsonPath("$.password").exists())
//            .andExpect(jsonPath("$.phone").exists())
//            .andDo(print());
//
//        verify(userController).signUp(signUpRequestDto);
//    }
//
//}