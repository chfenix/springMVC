package cn.solwind.admin.entity;

import java.util.Date;

public class SysRole {
    /**
     * 
     * SYS_ROLE.ID
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * 角色名称
     * SYS_ROLE.NAME
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 角色编码
     * SYS_ROLE.CODE
     *
     * @mbg.generated
     */
    private String code;

    /**
     * 状态
            1:有效
            0：无效
     * SYS_ROLE.STATUS
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     * 创建日期
     * SYS_ROLE.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * 创建用户
     * SYS_ROLE.CREATE_USER
     *
     * @mbg.generated
     */
    private String createUser;

    /**
     * 修改时间
     * SYS_ROLE.MODIFY_TIME
     *
     * @mbg.generated
     */
    private Date modifyTime;

    /**
     * 修改用户
     * SYS_ROLE.MODIFY_USER
     *
     * @mbg.generated
     */
    private String modifyUser;

    /**
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @mbg.generated
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @mbg.generated
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @mbg.generated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @mbg.generated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     *
     * @mbg.generated
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     *
     * @mbg.generated
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     *
     * @mbg.generated
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     *
     * @mbg.generated
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     *
     * @mbg.generated
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     *
     * @mbg.generated
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
}