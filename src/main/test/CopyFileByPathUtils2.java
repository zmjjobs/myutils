/*
package test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import result.F8;
import util.ReadFileUtils;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

*/
/**
 * @author zhumengjun
 * @since 2020-06-14 21:32
 * @desc 通过文件列表路径将文件copy出来
 *//*

public class CopyFileByPathUtils2 {
	private static final Log logger = LogFactory.getLog(CopyFileByPathUtils2.class);
	*/
/**
	 * 记录 改动文件列表 的文件
	 * 文件的字符编码要与工程相同
	 * *//*

	private static String recordPathFile = "/改动文件列表.txt";

	*/
/** 文件要保存到哪个路径下  *//*

	private static String distRoot = "/改动文件";

	*/
/** .java文件源路径  *//*

	private static String javaSrcRoot = "/src/main/java";

	*/
/** .class文件源路径  *//*

	private static String classSrcRootA = "/out/artifacts";
	private static String classSrcRootM = "";
	private static String classSrcRootB = "_Web_exploded/WEB-INF/classes";

	*/
/** .class文件目标路径  *//*

	private static String classDistRoot = "/classes";

	*/
/** 工作空间路径 *//*

	private static String workSpacePath;

	private static final String SUFFIX_JAVA = ".java";
	private static final String SUFFIX_CLASS = ".class";

	*/
/** 字符集编码 *//*

	private static String systemEncode = "UTF-8";

	private static boolean isNeedJavaFile;

	public static void main(String[] args) {
		try {
			Properties properties = ReadFileUtils.getProperties();

			// 获取键盘输入的字符串
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入工作空间路径 ：");
			workSpacePath = sc.next();
			workSpacePath = workSpacePath.replace("\\","/");

			String changeFileListPath = properties.getProperty("changeFileListPath");
			if (changeFileListPath.length() > 0) {
				recordPathFile = changeFileListPath.replace("\\","/");
				logger.info("config.properties配置改动文件列表路径="+recordPathFile);
			} else {
				logger.info("config.properties未配置改动文件列表路径");
				System.out.println("请输入 改动文件列表路径(输入Y则为工作空间下"+recordPathFile+") ：");
				changeFileListPath = sc.next();
				if ("Y".equalsIgnoreCase(changeFileListPath)) {
					recordPathFile = workSpacePath + recordPathFile;
				} else {
					recordPathFile = changeFileListPath.replace("\\","/");
				}
			}

			String fileOutputPath = properties.getProperty("fileOutputPath");
			if (fileOutputPath.length() > 0) {
				distRoot = fileOutputPath.replace("\\","/");
				logger.info("config.properties配置文件输出路径="+distRoot);
			} else {
				logger.info("config.properties未配置文件输出路径");
				System.out.println("请输入 文件输出路径(输入Y则为工作空间下"+distRoot+") ：");
				fileOutputPath = sc.next();
				if ("Y".equalsIgnoreCase(fileOutputPath)) {
					distRoot = workSpacePath + distRoot;
				} else {
					distRoot = fileOutputPath.replace("\\","/");
				}
			}


			String isNeedJavaFileStr = properties.getProperty("isNeedJavaFile");
			if (isNeedJavaFileStr.length() > 0) {
				isNeedJavaFile = isNeedJavaFileStr.equalsIgnoreCase("true");
				logger.info("config.properties配置是否需要.java文件="+isNeedJavaFile);
			} else {
				logger.info("config.properties未配置是否需要.java文件");
				System.out.println("是否需要.java文件(Y/N) ：");
				if ("Y".equalsIgnoreCase(sc.next())) {
					isNeedJavaFile = true;
				}
			}
			copyFileByPaths();
		} catch (Exception e) {
			e.printStackTrace();
		}

		*/
/*try {
			FileUtils.copyDirectoryToDirectory(new File("D:\\ideaWorkSpace\\XiaMenXiangYe\\DevBranch\\out\\production\\itreasuryEJB/"),
					new File("D:\\ideaWorkSpace\\XiaMenXiangYe\\DevBranch\\itreasuryEJB/classes"));
		} catch (IOException e) {
			e.printStackTrace();
		}*//*

	}

	private static void copyFileByPaths() {

		StringBuilder successPath = new StringBuilder();
		StringBuilder failPath = new StringBuilder();
		try {
			List<String> pathList = readPathList();
			for (String path : pathList) {
				try {
					path = path.trim();
					if (path.equals("")) continue;
					if (path.startsWith("C:") || path.startsWith("D:") || path.startsWith("E:")) {
						path = path.substring(path.indexOf(workSpacePath) + workSpacePath.length() + 1);
					}

					if (path.endsWith(SUFFIX_JAVA)) {
						if (isNeedJavaFile) {
							FileUtils.copyFile(new File(workSpacePath + path), new File(distRoot + path));
						}
						path = path.replace("\\", "/");
						classSrcRootM = path.substring(1);
						classSrcRootM = "/"+classSrcRootM.substring(0, classSrcRootM.indexOf("/"));

						String classSrcRoot = classSrcRootA + classSrcRootM.replace("-","_") + classSrcRootB;
						String srcParentRoot = workSpacePath + path.substring(0,path.lastIndexOf("/")).replace(classSrcRootM+javaSrcRoot,classSrcRoot);
						String javaName = path.substring(path.lastIndexOf("/") + 1,path.indexOf(SUFFIX_JAVA));
						File[] files = (new File(srcParentRoot)).listFiles();
						for (File currFile : files) {
							if (currFile.getName().equals(javaName + SUFFIX_CLASS)
									|| (currFile.getName().startsWith(javaName + "$") && currFile.getName().endsWith(SUFFIX_CLASS))) {
								String currSrcPath = currFile.getAbsolutePath().replace("\\", "/");
								currSrcPath = classSrcRootM+classDistRoot + currSrcPath.substring(currSrcPath.indexOf(classSrcRoot) + classSrcRoot.length());
								FileUtils.copyFile(currFile, new File(distRoot + currSrcPath));

							}
						}
					} else {
						FileUtils.copyFile(new File(workSpacePath + path), new File(distRoot + path));
					}
					successPath.append(path).append("\n");
				} catch (Exception e) {
					failPath.append(path).append("\n");
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SUCCESS--------START>>>>>>>");
		System.out.print(successPath.toString());
		System.out.println("SUCCESS--------END<<<<<<<<\n");
		if (failPath.length() > 0) {
			System.out.println("FAIL--------START!!!!!!!");
			System.out.print(failPath.toString());
			System.out.println("FAIL--------END^^^^^^^");
			new F8();
		}

	}

	private static List<String> readPathList() throws Exception {
		if (systemEncode == null) {
			systemEncode = System.getProperty("file.encoding");
		}
		System.out.println("字符集编码："+systemEncode);
		List<String> pathList = FileUtils.readLines(new File(recordPathFile),systemEncode);
		if (pathList == null || pathList.size() <= 0) {
			throw new Exception("文件列表为空！");
		}
		return pathList;
	}

}*/
