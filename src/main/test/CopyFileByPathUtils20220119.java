import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * @author zhumengjun
 * @since 2020-06-14 21:32
 * @desc 通过文件列表路径将文件copy出来
 */
public class CopyFileByPathUtils20220119 {
	private static final Log logger = LogFactory.getLog(CopyFileByPathUtils20220119.class);
	/**
	 * 记录 改动文件列表 的文件
	 * 文件的字符编码要与工程相同
	 * */
	private static String recordPathFile = "/改动文件列表.txt";

	/** 文件要保存到哪个路径下  */
	private static String distRoot = "/改动文件";

	/** .java文件源路径  */
	private static String javaSrcRoot = "/src/main/java";

	/** .class文件源路径  */
	private static String classSrcRootA = "/out/artifacts";
	private static String classSrcRootM = "";
	private static String classSrcRootB = "_Web_exploded/WEB-INF/classes";

	/** .class文件目标路径  */
	private static String classDistRoot = "/classes";

	/** 工作空间路径 */
	private static String workSpacePath;

	private static final String SUFFIX_JAVA = ".java";
	private static final String SUFFIX_CLASS = ".class";

	/** 字符集编码 */
	private static String systemEncode = "UTF-8";

	private static boolean isNeedJavaFile;

	public static void main(String[] args) {
		try {

			defineVariable(args);
			copyFileByPaths();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void defineVariable(String[] args) {
		boolean hasNeedJavaFileField = false;
		String changeFileListPath = null;
		String fileOutputPath = null;
		if (args != null) {
			for(int i = 0; i < args.length; i++){
				String keyValue[] = args[i].split("=");
				if (keyValue.length != 2) continue;
				switch (keyValue[0]) {
					case "workSpacePath":
						workSpacePath = keyValue[1];
						break;
					case "changeFileListPath":
						changeFileListPath = keyValue[1];
						break;
					case "fileOutputPath":
						fileOutputPath = keyValue[1];
						break;
					case "needJavaFile":
						if (!keyValue[1].isEmpty()) {
							isNeedJavaFile = keyValue[1].equalsIgnoreCase("true");
							hasNeedJavaFileField = true;
						}

						break;
				}
			}
		}

		// 获取键盘输入的字符串
		Scanner sc = new Scanner(System.in);

		if (workSpacePath == null || workSpacePath.isEmpty()) {
			System.out.println("不能自动获取到工作空间路径");
			System.out.println("请输入工作空间路径 ：");
			workSpacePath = sc.next();
		}
		workSpacePath = workSpacePath.replace("\\","/");
		logger.info("工作空间路径="+workSpacePath);


		if (changeFileListPath == null || changeFileListPath.isEmpty()) {
			System.out.println("不能自动获取到改动文件列表路径");
			System.out.println("请输入 改动文件列表路径(输入Y则为工作空间下"+recordPathFile+") ：");
			changeFileListPath = sc.next();
			if ("Y".equalsIgnoreCase(changeFileListPath)) {
				recordPathFile = workSpacePath + recordPathFile;
			} else {
				recordPathFile = changeFileListPath;
			}
		} else {
			recordPathFile = changeFileListPath;
		}
		recordPathFile = recordPathFile.replace("\\","/");
		logger.info("改动文件列表路径="+recordPathFile);


		if (fileOutputPath == null || fileOutputPath.isEmpty()) {
			System.out.println("不能自动获取到文件输出路径");
			System.out.println("请输入 文件输出路径(输入Y则为工作空间下"+distRoot+") ：");
			fileOutputPath = sc.next();
			if ("Y".equalsIgnoreCase(fileOutputPath)) {
				distRoot = workSpacePath + distRoot;
			} else {
				distRoot = fileOutputPath;
			}
		} else {
			distRoot = fileOutputPath;
		}
		distRoot = distRoot.replace("\\","/");
		logger.info("文件输出路径="+distRoot);

		if (!hasNeedJavaFileField) {
			System.out.println("不能自动判断是否需要.java文件");
			System.out.println("是否需要.java文件(Y/N) ：");
			if ("Y".equalsIgnoreCase(sc.next())) {
				isNeedJavaFile = true;
			}
		}
		logger.info("是否需要.java文件="+isNeedJavaFile);
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
			printLog2File(successPath, failPath);
			//用记事本打开d:/test.txt文件
			Process p = Runtime.getRuntime().exec( "notepad.exe " + distRoot + "/outputFile.log");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void printLog2File(StringBuilder successPath, StringBuilder failPath) throws FileNotFoundException {
		PrintStream ps = new PrintStream(distRoot + "/outputFile.log");
		System.setOut(ps);
		System.out.println("SUCCESS-----成功列表---START>>>>>>>");
		System.out.print(successPath.toString());
		System.out.println("SUCCESS--------END<<<<<<<<\n");
		if (failPath.length() > 0) {
			System.out.println("FAIL----失败列表----START!!!!!!!");
			System.out.print(failPath.toString());
			System.out.println("FAIL--------END^^^^^^^");
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

}