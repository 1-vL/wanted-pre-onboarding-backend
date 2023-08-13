package com.wanted.preonboarding.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.MyRestDoc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("develop")
@Sql(value = "classpath:db/teardown_wanted.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(properties = "jwt.secret = TEMP_JWT_SECRET")
public class BoardRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "betest@nate.com")
    @Test
    public void post_test() throws Exception {
        // given teardown.sql
        BoardRequest.PostDTO requestDTO = new BoardRequest.PostDTO();
        requestDTO.setTitle("테스트용 아무 게시글 제목");
        requestDTO.setContent("테스트용 게시글 내용");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/boards")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("게시글 작성 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @WithUserDetails(value = "betest@nate.com")
    @Test
    public void post_fail_test1() throws Exception {
        // given teardown.sql
        BoardRequest.PostDTO requestDTO = new BoardRequest.PostDTO();
        requestDTO.setTitle("테스트용 아무 게시글 제목");
        // 제목 또는 본문 누락
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/boards")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("게시글 작성 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("must not be empty:content"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @WithUserDetails(value = "betest@nate.com")
    @Test
    public void post_fail_test2() throws Exception {
        // given teardown.sql
        BoardRequest.PostDTO requestDTO = new BoardRequest.PostDTO();
        requestDTO.setContent("테스트용 아무 게시글 내용");
        // 제목 또는 본문 누락
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/boards")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("게시글 작성 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("must not be empty:title"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void getOne_test() throws Exception {
        // given teardown.sql
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/boards/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.title").value("ㅋㅋㅋㅋ"));
        resultActions.andExpect(jsonPath("$.response.content").value("테스트2"));
        resultActions.andExpect(jsonPath("$.response.author.id").value(1));
        resultActions.andExpect(jsonPath("$.response.author.email").value("betest@nate.com"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @Test
    public void getOne_fail_test() throws Exception {
        // given teardown.sql
        // 존재하지 않는 게시글
        int id = 100;

        // when
        ResultActions resultActions = mvc.perform(
                get("/boards/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("게시물 번호를 찾을 수 없습니다 : " + id));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @Test
    public void getAll_test() throws Exception {
        // given teardown.sql

        // when
        ResultActions resultActions = mvc.perform(
                get("/boards")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        // 1번째 게시글
        // 1,'테스트2','ㅋㅋㅋㅋ',1
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.boards[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.boards[0].title").value("ㅋㅋㅋㅋ"));
        resultActions.andExpect(jsonPath("$.response.boards[0].author.id").value(1));
        resultActions.andExpect(jsonPath("$.response.boards[0].author.email").value("betest@nate.com"));
        // 8번째 게시글
        // 8,'테스트2','다른작성자 22',2
        resultActions.andExpect(jsonPath("$.response.boards[7].id").value(8));
        resultActions.andExpect(jsonPath("$.response.boards[7].title").value("다른작성자 22"));
        resultActions.andExpect(jsonPath("$.response.boards[7].author.id").value(2));
        resultActions.andExpect(jsonPath("$.response.boards[7].author.email").value("betest22@nate.com"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @Test
    public void getAll_test_pageable() throws Exception {
        // given teardown.sql
        int page = 1;
        int size = 5;

        // when
        ResultActions resultActions = mvc.perform(
                get("/boards?page="+page+"&size="+size+"&sort=id,ASC")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        // 0페이지부터 시작, 1페이지는 6번부터
        // 6번째 게시글
        // 6,'테스트2','다른작성자 22',2
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.boards[0].id").value(6));
        resultActions.andExpect(jsonPath("$.response.boards[0].title").value("다른작성자 22"));
        resultActions.andExpect(jsonPath("$.response.boards[0].author.id").value(2));
        resultActions.andExpect(jsonPath("$.response.boards[0].author.email").value("betest22@nate.com"));
        // [{"id":6,"title":"다른작성자 22","author":{"id":2,"email":"betest22@nate.com"}},{"id":7,"title":"다른작성자 22","author":{"id":2,"email":"betest22@nate.com"}},{"id":8,"title":"다른작성자 22","author":{"id":2,"email":"betest22@nate.com"}}]

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "betest@nate.com")
    @Test
    public void edit_test() throws Exception {
        // given teardown.sql
        int id = 1;
        String title = "단위테스트 내부에서 수정된 제목";
        String content = "마찬가지로 단위 테스트 내부에서 수정된 본문";
        BoardRequest.EditDTO requestDTO = new BoardRequest.EditDTO();
        requestDTO.setId(id);
        requestDTO.setTitle(title);
        requestDTO.setContent(content);

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                put("/boards")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(id));
        resultActions.andExpect(jsonPath("$.response.title").value(title));
        resultActions.andExpect(jsonPath("$.response.content").value(content));
        resultActions.andExpect(jsonPath("$.response.author.id").value(id));
        resultActions.andExpect(jsonPath("$.response.author.email").value("betest@nate.com"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @WithUserDetails(value = "betest22@nate.com")
    @Test
    public void edit_fail_test1() throws Exception {
        // given teardown.sql
        // 다른 사람이 작성한 게시글 수정 시도
        int id = 1;
        String title = "단위테스트 내부에서 수정된 제목";
        String content = "마찬가지로 단위 테스트 내부에서 수정된 본문";
        BoardRequest.EditDTO requestDTO = new BoardRequest.EditDTO();
        requestDTO.setId(id);
        requestDTO.setTitle(title);
        requestDTO.setContent(content);

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                put("/boards")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이 게시글의 작성자가 아닙니다 : " + id));
        resultActions.andExpect(jsonPath("$.error.status").value(401));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @WithUserDetails(value = "betest@nate.com")
    @Test
    public void edit_fail_test2() throws Exception {
        // given teardown.sql
        // 존재하지 않는 게시글 번호 수정 시도
        int id = 100;
        String title = "단위테스트 내부에서 수정된 제목";
        String content = "마찬가지로 단위 테스트 내부에서 수정된 본문";
        BoardRequest.EditDTO requestDTO = new BoardRequest.EditDTO();
        requestDTO.setId(id);
        requestDTO.setTitle(title);
        requestDTO.setContent(content);

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                put("/boards")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("게시물 번호를 찾을 수 없습니다 : " + id));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "betest@nate.com")
    @Test
    public void delete_test() throws Exception {
        // given teardown.sql
        int id = 1;


        // when
        ResultActions resultActions = mvc.perform(
                delete("/boards/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @WithUserDetails(value = "betest22@nate.com")
    @Test
    public void delete_fail_test1() throws Exception {
        // given teardown.sql
        int id = 1;


        // when
        ResultActions resultActions = mvc.perform(
                delete("/boards/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이 게시글의 작성자가 아닙니다 : " + id));
        resultActions.andExpect(jsonPath("$.error.status").value(401));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @WithUserDetails(value = "betest@nate.com")
    @Test
    public void delete_fail_test2() throws Exception {
        // given teardown.sql
        int id = 100;


        // when
        ResultActions resultActions = mvc.perform(
                delete("/boards/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("게시물 번호를 찾을 수 없습니다 : " + id));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
