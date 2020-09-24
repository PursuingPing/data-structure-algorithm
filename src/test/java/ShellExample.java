import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShellExample {
    public static void main(String[] args) throws IOException {
        //cat命令
        File file= new File("");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        List<String> lines = new ArrayList<>();
        String str = null;
        while((str = bufferedReader.readLine()) != null) {
            lines.add(str);
        }

        //grep alibaba
        lines = lines.stream().filter(s -> s.contains("alibaba")).collect(Collectors.toList());

        //sort
        lines = lines.stream().sorted().collect(Collectors.toList());

        //uniq -c
        Map<String, Long> stringLongMap = lines.stream().sorted().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        //sort -rn 排序后逆序输出
        List<Long> res = stringLongMap.values().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        res.forEach(System.out::println);

    }
}
