package cn.solwind.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.solwind.admin.entity.Sysbook;

/**
* @author Zln
* @version 2018-12-05 14:31
* 
* SYSBOOK Dao
*/

public interface SysbookDao {

	/**
	 * 查询所有有效的Sysbook数据
	 * @return
	 */
	@Select({
		" SELECT ",
		  " ID,TYPE_CODE,TYPE_NAME,LIST_CODE,LIST_NAME,STATUS,MODIFY_FLAG,PRI,REMARK,CREATE_USER,CREATE_TIME,UPDATE_USER,UPDATE_TIME",
		" FROM SYSBOOK SYSBOOK",
		" WHERE",
		  " SYSBOOK.STATUS=${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}",
		" ORDER BY SYSBOOK.TYPE_CODE,SYSBOOK.LIST_CODE,SYSBOOK.PRI"
	})
	public List<Sysbook> findAllValidSysbook();
	
	
	@Select({"<script>",
		" select ",
		  " ID,TYPE_CODE,TYPE_NAME,LIST_CODE,LIST_NAME,STATUS,MODIFY_FLAG,PRI,REMARK,CREATE_USER,CREATE_TIME,UPDATE_USER,UPDATE_TIME",
		" FROM SYSBOOK SYSBOOK",
		" WHERE",
		  " SYSBOOK.STATUS=${@cn.solwind.admin.common.Constants@COMMON_STATUS_VALID}",
		  "<when test='typeCode!=null'>",
	        "AND SYSBOOK.TYPE_CODE = #{typeCode}",
	        "</when>",
		" ORDER BY SYSBOOK.TYPE_CODE,SYSBOOK.LIST_CODE,SYSBOOK.PRI",
		  "</script>"})
	public List<Sysbook> selectByPage(@Param("typeCode") String typeCode,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
