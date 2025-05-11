package com.deepak.journalapp;


import com.deepak.journalapp.entity.User;
import com.deepak.journalapp.repository.UserRepository;
import com.deepak.journalapp.service.UserArgumentSourceProvider;
import com.deepak.journalapp.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @ParameterizedTest
    @Disabled
    @ValueSource(strings = {"ram", "sima", "deepak"})
    public void findByUserName(String name){
        User user = userRepository.findByUserName(name);
        assertNotNull(user, "failed for "+name);
        //assertTrue(!user.getJournalEntries().isEmpty());
    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentSourceProvider.class)
    public void testCreateNewUser(User user){
        assertTrue(userService.createNewUser(user));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,5,7",
            "76,34,110"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a + b);
    }
}
