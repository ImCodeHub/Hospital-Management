package com.Hospital.Hospital.Management.Service.Authenticate;

import java.util.HashSet;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {
    private final Set<String> blacklist = new HashSet<>();

    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }
}