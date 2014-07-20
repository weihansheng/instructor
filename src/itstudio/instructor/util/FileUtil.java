package itstudio.instructor.util;

import java.io.File;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * 文件工具类
 * 
 */
public class FileUtil {

	/**
	 * 检验SDcard状态
	 * 
	 * @return boolean
	 */
	public static boolean checkSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static double getDirSize(File file) {     
        //判断文件是否存在     
        if (file.exists()) {     
            //如果是目录则递归计算其内容的总大小    
            if (file.isDirectory()) {     
                File[] children = file.listFiles();     
                double size = 0;     
                for (File f : children)     
                    size += getDirSize(f);     
                return size;     
            } else {//如果是文件则直接返回其大小,以“兆”为单位   
                double size = (double) file.length() / 1024 / 1024;        
                return size;     
            }     
        } else {     
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");     
            return 0.0;     
        }     
    }
	
	// 新建文件夹
	public static boolean  makeDir(String path){
		
		if(!checkSDCard()){
			
			return false;
		}
		File file = new File(path);
		if(!file.exists()){
			
			file.mkdirs();// 创建文件夹
		}
		return true;
	}
}
