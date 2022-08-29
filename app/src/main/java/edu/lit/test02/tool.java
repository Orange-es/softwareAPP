package edu.lit.test02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tool {

    //Unicode解码
    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }



    //    判断字符串是中文还是英文
    //    缺点：只能检测出中文汉字不能检测中文标点；
    //    优点：利用正则效率高；
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    //截取某个字符之前的字符串
    public static String substringBefore(String s,String b){
        String str1 = s.substring(0, s.indexOf(b));
        //System.out.println("截取"+b+"之前字符串:"+str1);
        return str1;
    }

    //java截取某个字符之后的字符串
    public static String substringAfter(String s,String b){
        String str1 = s.substring(0, s.indexOf(b));
        String str2 = s.substring(str1.length()+1, s.length());
        //System.out.println("截取"+b+"之后字符串:"+str2);
        return str2;
    }

    //获取src输入的参数
    public static String getSrc(String s){
        //该子字符串从指定索引处的字符开始，直到此字符串末尾
//        String s1 = s.substring(38);
//        System.out.println(s1);
        //上面的方法不严谨 直接截取[之后的
        String s1 = substringAfter(s,"[");
        System.out.println("截取[之后：----->:"+s1);
        //截取逗号之前的
        String s2 = substringBefore(s1, ",");
        System.out.println("截取逗号之前的----->:"+s2);
        //截取：之后
        String s3 = substringAfter(s2, ":");
        System.out.println("截取：之后----->:"+s3);
        String s4 = s3.replace('"', ' ').trim();
        System.out.println("src----->:"+s4);
        return s4;
    }

    //获取返回的dst
    public static String getDst(String s){
        //该子字符串从指定索引处的字符开始，直到此字符串末尾
        String s1 = s.substring(38);
        //System.out.println(s1);

        //截取逗号之后的
        String s2 = substringAfter(s1, ",");
        //System.out.println(s2);
        //截取：之后
        String s3 = substringAfter(s2, ":");
        //System.out.println(s3);
        //截取}之前
        String s4 = substringBefore(s3, "}");
        System.out.println(s4);
        String s5 = s4.replace('"', ' ').trim();
        return s5;
    }

    //设置的字符串
    public static String taskStr(String s){
        String src = getSrc(s);
        String dst = getDst(s);
        System.out.println("src:"+src);
        System.out.println("dst:"+dst);
        String re = "IN："+src+"\nOUT："+dst;
        return re;
    }


    //get请求
    public  static String get(String url) throws Exception {
        String content = null;
        URLConnection urlConnection = new URL(url).openConnection();
        HttpURLConnection connection = (HttpURLConnection) urlConnection;
        connection.setRequestMethod("GET");
        //连接
        connection.connect();
        //得到响应码
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                    (connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder bs = new StringBuilder();
            String l;
            while ((l = bufferedReader.readLine()) != null) {
                bs.append(l).append("\n");
            }
            content = bs.toString();
        }
        return content;
    }

    public static String getDate(){
        Date date=new Date();//此时date为当前的时间
//        System.out.println(date);
        SimpleDateFormat dateFormat= null;//设置当前时间的格式，为年-月-日
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        }
        String s = dateFormat.format(date);
        return s;

    }

    public static String getEn(String s){
        String en ;
        String ch ;
        System.out.println(s);
        String q = "\"";
        s = s.replaceAll(q,"");
        String en1 = substringAfter(s, "ent");
        String en2 = substringAfter(en1, "t");
        System.out.println("en2:"+en2);
        String en3 = substringBefore(en2,"note");
        en3 = substringAfter(en3,":");
        en3 = en3.replaceAll(",","");
        en = en3;
        System.out.println(en);
        return en+".";
    }

    public static String getCh(String s){
        String ch;
        ch = substringAfter(s,"note");
//        System.out.println(ch);
        ch = substringAfter(ch,"e");
//        System.out.println(ch);
        ch = substringBefore(ch,",");
//        System.out.println(ch);
        ch = substringAfter(ch,":");
        ch = ch.replaceAll("\"","");
        System.out.println(ch);
        return ch;
    }

}
