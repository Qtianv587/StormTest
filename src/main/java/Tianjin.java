//entity_type:[机构，服务]
//service_type:[AIS服务，海事气象服务，港口服务]
//service_name:[测试一，测试二，测试三]
//status:[304, 500, 404, 200]
//latitude:[25-41] longitude:[99-120]
//province:[山东，广东，广西，江西，上海，湖北，浙江]


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

public class Tianjin {
    public static void main(String[] args) throws FileNotFoundException {
        String[] entity_types = {"机构", "服务"};
        String[] service_types = {"AIS服务", "海事气象服务", "港口服务"};
        String[] service_names = {"测试一", "测试二", "测试三"};
        String[] statuses = {"304", "500", "404"};
        String[] provinces = {"山东", "广东", "广西", "江西", "上海", "湖北", "浙江"};
        System.setOut(new PrintStream("/Users/qitian/StormProjects/StormTest/src/main/resources/logs.txt"));
        for (int i = 0; i < 80000; i++) {
            System.out.print(genRandomString(entity_types));
            System.out.print(" ");
            System.out.print(genRandomString(service_types));
            System.out.print(" ");
            System.out.print(genRandomString(service_names));
            System.out.print(" ");
            System.out.print("200");
            System.out.print(" ");
            System.out.print(nextDouble(25, 41));
            System.out.print(" ");
            System.out.print(nextDouble(99, 120));
            System.out.print(" ");
            System.out.println(genRandomString(provinces));
        }
        for (int i = 0; i < 20000; i++) {
            System.out.print(genRandomString(entity_types));
            System.out.print(" ");
            System.out.print(genRandomString(service_types));
            System.out.print(" ");
            System.out.print(genRandomString(service_names));
            System.out.print(" ");
            System.out.print(genRandomString(statuses));
            System.out.print(" ");
            System.out.print(nextDouble(25, 41));
            System.out.print(" ");
            System.out.print(nextDouble(99, 120));
            System.out.print(" ");
            System.out.println(genRandomString(provinces));
        }
    }

    public static String genRandomString(String[] strings) {
        int random = (int) (Math.random() * strings.length);
        return strings[random];
    }

    public static String nextDouble(double min, double max) {
        double value = min + ((max - min) * new Random().nextDouble());
        return String.format("%.4f", value);
    }
}
