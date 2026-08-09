package com.swiggy.swiggy_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestMailConfig.class)
class SwiggyBackendApplicationTests {

    @Test
    void contextLoads() {
    }
}
