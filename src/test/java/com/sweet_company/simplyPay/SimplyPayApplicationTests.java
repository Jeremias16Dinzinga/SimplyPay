package com.sweet_company.simplyPay;

import com.sweet_company.simplyPay.domain.user.TypeUser;
import com.sweet_company.simplyPay.domain.user.UserEntity;
import com.sweet_company.simplyPay.dto.UserDto;
import com.sweet_company.simplyPay.repositories.UserRepository;
import com.sweet_company.simplyPay.services.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class SimplyPayApplicationTests {
	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private UserService userService;
	private UserDto userDto;

	@Before("")
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		userDto = new UserDto("Jeremias", "Dinzinga", "874635",2000.00,"jeremias@gmail.com","1234567", TypeUser.COMMON);
	}

	@Test
	public void testCreateUser() {

		UserEntity expectedUser = new UserEntity(userDto);
		// doNothing().when(userRepository).save(any(UserEntity.class));

		UserEntity actualUser = userService.createUser(userDto);

		assertNotNull(actualUser);
		assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
		assertEquals(expectedUser.getEmail(), actualUser.getEmail());
		// verify(userRepository, times(1)).save(any(UserEntity.class));
	}

}
