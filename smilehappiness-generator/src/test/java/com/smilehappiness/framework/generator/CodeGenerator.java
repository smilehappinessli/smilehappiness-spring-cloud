package com.smilehappiness.framework.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * 代码生成类-简单版
 * <p/>
 *
 * @author smilehappiness
 * @Date 2020/6/27 9:06
 */
public class CodeGenerator {

    /**
     * 读取控制台内容
     *
     * @param tip
     * @return
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }

        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/generator/src/main/java");
        //TODO 可能需要修改
        gc.setAuthor("smilehappiness");
        gc.setOpen(true);
        //实体属性开启 Swagger2 注解
        gc.setSwagger2(true);
        gc.setServiceName("%sService");

        //------------------new--------------------
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        //------------------new----------------------

        mpg.setGlobalConfig(gc);

        // 数据源配置 TODO 需要修改
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:13306/my_shares?useUnicode=true&useSSL=false&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("smile@123");

        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                if (fieldType.toLowerCase().contains("datetime")) {
                    return DbColumnType.DATE;
                }
                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
            }
        });


        mpg.setDataSource(dsc);

        // 包配置 TODO 需要修改
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.smilehappiness");
        pc.setModuleName("framework");

        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ，如果你Entity设置了前后缀，此处注意 xml 的名称会跟着发生变化！！！
                //TODO 可能需要修改
                return projectPath + "/generator/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

//        private String entity = "/templates/entity.java";
//        private String entityKt = "/templates/entity.kt";
//        private String service = "/templates/service.java";
//        private String serviceImpl = "/templates/serviceImpl.java";
//        private String mapper = "/templates/mapper.java";
//        private String xml = "/templates/mapper.xml";
//        private String controller = "/templates/controller.java";

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseVo.class);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //strategy.setSuperControllerClass("");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        /*strategy.setSuperEntityColumns("id", "createdTime", "createdBy", "updatedTime",
                "updatedBy", "deleteTime", "deleteBy", "isDelete");*/
        strategy.setControllerMappingHyphenStyle(true);
        //设置表前缀，目前业务未详细拆分，暂时不需要设置
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        // strategy.setLogicDeleteFieldName("is_delete");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
