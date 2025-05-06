package com.xmind.utils;

import com.xmind.exception.AccessDeniedException;
import com.xmind.exception.BadRequestException;
import com.xmind.security.entity.UserEntity;
import com.xmind.security.models.Roles;
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
            throw null;
        }
        return user.getId();
    }

    public static Boolean isAdmin() {
        UserEntity user = getCurrentUser();
        if (user == null) {
            throw new BadRequestException("Current user can not parsed");
        }
        return user.getRoles().contains(Roles.ADMIN);
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
