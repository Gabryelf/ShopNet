package com.example.ShopNet.services;
import com.example.ShopNet.models.User;
import com.example.ShopNet.models.enums.Role;
import com.example.ShopNet.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUserSuccess() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        assertTrue(userService.createUser(user));

        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(Collections.singleton( Role.ROLE_USER), user.getRoles());
    }

    @Test
    void testCreateUserFailure() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        assertFalse(userService.createUser(user));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testBanUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setActive(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.banUser(1L);

        assertFalse(user.isActive());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testChangeUserRoles() {
        User user = new User();
        user.setRoles(new HashSet<>(Arrays.asList(Role.ROLE_USER, Role.ROLE_ADMIN)));

        Map<String, String> form = new HashMap<>();
        form.put("ROLE_USER", "on");

        userService.changeUserRoles(user, form);

        assertEquals(Collections.singleton(Role.ROLE_USER), user.getRoles());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserByPrincipal() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        assertEquals(user, userService.getUserByPrincipal(principal));
    }
}
