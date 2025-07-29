package com.skysoft.linkedin.posts_service.auth;

public class UserContextHolder {

    private static final ThreadLocal<Long> currentUser =new ThreadLocal<>();

    public static Long getCurrentUserId(){
        return currentUser.get();
    }
    public static void setCurrentUserId(Long userId){
        currentUser.set(userId);
    }
    public static void clear(){
        //avoid memory lickage
        currentUser.remove();
    }
}
