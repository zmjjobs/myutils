import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadFileUtils {
    private static final Log logger = LogFactory.getLog(ReadFileUtils.class);
    public static Properties getProperties() throws IOException {

        try {
            Properties properties = new Properties();
            // 使用InPutStream流读取properties文件
            //BufferedReader bufferedReader = new BufferedReader(new FileReader("config/config.properties"));
            BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\ideaWorkSpace\\CopyFile\\config\\config.properties"));
            //BufferedReader bufferedReader = new BufferedReader(new FileReader(propertiesPath));
            properties.load(bufferedReader);
            logger.info("读取config/config.properties成功");
            return properties;
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("读取config/config.properties有误");
        }
        return null;
    }
}
