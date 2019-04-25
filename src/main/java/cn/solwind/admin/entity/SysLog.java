package cn.solwind.admin.entity;

import java.util.Date;

public class SysLog {
    /**
     * 主键
     * SYS_LOG.ID
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * 操作员ID
     * SYS_LOG.OPER_ID
     *
     * @mbg.generated
     */
    private Long operId;

    /**
     * 登录用户名
     * SYS_LOG.USER_NAME
     *
     * @mbg.generated
     */
    private String userName;

    /**
     * IP地址
     * SYS_LOG.IP_ADDRESS
     *
     * @mbg.generated
     */
    private String ipAddress;

    /**
     * 日志类型
            1:登录
            2:操作
     * SYS_LOG.TYPE
     *
     * @mbg.generated
     */
    private Integer type;

    /**
     * 创建日期
     * SYS_LOG.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * 备注
     * SYS_LOG.MEMO
     *
     * @mbg.generated
     */
    private String memo;

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
    public Long getOperId() {
        return operId;
    }

    /**
     *
     * @mbg.generated
     */
    public void setOperId(Long operId) {
        this.operId = operId;
    }

    /**
     *
     * @mbg.generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @mbg.generated
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @mbg.generated
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     *
     * @mbg.generated
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     *
     * @mbg.generated
     */
    public Integer getType() {
        return type;
    }

    /**
     *
     * @mbg.generated
     */
    public void setType(Integer type) {
        this.type = type;
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
    public String getMemo() {
        return memo;
    }

    /**
     *
     * @mbg.generated
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }
}