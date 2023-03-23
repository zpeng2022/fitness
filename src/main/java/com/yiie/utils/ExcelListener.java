package com.yiie.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.yiie.vo.data.ExcelBlackInfoVO;
import lombok.SneakyThrows;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<ExcelBlackInfoVO> {
    private String deptId;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    //一行一行读取excel内容
    @SneakyThrows
    @Override
    public void invoke(ExcelBlackInfoVO excelBlackInfoVO, AnalysisContext analysisContext) {
        System.out.print("数据："+excelBlackInfoVO+"\n");
        ssc ssc=new ssc();
        ssc.insertDataByEntity(excelBlackInfoVO,deptId);
    }

    //读取表头
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
//        super.invokeHead(headMap, context);
        System.out.print("表头:"+headMap+"\n");
    }

    //读取完后做的动作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.print("数据读取完毕\n");
    }
}
