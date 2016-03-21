package com.weiweidounai.ganleme;

import android.content.Context;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * Created by haha on 2016/3/9.
 */
public class FileHelper {
    private Context mContext;

    public FileHelper() {
    }

    public FileHelper(Context mContext) {
        super();
        this.mContext = mContext;
    }

    /*
    * 这里定义的是一个文件保存的方法，写入到文件中，所以是输出流
    * */
    public void save(String filename, String filecontent) throws Exception {
        //这里我们使用私有模式,创建出来的文件只能被本应用访问,还会覆盖原文件哦
        FileOutputStream output = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
        output.write(filecontent.getBytes());  //将String字符串以字节流的形式写入到输出流中
        output.close();         //关闭输出流
    }


    /*
    * 这里定义的是文件读取的方法
    * */
    public String read(String filename) throws IOException {
        //打开文件输入流
        FileInputStream input = mContext.openFileInput(filename);
        byte[] temp = new byte[1024];
        StringBuilder sb = new StringBuilder("");
        int len = 0;
        //读取文件内容:
        while ((len = input.read(temp)) > 0) {
            sb.append(new String(temp, 0, len));
        }
        //关闭输入流
        input.close();
        return sb.toString();
    }
    /*
    * plan数据保存方法--文件保存
    * */
    public void savePlanData(String filename,String filecontent)throws Exception {
        //替换原有的值
        String[] filedate = filecontent.split(",");
        boolean isIn = false;
        String[] plansDataInFile = this.getFileStrings(filename);
        StringBuffer sBuffer = new StringBuffer();
        if(plansDataInFile!=null){
            for(int i=0;i<plansDataInFile.length;i++){
                String[] olddata = plansDataInFile[i].split(",");
                if(filedate[0].equals(olddata[0])){
                    sBuffer.append(filecontent + "\n");
                    isIn=true;
                }else
                    sBuffer.append(plansDataInFile[i] + "\n");
            }
        }
        if(!isIn)
            sBuffer.append(filecontent + "\n");
        String re = sBuffer.toString();
        re=re.substring(0,re.length()-1);
        this.save(filename, re);
    }

    /*
    * 得到指定plan的值
    * */
    public int getPanComplete(String planType,String filename){
        int re =0;
        String[] plansDataInFile = this.getFileStrings(filename);
        if(plansDataInFile!=null){
            for(int i=0;i<plansDataInFile.length;i++){
                String[] olddata = plansDataInFile[i].split(",");
                if(planType.equals(olddata[0])){
                    re=Integer.parseInt(olddata[1]);
                    break;
                }
            }
        }
        return re;
    }

    /*
    * get size of plans
    * */
    public int getPlanSize(String filename){
        int re =0;
        String[] plans=this.getFileStrings(filename);
        if(plans!=null&&!"".equals(plans[0]))
            re=plans.length;
        return re;
    }
     /*
    * save plans
    * */
    public void savePlans(String filename,String plan){
        String[] plansInFile = this.getFileStrings(filename);
        StringBuffer plans = new StringBuffer();
        plans.append(plan);
        if(plansInFile!=null){
            for(int i=0;i<plansInFile.length;i++){
                if(plan.equals(plansInFile[i])){
                    Toast.makeText(mContext, "习惯:"+plan+"  已经存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                plans.append("\n"+plansInFile[i]);
            }
        }
        try {
                this.save(filename, plans.toString());
         } catch (Exception e) {
                e.printStackTrace();
          }
        Toast.makeText(mContext, "习惯： "+plan+"  保存成功", Toast.LENGTH_SHORT).show();
    }
    /*
    * delete plan
    * */
    public void deletePlan(String filename,String plan){

        try{
            String plans = this.read(filename);
            if(plans!=null&&(plans.indexOf("\n"+plan)>-1||plans.indexOf(plan+"\n")>-1)){
                this.save(filename,plans.replace("\n"+plan,"").replace(plan+"\n",""));
                Toast.makeText(mContext, "习惯： "+plan+"  删除成功", Toast.LENGTH_SHORT).show();
                return;
            }
            if(plans!=null&&plans.indexOf("\n")<0&&plan.equals(plans)){
                this.save(filename,"");
                Toast.makeText(mContext, "习惯： "+plan+"  删除成功", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(mContext, "习惯： "+plan+"  不存在", Toast.LENGTH_SHORT).show();


    }

    public String[] getFileStrings(String filename){
        String[] re =null;
        try {
            re=this.read(filename).split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return re;
    }
}
