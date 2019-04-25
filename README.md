# SpringMVC项目空框架
基于Java注解，集成MyBatis、Shiro、AdminLTE

数据库表
/doc/DB

建表完成后执行init.sql初始化角色权限

初始化admin用户密码可通过cn.solwind.test.utils.TestPasswordHelper.testGenPwd()进行生成

使用Mybatis Generator生成entity和Dao，相关配置文件在generatorConfig.xml