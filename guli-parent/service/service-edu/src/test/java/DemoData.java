import lombok.Data;
import org.junit.Test;

/**
 * @author 安逸i
 * @version 1.0
 */
import com.alibaba.excel.annotation.ExcelProperty;
//设置表头和添加的数据字段
@Data
public class DemoData {
    //设置表头名称
    @ExcelProperty("学生编号")
    private int sno;
    //设置表头名称
    @ExcelProperty("学生姓名")
    private String sname;
    public int getSno() {
        return sno;
    }
}

