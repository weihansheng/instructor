
package itstudio.instructor.image.dispose;

import itstudio.instructor.util.Base64Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**   
 * @Description  
 * @author MR.Wang  
 * @date 2014-7-15 上午9:41:52 
 * @version V1.0   
 */
public class CompressionImage {
	
	public  static final String FILE_PATH = "/Android/data/test"; 
	private  final String SDCARD_ERROR = "sd不存在";
	
	public String getPhoto(String allPath){
		
		Bitmap camorabitmap = BitmapFactory.decodeFile(allPath); 
		if(null != camorabitmap ){
			
		    // 下面这两句是对图片按照一定的比例缩放，这样就可以完美地显示出来。
		    int scale = ImageThumbnail.reckonThumbnail(camorabitmap.getWidth(),camorabitmap.getHeight(), 500, 600);   
		    Bitmap bitmap = ImageThumbnail.PicZoom(camorabitmap, camorabitmap.getWidth() / scale,camorabitmap.getHeight() / scale);  
		    //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常  
		    camorabitmap.recycle();  
		    
			String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					
					return SDCARD_ERROR;
			}
			FileOutputStream fileOutputStream = null;
			File file = new File(Environment.getExternalStorageDirectory()+FILE_PATH);
			if(!file.exists()){
				
				file.mkdirs();// 创建文件夹
			}
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String str = df.format(new Date());// new Date()为获取当前系统时
			str = Base64Util. Base64Docode(str);
			
			String fileName = "thum"+allPath; // 小图地址名字
			String result = fileName;
			
			try {
					fileOutputStream = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);// 把数据写入文件
			} 
			catch (FileNotFoundException e) {
					e.printStackTrace();
					result = SDCARD_ERROR; 
				} finally {
					try {
						fileOutputStream.flush();
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			return result;
		}
		return SDCARD_ERROR;

	}
}
