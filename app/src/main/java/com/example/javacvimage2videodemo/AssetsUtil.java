package com.example.javacvimage2videodemo;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetsUtil {
    /**
     *  从assets目录中复制整个文件夹内容,考贝到 /data/data/包名/files/目录中
     *  @param  activity  activity 使用CopyFiles类的Activity
     *  @param  fromPath  String  文件路径,如：/assets/aa
     */
    public static void copyAssetsDir2Phone(Activity activity, String fromPath,String toPath){
        try {
            String[] fileList = activity.getAssets().list(fromPath);
            if(fileList.length>0) {//如果是目录
                File file=new File(toPath);//new File(activity.getFilesDir().getAbsolutePath()+ File.separator+fromPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName:fileList){
                    fromPath=fromPath+File.separator+fileName;

                    copyAssetsDir2Phone(activity,fromPath,toPath);

                    fromPath=fromPath.substring(0,fromPath.lastIndexOf(File.separator));
                    Log.e("oldPath",fromPath);
                }
            } else {//如果是文件
                toPath = toPath + File.separator + new File(fromPath).getName();

                InputStream inputStream=activity.getAssets().open(fromPath);
                File file=new File(toPath);//new File(activity.getFilesDir().getAbsolutePath()+ File.separator+fromPath);
                Log.i("copyAssets2Phone","file:"+file);
                if(!file.exists() || file.length()==0) {
                    FileOutputStream fos=new FileOutputStream(file);
                    int len=-1;
                    byte[] buffer=new byte[1024];
                    while ((len=inputStream.read(buffer))!=-1){
                        fos.write(buffer,0,len);
                    }
                    fos.flush();
                    inputStream.close();
                    fos.close();
                    showToast(activity,"模型文件复制完毕");
                } else {
                    showToast(activity,"模型文件已存在，无需复制");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void copyAssetsDir2Sdcard(Activity activity, String fromPath,String toPath){
        try {
            String[] fileList = activity.getAssets().list(fromPath);
            Log.d("ddebug"," copyAssetsDir2Sdcard  fileList.length = " + fileList.length);
            Log.d("ddebug","  copyAssetsDir2Sdcard toPath= " + toPath);
            if(fileList.length>0) {//如果是目录
                File file=new File(toPath);//new File(activity.getFilesDir().getAbsolutePath()+ File.separator+fromPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName:fileList){
                    fileName=fromPath+File.separator+fileName;
                    copyAssetsFile2Phone(activity,fileName,toPath);
                }
                Log.d("ddebug","文件全部复制完毕 --- " + toPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将文件从assets目录，考贝到 /data/data/包名/files/ 目录中。assets 目录中的文件，会不经压缩打包至APK包中，使用时还应从apk包中导出来
     * @param fileName 文件名,如aaa.txt
     */
    public static void copyAssetsFile2Phone(Activity activity, String fileName,String toPath){

        try {
            InputStream inputStream = activity.getAssets().open(fileName);
            toPath = toPath + File.separator + fileName;
            Log.d("ddebug","  fileName = " + fileName + "---toPath = " + toPath);
            String dirPath = toPath.substring(0,toPath.lastIndexOf(File.separator));
            File dir = new File(dirPath);
            if(!dir.exists()){
                dir.mkdirs();
                Log.d("ddebug","目录不存在已创建 " + dirPath);
            }
            //getFilesDir() 获得当前APP的安装路径 /data/data/包名/files 目录
            File file = new File(toPath);//new File(activity.getFilesDir().getAbsolutePath() + File.separator + fileName);
            if(!file.exists() || file.length()==0) {
                file.createNewFile();
                FileOutputStream fos =new FileOutputStream(file);//如果文件不存在，FileOutputStream会自动创建文件
                int len=-1;
                byte[] buffer = new byte[1024];
                while ((len=inputStream.read(buffer))!=-1){
                    fos.write(buffer,0,len);
                }
                fos.flush();//刷新缓存区
                inputStream.close();
                fos.close();
                showToast(activity,"模型文件复制完毕 --- " + toPath);
            } else {
                showToast(activity,"模型文件已存在，无需复制");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void showToast(Activity activity, String msg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
