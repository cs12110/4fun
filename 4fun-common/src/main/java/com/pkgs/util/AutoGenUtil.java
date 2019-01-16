package com.pkgs.util;

import java.io.File;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * TODO: 代码生成器
 * <p>
 * Author: cs12110 create at: 2019/1/6 20:44 Since: 1.0.0
 */
public class AutoGenUtil {

    /**
     * 类属性和数据库字段转换方法
     */
    private static Function<String, String> toSqlColumnNameFunction = filedName -> {
        StringBuilder builder = new StringBuilder(filedName.length());
        for (char ch : filedName.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                builder.append("_");
                builder.append((char) (ch + 32));
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    };

    /**
     * 转换实体类为Mapper接口名称
     */
    private static Function<String, String> toMapperInterfaceNameFunction = entityName -> {
        int index = entityName.indexOf("Entity");
        if (index != -1) {
            String name = entityName.substring(0, index);
            return "com.pkgs.mapper." + name + "Mapper";
        }
        return entityName;
    };

    /**
     * 转换成简易的mapper接口
     */
    private static Function<String, String> toSimpleMapperInterfaceNameFunction = entityName -> {
        int index = entityName.indexOf("Entity");
        if (index != -1) {
            String name = entityName.substring(0, index);
            return name + "Mapper";
        }
        return entityName;
    };

    /**
     * 转换实体类名称为表名
     */
    private static Function<String, String> toTableNameFunction = entityName -> {
        int index = entityName.indexOf("Entity");
        if (index != -1) {
            entityName = entityName.substring(0, index);
        }

        StringBuilder builder = new StringBuilder();
        for (char ch : entityName.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                if (builder.length() > 0) {
                    builder.append("_");
                }
                builder.append((char) (ch + 32));
            } else {
                builder.append(ch);
            }
        }
        builder.append("_t");
        return builder.toString();
    };

    /**
     * 日期处理类
     */
    private static Supplier<String> dateSupplier = () -> {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    };

    /**
     * 转换类型
     */
    private static Function<Class<?>, String> typeTranslateFun = (type) -> {
        if (type == int.class || type == Integer.class) {
            return "int";
        }
        if (type == float.class || type == Float.class) {
            return "float";
        }
        if (type == double.class || type == Double.class) {
            return "double";
        }

        return "varchar";
    };

    /**
     * 初始化engine
     */
    private static VelocityEngine engine = new VelocityEngine();

    static {
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();
    }

    /**
     * 将生产的代码存放到硬盘去
     *
     * @param entityClass 实体类对象
     * @param path        磁盘路径
     */
    public static void genCode(Class<?> entityClass, String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        // String mapperXml = genMapperXml(entityClass);
        // String mapperInterface = genMapperInterface(entityClass);
    }

    // /**
    // * 保存数据到文件
    // *
    // * @param path
    // * 文件路径
    // * @param code
    // * 数据
    // */
    // private static void save2Disk(String path, String code) {
    // FileOutputStream out = null;
    // try {
    // out = new FileOutputStream(new File(path));
    // out.write(code.getBytes());
    // out.flush();
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // try {
    // if (null != out) {
    // out.close();
    // }
    // } catch (Exception e) {
    // //do nothing
    // }
    // }
    // }

    /**
     * 生成Mapper xml
     *
     * @param entityClass 实体类
     * @return String
     */
    public static String genMapperXml(Class<?> entityClass) {
        String entityName = entityClass.getName();
        String mapperName = toMapperInterfaceNameFunction.apply(entityClass.getSimpleName());
        String tableName = toTableNameFunction.apply(entityClass.getSimpleName());

        List<Map<String, String>> list = new ArrayList<>();
        try {
            while (entityClass != Object.class) {
                Field[] fieldArr = entityClass.getDeclaredFields();
                for (Field f : fieldArr) {
                    Map<String, String> map = new HashMap<>(3);
                    String fieldName = f.getName();
                    map.put("fieldName", fieldName);
                    map.put("columnName", toSqlColumnNameFunction.apply(fieldName));
                    map.put("type", typeTranslateFun.apply(f.getType()));
                    list.add(map);
                }
                entityClass = entityClass.getSuperclass();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        VelocityContext ctx = new VelocityContext();
        ctx.put("tableName", tableName);
        ctx.put("mapperName", mapperName);
        ctx.put("entityName", entityName);
        ctx.put("fields", list);

        String path = "code/MapperXML.vm";
        return genCode(ctx, path);
    }

    /**
     * 生成Mapper接口
     *
     * @return String
     */
    public static String genMapperInterface(Class<?> entityClass) {
        VelocityContext ctx = new VelocityContext();
        // 给模板的name属性字段赋值
        String className = entityClass.getSimpleName();
        ctx.put("mapperName", toSimpleMapperInterfaceNameFunction.apply(className));
        ctx.put("entityName", className);
        ctx.put("createTime", dateSupplier.get());

        String path = "/code/Mapper.vm";
        return genCode(ctx, path);
    }

    /**
     * 生成代码字符串
     *
     * @param ctx          {@link VelocityContext} 生成代码所需的参数上下文
     * @param templatePath 模板位置,如:`code/Mapper.xml`
     * @return String
     */
    private static String genCode(VelocityContext ctx, String templatePath) {
        String codeStr = null;
        try {
            // 模板文件放置位置
            Template template = engine.getTemplate(templatePath);
            StringWriter writer = new StringWriter();
            template.merge(ctx, writer);
            codeStr = writer.toString();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codeStr;
    }

}
