import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 对比目录中不同的文件
 */
public class CompareFilesInTwoDirectories {
    public static void main(String[] args)   {
        List<String> pathA = new ArrayList<>();
        List<String> pathB = new ArrayList<>();
        File file = new File("C:\\Users\\mjzhud\\Desktop\\旧的\\20230406-01-河钢核心与idms集成-01-刘占鹏-黄金双\\02改动文件\\01代码补丁");
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                pathA.add(f.getName());
            }
        }
        file = new File("C:\\Users\\mjzhud\\Desktop\\新的\\02改动文件\\01代码补丁");
        files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                pathB.add(f.getName());
            }
        }
        pathA.removeAll(pathB);
        for (String path: pathA) {
            System.out.println(path);
        }
    }

    
    
}