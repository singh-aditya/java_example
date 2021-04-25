package com.company.todos.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UtilsTest {

    private Utils utils = new Utils();

    @Test
    void generateUserId() {
        String userId = utils.generateUserId(30);
        assertNotNull(userId);
        assertEquals(30, userId.length());

        String userId2 = utils.generateUserId(30);
        assertFalse(userId.equalsIgnoreCase(userId2));
    }
}