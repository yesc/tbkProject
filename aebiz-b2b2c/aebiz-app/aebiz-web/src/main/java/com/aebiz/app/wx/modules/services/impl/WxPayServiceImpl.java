package com.aebiz.app.wx.modules.services.impl;

import com.aebiz.app.web.commons.utils.HttpClientUtil;
import com.aebiz.app.web.commons.utils.WXPayUtil;
import com.aebiz.app.wx.modules.models.WxGetPayInfoQO;
import com.aebiz.app.wx.modules.services.WxConfigService;
import com.aebiz.app.wx.modules.services.WxPayService;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zenghaorong
 * @Date: 2019/4/19  14:17
 * @Description: 微信支付实现
 */
public class WxPayServiceImpl implements WxPayService {

    private static final Log log = Logs.get();

    @Autowired
    private PropertiesProxy config;
    @Autowired
    private WxConfigService wxConfigService;


    @Override
    public String wxGetPayInfo(WxGetPayInfoQO wxGetPayInfoQO) {
        try {

            String key = config.get("wx.pay.sign.key");
            String appid = config.get("wx.pay.AppID");
            String appSecret = config.get("wx.pay.AppSecret");
            String mchid = config.get("wx.pay.mchid");
            String notify_url = config.get("wx.pay.notify_url");

            Map map = new HashMap();
            String nonce_str = String.valueOf(System.currentTimeMillis());
            double total=Double.parseDouble(wxGetPayInfoQO.getTotal_fee())*100;
            map.put("appid",appid);
            map.put("mch_id",mchid);
            map.put("nonce_str", nonce_str);
            map.put("body", wxGetPayInfoQO.getProductBody());
            map.put("out_trade_no", wxGetPayInfoQO.getOut_trade_no());
            map.put("total_fee",total); //金额(分)
            map.put("spbill_create_ip", wxGetPayInfoQO.getThisIp());
            map.put("notify_url", notify_url);//通知地址
            map.put("trade_type", "JSAPI");//通知地址
            map.put("product_id", wxGetPayInfoQO.getProductId());
            String signStr = WXPayUtil.generateSignature(map, key);//签名参数
            map.put("sign", signStr);
            log.info("传入map参数:" + JSON.toJSONString(map));
            JSONObject mapNobject = JSONObject.fromObject(map);
            String postXml = json2Xml(mapNobject);
            //3.统一下单
            String wxXml = HttpClientUtil.submitHttpDate("https://api.mch.weixin.qq.com/pay/unifiedorder", postXml);
            log.info("微信下单返回参数" + wxXml);
            JSONObject xmlJson =  xml2Json(wxXml);
            log.info("微信下单返回参数信息："+xmlJson.toString());
            //判断下单是否成功
             if(!"SUCCESS".equals(xmlJson.get("return_code"))){
                 log.error("微信下单失败："+xmlJson.get("return_msg"));
                 return null;
             }else {
                 return xmlJson.toString();
             }
        }catch (Exception e){
            log.error("请求微信支付二维码异常",e);
        }
        return null;
    }

    public static JSONObject xml2Json(String xml){
        XMLSerializer xmlSerializer = new XMLSerializer();
        //将xml转为json（注：如果是元素的属性，会在json里的key前加一个@标识）
        String  json =  xmlSerializer.read(xml).toString();
        return JSONObject.fromObject(json);
    }

    public static String json2Xml(JSONObject json) throws DocumentException {
        String sXml = "";
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setTypeHintsEnabled(false);
        xmlSerializer.setRootName("xml");
        String sContent = xmlSerializer.write(json,"utf-8");
        try {
            Document docCon = DocumentHelper.parseText(sContent);
            sXml = docCon.getRootElement().asXML();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return sXml;
    }


    public static void main(String[] args) throws DocumentException {
        String s = "<xml>\n" +
                "   <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "   <return_msg><![CDATA[OK]]></return_msg>\n" +
                "   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                "   <mch_id><![CDATA[10000100]]></mch_id>\n" +
                "   <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>\n" +
                "   <openid><![CDATA[oUpF8uMuAJO_M2pxb1Q9zNjWeS6o]]></openid>\n" +
                "   <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>\n" +
                "   <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "   <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>\n" +
                "   <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "</xml>";

        /**
         * xml转json对象
         */
        JSONObject xmlJson =  xml2Json(s);
        System.out.println(xmlJson.toString());
        //json转xml
        System.out.println(json2Xml(xmlJson));
    }


}