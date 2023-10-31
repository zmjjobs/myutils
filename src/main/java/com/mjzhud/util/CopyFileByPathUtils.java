package com.mjzhud.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * @author zhumengjun
 * @since 2023-09-08 21:32
 * @desc 通过文件列表路径将文件copy出来
 *
 * newSystem:
 * workSpacePath=D:\ideaWorkSpace\HaiNanNongKen\branches\newidms changeFileListPath=C:\Users\mjzhud\Desktop\改动文件列表.txt fileOutputPath=D:\MyOutputFile needJavaFile=false classSrcRoot=/out/production/#ModuleName# javaSrcRoot=/src/main/java classOutputRoot=/WEB-INF/classes
 *
 * oldSystem:
 * workSpacePath=D:\eclipseWorkSpace\TYong\HeXinYunWei20230707 changeFileListPath=C:\Users\mjzhud\Desktop\改动文件列表.txt fileOutputPath=D:\MyOutputFile needJavaFile=true javaSrcRoot=/src classOutputRoot=/classes classSrcRoot=/#ModuleName#/classes
 */
public class CopyFileByPathUtils {
	//private static final Log logger = LogFactory.getLog(CopyFileByPathUtils.class);
	/**
	 * 存放改动文件列表的文件路径
	 * 文件的字符编码要与工程相同
	 * */
	private static String changeFileListPath;
	/** 文件要保存到哪个路径下  */
	private static String fileOutputPath;
	/** .java文件源路径  */
	private static String javaSrcRoot;
	/** resources下文件源路径  */
	private static String resourcesSrcRoot = "/src/main/resources";
	/** webapp下文件源路径  */
	private static String webappSrcRoot = "/src/main/webapp";
	/** .class文件源路径  */
	private static String classSrcRoot;
	/** .class文件目标路径  */
	private static String classOutputRoot;
	/** 工作空间路径 */
	private static String workSpacePath;
	private static final String SUFFIX_JAVA = ".java";
	private static final String SUFFIX_CLASS = ".class";
	private static final String MODULE_NAME_REPLACE = "#ModuleName#";
	private static final String MODULE_NAME_REPLACE_MIDLINE2UNDERLINE = "#ModuleName_Midline2Underline#";

	/** 是否需要输出.java文件 */
	private static boolean isNeedJavaFile;

