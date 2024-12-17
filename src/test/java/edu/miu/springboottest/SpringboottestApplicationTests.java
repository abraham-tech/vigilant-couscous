package edu.miu.springboottest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "spring.main.lazy-initialization=true",
classes = {SpringboottestApplication.class})
class SpringboottestApplicationTests {

	@Test
	void contextLoads() {
	}

}
