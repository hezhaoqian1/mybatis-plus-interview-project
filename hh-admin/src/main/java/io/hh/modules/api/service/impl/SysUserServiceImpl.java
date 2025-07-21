package io.hh.modules.api.service.impl;

import io.hh.modules.api.entity.SysUser;
import io.hh.modules.api.entity.SysDept;
import io.hh.modules.api.mapper.SysUserMapper;
import io.hh.modules.api.service.SysUserService;
import io.hh.modules.api.service.SysDeptService;
import io.hh.modules.api.dto.UserQueryVo;
import io.hh.modules.api.dto.UserResultDto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2024-11-30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public UserResultDto pageQuery(UserQueryVo queryVo) {
        UserQueryVo.UserCondition condition = queryVo.getCondition();
        UserQueryVo.PageInfo pageInfo = queryVo.getPage();

        LambdaQueryWrapper<SysUser> userWrapper = new LambdaQueryWrapper<>();
        
        if (condition.getGender() != null) {
            userWrapper.eq(SysUser::getGender, condition.getGender());
        }
        
        if (StringUtils.hasText(condition.getUsername())) {
            userWrapper.like(SysUser::getUsername, condition.getUsername());
        }
        
        if (StringUtils.hasText(condition.getDeptName())) {
            LambdaQueryWrapper<SysDept> deptWrapper = new LambdaQueryWrapper<>();
            deptWrapper.like(SysDept::getName, condition.getDeptName());
            List<SysDept> deptList = sysDeptService.list(deptWrapper);
            
            if (!deptList.isEmpty()) {
                List<Long> deptIds = deptList.stream()
                    .map(SysDept::getId)
                    .collect(Collectors.toList());
                userWrapper.in(SysUser::getDeptId, deptIds);
            } else {
                userWrapper.eq(SysUser::getId, -1L);
            }
        }
        
        Page<SysUser> page = new Page<>(pageInfo.getCurrent(), pageInfo.getSize());
        Page<SysUser> userPage = this.page(page, userWrapper);
        
        List<SysUser> userList = userPage.getRecords();
        
        Map<Long, String> deptNameMap = null;
        if (!userList.isEmpty()) {
            List<Long> userDeptIds = userList.stream()
                .map(SysUser::getDeptId)
                .filter(id -> id != null)
                .distinct()
                .collect(Collectors.toList());
            
            if (!userDeptIds.isEmpty()) {
                List<SysDept> deptList = sysDeptService.listByIds(userDeptIds);
                deptNameMap = deptList.stream()
                    .collect(Collectors.toMap(SysDept::getId, SysDept::getName));
            }
        }
        
        final Map<Long, String> finalDeptNameMap = deptNameMap;
        List<UserResultDto.UserInfo> userInfoList = userList.stream()
            .map(user -> {
                UserResultDto.UserInfo userInfo = new UserResultDto.UserInfo();
                userInfo.setId(user.getId());
                userInfo.setUsername(user.getUsername());
                userInfo.setGender(user.getGender());
                userInfo.setRealName(user.getRealName());
                
                if (finalDeptNameMap != null && user.getDeptId() != null) {
                    userInfo.setDeptName(finalDeptNameMap.get(user.getDeptId()));
                }
                
                return userInfo;
            })
            .collect(Collectors.toList());
        
        UserResultDto result = new UserResultDto();
        result.setList(userInfoList);
        result.setTotal(userPage.getTotal());
        
        return result;
    }

}
