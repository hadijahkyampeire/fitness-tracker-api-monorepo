//package cs544.fit.workout_service.service;
//
//import org.springframework.security.core.*;
//import org.springframework.stereotype.Service;
//import java.util.Collection;
//import cs544.fit.workout_service.security.CustomUserDetails;
//
//@Service
//public class AuthService {
//
//    public Long getCurrentUserId() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
//        return user.getId();
//    }
//
//    public boolean isAdmin() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
//        return user.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
//    }
//}
//
