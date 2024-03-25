package com.example.ShopNet.services;

import com.example.ShopNet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис пользовательских данных, реализующий интерфейс UserDetailsService.
 * Этот сервис загружает информацию о пользователе из репозитория пользователей по его адресу электронной почты.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Загружает данные пользователя по его адресу электронной почты.
     *
     * @param email Адрес электронной почты пользователя.
     * @return UserDetails объект, представляющий информацию о пользователе.
     * @throws UsernameNotFoundException Если пользователь с указанным адресом электронной почты не найден.
     */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail( email );
    }
}


