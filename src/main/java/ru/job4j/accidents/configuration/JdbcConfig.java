package ru.job4j.accidents.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Данный класс описывает базовую
 * конфигурацию JDBC.
 *
 * Аннотация {@link PropertySource} сообщает
 * Spring считывать файл, который мы укажем
 * в скобках.
 *
 * С помощью аннотации {@link Value}
 * мы получаем настройки для работы с БД.
 *
 * @author Constantine on 04.07.2024
 */
@Configuration
@PropertySource("classpath:application.yml")
public class JdbcConfig {

    /**
     * Метод загружает пул соединений.
     *
     * {@link DataSource} представляет собой фабрику
     * соединений к физической БД.
     *
     * Для успешного соединения мы указываем
     * драйвер, URL-адрес или строку подключения
     * к источнику данных (протокол/тип БД,
     * хост, порт, имя БД), логин, пароль.
     */
    @Bean
    public DataSource ds(@Value("${spring.datasource.driver-class-name}") String driver,
                         @Value("${spring.datasource.url}") String url,
                         @Value("${spring.datasource.username}") String username,
                         @Value("${spring.datasource.password}") String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    /**
     * Метод создает обертку для работы
     * с базой данных.
     */
    @Bean
    public JdbcTemplate jdbc(DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
