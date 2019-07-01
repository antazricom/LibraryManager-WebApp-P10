package com.antazri.service.utils;

import org.mindrot.jbcrypt.BCrypt;

public class HashPasswordUtils {

    public static String hashPassword(String pPassword) {

        return BCrypt.hashpw(pPassword, BCrypt.gensalt());
    }
}
