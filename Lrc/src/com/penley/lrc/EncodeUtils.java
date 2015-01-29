package com.penley.lrc;

import java.io.*;
import java.util.Properties;

/**
 * Created by 李攀 on 2015/1/29.
 */
public class EncodeUtils {
    /**
     * 获取文件编码格式　错误高，不完全
     * @param in
     * @return
     */
    public String getEncodeType(InputStream in) {
        Properties prop = new Properties();

        String code = null;
        byte[] head = new byte[3];
        try {
            in.read(head);
            prop.load(in);
            String value = prop.getProperty("encoding");
            System.out.println("value = " + value);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(head[0] + ":" + head[1] + ":" + head[2]);

        if (head[0] == -1 && head[1] == -2)
            code = "UTF-16";
        if (head[0] == -2 && head[1] == -1)
            code = "Unicode";
        if (head[0] == -17 && head[1] == -69 && head[2] == -65)
            code = "UTF-8";
        if (head[0] == 83 && head[1] == 116 && head[2] == 114)
            code = "Big5";// or shift js

        return code;
    }

    /**
     * 获取文件编码格式，常见中英文编码格式也不完全，准确率一般
     * @param sourceFile
     * @return
     */
    private static String getFileCharset(File sourceFile) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset; //文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF
                    && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; //文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; //文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; //文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

    public static void main(String[] args) {
        File file = new File("F://test.txt");
        System.out.println(getFileCharset(file));
//        try {
//            System.out.println(new EncodeUtils().getEncodeType(new FileInputStream(file)));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
