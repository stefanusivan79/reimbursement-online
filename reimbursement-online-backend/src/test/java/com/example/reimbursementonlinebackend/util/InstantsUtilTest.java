package com.example.reimbursementonlinebackend.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class InstantsUtilTest {

    private static InstantsUtil instantsUtil;

    @BeforeAll
    static void beforeAll() {
        instantsUtil = new InstantsUtil();
    }

    @Test
    void convertInstantToEpoch() {
        Instant date = Instant.parse("2018-11-30T18:35:24.00Z");
        Long dateEpochSecond = instantsUtil.convertInstantToEpoch(date);

        assertEquals(1543602924, dateEpochSecond);
        assertNotNull(dateEpochSecond);
    }

    @Test
    void convertInstantNull() {
        Long dateEpochSecond = instantsUtil.convertInstantToEpoch(null);
        assertEquals(0, dateEpochSecond);
    }
}