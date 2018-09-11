package cn.wolfcode.p2p.base.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IVerifyCodeService;
import cn.wolfcode.p2p.base.util.Constants;
import cn.wolfcode.p2p.base.util.DateUtil;
import cn.wolfcode.p2p.base.util.VerifyCodeContext;
import cn.wolfcode.p2p.base.vo.VerifyCodeVo;

/**
 * Created by WangZhe on 2018/7/22.
 */
@Service@Transactional
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    @Override
    public void sendVerifyCode(String phoneNumber) {
        //首先判断又没有 再次判断是否超过90秒
        VerifyCodeVo verifyCodeVo = VerifyCodeContext.getCurrentVerifyCode();
        //判断你上次发送事件和现在事件比叫是否超过90秒
        if (verifyCodeVo == null ||
                !verifyCodeVo.getPhoneNumber() .equals(phoneNumber) ||
                DateUtil.getTimeBetween(verifyCodeVo.getSendTime(), new Date()) > Constants.VERIFYCODE_INTERVAL_TIME) {
            //生成六位数的验证码
            String varifyCode = UUID.randomUUID().toString().substring(0, 6);
            System.out.println(varifyCode);
            //拼接短信内容
            StringBuilder msg= new StringBuilder(50);
            msg.append("这是您的手机验证码").append(varifyCode).append(",请您尽快使用,验证码的有效时间为").append(Constants.VERIFYCODE_VAILD_TIME).append("分钟");
            //执行真正的短信发送
            /*try {
                sendRealMsg(phoneNumber,msg.toString());
            } catch (Exception e) {
                e.printStackTrace();
                throw  new DisplayableException("验证码异常");
            }*/
            //需要把验证码和发送的手机,发送的时间放到session中
            VerifyCodeVo vo = new VerifyCodeVo();
            vo.setPhoneNumber(phoneNumber);
            vo.setSendTime(new Date());
            vo.setVerifyCode(varifyCode);
            VerifyCodeContext.setVerifyCode(vo);
        } else {
            throw new DisplayableException("你的操作过于频繁,请稍后再试");
        }
    }
    void sendRealMsg(String phoneNumber,String msg) throws Exception {
        //http://utf8.api.smschinese.cn/?Uid=本站用户名&Key=接口安全秘钥&smsMob=手机号码&smsText=验证码:8888
        //String ss = "a59d0f61abe76ba7e4d9b263308e16d2";
            URL url = new URL("http://utf8.api.smschinese.cn");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            StringBuilder param = new StringBuilder(50);
            //?info=体育新闻&loc=北京市海淀区信息路28号&userid=222&appkey=您申请的APPKEY
            param.append("Uid=").append("wzzst310")
                    .append("&Key=").append("d41d8cd98f00b204e980")
                    .append("&smsMob=").append(phoneNumber)
                    .append("&smsText=").append(msg);
            conn.getOutputStream().write(param.toString().getBytes(Charset.forName("utf-8")));
            conn.connect();
            String respStr = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("utf-8"));
            int respCode = Integer.parseInt(respStr);
            if (respCode < 0) {
                throw new DisplayableException("短信接口异常,请联系管理员");
            }

    }
}
