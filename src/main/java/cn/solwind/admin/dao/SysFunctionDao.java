package cn.solwind.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import cn.solwind.admin.entity.SysFunction;

public interface SysFunctionDao {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_function
	 * @mbg.generated
	 */
	@Delete({ "delete from sys_function", "where ID = #{id,jdbcType=BIGINT}" })
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_function
	 * @mbg.generated
	 */
	@Insert({ "insert into sys_function (ID, NAME, ", "CODE, ICON_CLASS, ",
			"LINK_TARGET, SORT, ", "TYPE, PARENT_ID, REMARK, ",
			"CREATE_TIME, CREATE_USER, ", "MODIFY_TIME, MODIFY_USER, ",
			"ACTION)",
			"values (#{id,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
			"#{code,jdbcType=VARCHAR}, #{iconClass,jdbcType=VARCHAR}, ",
			"#{linkTarget,jdbcType=VARCHAR}, #{sort,jdbcType=INTEGER}, ",
			"#{type,jdbcType=INTEGER}, #{parentId,jdbcType=BIGINT}, #{remark,jdbcType=VARCHAR}, ",
			"#{createTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=VARCHAR}, ",
			"#{modifyTime,jdbcType=TIMESTAMP}, #{modifyUser,jdbcType=VARCHAR}, ",
			"#{action,jdbcType=LONGVARCHAR})" })
	int insert(SysFunction record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_function
	 * @mbg.generated
	 */
	@Select({ "select",
			"ID, NAME, CODE, ICON_CLASS, LINK_TARGET, SORT, TYPE, PARENT_ID, REMARK, CREATE_TIME, ",
			"CREATE_USER, MODIFY_TIME, MODIFY_USER, ACTION",
			"from sys_function", "where ID = #{id,jdbcType=BIGINT}" })
	@Results({
			@Result(column = "ID", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODE", property = "code", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ICON_CLASS", property = "iconClass", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LINK_TARGET", property = "linkTarget", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SORT", property = "sort", jdbcType = JdbcType.INTEGER),
			@Result(column = "TYPE", property = "type", jdbcType = JdbcType.INTEGER),
			@Result(column = "PARENT_ID", property = "parentId", jdbcType = JdbcType.BIGINT),
			@Result(column = "REMARK", property = "remark", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CREATE_TIME", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CREATE_USER", property = "createUser", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MODIFY_TIME", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "MODIFY_USER", property = "modifyUser", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACTION", property = "action", jdbcType = JdbcType.LONGVARCHAR) })
	SysFunction selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_function
	 * @mbg.generated
	 */
	@Select({ "select",
			"ID, NAME, CODE, ICON_CLASS, LINK_TARGET, SORT, TYPE, PARENT_ID, REMARK, CREATE_TIME, ",
			"CREATE_USER, MODIFY_TIME, MODIFY_USER, ACTION",
			"from sys_function" })
	@Results({
			@Result(column = "ID", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODE", property = "code", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ICON_CLASS", property = "iconClass", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LINK_TARGET", property = "linkTarget", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SORT", property = "sort", jdbcType = JdbcType.INTEGER),
			@Result(column = "TYPE", property = "type", jdbcType = JdbcType.INTEGER),
			@Result(column = "PARENT_ID", property = "parentId", jdbcType = JdbcType.BIGINT),
			@Result(column = "REMARK", property = "remark", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CREATE_TIME", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CREATE_USER", property = "createUser", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MODIFY_TIME", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "MODIFY_USER", property = "modifyUser", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACTION", property = "action", jdbcType = JdbcType.LONGVARCHAR) })
	List<SysFunction> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_function
	 * @mbg.generated
	 */
	@Update({ "update sys_function", "set NAME = #{name,jdbcType=VARCHAR},",
			"CODE = #{code,jdbcType=VARCHAR},",
			"ICON_CLASS = #{iconClass,jdbcType=VARCHAR},",
			"LINK_TARGET = #{linkTarget,jdbcType=VARCHAR},",
			"SORT = #{sort,jdbcType=INTEGER},",
			"TYPE = #{type,jdbcType=INTEGER},",
			"PARENT_ID = #{parentId,jdbcType=BIGINT},",
			"REMARK = #{remark,jdbcType=VARCHAR},",
			"CREATE_TIME = #{createTime,jdbcType=TIMESTAMP},",
			"CREATE_USER = #{createUser,jdbcType=VARCHAR},",
			"MODIFY_TIME = #{modifyTime,jdbcType=TIMESTAMP},",
			"MODIFY_USER = #{modifyUser,jdbcType=VARCHAR},",
			"ACTION = #{action,jdbcType=LONGVARCHAR}",
			"where ID = #{id,jdbcType=BIGINT}" })
	int updateByPrimaryKey(SysFunction record);
	
	@Select({
		" SELECT ",
		  " DISTINCT FUNC.* ",
		" FROM SYS_FUNCTION FUNC,SYS_ROLE ROLE,SYS_ROLE_FUNC RF,SYS_USER USER,SYS_USER_ROLE UR",
		" WHERE",
		  " USER.ID=#{userId}",
		  " AND UR.USER_ID=USER.ID AND UR.ROLE_ID=ROLE.ID",
		  " AND RF.ROLE_ID= ROLE.ID AND RF.FUNC_ID=FUNC.ID",
		  " AND FUNC.STATUS=${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}",
		  " AND ROLE.STATUS=${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}",
		" ORDER BY FUNC.SORT"
	})
	List<SysFunction> getUserFunc(@Param("userId") Long userId);
	
	@Select({
		" SELECT ",
		  " DISTINCT FUNC.* ",
		" FROM SYS_FUNCTION FUNC,SYS_ROLE ROLE,SYS_ROLE_FUNC RF,SYS_USER USER,SYS_USER_ROLE UR",
		" WHERE",
		  " USER.ID=#{userId}",
		  " AND FUNC.TYPE=${@cn.solwind.admin.common.Constants@FUNC_TYPE_MENU}",
		  " AND UR.USER_ID=USER.ID AND UR.ROLE_ID=ROLE.ID",
		  " AND RF.ROLE_ID= ROLE.ID AND RF.FUNC_ID=FUNC.ID",
		  " AND FUNC.STATUS=${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}",
		  " AND ROLE.STATUS=${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}",
		" ORDER BY FUNC.SORT"
	})
	List<SysFunction> getUserMenu(@Param("userId") Long userId);
	
	@Select({
		" SELECT ",
		  " FUNC.* ",
		" FROM SYS_FUNCTION FUNC",
		" WHERE",
		  " FUNC.STATUS=${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}",
		  " AND FUNC.ACTION IS NOT NULL"
	})
	List<SysFunction> getAllValidFunc();
	
}