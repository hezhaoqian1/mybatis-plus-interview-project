package io.hh;

import io.hh.modules.api.dto.UserQueryVo;
import io.hh.modules.api.dto.UserResultDto;
import io.hh.modules.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test {
    
    @Autowired
    private SysUserService sysUserService;
    
    @org.junit.jupiter.api.Test
    public void testPageQuery() {
        UserQueryVo queryVo = new UserQueryVo();
        
        UserQueryVo.UserCondition condition = new UserQueryVo.UserCondition();
        condition.setDeptName("技术");
        condition.setGender(0);
        condition.setUsername("admin");
        queryVo.setCondition(condition);
        
        UserQueryVo.PageInfo pageInfo = new UserQueryVo.PageInfo();
        pageInfo.setSize(10);
        pageInfo.setCurrent(1);
        pageInfo.setTotal(15L);
        queryVo.setPage(pageInfo);
        
        UserResultDto result = sysUserService.pageQuery(queryVo);
        
        System.out.println("查询结果：");
        System.out.println("总记录数：" + result.getTotal());
        System.out.println("当前页数据：");
        if (result.getList() != null) {
            for (UserResultDto.UserInfo userInfo : result.getList()) {
                System.out.println("ID: " + userInfo.getId() + 
                                 ", 用户名: " + userInfo.getUsername() + 
                                 ", 性别: " + userInfo.getGender() + 
                                 ", 真实姓名: " + userInfo.getRealName() + 
                                 ", 部门名称: " + userInfo.getDeptName());
            }
        }
    }
}