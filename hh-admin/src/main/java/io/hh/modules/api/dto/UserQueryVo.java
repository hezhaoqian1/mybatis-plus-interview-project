package io.hh.modules.api.dto;

import lombok.Data;

@Data
public class UserQueryVo {
    
    private UserCondition condition;
    
    private PageInfo page;
    
    @Data
    public static class UserCondition {
        private String deptName;
        private Integer gender;
        private String username;
    }
    
    @Data
    public static class PageInfo {
        private Integer size;
        private Integer current;
        private Long total;
    }
}