	public static void main(String[] args) {
		try {
			System.out.println("本系统用于抽取文件补丁，如有问题请联系mjzhud@isoftstone.com\n");
			defineVariable(args);
			copyFileByPaths();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void defineVariable(String[] args) throws Exception {
		boolean hasNeedJavaFileField = false;
		if (args != null) {
			for(int i = 0; i < args.length; i++){
				String keyValue[] = args[i].split("=");
				if (keyValue.length != 2) continue;
				keyValue[0] = keyValue[0].trim();
				keyValue[1] = keyValue[1].trim();
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
					case "webappSrcRoot":
						webappSrcRoot = keyValue[1];
						break;
					case "classSrcRoot":
						classSrcRoot = keyValue[1];
						break;
					case "javaSrcRoot":
						javaSrcRoot = keyValue[1];
						break;
					case "resourcesSrcRoot":
						resourcesSrcRoot = keyValue[1];
						break;
					case "classOutputRoot":
						classOutputRoot = keyValue[1];
						break;
					case "needJavaFile":
						isNeedJavaFile = keyValue[1].equalsIgnoreCase("true");
						hasNeedJavaFileField = true;
						break;
				}
			}
		}
		if (StringUtils.isBlank(javaSrcRoot)) {
			throw new Exception("不能直接获取到.java文件源路径, 请在.bat文件中配置");
		}
		javaSrcRoot = javaSrcRoot.replace("\\","/");
		System.out.println("直接获取到.java文件源路径："+javaSrcRoot+"\n");

		webappSrcRoot = webappSrcRoot.replace("\\","/");
		System.out.println("直接获取到webapp下文件源路径："+webappSrcRoot+"\n");

		resourcesSrcRoot = resourcesSrcRoot.replace("\\","/");
		System.out.println("直接获取到resources下文件源路径："+resourcesSrcRoot+"\n");

		if (StringUtils.isBlank(classSrcRoot)) {
			throw new Exception("不能直接获取到.class文件源路径, 请在.bat文件中配置");
		}
		classSrcRoot = classSrcRoot.replace("\\","/");
		System.out.println("直接获取到.class文件源路径："+classSrcRoot+"\n");

		// 获取键盘输入的字符串
		Scanner sc = new Scanner(System.in);
		if (StringUtils.isBlank(workSpacePath)) {
			System.out.println("不能直接获取到工作空间路径,请输入：");
			workSpacePath = sc.next();
		}
		workSpacePath = checkScString(workSpacePath, sc,"路径有误，请重新输入 工作空间路径 ：",false);
		System.out.println("工作空间路径："+workSpacePath+"\n");

		if (StringUtils.isBlank(changeFileListPath)) {
			System.out.println("\n不能直接获取到存放改动文件列表的文件路径");
			System.out.println("请输入 存放改动文件列表的文件路径(输入Y则为工作空间下选择txt文件) ：");
			changeFileListPath = sc.next();
			if ("Y".equalsIgnoreCase(changeFileListPath)) {
				List<String> txtPath = null;
				txtPath = MyFileUtils.getTxtPath(workSpacePath);
				int txtPathSize = txtPath.size();
				if (txtPathSize > 0) {
					for (int i = 0;i < txtPathSize;i++) {
						System.out.println((i+1) + "."+txtPath.get(i).replace(workSpacePath,""));
					}
					System.out.println("\n请输入编号(如果都不符合条件 则输入Y)：");
					changeFileListPath = sc.next();
					if ("Y".equalsIgnoreCase(changeFileListPath)) {
						System.out.println("\n请输入 存放改动文件列表的文件路径：");
						changeFileListPath = sc.next();
					} else {
						int index = NumberUtils.parseInt(changeFileListPath,-1);
						if (index <= 0 || index > txtPathSize) {
							System.out.println("\n编号错误,请直接输入 存放改动文件列表的文件路径：");
							changeFileListPath = sc.next();
						} else {
							changeFileListPath = txtPath.get(index-1);
						}
					}
				} else {
					System.out.println("\n没找到.txt文件！请输入 存放改动文件列表的文件路径：");
					changeFileListPath = sc.next();
				}
			}
		}
		changeFileListPath = checkScString(changeFileListPath, sc,"路径有误，请重新输入 存放改动文件列表的文件路径 ：",true);
		//changeFileListPath = changeFileListPath.replace("\\","/");
		System.out.println("存放改动文件列表的文件路径："+changeFileListPath+"\n");
		fileOutputPath = checkScString(fileOutputPath, sc,"路径有误，请重新输入 文件输出路径 ：",false);
		//fileOutputPath += "/"+nowDateTime();
		System.out.println("文件输出路径="+fileOutputPath+"\n");

		if (!hasNeedJavaFileField) {
			System.out.println("不能直接判断是否需要.java文件");
			System.out.println("是否需要.java文件(Y/N) ：");
			if ("Y".equalsIgnoreCase(sc.next().trim())) {
				isNeedJavaFile = true;
			}
		}
		System.out.println("是否需要.java文件:"+(isNeedJavaFile == true ? "是":"否")+"\n");
	}

	private static String checkScString(String str, Scanner sc,String message,boolean isFile) {
		str = str.trim();
		while(
				(SystemUtils.isWindows() && str.indexOf(":") != 1)
						|| (SystemUtils.isMacOs() && !str.startsWith("/User"))
						|| (isFile && !str.endsWith(".txt"))) {
			System.out.println(message);
			str = sc.next();
		}
		str = str.replace("\\","/");
		if (str.endsWith("/")) {
			str = str.substring(0,str.length() - 1);
		}
		return str;
	}

	/*private static String nowDateTime(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		return formatter.format(calendar.getTime());
	}*/

	private static void copyFileByPaths() {
		StringBuilder successPath = new StringBuilder();
		StringBuilder failPath = new StringBuilder();
		try {
			Set<String> processedSet = new HashSet<>();//为了去重，而保存的set集合

			List<String> pathList = MyFileUtils.readPathList(changeFileListPath);
			for (String path : pathList) {
				try {
					path = path.trim();
					if (path.equals("")) continue;
					if (
							(SystemUtils.isWindows() && path.indexOf(":") == 1)//如果第二个为冒号，例如D: E: F:
									|| (SystemUtils.isMacOs() && path.startsWith("/User"))//如果是mac本，则是/User开头
					) {
						path = path.substring(path.indexOf(workSpacePath) + workSpacePath.length() + 1);
					}
					path = path.replace("\\", "/");

					int oldSize = processedSet.size();
					processedSet.add(path);
					if (oldSize == processedSet.size()) continue;

					if (path.endsWith(SUFFIX_JAVA)) {
						if (isNeedJavaFile) {
							FileUtils.copyFile(new File(workSpacePath + path), new File(fileOutputPath + path));
						}
						copyClassFile(path);
					} else if (path.contains(webappSrcRoot)) {
						FileUtils.copyFile(new File(workSpacePath + path), new File(fileOutputPath + path.replace(webappSrcRoot,"")));
					} else if (path.contains(resourcesSrcRoot))  {
						FileUtils.copyFile(new File(workSpacePath + path), new File(fileOutputPath + path.replace(resourcesSrcRoot,classOutputRoot)));
					} else {
						FileUtils.copyFile(new File(workSpacePath + path), new File(fileOutputPath + path));
					}
					successPath.append(path).append("\n");
				} catch (Exception e) {
					failPath.append(path).append("\n");
					e.printStackTrace();
				}
			}
			printLog2File(successPath, failPath);
			//用记事本打开文件
			Process p = Runtime.getRuntime().exec( "notepad.exe " + fileOutputPath + "/url.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void copyClassFile(String path) throws IOException {
		String moduleName = path.substring(1);
		moduleName = moduleName.substring(0, moduleName.indexOf("/"));
		String currClassSrcRoot = null;
		currClassSrcRoot = classSrcRoot.replace(MODULE_NAME_REPLACE_MIDLINE2UNDERLINE,moduleName.replace("-","_")).replace(MODULE_NAME_REPLACE,moduleName);
		moduleName = "/"+moduleName;
		String srcParentRoot = workSpacePath + path.substring(0,path.lastIndexOf("/")).replace(moduleName+javaSrcRoot,currClassSrcRoot);

		String javaName = path.substring(path.lastIndexOf("/") + 1,path.indexOf(SUFFIX_JAVA));
		File[] files = (new File(srcParentRoot)).listFiles();
		for (File currFile : files) {
			if (currFile.getName().equals(javaName + SUFFIX_CLASS)
					|| (currFile.getName().startsWith(javaName + "$") && currFile.getName().endsWith(SUFFIX_CLASS))) {
				String currSrcPath = currFile.getAbsolutePath().replace("\\", "/");
				currSrcPath = moduleName+classOutputRoot + currSrcPath.substring(currSrcPath.indexOf(currClassSrcRoot) + currClassSrcRoot.length());
				FileUtils.copyFile(currFile, new File(fileOutputPath + currSrcPath));
			}
		}
	}

	/**
	 * 打印日志到文件中
	 * @param successPath  所有成功列表路径
	 * @param failPath 所有失败列表路径
	 * @throws FileNotFoundException
	 */
	private static void printLog2File(StringBuilder successPath, StringBuilder failPath) throws IOException {
		File logFile = new File(fileOutputPath + "/url.txt");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		PrintStream ps = new PrintStream(logFile);
		System.setOut(ps);
		if (successPath != null && successPath.length() > 0) {
			System.out.print(successPath.toString());
			System.out.println();
		}
		if (failPath != null && failPath.length() > 0) {
			System.out.println();
			System.out.println("--------------下面的为--- 失败列表:--------------------------------:");
			System.out.print(failPath.toString());
			System.out.println();
		}
		ps.close();
	}

}