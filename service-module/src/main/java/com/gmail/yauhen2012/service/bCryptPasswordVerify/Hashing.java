package com.gmail.yauhen2012.service.bCryptPasswordVerify;

import java.util.function.Function;

import static com.gmail.yauhen2012.service.bCryptPasswordVerify.constant.VerifyConstant.LOG_ROUNDS;

public class Hashing {

    private static UpdatableBCrypt bCrypt = new UpdatableBCrypt(LOG_ROUNDS);

    public static String hash(String password) {
        return bCrypt.hash(password);
    }

    public static boolean verifyAndUpdateHash(String password, String hash, Function<String, Boolean> updateFunc) {
        return bCrypt.verifyAndUpdateHash(password, hash, updateFunc);
    }

}
