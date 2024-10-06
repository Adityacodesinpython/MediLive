package com.pharma_manager.MediLive;

import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.entity.UserEntity;
import com.pharma_manager.MediLive.service.Impl.UserArgumentsProvider;
import com.pharma_manager.MediLive.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MediLiveApplicationTests {

	@Autowired
	private UserService userService;
	@Disabled
	@Test
	public void testDeleteByUserName() {
		userService.deleteByUserName("4");
	}

	@Test
	@Disabled
	public void testGetUserById(){
		UserDto user = userService.getUserById(38L);
		assertNotNull(user);
	}
	@Disabled
	@ParameterizedTest
	@ArgumentsSource(UserArgumentsProvider.class)
	public void testSaveNewUser(UserDto userDto) {
		assertNotNull(userService.saveNewUser(userDto));
	}
	@Disabled
	@ParameterizedTest
	@CsvSource({
			"4, 8, 4",
			"5, 10, 5"
	})
	public void testTest(int expected, int a, int b) {
		assertEquals(expected, a - b);
	}

}
