package com.yiie.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yiie.vo.data.ExcelBlackInfoVO;
import com.yiie.vo.data.PhysicalInfoVo;
import lombok.SneakyThrows;

public class ExcelListenerPhysicalTest extends AnalysisEventListener<PhysicalInfoVo> {
    @Override
    @SneakyThrows
    public void invoke(PhysicalInfoVo physicalInfoVo, AnalysisContext analysisContext) {
        System.out.print("数据："+physicalInfoVo+"\n");
        ssc ssc=new ssc();
        ssc.insertDataByEntity_Physical(physicalInfoVo);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
