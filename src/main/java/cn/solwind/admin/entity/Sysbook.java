package cn.solwind.admin.entity;

import java.util.Date;

public class Sysbook {
    /**
     * ID
     * SYSBOOK.ID
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * 类型编号
     * SYSBOOK.TYPE_CODE
     *
     * @mbg.generated
     */
    private String typeCode;

    /**
     * 类型名称
     * SYSBOOK.TYPE_NAME
     *
     * @mbg.generated
     */
    private String typeName;

    /**
     * 明细编号
     * SYSBOOK.LIST_CODE
     *
     * @mbg.generated
     */
    private String listCode;

    /**
     * 明细名称
     * SYSBOOK.LIST_NAME
     *
     * @mbg.generated
     */
    private String listName;

    /**
     * 状态 数据字典条目状态：0:删除，1:有效
     * SYSBOOK.STATUS
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     * 可修改标志
            0：不可修改
            1：可修改
     * SYSBOOK.MODIFY_FLAG
     *
     * @mbg.generated
     */
    private Integer modifyFlag;

    /**
     * 显示顺序
     * SYSBOOK.PRI
     *
     * @mbg.generated
     */
    private Integer pri;

    /**
     * 备注
     * SYSBOOK.REMARK
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * 
     * SYSBOOK.CREATE_USER
     *
     * @mbg.generated
     */
    private String createUser;

    /**
     * 创建日期
     * SYSBOOK.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * 最后修改人
     * SYSBOOK.UPDATE_USER
     *
     * @mbg.generated
     */
    private String updateUser;

    /**
     * 最后修改日期
     * SYSBOOK.UPDATE_TIME
     *
     * @mbg.generated
     */
    private Date updateTime;

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
    public String getTypeCode() {
        return typeCode;
    }

    /**
     *
     * @mbg.generated
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     *
     * @mbg.generated
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     *
     * @mbg.generated
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     *
     * @mbg.generated
     */
    public String getListCode() {
        return listCode;
    }

    /**
     *
     * @mbg.generated
     */
    public void setListCode(String listCode) {
        this.listCode = listCode;
    }

    /**
     *
     * @mbg.generated
     */
    public String getListName() {
        return listName;
    }

    /**
     *
     * @mbg.generated
     */
    public void setListName(String listName) {
        this.listName = listName;
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
    public Integer getModifyFlag() {
        return modifyFlag;
    }

    /**
     *
     * @mbg.generated
     */
    public void setModifyFlag(Integer modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    /**
     *
     * @mbg.generated
     */
    public Integer getPri() {
        return pri;
    }

    /**
     *
     * @mbg.generated
     */
    public void setPri(Integer pri) {
        this.pri = pri;
    }

    /**
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     *
     * @mbg.generated
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}