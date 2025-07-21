package io.hh.modules.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author ${author}
 * @since 2024-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDept implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 上级ID
     */
    private Long pid;

    /**
     * 所有上级ID，用逗号分开
     */
    private String pids;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;


}
