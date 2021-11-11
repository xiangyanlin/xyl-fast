package com.xyl.fast.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@Document(indexName = "user_index", shards = 3, replicas = 1)
public class EsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public EsUser(Integer id, String userName, String sex) {
        this.id = id;
        this.userName = userName;
        this.sex = sex;
    }

    /**
     * 主键ID
     */
    @Id
    private Integer id;

    /**
     * 角色编号
     */
    @Field(type = FieldType.Text)
    private Integer roleId;

    /**
     * 用户名
     */
    @Field(type = FieldType.Keyword)
    private String userName;

    /**
     * 密码
     */
    @Field(type = FieldType.Keyword)
    private String password;

    /**
     * 头像路径
     */
    @Field(type = FieldType.Keyword)
    private String avatar;

    /**
     * 邮箱
     */
    @Field(type = FieldType.Keyword)
    private String email;

    /**
     * 手机号
     */
    @Field(type = FieldType.Keyword)
    private String mobile;

    /**
     * 性别0未知，1男性，2女性
     */
    @Field(type = FieldType.Keyword)
    private String sex;

    /**
     * 真实姓名
     */
    @Field(type = FieldType.Keyword)
    private String realName;

    /**
     * 身份证号
     */
    @Field(type = FieldType.Keyword)
    private String idCard;

    /**
     * 职业
     */
    @Field(type = FieldType.Keyword)
    private String profession;

    /**
     * 学历1大专以下，2大专，3本科，4研究生，5研究生以上
     */
    @Field(type = FieldType.Keyword)
    private String education;

    /**
     * 房源数量
     */
    @Field(type = FieldType.Keyword)
    private Integer houseNum;

    /**
     * 0未认证，1已认证
     */
    @Field(type = FieldType.Keyword)
    private String identify;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Keyword)
    private Date createTime;

    /**
     * 备注信息
     */
    @Field(type = FieldType.Keyword)
    private String remark;
}