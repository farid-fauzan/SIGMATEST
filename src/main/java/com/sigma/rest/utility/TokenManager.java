package com.sigma.rest.utility;

import java.util.HashSet;
import java.util.Set;

public class TokenManager {
    private static final Set<String> validTokens = new HashSet<>();

    public static void addToken(String token) {
        validTokens.add(token);
    }

    public static void removeToken(String token) {
        validTokens.remove(token);
    }

    public static boolean isValidToken(String token) {
        return validTokens.contains(token);
    }
}