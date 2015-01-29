package com.penley.lrc;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * 此类用来解析LRC文件
 * 
 */
public class LrcParser {
    private LrcInfo lrcinfo = new LrcInfo();
    //存放临时时间
    private long currentTime = 0;
    //存放临时歌词
    private String currentContent = null;
    //用于保存时间点和歌词之间的对应关系
    //private Map<Long,String> maps =new HashMap<Long,String>();
    private Map<Long, String> maps = new TreeMap<Long, String>();

    public static void main(String[] args) {
        LrcInfo parser = null;
        try {
            parser = new LrcParser().parser("F://b.lrc");
            System.out.println("parser.toString() = " + parser.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new SoutLrcThread(parser).start();
    }

    static class SoutLrcThread extends Thread {
        private LrcInfo lrcInfo;
        private long curTime = 0;
        private final Map<Long, String> infos;
        int counts = 0;

        boolean isRun = true;
        int isRuns = 0;

        SoutLrcThread(LrcInfo lrcInfo) {
            this.lrcInfo = lrcInfo;
            infos = lrcInfo.getInfos();
            counts = infos.size();
        }

        @Override
        public void run() {
            super.run();
            while (isRuns < counts) {
//                System.out.print("*");
                if (infos.get(curTime) != null) {
                    System.out.print(isRuns + ":" + curTime);
                    System.out.print(infos.get(curTime));
                    isRuns++;
                    System.out.println();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                curTime += 10;
            }
        }
    }


    /*
     * 根据文件路径，读取文件，返回一个输入流
     * @param	path	文件路径
     * @return InputStream 文件输入流
     * @throws FileNotFoundException
     * */
    private InputStream readLrcFile(String path) throws FileNotFoundException {
        File f = new File(path);
        InputStream ins = new FileInputStream(f);
        return ins;
    }

    public LrcInfo parser(String path) throws Exception {
        InputStream in = readLrcFile(path);
        lrcinfo = parser(in);
        return lrcinfo;
    }

    /**
     * @param inputStream 输入流
     *                    if necessary get the encode type
     * @return
     */
    public LrcInfo parser(InputStream inputStream) throws IOException {
        //包装对象
        InputStreamReader inr = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inr);
        //一行一行的读，每读一行解析一行
        String line = null;
        while ((line = reader.readLine()) != null) {
            parserLine(line);
        }
        //全部解析完后，设置info
        lrcinfo.setInfos(maps);
        Iterator<Entry<Long, String>> iter = maps.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Long, String> entry = (Entry<Long, String>) iter.next();
            Long key = entry.getKey();
            String val = entry.getValue();
        }
        return lrcinfo;
    }

    /**
     * 利用正则表达式解析每行具体语句
     * 并将解析完的信息保存到LrcInfo对象中
     *
     * @param line
     */
    private void parserLine(String line) {
        //获取歌曲名信息
        if (line.startsWith("[ti:")) {
            String title = line.substring(4, line.length() - 1);
            lrcinfo.setTitle(title);
        }
        //取得歌手信息
        else if (line.startsWith("[ar:")) {
            String artist = line.substring(4, line.length() - 1);
            lrcinfo.setArtist(artist);
        }
        //取得专辑信息
        else if (line.startsWith("[al:")) {
            String album = line.substring(4, line.length() - 1);
            lrcinfo.setAlbum(album);
        }
        //取得歌词制作者
        else if (line.startsWith("[by:")) {
            String bysomebody = line.substring(4, line.length() - 1);
            lrcinfo.setBySomeBody(bysomebody);
        }
        //通过正则表达式取得每句歌词信息
        else {
            //设置正则表达式
            String reg = "\\[(\\d{1,2}:\\d{1,2}\\.\\d{1,2})\\]|\\[(\\d{1,2}:\\d{1,2})\\]";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(line);
            //如果存在匹配项则执行如下操作
            while (matcher.find()) {
                //得到匹配的内容
                String msg = matcher.group();
                //得到这个匹配项开始的索引
                int start = matcher.start();
                //得到这个匹配项结束的索引
                int end = matcher.end();
                //得到这个匹配项中的数组
                int groupCount = matcher.groupCount();
                for (int index = 0; index < groupCount; index++) {
                    String timeStr = matcher.group(index);
                    if (index == 0) {
                        //将第二组中的内容设置为当前的一个时间点
                        currentTime = str2Long(timeStr.substring(1, timeStr.length() - 1));
                    }
                }
                //得到时间点后的内容
                String[] content = pattern.split(line);
                //for(int index =0; index<content.length; index++){
                //if(index==content.length-1){
                //将内容设置魏当前内容
                currentContent = content[content.length - 1];
                //}
                //}
                //设置时间点和内容的映射
                maps.put(currentTime, currentContent);
                //遍历map
            }
        }
    }

    private long str2Long(String timeStr) {
        //将时间格式为xx:xx.xx，返回的long要求以毫秒为单位
        String[] s = timeStr.split("\\:");
        int min = Integer.parseInt(s[0]);
        int sec = 0;
        int mill = 0;
        if (s[1].contains(".")) {
            String[] ss = s[1].split("\\.");
            sec = Integer.parseInt(ss[0]);
            mill = Integer.parseInt(ss[1]);
        } else {
            sec = Integer.parseInt(s[1]);
        }
        return min * 60 * 1000 + sec * 1000 + mill * 10;
    }
}
