package com.weiweidounai.ganleme;

import android.content.Context;

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
        StringBuffer sBuffer = new StringBuffer();
            //��֤��һ��û���ļ�ʱ��������
        try{
            FileInputStream fis = mContext.openFileInput(filename);
            DataInputStream dataIO = new DataInputStream(fis);
            String strLine = null;
            while((strLine =  dataIO.readLine()) != null) {
                String[] olddata = strLine.split(",");
                if(filedate[0].equals(olddata[0]))
                {
                    sBuffer.append(filecontent + "\n");
                    isIn=true;
                }
                else
                    sBuffer.append(strLine + "\n");
            }
            dataIO.close();
            fis.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(!isIn)
            sBuffer.append(filecontent + "\n");
        this.save(filename, sBuffer.toString());
    }

    /*
    * �õ�ָ��plan��ֵ
    * */
    public int getPanComplete(int pos,String filename){
        int re =0;
        try{
            FileInputStream fis = mContext.openFileInput(filename);
            DataInputStream dataIO = new DataInputStream(fis);
            String strLine = null;
            while((strLine =  dataIO.readLine()) != null) {
                String[] olddata = strLine.split(",");
                if(String.valueOf(pos).equals(olddata[0]))
                {
                    re=Integer.parseInt(olddata[1]);
                    break;
                }
            }
            dataIO.close();
            fis.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return re;
    }
}
