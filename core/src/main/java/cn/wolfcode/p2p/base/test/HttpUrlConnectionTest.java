package cn.wolfcode.p2p.base.test;

import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by WangZhe on 2018/7/22.
 */
public class HttpUrlConnectionTest {
    /*public static void main(String[] args) {
        try {
            URL url = new URL("https://way.jd.com/turing/turing");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            StringBuilder param = new StringBuilder(50);
            //?info=体育新闻&loc=北京市海淀区信息路28号&userid=222&appkey=您申请的APPKEY
            param.append("info=").append("笨蛋")
                    .append("&loc=").append("湖南省长沙市")
                    .append("&userid=").append("jd_5b07542620055")
                    .append("&appkey=").append("a59d0f61abe76ba7e4d9b263308e16d2");
            conn.getOutputStream().write(param.toString().getBytes(Charset.forName("utf-8")));
            conn.connect();
            String respStr = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("utf-8"));
            System.out.println(respStr);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
