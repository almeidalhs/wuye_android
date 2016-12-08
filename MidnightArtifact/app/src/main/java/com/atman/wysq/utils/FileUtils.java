package com.atman.wysq.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {
	
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/wysq/image/";
	public static String SDPATH_FILE = Environment.getExternalStorageDirectory()
			+ "/wysq/file/";
	public static String SDPATH_FILE_BOOT = Environment.getExternalStorageDirectory()
			+ "/wysq/";

	public static void saveBitmap(Bitmap bm, String picName) {
		Log.e("", "保存图片");
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			Log.e("", "已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String convertCodeAndGetText(String fileName) {//

		File file = new File(SDPATH_FILE + fileName);
		BufferedReader reader;
		String text = "";
		try {

			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream in = new BufferedInputStream(fis);
			in.mark(4);
			byte[] first3bytes = new byte[3];
			in.read(first3bytes);
			in.reset();
			if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
					&& first3bytes[2] == (byte) 0xBF) {// utf-8

				reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

			} else if (first3bytes[0] == (byte) 0xFF
					&& first3bytes[1] == (byte) 0xFE) {

				reader = new BufferedReader(
						new InputStreamReader(in, "unicode"));
			} else if (first3bytes[0] == (byte) 0xFE
					&& first3bytes[1] == (byte) 0xFF) {

				reader = new BufferedReader(new InputStreamReader(in,
						"utf-16be"));
			} else if (first3bytes[0] == (byte) 0xFF
					&& first3bytes[1] == (byte) 0xFF) {

				reader = new BufferedReader(new InputStreamReader(in,
						"utf-16le"));
			} else {

				reader = new BufferedReader(new InputStreamReader(in, "GBK"));
			}
			String str = reader.readLine();

			while (str != null) {
				text = text + str + "\n";
				str = reader.readLine();

			}
			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * 读取SD卡中文本文件
	 *
	 * @param fileName
	 * @return
	 */
	public static String readSDFile(String fileName) {
		StringBuffer sb = new StringBuffer();
		File file = new File(SDPATH_FILE + fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			int c;
			while ((c = fis.read()) != -1) {
				sb.append((char) c);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static File createSDFileDir(String dirName) throws IOException {
		File dir = new File(SDPATH_FILE + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdirs());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除�?��文件
			else if (file.isDirectory())
				deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

}
