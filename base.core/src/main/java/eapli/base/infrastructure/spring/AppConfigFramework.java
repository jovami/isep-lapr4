package eapli.base.infrastructure.spring;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;

/**
 * AppConfigFramework
 */
@Configuration
@Profile("Test.Config")
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class, /* TODO: check */
})
@ComponentScan(basePackages = {
        // Dump everything until it works

        // "eapli.framework.infrastructure.pubsub.impl.inprocess.service",

        // "eapli.framework.infrastructure.pubsub.impl.simplepersistent.service",
        // "eapli.framework.infrastructure.pubsub.impl.simplepersistent.model",
        // "eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.jpa",

        "eapli.framework.infrastructure.authz.application",
        "eapli.framework.infrastructure.authz.repositories.impl",
        "eapli.framework.infrastructure.authz.domain.model",
})
public class AppConfigFramework {

    @Bean
    public PasswordEncoder encoder() {
        return new PlainTextEncoder();
    }
}
