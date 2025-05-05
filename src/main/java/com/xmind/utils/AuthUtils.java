package com.xmind.utils;

import com.xmind.exception.AccessDeniedException;
import com.xmind.security.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static void isRealOwner(Long userId) {
        UserEntity user = getCurrentUser();
        if (user == null || !user.getId().equals(userId)) {
            throw new AccessDeniedException();
        }
    }

    public static String getCurrentUsername() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getName();
    }

    public static Long getCurrentUserId() {
        UserEntity user = getCurrentUser();
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    public static UserEntity getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserEntity) principal;
        }
        return null;
    }
}
