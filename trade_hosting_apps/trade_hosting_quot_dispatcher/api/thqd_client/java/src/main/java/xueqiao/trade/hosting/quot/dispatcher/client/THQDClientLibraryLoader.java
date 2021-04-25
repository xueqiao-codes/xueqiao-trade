package xueqiao.trade.hosting.quot.dispatcher.client;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class THQDClientLibraryLoader {
private static boolean isInited = false;
	
	public static void init() {
		if (!isInited) {
			synchronized(THQDClientLibraryLoader.class) {
				if (!isInited) {
					try {
						loadLib();
						isInited = true;
					} catch (IOException e) {
						e.printStackTrace();
						Runtime.getRuntime().exit(1);
					}
				}
			}
		}
	}
	
	private static void loadLib() throws IOException 
    {  
        String libFullName = "libTHQDClient_java.so";  
        InputStream in = null;  
        BufferedInputStream reader = null;  
        FileOutputStream writer = null;  

        File extractedLibFile = File.createTempFile("libTHQDClient_java",".so"); 
        try { 
            in = THQDClientLibraryLoader.class.getResourceAsStream("/linux/" + libFullName);  
            reader = new BufferedInputStream(in);  
            writer = new FileOutputStream(extractedLibFile);  
            byte[] buffer = new byte[1024];  
            while (reader.read(buffer) > 0){  
                writer.write(buffer);  
                buffer = new byte[1024];
            }  
            writer.flush();
        } catch (IOException e){  
            e.printStackTrace();  
        } finally {  
            if(in!=null)  
                in.close();  
            if(writer!=null)  
                writer.close();  
        }
        System.out.println("loading " + extractedLibFile.toString());
        System.load(extractedLibFile.toString());

        if (extractedLibFile.exists() && StringUtils.isEmpty(System.getenv("XQ_RESERVE_SO"))) {
            extractedLibFile.delete();
        }
    }  
}
