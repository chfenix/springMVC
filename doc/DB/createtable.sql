/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/4/25 11:03:44                           */
/*==============================================================*/


drop index U_SYSBOOK_TYPELIST on SYSBOOK;

drop index I_SYSBOOK_TYPECODE on SYSBOOK;

drop table if exists SYSBOOK;

drop index U_FUNC_CODE on SYS_FUNCTION;

drop table if exists SYS_FUNCTION;

drop table if exists SYS_LOG;

drop table if exists SYS_ROLE;

drop table if exists SYS_ROLE_FUNC;

drop table if exists SYS_USER;

drop table if exists SYS_USER_ROLE;

/*==============================================================*/
/* Table: SYSBOOK                                               */
/*==============================================================*/
create table SYSBOOK
(
   ID                   bigint not null auto_increment comment 'ID',
   TYPE_CODE            varchar(32) not null comment '类型编号',
   TYPE_NAME            varchar(255) not null comment '类型名称',
   LIST_CODE            varchar(32) not null comment '明细编号',
   LIST_NAME            varchar(255) not null comment '明细名称',
   STATUS               smallint not null default 1 comment '状态 数据字典条目状态：0:删除，1:有效',
   MODIFY_FLAG          tinyint comment '可修改标志
            0：不可修改
            1：可修改',
   PRI                  int comment '显示顺序',
   REMARK               varchar(255) comment '备注',
   CREATE_USER          varchar(100),
   CREATE_TIME          datetime comment '创建日期',
   UPDATE_USER          varchar(100) comment '最后修改人',
   UPDATE_TIME          datetime comment '最后修改日期',
   primary key (ID)
);

alter table SYSBOOK comment '数据字典表';

-- 通用状态 --
INSERT INTO SYSBOOK(TYPE_CODE,TYPE_NAME,LIST_CODE,LIST_NAME,STATUS,PRI,REMARK,CREATE_TIME)
VALUES('COMMON_VALID','通用状态','1','有效',1,1,'通用状态',NOW());

INSERT INTO SYSBOOK(TYPE_CODE,TYPE_NAME,LIST_CODE,LIST_NAME,STATUS,PRI,REMARK,CREATE_TIME)
VALUES('COMMON_VALID','通用状态','0','无效',1,0,'通用状态',NOW());

-- 通用是否 --
INSERT INTO SYSBOOK(TYPE_CODE,TYPE_NAME,LIST_CODE,LIST_NAME,STATUS,PRI,REMARK,CREATE_TIME)
VALUES('COMMON_YES','通用是否','1','是',1,1,'通用是否',NOW());

INSERT INTO SYSBOOK(TYPE_CODE,TYPE_NAME,LIST_CODE,LIST_NAME,STATUS,PRI,REMARK,CREATE_TIME)
VALUES('COMMON_NO','通用是否','0','否',1,0,'通用是否',NOW());

/*==============================================================*/
/* Index: I_SYSBOOK_TYPECODE                                    */
/*==============================================================*/
create index I_SYSBOOK_TYPECODE on SYSBOOK
(
   TYPE_CODE
);

/*==============================================================*/
/* Index: U_SYSBOOK_TYPELIST                                    */
/*==============================================================*/
create unique index U_SYSBOOK_TYPELIST on SYSBOOK
(
   TYPE_CODE,
   LIST_CODE
);

/*==============================================================*/
/* Table: SYS_FUNCTION                                          */
/*==============================================================*/
create table SYS_FUNCTION
(
   ID                   bigint not null auto_increment,
   NAME                 varchar(50) comment '菜单名称',
   CODE                 varchar(40),
   ACTION               longtext comment '相应的操作，如地址',
   ICON_CLASS           varchar(50) comment '图标CSS类',
   LINK_TARGET          varchar(1) comment 'html连接的target属性
            0:框架打开
            1:新窗口打开',
   SORT                 int comment '显示顺序',
   TYPE                 int comment '类型
            1:菜单
            2:操作功能',
   PARENT_ID            bigint comment '上级菜单ID，如果无父节点，则为空',
   REMARK               varchar(100),
   STATUS               int comment '状态
            1:有效
            0:无效',
   CREATE_TIME          datetime comment '创建日期',
   CREATE_USER          varchar(255) comment '创建用户',
   MODIFY_TIME          datetime comment '修改时间',
   MODIFY_USER          varchar(255) comment '修改用户',
   primary key (ID)
);

alter table SYS_FUNCTION comment '系统菜单表';

/*==============================================================*/
/* Index: U_FUNC_CODE                                           */
/*==============================================================*/
create unique index U_FUNC_CODE on SYS_FUNCTION
(
   CODE
);

/*==============================================================*/
/* Table: SYS_LOG                                               */
/*==============================================================*/
create table SYS_LOG
(
   ID                   bigint not null auto_increment comment '主键',
   OPER_ID              bigint comment '操作员ID',
   USER_NAME            varchar(40) comment '登录用户名',
   IP_ADDRESS           varchar(20) comment 'IP地址',
   TYPE                 smallint comment '日志类型
            1:登录
            2:操作',
   CREATE_TIME          datetime comment '创建日期',
   MEMO                 varchar(2000) comment '备注',
   primary key (ID)
);

alter table SYS_LOG comment '系统日志表';

/*==============================================================*/
/* Table: SYS_ROLE                                              */
/*==============================================================*/
create table SYS_ROLE
(
   ID                   bigint not null auto_increment,
   NAME                 varchar(50) comment '角色名称',
   CODE                 varchar(20) not null comment '角色编码',
   STATUS               int comment '状态
            1:有效
            0：无效',
   CREATE_TIME          datetime not null comment '创建日期',
   CREATE_USER          varchar(255) comment '创建用户',
   MODIFY_TIME          datetime comment '修改时间',
   MODIFY_USER          varchar(255) comment '修改用户',
   primary key (ID)
);

alter table SYS_ROLE comment '系统角色表';

/*==============================================================*/
/* Table: SYS_ROLE_FUNC                                         */
/*==============================================================*/
create table SYS_ROLE_FUNC
(
   ROLE_ID              bigint not null,
   FUNC_ID              bigint not null,
   primary key (ROLE_ID, FUNC_ID)
);

alter table SYS_ROLE_FUNC comment '角色菜单关联表';

/*==============================================================*/
/* Table: SYS_USER                                              */
/*==============================================================*/
create table SYS_USER
(
   ID                   bigint not null auto_increment,
   USER_NAME            varchar(50) comment '登录用户名',
   PASSWORD             varchar(50) comment '密码',
   LOGIN_DATE           datetime comment '最后一次登录时间',
   MOBILE               varchar(20) comment '手机号',
   EMAIL                varchar(100) comment 'EMAIL',
   NAME                 varchar(100) not null comment '用户名称',
   STATUS               tinyint,
   REMARK               varchar(100),
   CREATE_TIME          datetime comment '创建日期',
   CREATE_USER          varchar(255) comment '创建用户',
   MODIFY_TIME          datetime comment '修改时间',
   MODIFY_USER          varchar(255) comment '修改用户',
   primary key (ID)
);

alter table SYS_USER comment '系统用户表';

/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
create table SYS_USER_ROLE
(
   USER_ID              bigint not null,
   ROLE_ID              bigint not null,
   primary key (USER_ID, ROLE_ID)
);

alter table SYS_USER_ROLE comment '用户角色关联表';

