package tech.makers.aceplay.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

// https://www.youtube.com/watch?v=L4vkcgRnw2g&t=651s
@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserDetailsServiceImplTest {

  @Autowired
  private UserRepository repository;

  @Autowired
  private UserDetailsServiceImpl subject;

  @Test
  void whenUserExists_testLoadUserByUsernameReturnsUser() {
    repository.save(new User("kay", "password"));
    UserDetails result = subject.loadUserByUsername("kay");
    assertEquals("kay", result.getUsername());
  }

  @Test
  void whenUserDoesNotExist_testLoadUserByUsernameThrows() {
    assertThrows(UsernameNotFoundException.class, () ->
        subject.loadUserByUsername("kay"));
  }
}
