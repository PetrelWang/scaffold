package com.bbc.parse;

import com.bbc.common.ReadExcelData;
import com.bbc.common.ZipUtil;
import com.spire.doc.Document;
import com.spire.doc.documents.DocumentObjectType;
import com.spire.doc.fields.DocPicture;
import com.spire.doc.interfaces.ICompositeObject;
import com.spire.doc.interfaces.IDocumentObject;
import org.springframework.boot.system.ApplicationHome;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


public class ParseWord {


    private static ReportContent parseData(String wordFilePath, String zipFilePath) throws IOException, ParseException, IllegalAccessException {
        Map<String, String> unzip = ZipUtil.unzip(zipFilePath);
        List<List<String>> tableGradient = ReadExcelData.returnTableGradientExcelData(unzip.get("流温流压梯度测试成果表.xlsx"));
        List<List<String>> tableData = ReadExcelData.returnTableDataExcelData(unzip.get("实测时间压力、温度数据表.xlsx"), 0, 1);
        List<List<String>> tableTestGradient = ReadExcelData.returnTableDataExcelData(unzip.get("梯度测试对比表.xlsx"), 0, 3);
        List<List<String>> tableTestResult = ReadExcelData.returnTableTestResultData(unzip.get("测试成果对比表.xlsx"));
        ReportContent content = parseWord(unzip,wordFilePath);
        content.setTableTestGradient(tableTestGradient);
        content.setTableData(tableData);
        content.setTableGradient(tableGradient);
        content.setTableTestResult(tableTestResult);
        return content;
    }

    public static ReportContent parseWord(Map<String,String> map ,String filePath) throws IOException {
        String[] imageUrls = new String[]{"","","","","","","",""};
        imageUrls[0]= map.get("图1近两次梯度测试压力对比图.png");
        imageUrls[1]= map.get("图2近两次梯度压力梯度对比图.png");
        imageUrls[2]=map.get("图3近两次梯度测试温度对比图.png");
        imageUrls[3]=map.get("图4近两次梯度测试温度对比图.png");
        imageUrls[4]=map.get("实测压力、温度历史图.png");
        imageUrls[5]=map.get("流温、流压梯度分析图.png");
        ReportContent content = new ReportContent();
        Document document = new Document();
        document.loadFromFile(filePath);
        String text = document.getText();
        String[] split = text.split("\r\n");
        List<String> collect = Arrays.stream(split).collect(Collectors.toList());
        String wellName = collect.get(7);
        int wellNameIndex = wellName.indexOf("井");
        String boreHole = wellName.substring(0, wellNameIndex);
        content.setBorehole(boreHole);
        String layer = collect.get(230);
        int layerBeginIndex = layer.indexOf("：");
        int layerEndIndex = layer.indexOf("。");
        String layerName = layer.substring(layerBeginIndex + 1, layerEndIndex);
        content.setDate(collect.get(21));
        content.setLayer(layerName);
        String section = collect.get(231);
        int sectionBeginIndex = section.indexOf("：");
        int sectionEndIndex = section.indexOf("，");
        String sectionStr = section.substring(sectionBeginIndex + 1, sectionEndIndex);
        int sectionIndex = sectionStr.indexOf("（");
        content.setSection(sectionStr.substring(0, sectionIndex));
        content.setProSection(sectionStr);
        String range = collect.get(249);
        int rangeBeginIndex = range.indexOf("最终下深");
        int rangeEndIndex = range.indexOf("。");
        content.setRange("0-" + range.substring(rangeBeginIndex + 4, rangeEndIndex));
        int depthEndIndex = section.indexOf("。");
        int depthBeginIndex = section.indexOf("度");
        content.setDepth(section.substring(depthBeginIndex + 1, depthEndIndex));
        content.setTestTargetDepthRange("0-" + range.substring(rangeBeginIndex + 4, rangeEndIndex));
        content.setTestRequire(collect.get(249));
        String hydrogenSulfideContent = collect.get(206);
        int hyBeginIndex = hydrogenSulfideContent.indexOf("：");
        int hyEndIndex = hydrogenSulfideContent.indexOf("。");
        content.setHydrogenSulfideContent(hydrogenSulfideContent.substring(hyBeginIndex + 1, hyEndIndex));
        String hseContent = collect.get(237).substring(collect.get(237).indexOf("）") + 1);
        content.setHseContent(hseContent);
        String manometerCode = collect.get(425);
        int manBeginIndex = manometerCode.indexOf("（");
        int manEndIndex = manometerCode.indexOf("）");
        content.setManometerCode(manometerCode.substring(manBeginIndex + 1, manEndIndex));
        String manometerDate = collect.get(431);
        content.setManometerDate(manometerDate);
        content.setManometerOutside(collect.get(429) + "mm");
        images(content, imageUrls, filePath);
        //保存井的基础数据
        saveBaseData(collect, content);
        //保存非流体性质
        saveFluidProp(collect, content);
        //保存近期生产数据表
        saveTableProduction(collect, content);
        return content;
    }

