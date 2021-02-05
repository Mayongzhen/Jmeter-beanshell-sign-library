# Jmeter-beanshell-sign-library
基于jmeter beanshell 调用sign签名 支持 "md5"."hmac"."HmacMD5"签名
打包生成 sign.jar
复制到 $JEMETER_HOME/lib/ext/



import com.j.sign.SignClass;
import java.util.Map;
import java.util.TreeMap;

Map hashMap=new TreeMap();
hashMap.put("mobile", "${mobile}");
hashMap.put("skuNo","${skuNo}");
hashMap.put("areaCode","${areaCode}");
hashMap.put("method", "open.user.buy.check");
hashMap.put("v", "2.0");
hashMap.put("appId", "2031023");
hashMap.put("signMethod", "md5");
hashMap.put("timestamp", "${timestamp}");
String signstr=new SignClass().signRequest(hashMap,"4d3bf5f077b2375243c2a24b7853dcd43c664276","md5");
vars.put("sign",signstr);
log.info("===="+signstr);
