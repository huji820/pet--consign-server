package com.jxywkj.application.pet.common.utils.dadi;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Random;

public class Md5UtilForJt {



	/***
	 * @author zhanghanxu
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
		public static String CutString(String str, int startIndex, int length) {
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
		public static String CutString(String str, int startIndex) {
			return CutString(str, startIndex, str.length());
		}

		/**
		 * @功能:返回文件是否存在
		 * @param filename:文件名
		 * @return:是否存在
		 */
		public static boolean FileExists(String filename) {
			File f = new File(filename);
			return f.exists();
		}

		/**
		 * @：功能MD5函数
		 * @param str:原始字符串
		 * @return:原始字符串
		 * @throws UnsupportedEncodingException
		 */
		public static String MD5(String str,String charset) throws UnsupportedEncodingException {
			// return md5.convert(str);
			StringBuffer sb = new StringBuffer();
			String part = null;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] md5 = md.digest(str.getBytes(charset));

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
		public static boolean StrIsNullOrEmpty(String str) {
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
		static private byte[] GetKey(byte[] pass, int kLen) {
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
		public static String RandomString(int lens) {
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
		 * @author zhanghanxu
		 * @功能：加密算法
		 * @param source：需要解密的字符串
		 * @param key：加密的秘钥
		 * @return
		 * @date 20150107
		 */
		public static String authcodeEncode(String source, String key,String charset) {
			return decipher(source, key, Encode, 0,charset);

		}




		/**
		 * 功能:RC4 原始算法
		 * @param input:原始字串数组
		 * @param pass:密钥
		 * @return:处理后的字串数组
		 */
		private static byte[] RC4(byte[] input, String pass) {
			if (input == null || pass == null)
				return null;

			byte[] output = new byte[input.length];
			byte[] mBox = GetKey(pass.getBytes(), 256);

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
				String operation, int expiry,String charset){
			try{
			if (source == null || key == null) {
				return "";
			}

			int ckey_length = 4;
			String keya, keyb, keyc, cryptkey, result;

			key = MD5(key,charset);

			keya = MD5(CutString(key, 0, 16),charset);

			keyb = MD5(CutString(key, 16, 16),charset);

			keyc = ckey_length > 0 ? (operation.equals(Decode) ? CutString(
					source, 0, ckey_length) : RandomString(ckey_length))
					: "";

			cryptkey = keya + MD5(keya + keyc,charset);

			source = "0000000000" + CutString(MD5(source + keyb,charset), 0, 16)
					+ source;

			byte[] temp = RC4(source.getBytes(charset), cryptkey);

			return keyc + Base64.encodeBase64String(temp);
			}catch(Exception e){
				e.printStackTrace();
				return"";
			}
		}
		public static void main(String[] args) {
//			String key = "JTNXSCXBXWFVCH5LK4N1UO76BJE20ZRA3S8";
//			String xml = "<InPName>任盈盈</InPName>";
			String charset = "UTF-8";
//			String reXMl=authcodeEncode(xml,key,charset);//加密
//			System.out.println(reXMl);

			String reXML=authcodeDecode("ebgr5jQYyuPo9bENaR01sTckpNgNP2DvejsPFezh","JTNXSCXBXWFVCH5LK4N1UO76BJE20ZRA3S8",charset);//解密

			System.out.println("===================>"+reXML);

		}
		/**
		 * @param source:原始字符串
		 * @param key：秘钥
		 * @param operation：加解密操作
		 * @param expiry：加密字串过期时间
		 * @return：加密后的字符串
		 */
		private static String authcode(String source, String key,
				String operation, int expiry,String charset) {
			try {
				if (source == null || key == null) {
					return "";
				}

				int ckey_length = 4;
				String keya, keyb, keyc, cryptkey, result;

				key = MD5(key,charset);

				keya = MD5(CutString(key, 0, 16),charset);

				keyb = MD5(CutString(key, 16, 16),charset);

				keyc = ckey_length > 0 ? (operation.equals(Decode) ? CutString(
						source, 0, ckey_length) : RandomString(ckey_length))
						: "";

						cryptkey = keya + MD5(keya + keyc,charset);

						if (operation.equals(Decode)) {
							byte[] temp;

							temp = Base64.decodeBase64(CutString(source, ckey_length));
							result = new String(RC4(temp, cryptkey));
							if (CutString(result, 10, 16).equals(
									CutString(MD5(CutString(result, 26) + keyb,charset), 0, 16))) {
								return CutString(result, 26);
							} else {
								temp = Base64.decodeBase64(CutString(source + "=", ckey_length));
								result = new String(RC4(temp, cryptkey));
								if (CutString(result, 10, 16)
										.equals(CutString(
												MD5(CutString(result, 26) + keyb,charset), 0, 16))) {
									return CutString(result, 26);
								} else {
									temp = Base64.decodeBase64(CutString(source + "==",
											ckey_length));
									result = new String(RC4(temp, cryptkey));
									if (CutString(result, 10, 16).equals(
											CutString(MD5(CutString(result, 26) + keyb,charset), 0,
													16))) {
										return CutString(result, 26);
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
		 * @author zhanghanxu
		 * @功能：解密算法
		 * @param source
		 * @param key
		 * @param expiry
		 * @return
		 * @date 20150107
		 */
		public static String authcodeDecode(String source, String key,String charset) {
			return authcode(source, key, Decode, 0,charset);

		}

}
