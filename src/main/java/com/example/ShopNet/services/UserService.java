package com.example.ShopNet.services;

import com.example.ShopNet.models.User;
import com.example.ShopNet.models.enums.Role;
import com.example.ShopNet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Сервис для работы с пользователями.
 * Обеспечивает операции создания, получения списка, блокировки, изменения ролей и аутентификации пользователей.
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Создает нового пользователя.
     *
     * @param user Пользователь для создания.
     * @return true, если пользователь успешно создан, иначе false.
     */

    public boolean createUser(User user){
        String email = user.getEmail();
        if(userRepository.findByEmail( email) != null)
            return false;
        user.setActive( true );
        user.setPassword( passwordEncoder.encode( user.getPassword() ) );
        user.getRoles().add( Role.ROLE_USER );
        log.info( "Saving new user with email {}", email );
        userRepository.save( user );
        return true;
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список всех пользователей.
     */

    public List<User> list(){
        return userRepository.findAll();
    }

    /**
     * Блокирует или разблокирует пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     */
    public void banUser(Long id) {
        User user = userRepository.findById( id ).orElse( null );
        if(user != null){
            if(user.isActive()){
                user.setActive(false);
                log.info( "Ban user with id = {}; email : {}", user.getId(), user.getEmail() );
            }
            else {
                user.setActive( true );
                log.info( "UnBan user with id = {}; email : {}", user.getId(), user.getEmail() );
            }

            userRepository.save( user );
        }

    }

    /**
     * Изменяет роли пользователя.
     *
     * @param user Пользователь, роли которого нужно изменить.
     * @param form Форма с новыми ролями.
     */
    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect( Collectors.toSet());
        user.getRoles().clear();
        for(String key : form.keySet()){
            if(roles.contains( key )){
                user.getRoles().add( Role.valueOf( key ) );
            }
        }
        userRepository.save( user );
    }

    /**
     * Возвращает пользователя по его принципалу.
     *
     * @param principal Принципал пользователя.
     * @return Пользователь, соответствующий принципалу.
     */
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // You can customize how user details are created based on your User entity
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Role::name).toArray(String[]::new))
                .build();
    }

    /**
     * Проверяет аутентификацию пользователя.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @return true, если аутентификация прошла успешно, иначе false.
     */
    public boolean authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);

        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }

        return false;
    }

}
