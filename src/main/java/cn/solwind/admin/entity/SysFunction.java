package cn.solwind.admin.entity;

import java.util.Date;
import java.util.List;

public class SysFunction {
    /**
     * 
     * SYS_FUNCTION.ID
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * 菜单名称
     * SYS_FUNCTION.NAME
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 
     * SYS_FUNCTION.CODE
     *
     * @mbg.generated
     */
    private String code;

    /**
     * 图标CSS类
     * SYS_FUNCTION.ICON_CLASS
     *
     * @mbg.generated
     */
    private String iconClass;

    /**
     * html连接的target属性
            0:框架打开
            1:新窗口打开
     * SYS_FUNCTION.LINK_TARGET
     *
     * @mbg.generated
     */
    private String linkTarget;

    /**
     * 显示顺序
     * SYS_FUNCTION.SORT
     *
     * @mbg.generated
     */
    private Integer sort;

    /**
     * 类型
            1:菜单
            2：按钮
     * SYS_FUNCTION.TYPE
     *
     * @mbg.generated
     */
    private Integer type;

    /**
     * 上级菜单ID，如果无父节点，则为空
     * SYS_FUNCTION.PARENT_ID
     *
     * @mbg.generated
     */
    private Long parentId;

    /**
     * 
     * SYS_FUNCTION.REMARK
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * 状态
            1:有效
            0:无效
     * SYS_FUNCTION.STATUS
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     * 创建日期
     * SYS_FUNCTION.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * 创建用户
     * SYS_FUNCTION.CREATE_USER
     *
     * @mbg.generated
     */
    private String createUser;

    /**
     * 修改时间
     * SYS_FUNCTION.MODIFY_TIME
     *
     * @mbg.generated
     */
    private Date modifyTime;

    /**
     * 修改用户
     * SYS_FUNCTION.MODIFY_USER
     *
     * @mbg.generated
     */
    private String modifyUser;

    /**
     * 相应的操作，如地址
     * SYS_FUNCTION.ACTION
     *
     * @mbg.generated
     */
    private String action;
    
    /**
     * 用于存放菜单的子菜单
     * 
     * @mbg.generated
     */
    private List<SysFunction> childList;
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
    public String getIconClass() {
        return iconClass;
    }

    /**
     *
     * @mbg.generated
     */
    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    /**
     *
     * @mbg.generated
     */
    public String getLinkTarget() {
        return linkTarget;
    }

    /**
     *
     * @mbg.generated
     */
    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;
    }

    /**
     *
     * @mbg.generated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     *
     * @mbg.generated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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
    public Long getParentId() {
        return parentId;
    }

    /**
     *
     * @mbg.generated
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    /**
     *
     * @mbg.generated
     */
    public String getAction() {
        return action;
    }

    /**
     *
     * @mbg.generated
     */
    public void setAction(String action) {
        this.action = action;
    }

	public List<SysFunction> getChildList() {
		return childList;
	}

	public void setChildList(List<SysFunction> childList) {
		this.childList = childList;
	}
}