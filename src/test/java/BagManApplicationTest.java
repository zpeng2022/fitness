import com.alibaba.excel.EasyExcel;
import com.yiie.common.service.GymOrderService;
import com.yiie.common.service.GymService;
import com.yiie.entity.Gym;
import com.yiie.utils.ExcelListener;
import com.yiie.utils.OSS;
import com.yiie.utils.TimeUtile;
import com.yiie.vo.data.ExcelBlackInfoVO;
import com.yiie.vo.data.ExcelBlackType;
import com.yiie.vo.data.SportAndValue;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class BagManApplicationTest {
    /*@Test
    public void getBase64(){
        File file = new File("C:\\Users\\Lenovo\\Desktop\\default2.png");
        String base64EncoderImg = "";
        byte[] data=null;
        try {
            //获取输入流
            FileInputStream fis = new FileInputStream(file);

            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            data = bos.toByteArray();
            //
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        base64EncoderImg = Base64.encodeBase64String(data);
        base64EncoderImg = "data:image/png;base64," + base64EncoderImg;
        System.out.println("base64EncoderImg:\n"+base64EncoderImg);
    }
    @Test
    private void get1(){
//        String s=

    }*/
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void dm(){
        System.out.println(jdbcTemplate.queryForList(
                "SELECT banner as 版本信息 FROM v$version"));
    }
    @Test
    public void trim(){
        System.out.print(")08:00 - 11:00".trim());
    }
}
