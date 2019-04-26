package cn.solwind.test.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.solwind.admin.config.RootConfig;
import cn.solwind.admin.utils.PasswordHelper;

/**
* @author Zhouluning
* @version 创建时间：2019-04-25
*
* 类说明
*/


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class TestPasswordHelper {

	/**
	 * 生成用户密码
	 */
	@Test
	public void testGenPwd() {
		System.out.println(PasswordHelper.generateUserPwd("admin", "111111")); 
	}

}
