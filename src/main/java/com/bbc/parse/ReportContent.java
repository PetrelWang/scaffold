package com.bbc.parse;

import lombok.Data;

import java.util.List;

@Data
public class ReportContent {

    /**
     * 封面 - 井名
     */
    private String borehole;

    /**
     * 封面 - 测试时间
     */
    private String date;

    /**
     * 封面 - 测试层位
     */
    private String layer;
    /**
     * 生产层位
     */
    private String proLayer;

    /**
     * 一、封面 - 测试井段
     */
    private String section;

    /**
     * 一、测试井基础数据 - 生产井段
     */
    private String proSection;

    /**
     * 测试目的 - 测试范围
     */
    private String range;

    /**
     * 测试目的 - 折算至产层中部深
     */
    private String depth;

    /**
     * 测试目的  - 的深度
     */
    private String testTargetDepthRange;

    /**
     * 测试要求
     */
    private String testRequire;

    /**
     * HSE提示
     */
    private String hseNote;

    /**
     * 3 TODO 中深压力、温度 - 中深压力和温度分析内容  空着
     */
    private String contentOfPandT;

    /**
     * 4、梯度分析 - 梯度分析内容 用户自己填写
     */
    private String contentOfGradientAnalysis;

    /**
     * 5、对比分析 - 智能分析内容 用户自己填写
     */
    private String contentOfIntelligentAnalytics;

    /**
     * 2、流温流压梯度表及回归方程 梯度方程  （多行文本） 等施工数据
     */
    private String contentGradientEquation;

    /**
     * 表3  JY7-6X井压力计参数表 中的压力计编号
     */
    private String manometerCode;

    /**
     * 表3  JY7-6X井压力计参数表 中的压力计日期
     */
    private String manometerDate;

    /**
     * 表3  JY7-6X井压力计参数表 中的压力计外径
     */
    private String manometerOutside;

    /**
     * 图片路径,按索引依次为：
     * 0 近两次梯度测试压力对比图
     * 1 近两次梯度压力梯度对比图
     * 2 近两次梯度测试温度对比图
     * 3 近两次梯度测试温度对比图
     * 4 实测压力、温度历史图
     * 5 流温、流压梯度分析图
     * 6 井井身结构示意图(施工方案)
     * 7 井完井管柱示意图(施工方案)
     */
    private String[] images;

    /**
     * 表1  JY7-6X井基础数据表
     */
    private List<List<String>> tableBaseData;

    /**
     * 表2  JY7-6X井流体性质参数表
     */
    private List<List<String>> tableFluidProp;

    /**
     * 表5  JY7-6X井近期生产数据表
     */
    private List<List<String>> tableProduction;

    /**
     * 表6  流温流压梯度测试成果表
     */
    private List<List<String>> tableGradient;

    /**
     * 表7  测试成果对比表
     */
    private List<List<String>> tableTestResult;


    /**
     * 表8  梯度测试对比表
     */
    private List<List<String>> tableTestGradient;


    /**
     * 附表1   实测时间压力、温度数据表
     */
    private List<List<String>> tableData;
    /**
     * 硫化氢含量
     */
    private String hydrogenSulfideContent;
    /**
     * hse提示内容
     */
    private String hseContent;

}
