package cn.solwind.mybatis.generator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.DefaultCommentGenerator;

/**
 * * mybatis generator 自定义comment生成器. * 基于MBG 1.3.2. *
 */
public class MyCommentGenerator extends DefaultCommentGenerator
		implements CommentGenerator {

	private Properties properties;
	private Properties systemPro;
	private boolean suppressDate;
	private boolean suppressAllComments;
	private String currentDateStr;

	public MyCommentGenerator() {
		super();
		properties = new Properties();
		systemPro = System.getProperties();
		suppressDate = false;
		suppressAllComments = false;
		currentDateStr = (new SimpleDateFormat("yyyy-MM-dd"))
				.format(new Date());
	}
	
	

	@Override
	protected void addJavadocTag(JavaElement javaElement,
            boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
        }
        javaElement.addJavaDocLine(sb.toString());
    }



	@Override
	public void addClassComment(InnerClass innerClass,
			IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
		super.addClassComment(innerClass, introspectedTable, markAsDoNotDelete);
	}



	@Override
	public void addClassComment(InnerClass innerClass,
			IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        sb.append(" * This class corresponds to the database table "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString());

        addJavadocTag(innerClass, false);

        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
	}



	@Override
	public void addFieldComment(Field field,
			IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		// 添加字段注释
		StringBuffer sb = new StringBuffer();
		field.addJavaDocLine("/**");
		if (introspectedColumn.getRemarks() != null)
			field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
			
		sb.append(" * ");
		sb.append(introspectedTable.getFullyQualifiedTable().toString()
				.toUpperCase());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName().toUpperCase());
		field.addJavaDocLine(sb.toString());
		addJavadocTag(field, false);
		field.addJavaDocLine(" */");

	}

	@Override
	public void addGetterComment(Method method,
			IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {

        method.addJavaDocLine("/**"); //$NON-NLS-1$

        addJavadocTag(method, false);

        method.addJavaDocLine(" */"); //$NON-NLS-1$
	}

	@Override
	public void addSetterComment(Method method,
			IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		method.addJavaDocLine("/**"); //$NON-NLS-1$

        addJavadocTag(method, false);

        method.addJavaDocLine(" */"); //$NON-NLS-1$
	}

}