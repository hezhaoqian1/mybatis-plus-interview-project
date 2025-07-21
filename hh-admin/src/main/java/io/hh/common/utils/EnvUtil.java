package io.hh.common.utils;

import org.springframework.core.env.Environment;

import java.util.Objects;


public class EnvUtil {

    private static Environment ENV;

    public static void initEnvApplication(Environment env){
        if(Objects.isNull(ENV)){
            ENV=env;
        }
    }

    public static Environment getENV(){
        return ENV;
    }

}
