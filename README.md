# ShopNet

Обзор проекта:  краткое описание разработанного приложения на Spring Boot.

Данный проект представляет собой мультипользовательский веб-портал, созданный с использованием фреймворка Spring Boot. Целью разработки данного приложения было предоставление пользователям возможности создания и обмена персональными продуктами и играми на платформе Unity.

Основные компоненты и функциональные возможности приложения включают в себя:
    1. Личные кабинеты пользователей: Каждый пользователь имеет свой личный кабинет, в котором он может создавать, редактировать и управлять своими продуктами и материалами.
    2. Создание продуктов: Пользователи могут создавать собственные продукты, включая информацию о них и прикрепляя различные промо-материалы, такие как фото, видео и архивы с играми на Unity.
    3. Обмен продуктами: Пользователи могут просматривать и играть в продукты других пользователей, а также запускать и загружать их себе.
    4. Аутентификация и авторизация: Реализована система регистрации и авторизации пользователей, а также разграничение прав доступа между администраторами и обычными пользователями.
    5. Управление данными в базе данных: Все данные о пользователях, их продуктах и материалах хранятся в реляционной базе данных MySQL.

                                  
Основной стек технологий, использованный в разработке приложения, включает в себя:
    • Spring Boot для обеспечения основной структуры и функциональности приложения.
    • Технологии web, security и thymeleaf для реализации веб-интерфейса и обеспечения безопасности приложения.
    • JPA для работы с базой данных и ORM.
    • MySQL для хранения данных пользователей и их продуктов.
    • Apache для развертывания и обслуживания веб-приложения.
    • Mockito для тестирования приложения.
Таким образом, это приложение представляет собой полноценную платформу для создания, обмена и просмотра персональных продуктов и игр на платформе Unity, обеспечивая удобный и безопасный интерфейс для пользователей.  


![Uploading image.png…]()










                                                                     
Актуальность темы: почему разработка мультипользовательского веб-портала с функциональностью создания и обмена персональными продуктами и играми на платформе Unity является важной задачей.
Разработка мультипользовательского веб-портала с функциональностью создания и обмена персональными продуктами и играми на платформе Unity становится все более актуальной в современном мире по нескольким причинам.
Во-первых, с ростом популярности онлайн-игр и потребности в совместном взаимодействии игроков возникает необходимость в платформе, которая позволит пользователям легко создавать и делиться собственными играми с другими участниками сообщества. Мультипользовательский веб-портал с поддержкой Unity обеспечивает идеальное окружение для этого, позволяя пользователям загружать свои игры, просматривать и играть в игры других пользователей, а также обмениваться опытом и обратной связью.

                                              
Во-вторых, с развитием технологий создания игр на платформе Unity, все больше людей становится заинтересованными в создании собственных игр и демонстрации их широкой аудитории. Веб-портал с функциональностью обмена играми предоставляет простой и удобный способ публикации игр, а также получения обратной связи от других пользователей.
Кроме того, такой портал стимулирует социальное взаимодействие и обмен опытом среди пользователей, что способствует росту сообщества разработчиков игр и созданию более качественных продуктов. Он также предоставляет возможность пользователям находить новые игры и идеи, что может стимулировать их творческий потенциал и вдохновлять на создание новых проектов.
Таким образом, разработка мультипользовательского веб-портала с функциональностью создания и обмена персональными продуктами и играми на платформе Unity не только актуальна с точки зрения технических возможностей и потребностей рынка, но и способствует развитию сообщества разработчиков и социальному взаимодействию пользователей. На рынке информационных и цифровых продуктов существует множество аналогичных платформ, ниже приведены некоторые известные российские и зарубежные аналоги:
    1. Unity Connect: Это официальная платформа Unity для разработчиков, где они могут создавать и делиться своими проектами, находить партнеров и нанимать специалистов.
    2. Itch.io: Itch.io является платформой для распространения, продажи и обмена независимыми играми и программами. Здесь разработчики могут выкладывать свои проекты и взаимодействовать с сообществом.
    3. Game Jolt: Это аналогичная платформа, где разработчики могут публиковать, продавать и обмениваться играми и другими цифровыми контентом.
    4. IndieDB: IndieDB предоставляет возможность независимым разработчикам продвигать свои игры и проекты, создавать страницы для обратной связи с сообществом и делиться опытом.
                                   
    5. Steam Workshop: Это часть игрового сервиса Steam, где пользователи могут создавать, загружать и обмениваться контентом для игр, включая модификации, карты и другие дополнения.
    6. Конкуренты на российском рынке: Среди российских аналогов можно выделить такие платформы, как GameDev.ru и CyberX Games, которые также предоставляют инструменты и сообщество для разработчиков игр и других проектов.

Цель и задачи исследования: определение целей, которые я ставлю перед собой при написании дипломной работы, и конкретных задач, которые необходимо решить.
Целью дипломной работы является разработка мультипользовательского многофункционального веб-портала. Задачи которые предстояло решить:
    1. Проектирование архитектуры приложения: Необходимо разработать архитектуру приложения, определить основные компоненты, их взаимосвязь и функциональные возможности. Это включает в себя выбор подходящих технологий и инструментов для реализации функционала портала.
    2. Разработка пользовательского интерфейса: Требуется разработать удобный и интуитивно понятный пользовательский интерфейс, позволяющий пользователям легко создавать, просматривать и обмениваться продуктами и играми. Это включает в себя создание страниц для регистрации и авторизации пользователей, личных кабинетов, страниц продуктов и материалов.
    3. Реализация backand и взаимодействие с базой данных: Необходимо реализовать серверную часть приложения с использованием фреймворка Spring Boot и обеспечить взаимодействие с базой данных MySQL. Это включает в себя создание RESTfull API для работы с данными пользователей, продуктов и игр.

                
    4. Внедрение системы аутентификации и авторизации: Требуется реализовать систему регистрации и аутентификации пользователей, а также разграничение доступа между администраторами и обычными пользователями. Это включает в себя использование технологии Spring Security для обеспечения безопасности приложения.
    5. Тестирование и отладка: Необходимо провести тестирование приложения на различных этапах разработки для выявления и устранения возможных ошибок и проблем. Это включает в себя написание и запуск автоматизированных тестов с использованием фреймворка Mockito, а также ручное тестирование пользовательского интерфейса.
    6. Документация и подготовка к выпуску: Требуется составить документацию по проекту, включающую описание архитектуры, инструкции по установке и использованию, а также другие сопроводительные материалы. После завершения разработки необходимо подготовить приложение к выпуску и развертыванию на сервере.
