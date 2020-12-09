package com.jxywkj.application.pet.common.utils.dadi;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Random;

public class Encryption {

	/***
	 * @author liujunwei
	 * 加密算法
	 * 加密
	 * 解密
	 */
		public static String Encode = "Encode";
		public static String Decode = "Decode";


		/**
		 * @功能：从字符串的指定位置截取指定长度的子字符串
		 * @param str:原字符串
		 * @param startIndex:子字符串的起始位置
		 * @param length
		 * @return:子字符串
		 */
		public static String cutString(String str, int startIndex, int length) {
			if (startIndex >= 0) {
				if (length < 0) {
					length = length * -1;
					if (startIndex - length < 0) {
						length = startIndex;
						startIndex = 0;
					} else {
						startIndex = startIndex - length;
					}
				}

				if (startIndex > str.length()) {
					return "";
				}

			} else {
				if (length < 0) {
					return "";
				} else {
					if (length + startIndex > 0) {
						length = length + startIndex;
						startIndex = 0;
					} else {
						return "";
					}
				}
			}

			if (str.length() - startIndex < length) {

				length = str.length() - startIndex;
			}

			return str.substring(startIndex, startIndex + length);
		}

		/**
		 * @功能：从字符串的指定位置开始截取到字符串结尾的了符串
		 * @param str:原字符串
		 * @param startIndex:子字符串的起始位置
		 * @return:子字符串
		 */
		public static String cutString(String str, int startIndex) {
			return cutString(str, startIndex, str.length());
		}

		/**
		 * @功能:返回文件是否存在
		 * @param filename:文件名
		 * @return:是否存在
		 */
		public static boolean fileExists(String filename) {
			File f = new File(filename);
			return f.exists();
		}

		/**
		 * @：功能MD5函数
		 * @param str:原始字符串
		 * @return:原始字符串
		 */
		public static String mD5(String str) {
			// return md5.convert(str);
			StringBuffer sb = new StringBuffer();
			String part = null;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] md5 = md.digest(str.getBytes());

