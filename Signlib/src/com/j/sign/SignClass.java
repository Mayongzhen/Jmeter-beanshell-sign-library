package com.j.sign;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SignClass {
	 private static final String CHARSET_UTF8 = "UTF-8";
	  private static final String SIGN_METHOD_MD5 = "md5";
	  private static final String SIGN_METHOD_HMAC = "hmac";
	  private static final String SIGN_METHOD_HMAC_MD5 = "HmacMD5";
	  public static String signRequest(Map<String, String> params, String secret, String signMethod)
		       {

		    // 第一步：检查参数是否已经排序
		    String[] keys = params.keySet().toArray(new String[0]);
		    Arrays.sort(keys);
		    // 第二步：把所有参数名和参数值串在一起
		    StringBuilder query = new StringBuilder();
		    if (SIGN_METHOD_MD5.equalsIgnoreCase(signMethod)) {
		      query.append(secret);
		    }
		    for (String key : keys) {
		      String value = params.get(key);
		     
		        query.append(key).append(value);
		     
		    }

		    // 第三步：使用MD5/HMAC加密
		    byte[] bytes=null;
		    if (SIGN_METHOD_HMAC.equals(signMethod)) {
		      try {
				bytes = encryptHMAC(query.toString(), secret);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    } else {
		      query.append(secret);
		      try {
				bytes = encryptMD5(query.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }

		    // 第四步：把二进制转化为大写的十六进制(正确签名应该为32大写字符串，此方法需要时使用)
		    return byte2hex(bytes);
		  }

		  public static byte[] encryptHMAC(String data, String secret) throws IOException {
		    byte[] bytes = null;
		    try {
		      SecretKey secretKey = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), SIGN_METHOD_HMAC_MD5);
		      Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		      mac.init(secretKey);
		      bytes = mac.doFinal(data.getBytes(CHARSET_UTF8));
		    } catch (GeneralSecurityException gse) {
		      throw new IOException(gse.toString());
		    }
		    return bytes;
		  }


		  public static byte[] encryptMD5(String data) throws IOException {
		    return encryptMD5(data.getBytes(CHARSET_UTF8));
		  }


		  public static String byte2hex(byte[] bytes) {
		    StringBuilder sign = new StringBuilder();
		    for (int i = 0; i < bytes.length; i++) {
		      String hex = Integer.toHexString(bytes[i] & 0xFF);
		      if (hex.length() == 1) {
		        sign.append("0");
		      }
		      sign.append(hex.toUpperCase());
		    }
		    return sign.toString();
		  }

		  public static byte[] encryptMD5(byte[] data) throws IOException {
		    try {
		      MessageDigest md = MessageDigest.getInstance(SIGN_METHOD_MD5.toUpperCase());
		      byte[] bytes = md.digest(data);
		      return bytes;
		    } catch (GeneralSecurityException var3) {
		      throw new IOException(var3.toString());
		    }
		  }

	public static void main(String[] args) {
		
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
		System.out.println(signstr);
		
	}
	
}
