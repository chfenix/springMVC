package cn.solwind.admin.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.solwind.admin.entity.SysRole;
import cn.solwind.admin.entity.SysUser;

/**
* @author wangxian
* @version 创建时间：2019年1月7日
*
*/
public interface SysUserDao {
	@Select(value="select * from sys_user where id=#{id} and user_name=#{userName} ")
	SysUser selectByParams(@Param("id") long id, @Param("userName") String userName);
	
	@Update(value="update sys_user set password=#{password},modify_user=#{userName},"
			+ "modify_time=DATE_FORMAT(#{modifyTime},'%Y-%m-%d %H:%i:%S') where id=#{id}")
	int updatePwd(@Param("id") long id, @Param("password") String password,
			@Param("userName") String userName,@Param("modifyTime") String modifyTime);
	
	//登录时通过用户名和密码查询用户，通过返回的数据判断是否登录成功
	@Select(value="select * from SYS_USER where user_name=#{username} and status=${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}")
	SysUser getUserByUserName(String username);
		
	//登录成功后更新最后登录时间，并通过返回值确认是否修改成功
	@Update(value="update sys_user set login_date=NOW() where id=#{id}")
	int updateLoginDate(@Param("id")Long id);
	
	//通过用户登录名和用户名查询用户信息
	@Select({"<script>"
			+ "select * from sys_user where status=${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}"
			+ "<when test='name!=null'>and name like #{name} </when>"
			+ "<when test='userName!=null'>and user_name like #{userName} </when>"
			+ "order by id desc"
			+ "</script>"})
	List<SysUser> queryUserInfo(@Param("name")String name,@Param("userName")String userName,@Param("pageNum")int pageNum, @Param("pageSize") int pageSize);
	
	//通过id查询用户信息
	@Select(value="select * from sys_user where id=#{id}")
	SysUser queryUserInfoById(String id);
	
	//通过id修改用户信息	
	@Update("<script>"
			+ "update sys_user set user_name=#{su.userName},name=#{su.name},"
			+ "<when test='su.password!=null'>password=#{su.password},</when>"
			+ "mobile=#{su.mobile},email=#{su.email},status=#{su.status},"
			+ "modify_user=#{su.modifyUser},modify_time=#{su.modifyTime}"
			+ " where id=#{su.id}"
			+ "</script>")
	int updateUserInfoById(@Param("su")SysUser user);
	
	//通过id删除用户信息，实则修改用户状态
	@Update(value="update sys_user set status=0,modify_user=#{modifyUser},modify_time=#{modifyTime} where id=#{id}")
	int delestUserInfoById(@Param("id")String id,@Param("modifyUser")String modifyUser,@Param("modifyTime")Date modifyTime);
	
	//添加用户信息
	@Insert(value="insert into sys_user(user_name,name,password,status,mobile,email,create_user,create_time)"
			+ "values "
			+ "(#{su.userName},#{su.name},#{su.password},#{su.status}"
			//+ "${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}"
			+ ",#{su.mobile},#{su.email},#{su.createUser},#{su.createTime})")
	//用于插入之后返回自增长的id
	@Options(useGeneratedKeys=true, keyProperty="su.id", keyColumn="id")
	int addUserInfo(@Param("su")SysUser user);
	
	//用户登录名重复性校验
	@Select(value="select count(*) from sys_user where user_name=#{userName} and id<>#{id} and status=${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}")
	int userNameDuplicate(@Param("userName")String userName,@Param("id")String id);
	
	//获取所有有效角色
	@Select({"SELECT * FROM sys_role WHERE STATUS = ${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}"})
	List<SysRole> getAllRole();
	
	//添加数据进入用户角色表
	@Insert({"<script>"
			+ "insert into sys_user_role(user_id,role_id) values "
			+ "<foreach collection='roleId' item='roleId' separator=','>"
			+ "(#{userId},#{roleId})"
			+ "</foreach>"
			+ "</script>"})
	int addUserRoleInfo(@Param("userId")String userId,@Param("roleId")String[] roleId);
	
	//插入一对多关系进入u_r表前进行删除操作
	@Delete({"delete from sys_user_role where user_id=#{userId}"})
	int deleteURByUserId(String userId);
	
	//根据userId获取U_R关系
	@Select({"select role_id from sys_user_role where user_id=#{userId}"})
	List<String> getURInfoById(String userId);
}

