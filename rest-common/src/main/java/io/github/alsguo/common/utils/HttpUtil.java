package io.github.alsguo.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http://blog.csdn.net/iijse/article/details/6201101
 */
public class HttpUtil {
	
	static ExecutorService  pool = Executors.newFixedThreadPool(5) ;
	static final long ONE_MB = 1024 * 1024;
	
	public static String get(String urlName) { 
		System.out.println("get:"+urlName);
		 String result = null;  
	        BufferedReader in = null;  
	        try {  
//	            String urlName = url + "?" + param;  
	            URL realUrl = new URL(urlName);  
	            // 打开和URL之间的连接  
	            URLConnection conn = realUrl.openConnection();  
	            // 设置通用的请求属性  
	            conn.setRequestProperty("accept", "*/*");  
	            conn.setRequestProperty("connection", "Keep-Alive");  
	            conn.setRequestProperty("user-agent",  
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
	            // 建立实际的连接  
	            conn.connect();  
//	            // 获取所有响应头字段  
//	            Map<String, List<String>> map = conn.getHeaderFields();  
	           
	            // 定义BufferedReader输入流来读取URL的响应  
	            in = new BufferedReader(  
	                    new InputStreamReader(conn.getInputStream())); 
	            String line="";  
	            String content = "";
	            while ((line = in.readLine()) != null) {  
	            	content +=   line+"\n";  
	            }  
	            result = content;
	        } catch (Exception e) {  
	        		System.out.println("error:"+urlName);
	            System.out.println("发送GET请求出现异常！" + e);  
	            return null;
	          
	        }  
	        // 使用finally块来关闭输入流  
	        finally {  
	            try {  
	                if (in != null) {  
	                    in.close();  
	                }  
	            } catch (IOException ex) {  
	            	 return null;
	            }  
	        }  
	        return result;  
	}
	
	public static String get(final String url, final String param) {  
       return get(url+"?"+param);
    }  
	
	public static void getNotify(final String url,final String param){
		pool.execute(new Runnable(){

			public void run() {
				get(url,param);
				
			}
			
		});
	}
	
	public static void rwBytesNotify(final String srcpath,final String decpath){
		
		pool.submit(new Runnable(){

			public void run() {
				rwBytesByUrl(srcpath,decpath);
			}
			
		});
	}
	
	public static byte[] rwBytesByUrl(String srcpath,String decpath){
		BufferedInputStream in = null;
		byte[] result = null;
		try {  
            URL realUrl = new URL(srcpath);  
            URLConnection conn = realUrl.openConnection();  
            //设置请求头信息
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
            conn.connect(); // 建立实际的连接  
//	        Map<String, List<String>> map = conn.getHeaderFields();   // 获取所有响应头字段  
            in = new BufferedInputStream(conn.getInputStream()); 
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);    
            
            byte[] temp = new byte[1024];        
            int size = 0;        
            while ((size = in.read(temp)) != -1) {        
               out.write(temp, 0, size);        
            }        
            result = out.toByteArray();  
            out.close();
			System.out.println("Read >> " + srcpath + " Success ! >>  Byte Length >> " + result.length);
			writeBytes(decpath, result);
        } catch (Exception e) {  
            System.out.println("Read >> " +srcpath +" >> Exception >> "+ e.getMessage());  
        }  finally {
        	try {
        		if(in!=null)
        			in.close();  
			} catch (Exception ex) {
				
			}
		}
		return result;
	}
	
	public static void writeBytes(String filepath,byte [] result){
		int lastIdx = filepath.lastIndexOf("/");
		String dirs = filepath.substring(0,lastIdx);
		String filename = filepath.substring(lastIdx+1);
		
		File file = new File(dirs);
		if(!file.exists()) 
			file.mkdirs();
		file =new File(dirs + "/"+filename);
		
		BufferedOutputStream out = null;		
		try {
			out = new BufferedOutputStream(new FileOutputStream(file)) ;
			int len = result.length;
			int n = Math.round(len/ONE_MB);
			int s = 0,e = 1024 * 1024;
			int remainderN = len % n;
			
			if(n>0){
 				for (int i = 0; i < n; i++) {
 					out.write(result,s,e);
					s = e;
					e = e + e;
					if (i == (n - 2) && remainderN > 0) e = len;
 				}
			}else{
				out.write(result,s,result.length);
			}
			System.out.println("Write " + filepath + " Success!");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				out.flush();
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	

	public static byte[] getBytes(String srcpath){
		BufferedInputStream in = null;
		 byte[] result = null;
		 try {  
             URL realUrl = new URL(srcpath);  
             URLConnection conn = realUrl.openConnection();  
             //设置请求头信息
             conn.setRequestProperty("accept", "*/*");  
             conn.setRequestProperty("connection", "Keep-Alive");  
             conn.setRequestProperty("user-agent",  "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
             conn.connect(); // 建立实际的连接  
//	         Map<String, List<String>> map = conn.getHeaderFields();   // 获取所有响应头字段  
             in = new BufferedInputStream(conn.getInputStream()); 
             ByteArrayOutputStream out = new ByteArrayOutputStream(1024);    
             
             byte[] temp = new byte[1024];        
             int size = 0;        
             while ((size = in.read(temp)) != -1) {        
                out.write(temp, 0, size);        
             }        
             result = out.toByteArray();  
             out.close();
			 System.out.println("Read >> " + srcpath + " Success ! >>  Byte Length >> " + result.length);
        } catch (Exception e) {  
            System.out.println("Read >> " +srcpath +" >> Exception >> "+ e.getMessage());  
        }  finally {
        	try {
        		if(in!=null)
        			in.close();  
			} catch (Exception ex) {
				
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		String srcpath = "http://www.bettbio.com/static/bett8351091463981063781/PRODUCT/5160526172051032/8465271468370467679.jpg";
		String decpath = "F:/files/static/bett8351091463981063781/PRODUCT/5160526172051032/8465271468370467679.jpg";
//		byte [] result = getByte(srcpath);
//		writeBytes(decpath, result);
//		getBytes("www.bettbio.com/static/bett8351091463981063781/PRODUCT/5160526172051032/8465271468370467679.jpg");
		rwBytesByUrl(srcpath, decpath);
	}
}
