package com.example.ShopNet.controllers;

import com.example.ShopNet.configurations.ArchiveExtractor;
import com.example.ShopNet.repositories.UserRepository;
import com.example.ShopNet.services.ArchiveService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UnityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ArchiveService archiveService;

    @MockBean
    private ArchiveExtractor archiveExtractor;

    @Autowired
    private UserRepository userRepository; // Предполагается, что у вас есть репозиторий для пользователей

    @Autowired
    @Qualifier("customUserDetailsServiceImplStub")
    private UserDetailsService userDetailsService;

    @Before
    public void setup() {
        // Создайте экземпляр заглушки и передайте его вашему контроллеру Spring Security
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("test", null, "ROLE_USER"));
    }

    @Test
    public void testStartUnityProject() throws Exception {
        // Arrange
        byte[] archiveData = {/* Ваш массив данных архива */};
        String projectName = "your_project_name"; // Название проекта

        // Указываем, что при вызове метода getArchiveData с определенным archiveId будет возвращен archiveData
        when(archiveService.getArchiveData(anyLong())).thenReturn(archiveData);
        // Указываем, что при вызове метода extractArchive с определенным массивом данных архива будет возвращено projectName
        when(archiveExtractor.extractArchive(any(byte[].class))).thenReturn(projectName);

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity("/unity/startArchive?archiveId=1", null, String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // Проверяем, что возвращенный URL содержит название проекта
        assertTrue(response.getBody().contains(projectName));
    }
}



