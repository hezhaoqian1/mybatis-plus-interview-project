package io.hh.modules.api.service;

import io.hh.modules.api.entity.SysUser;
import io.hh.modules.api.dto.UserQueryVo;
import io.hh.modules.api.dto.UserResultDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author ${author}
 * @since 2024-11-30
 */
public interface SysUserService extends IService<SysUser> {

    UserResultDto pageQuery(UserQueryVo queryVo);

}
