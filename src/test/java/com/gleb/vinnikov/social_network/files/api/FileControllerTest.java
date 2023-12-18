package com.gleb.vinnikov.social_network.files.api;

import com.gleb.vinnikov.social_network.auth.api.RegistrationRequest;
import com.gleb.vinnikov.social_network.auth.services.LoginService;
import com.gleb.vinnikov.social_network.db.entities.Role;
import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.db.repos.FileRepo;
import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FileControllerTest {

    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private LoginService loginService;
    @Autowired
    private FileRepo fileRepo;
    private final String USERNAME = "brenscrazy";
    private final String EMAIL = "glebmanufa@gmail.com";
    private final String PASSWORD = "qwerty123";
    private final String TEST_FILE_PATH = "classpath:application.properties";
    private String endpoint;
    private String userToken;

    @Before
    public void init() {
        endpoint = "http://localhost:" + port + "/files";
        userToken = loginService.registration(new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER))
                .getAccessToken();
    }

    @Test
    public void upload() throws IOException {
        File file = ResourceUtils.getFile(TEST_FILE_PATH);
        MultipartFile multipartFile = new MockMultipartFile("file", "application.properties", null,
                Files.readAllBytes(file.toPath()));

        UploadingResponse uploadingResponse = webTestClient.post().uri(endpoint)
                .accept(MediaType.MULTIPART_FORM_DATA)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new Request(multipartFile)), Request.class)
                .header("Authorization", "Bearer " + userToken)
                .exchange()
                .expectStatus().isAccepted()
                .returnResult(UploadingResponse.class)
                .getResponseBody().blockFirst();

    }

    @AllArgsConstructor
    static class Request {
        MultipartFile file;
    }

}
