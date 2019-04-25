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
   TYPE_CODE            varchar(32) not null comment '���ͱ��',
   TYPE_NAME            varchar(255) not null comment '��������',
   LIST_CODE            varchar(32) not null comment '��ϸ���',
   LIST_NAME            varchar(255) not null comment '��ϸ����',
   STATUS               smallint not null default 1 comment '״̬ �����ֵ���Ŀ״̬��0:ɾ����1:��Ч',
   MODIFY_FLAG          tinyint comment '���޸ı�־
            0�������޸�
            1�����޸�',
   PRI                  int comment '��ʾ˳��',
   REMARK               varchar(255) comment '��ע',
   CREATE_USER          varchar(100),
   CREATE_TIME          datetime comment '��������',
   UPDATE_USER          varchar(100) comment '����޸���',
   UPDATE_TIME          datetime comment '����޸�����',
   primary key (ID)
);

alter table SYSBOOK comment '�����ֵ��';

-- ͨ��״̬ --
INSERT INTO SYSBOOK(TYPE_CODE,TYPE_NAME,LIST_CODE,LIST_NAME,STATUS,PRI,REMARK,CREATE_TIME)
VALUES('COMMON_VALID','ͨ��״̬','1','��Ч',1,1,'ͨ��״̬',NOW());

INSERT INTO SYSBOOK(TYPE_CODE,TYPE_NAME,LIST_CODE,LIST_NAME,STATUS,PRI,REMARK,CREATE_TIME)
VALUES('COMMON_VALID','ͨ��״̬','0','��Ч',1,0,'ͨ��״̬',NOW());

-- ͨ���Ƿ� --
INSERT INTO SYSBOOK(TYPE_CODE,TYPE_NAME,LIST_CODE,LIST_NAME,STATUS,PRI,REMARK,CREATE_TIME)
VALUES('COMMON_YES','ͨ���Ƿ�','1','��',1,1,'ͨ���Ƿ�',NOW());

INSERT INTO SYSBOOK(TYPE_CODE,TYPE_NAME,LIST_CODE,LIST_NAME,STATUS,PRI,REMARK,CREATE_TIME)
VALUES('COMMON_NO','ͨ���Ƿ�','0','��',1,0,'ͨ���Ƿ�',NOW());

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
   NAME                 varchar(50) comment '�˵�����',
   CODE                 varchar(40),
   ACTION               longtext comment '��Ӧ�Ĳ��������ַ',
   ICON_CLASS           varchar(50) comment 'ͼ��CSS��',
   LINK_TARGET          varchar(1) comment 'html���ӵ�target����
            0:��ܴ�
            1:�´��ڴ�',
   SORT                 int comment '��ʾ˳��',
   TYPE                 int comment '����
            1:�˵�
            2:��������',
   PARENT_ID            bigint comment '�ϼ��˵�ID������޸��ڵ㣬��Ϊ��',
   REMARK               varchar(100),
   STATUS               int comment '״̬
            1:��Ч
            0:��Ч',
   CREATE_TIME          datetime comment '��������',
   CREATE_USER          varchar(255) comment '�����û�',
   MODIFY_TIME          datetime comment '�޸�ʱ��',
   MODIFY_USER          varchar(255) comment '�޸��û�',
   primary key (ID)
);

alter table SYS_FUNCTION comment 'ϵͳ�˵���';

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
   ID                   bigint not null auto_increment comment '����',
   OPER_ID              bigint comment '����ԱID',
   USER_NAME            varchar(40) comment '��¼�û���',
   IP_ADDRESS           varchar(20) comment 'IP��ַ',
   TYPE                 smallint comment '��־����
            1:��¼
            2:����',
   CREATE_TIME          datetime comment '��������',
   MEMO                 varchar(2000) comment '��ע',
   primary key (ID)
);

alter table SYS_LOG comment 'ϵͳ��־��';

/*==============================================================*/
/* Table: SYS_ROLE                                              */
/*==============================================================*/
create table SYS_ROLE
(
   ID                   bigint not null auto_increment,
   NAME                 varchar(50) comment '��ɫ����',
   CODE                 varchar(20) not null comment '��ɫ����',
   STATUS               int comment '״̬
            1:��Ч
            0����Ч',
   CREATE_TIME          datetime not null comment '��������',
   CREATE_USER          varchar(255) comment '�����û�',
   MODIFY_TIME          datetime comment '�޸�ʱ��',
   MODIFY_USER          varchar(255) comment '�޸��û�',
   primary key (ID)
);

alter table SYS_ROLE comment 'ϵͳ��ɫ��';

/*==============================================================*/
/* Table: SYS_ROLE_FUNC                                         */
/*==============================================================*/
create table SYS_ROLE_FUNC
(
   ROLE_ID              bigint not null,
   FUNC_ID              bigint not null,
   primary key (ROLE_ID, FUNC_ID)
);

alter table SYS_ROLE_FUNC comment '��ɫ�˵�������';

/*==============================================================*/
/* Table: SYS_USER                                              */
/*==============================================================*/
create table SYS_USER
(
   ID                   bigint not null auto_increment,
   USER_NAME            varchar(50) comment '��¼�û���',
   PASSWORD             varchar(50) comment '����',
   LOGIN_DATE           datetime comment '���һ�ε�¼ʱ��',
   MOBILE               varchar(20) comment '�ֻ���',
   EMAIL                varchar(100) comment 'EMAIL',
   NAME                 varchar(100) not null comment '�û�����',
   STATUS               tinyint,
   REMARK               varchar(100),
   CREATE_TIME          datetime comment '��������',
   CREATE_USER          varchar(255) comment '�����û�',
   MODIFY_TIME          datetime comment '�޸�ʱ��',
   MODIFY_USER          varchar(255) comment '�޸��û�',
   primary key (ID)
);

alter table SYS_USER comment 'ϵͳ�û���';

/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
create table SYS_USER_ROLE
(
   USER_ID              bigint not null,
   ROLE_ID              bigint not null,
   primary key (USER_ID, ROLE_ID)
);

alter table SYS_USER_ROLE comment '�û���ɫ������';