				for (int i = 0; i < md5.length; i++) {
					part = Integer.toHexString(md5[i] & 0xFF);
					if (part.length() == 1) {
						part = "0" + part;
					}
					sb.append(part);
				}

			} catch (NoSuchAlgorithmException ex) {
			}
			return sb.toString();
		}

		/**
		 * 功能:字段串是否为Null或为
		 * @param str
		 * @return
		 */
		public static boolean strIsNullOrEmpty(String str) {
			// #if NET1
			if (str == null || str.trim().equals("")) {
				return true;
			}

			return false;
		}

		/**
		 * 功能：用于 RC4 处理密码
		 * @param pass:密码字串
		 * @param kLen:>密钥长度，一般为 256
		 * @return
		 */
		static private byte[] getKey(byte[] pass, int kLen) {
			byte[] mBox = new byte[kLen];

			for (int i = 0; i < kLen; i++) {
				mBox[i] = (byte) i;
			}

			int j = 0;
			for (int i = 0; i < kLen; i++) {

				j = (j + (int) ((mBox[i] + 256) % 256) + pass[i % pass.length])
						% kLen;

				byte temp = mBox[i];
				mBox[i] = mBox[j];
				mBox[j] = temp;
			}

			return mBox;
		}

		/**
		 * 功能：生成随机字符
		 * @param lens:随机字符长度
		 * @return:随机字符
		 */
		public static String randomString(int lens) {
			char[] CharArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
					'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
					'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
			int clens = CharArray.length;
			String sCode = "";
			Random random = new Random();
			for (int i = 0; i < lens; i++) {
				sCode += CharArray[Math.abs(random.nextInt(clens))];
			}
			return sCode;
		}

		/**
		 * @author liujunwei
		 * @功能：加密算法
		 * @param source：需要解密的字符串
		 * @param key：加密的秘钥
		 * @param source
		 * @return
		 * @date 20150107
		 */
		public static String authcodeEncode(String source, String key) {
			return decipher(source, key, Encode, 0);

		}

		


		/**
		 * 功能:RC4 原始算法
		 * @param input:原始字串数组
		 * @param pass:密钥
		 * @return:处理后的字串数组
		 */
		private static byte[] rC4(byte[] input, String pass) {
			if (input == null || pass == null)
				return new String("").getBytes();

			byte[] output = new byte[input.length];
			byte[] mBox = getKey(pass.getBytes(), 256);

			// 加密
			int i = 0;
			int j = 0;

			for (int offset = 0; offset < input.length; offset++) {
				i = (i + 1) % mBox.length;
				j = (j + (int) ((mBox[i] + 256) % 256)) % mBox.length;

				byte temp = mBox[i];
				mBox[i] = mBox[j];
				mBox[j] = temp;
				byte a = input[offset];

				// byte b = mBox[(mBox[i] + mBox[j] % mBox.Length) % mBox.Length];
				// mBox[j] 一定比 mBox.Length 小，不需要在取模
				byte b = mBox[(toInt(mBox[i]) + toInt(mBox[j])) % mBox.length];

				output[offset] = (byte) ((int) a ^ (int) toInt(b));
			}

			return output;
		}

		public static int toInt(byte b) {
			return (int) ((b + 256) % 256);
		}

		public long getUnixTimestamp() {
			Calendar cal = Calendar.getInstance();
			return cal.getTimeInMillis() / 1000;
		}
		/**
		 * @param source:原始字符串
		 * @param key：秘钥
		 * @param operation：加解密操作
		 * @param expiry：加密字串过期时间
		 * @return：加密后的字符串
		 */
		private static String decipher(String source, String key,
				String operation, int expiry){
			try{
			if (source == null || key == null) {
				return "";
			}

			int ckey_length = 4;
			String keya, keyb, keyc, cryptkey, result;

			key = mD5(key);

			keya = mD5(cutString(key, 0, 16));

			keyb = mD5(cutString(key, 16, 16));

			keyc = ckey_length > 0 ? (operation.equals(Decode) ? cutString(
					source, 0, ckey_length) : randomString(ckey_length))
					: "";

			cryptkey = keya + mD5(keya + keyc);
			
			source = "0000000000" + cutString(mD5(source + keyb), 0, 16)
					+ source;

			byte[] temp = rC4(source.getBytes(), cryptkey);

			return keyc + Base64.decodeBase64(temp);
			}catch(Exception e){
				e.printStackTrace();
				return"";
			}
		}
		public static void main(String[] args) throws UnsupportedEncodingException {
//			String key = "wtf8Y59PMv1jlaqYJgPvj254yis68P";
//			String key = "wTm7o7wShvg2flBvboG3NUZNVyznnd";
			String key = "HJBLBCXBXWFVCH5LK4N1UO76BJE20ZRA3S8";
			String xml = "中国";
			System.out.println("====================================");
			String utf8 = new String(xml.getBytes("UTF-8"));  
			System.out.println("utf8>"+utf8);
			String unicode = new String(utf8.getBytes(),"UTF-8");   
			System.out.println("unicode>"+unicode);
			String gbk= new String(unicode.getBytes("GBK"),"GBK"); 
			System.out.println("gbk>"+gbk);
			System.out.println("====================================");
			String reXMl=authcodeEncode(gbk,key);//加密
			System.out.println("加密后的内容："+reXMl);
			String reXML=authcodeDecode(reXMl,key);//解密
	
			System.out.println("解密后的内容"+reXML);
			
			
			
//			String keyRst=Encryption.EncodeByMD5("CTRIPCXBXWFVCH5LK4N1UO76BJE20ZRA3S8SV5E2700201751103429");
//			System.out.println(keyRst);
			
		}
		/**
		 * @param source:原始字符串
		 * @param key：秘钥
		 * @param operation：加解密操作
		 * @param expiry：加密字串过期时间
		 * @return：加密后的字符串
		 */
		private static String authcode(String source, String key,
				String operation, int expiry) {
			try {
				if (source == null || key == null) {
					return "";
				}
				
				int ckey_length = 4;
				String keya, keyb, keyc, cryptkey, result;
				
				key = mD5(key);
				
				keya = mD5(cutString(key, 0, 16));
				
				keyb = mD5(cutString(key, 16, 16));
				
				keyc = ckey_length > 0 ? (operation.equals(Decode) ? cutString(
						source, 0, ckey_length) : randomString(ckey_length))
						: "";
						
						cryptkey = keya + mD5(keya + keyc);
						
						if (operation.equals(Decode)) {
							byte[] temp;
							temp = Base64.decodeBase64(cutString(source, ckey_length));
							result = new String(rC4(temp, cryptkey));
							if (cutString(result, 10, 16).equals(
									cutString(mD5(cutString(result, 26) + keyb), 0, 16))) {
								return cutString(result, 26);
							} else {
								temp = Base64.decodeBase64(cutString(source + "=", ckey_length));
								result = new String(rC4(temp, cryptkey));
								if (cutString(result, 10, 16)
										.equals(cutString(
												mD5(cutString(result, 26) + keyb), 0, 16))) {
									return cutString(result, 26);
								} else {
									temp = Base64.decodeBase64(cutString(source + "==",
											ckey_length));
									result = new String(rC4(temp, cryptkey));
									if (cutString(result, 10, 16).equals(
											cutString(mD5(cutString(result, 26) + keyb), 0,
													16))) {
										return cutString(result, 26);
									} else {
										return "2";
									}
								}
							}
						} else {
							return "加密操作不正确";
						}
			} catch (Exception e) {
				return "";
			}
		}
		/**
		 * @author liujunwei
		 * @功能：解密算法
		 * @param source
		 * @param key
		 * @param source
		 * @return
		 * @date 20150107
		 */
		public static String authcodeDecode(String source, String key) {
			return authcode(source, key, Decode, 0);

		}
		/**
		 * 
		 * Description 携程电子发票查询电子发票MD5加密方法
		 * @param str
		 * @return
		 * @returntype String
		 * @exception
		 * @auter zhangshaoguang
		 * @date 2017-4-18
		 */
		public static String EncodeByMD5(String str){
			MessageDigest md;
			char[] car = null;
			try {
				md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] digest = md.digest();
			car = new char[digest.length*2];
			int index=0;
			char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' }; 
			for(byte bt : digest){
				car[index++] = hexDigits[bt>>> 4 & 0xf];
				car[index++] = hexDigits[bt& 0xf];
			}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new String(car);
		}
}