    private static void saveTableProduction(List<String> collect, ReportContent content) {
        List<List<String>> tableProduction = new ArrayList<>();
        List<String> part1 = new ArrayList<>();
        for (int i = 124; i <= 133; i++) {
            if (i == 133) {
                part1.add(collect.get(i) + collect.get(i + 1));
                break;
            }
            part1.add(collect.get(i));
        }
        tableProduction.add(part1);
        int num =135;
        if(num<=204){
            for (int i = 0; i < 7; i++) {
                List<String> part2 = new ArrayList<>();
                for (int j = 0; j <= 9; j++) {
                    part2.add(collect.get(num));
                    num++;
                }
                tableProduction.add(part2);
            }
        }
        List<String> part3 = new ArrayList<>();
        for (int i = 205; i <= 206; i++) {
            part3.add(collect.get(i));
        }
        tableProduction.add(part3);

        content.setTableProduction(tableProduction);
    }

    private static void saveFluidProp(List<String> collect, ReportContent content) {
        List<List<String>> tableFluidProp = new ArrayList<>();
        int num = 209;
        if(num<=228){
            for (int i = 0; i < 5; i++) {
                List<String> fluidPropPart1 = new ArrayList<>();
                for (int j = 0; j <= 3; j++) {
                    fluidPropPart1.add(collect.get(num));
                    num++;
                }
                tableFluidProp.add(fluidPropPart1);
            }
        }
        content.setTableFluidProp(tableFluidProp);
    }

    private static void saveBaseData(List<String> collect, ReportContent content) {
        List<List<String>> tableBaseData = new ArrayList<>();
        List<String> tablePartList1 = new ArrayList<>();
        for (int j = 82; j <= 87; j++) {
            tablePartList1.add(collect.get(j));
        }
        tableBaseData.add(tablePartList1);
        List<String> tablePartList2 = new ArrayList<>();
        for (int i = 88; i <= 89; i++) {
            tablePartList2.add(collect.get(i));
        }
        tableBaseData.add(tablePartList2);
        List<String> tablePartList3 = new ArrayList<>();
        for (int i = 90; i <= 91; i++) {
            tablePartList3.add(collect.get(i));
        }
        tableBaseData.add(tablePartList3);
        int num = 92;
        if(num<=121){
            for (int i = 1; i <= 5; i++) {
                List<String> tablePartList5 = new ArrayList<>();
                for (int j = 1; j <= 6; j++) {
                    tablePartList5.add(collect.get(num));
                    num ++;
                }
                tableBaseData.add(tablePartList5);
            }
        }
        content.setTableBaseData(tableBaseData);
    }

    private static String getJarFilePath() {
        ApplicationHome home = new ApplicationHome(ParseWord.class);
        File jarFile = home.getSource();
        return jarFile.getParentFile().toString();
    }

    public static void images(ReportContent content, String[] imageUrls, String filePath) throws IOException {

        Document document = new Document();
        document.loadFromFile(filePath);
        File file = new File(filePath);
        String fileName = file.getName();
        System.out.println(fileName);
        Queue<ICompositeObject> nodes = new LinkedList<>();
        nodes.add(document);
        List<BufferedImage> images = new ArrayList<>();
        while (nodes.size() > 0) {
            ICompositeObject node = nodes.poll();
            for (int i = 0; i < node.getChildObjects().getCount(); i++) {
                IDocumentObject child = node.getChildObjects().get(i);
                if (child instanceof ICompositeObject) {
                    nodes.add((ICompositeObject) child);
                } else if (child.getDocumentObjectType() == DocumentObjectType.Picture) {
                    DocPicture picture = (DocPicture) child;
                    images.add(picture.getImage());
                }
            }
        }
        String jarFilePath = getJarFilePath();
        for (int i = 1; i <= 2; i++) {
            String path = String.format(jarFilePath + "\\" + fileName + "\\extractImage-%d.png", i);
            File file2 = new File(path);
            if (!file2.getParentFile().exists() && !file2.getParentFile().isDirectory()) {
                file2.mkdirs();
            }
            File file1 = new File(path);
            ImageIO.write(images.get(i), "PNG", file1);
            imageUrls[i + 5] = path;
            content.setImages(imageUrls);
        }
    }

}
