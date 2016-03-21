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
    * ���ﶨ�����һ���ļ�����ķ�����д�뵽�ļ��У������������
    * */
    public void save(String filename, String filecontent) throws Exception {
        //��������ʹ��˽��ģʽ,�����������ļ�ֻ�ܱ���Ӧ�÷���,���Ḳ��ԭ�ļ�Ŷ
        FileOutputStream output = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
        output.write(filecontent.getBytes());  //��String�ַ������ֽ�������ʽд�뵽�������
        output.close();         //�ر������
    }


    /*
    * ���ﶨ������ļ���ȡ�ķ���
    * */
    public String read(String filename) throws IOException {
        //���ļ�������
        FileInputStream input = mContext.openFileInput(filename);
        byte[] temp = new byte[1024];
        StringBuilder sb = new StringBuilder("");
        int len = 0;
        //��ȡ�ļ�����:
        while ((len = input.read(temp)) > 0) {
            sb.append(new String(temp, 0, len));
        }
        //�ر�������
        input.close();
        return sb.toString();
    }
    /*
    * plan���ݱ��淽��--�ļ�����
    * */
    public void savePlanData(String filename,String filecontent)throws Exception {
        //�滻ԭ�е�ֵ
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
    * �õ�ָ��plan��ֵ
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
                    Toast.makeText(mContext, "ϰ��:"+plan+"  �Ѿ�����", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(mContext, "ϰ�ߣ� "+plan+"  ����ɹ�", Toast.LENGTH_SHORT).show();
    }
    /*
    * delete plan
    * */
    public void deletePlan(String filename,String plan){

        try{
            String plans = this.read(filename);
            if(plans!=null&&(plans.indexOf("\n"+plan)>-1||plans.indexOf(plan+"\n")>-1)){
                this.save(filename,plans.replace("\n"+plan,"").replace(plan+"\n",""));
                Toast.makeText(mContext, "ϰ�ߣ� "+plan+"  ɾ���ɹ�", Toast.LENGTH_SHORT).show();
                return;
            }
            if(plans!=null&&plans.indexOf("\n")<0&&plan.equals(plans)){
                this.save(filename,"");
                Toast.makeText(mContext, "ϰ�ߣ� "+plan+"  ɾ���ɹ�", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(mContext, "ϰ�ߣ� "+plan+"  ������", Toast.LENGTH_SHORT).show();


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
