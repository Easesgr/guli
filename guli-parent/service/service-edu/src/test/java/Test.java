import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 安逸i
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) {
        String fileName = "E:\\write" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName,DemoData.class).sheet("学生表").doWrite(data());
    }
    //循环设置要添加的数据，最终封装到list集合中
    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }
}
