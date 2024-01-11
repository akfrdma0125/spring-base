package com.example.base.medium;

import com.example.base.common.controller.HealthCheckController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HealthCheckController.class)
class HealthCheckControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void 프로그램이_정상적으로_빌드되면_200을_반환한다() throws Exception {
        //when
        //then
        mockMvc.perform(get("/health-check"))
            .andExpect(status().isOk());
    }
}
