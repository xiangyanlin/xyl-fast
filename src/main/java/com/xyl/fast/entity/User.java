package com.xyl.fast.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * user
 * @author xyl
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public User(Integer id, String userName, String sex) {
        this.id = id;
        this.userName = userName;
        this.sex = sex;
    }

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 角色编号
     */
    private Integer roleId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别0未知，1男性，2女性
     */
    private String sex;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 职业
     */
    private String profession;

    /**
     * 学历1大专以下，2大专，3本科，4研究生，5研究生以上
     */
    private String education;

    /**
     * 房源数量
     */
    private Integer houseNum;

    /**
     * 0未认证，1已认证
     */
    private String identify;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 备注信息
     */
    private String remark;
}