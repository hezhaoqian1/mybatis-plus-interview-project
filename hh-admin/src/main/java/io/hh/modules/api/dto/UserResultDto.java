package io.hh.modules.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserResultDto {
    
    private List<UserInfo> list;
    
    private Long total;
    
    @Data
    public static class UserInfo {
        private String username;
        private Integer gender;
        private String realName;
        private Long id;
        private String deptName;
    }
}