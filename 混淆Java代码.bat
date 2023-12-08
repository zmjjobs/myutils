D:
cd D:\MyServer\Allatori-7.3-HunXiaoJavaDaiMa\tutorial\step01\files
copy D:\ideaWorkSpace\CopyFile\out\artifacts\CopyFile_jar\CopyFile.jar .\ /y
timeout /T 15
start RunAllatori.bat
timeout /T 15
copy obf-CopyFile.jar D:\ideaWorkSpace\CopyFile\iss-copy-file.jar /y
pause