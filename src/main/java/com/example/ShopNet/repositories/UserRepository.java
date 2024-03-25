package com.example.ShopNet.repositories;

import com.example.ShopNet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Репозиторий для взаимодействия с сущностью User в базе данных.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Находит пользователя по его электронной почте.
     *
     * @param email Адрес электронной почты пользователя для поиска.
     * @return Пользователь с указанным адресом электронной почты.
     */
    User findByEmail(String email);
}
