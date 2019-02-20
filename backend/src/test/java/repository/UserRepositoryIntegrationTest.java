package repository;

import config.ApplicationConfig;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = { ApplicationConfig.class })
//@ActiveProfiles("local")
@Transactional
class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void insertSimpleUsers() throws Exception {
        userRepository.save(User.SYSTEM);
        User user = new User();
        user.setUsername("test");
        userRepository.save(user);
        userRepository.save(User.SYSTEM);

        Assertions.assertThat(userRepository.findAll()).containsExactly(User.SYSTEM, user);
    }
}