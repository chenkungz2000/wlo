package com.wlo.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*import net.sf.json.JSONObject;*/

public class Utils {

	private static Logger log = LoggerFactory.getLogger(Utils.class);

	/**
	 * 获取当月第一天
	 *
	 * @return
	 */
	public static Date getFirstDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		// String last = sdf.format(ca.getTime());
		return c.getTime();
	}

	/**
	 * 获取当月最后一天
	 *
	 * @return
	 */
	public static Date getLastDay() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}

	/**
	 * 根据参数获得以前日期
	 *
	 * @param number
	 * @return
	 */
	public static Date ago(int number) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -number);
		return c.getTime();
	}

	/**
	 * 改变时分秒为00：00：00
	 *
	 * @param date
	 * @return
	 */
	public static Date changeDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		Date cdate = cal.getTime();
		return cdate;

	}

	/*
    * 日期向后推一年
    * */
	private Date toNextYear(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		//+1年
		calendar.add(calendar.YEAR, 1);
		return date = calendar.getTime();
	}

	/*获取时间差*/
	private int getDate(Date date, Date expdate) {
		int days = 0;
		try {
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String fromDate = simpleFormat.format(date);
			String toDate = simpleFormat.format(expdate);
			long from = simpleFormat.parse(fromDate).getTime();
			long to = simpleFormat.parse(toDate).getTime();
			days = (int) ((to - from) / (1000 * 60 * 60 * 24));

		} catch (Exception e) {
			e.getMessage();
		}
		return days;
	}

	/*
    * 过滤特殊字符*/
	private String requestToString(String str) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	/**
	 * 改变时分秒为00：00：00
	 *
	 * @param date
	 * @return
	 */
	public static Date changeDate2(Date date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String datef = df.format(date);
		return df.parse(datef);

	}

	/**
	 * 发起https请求并获取结果（GET、POST）
	 *
	 * @paramrequestUrl请求地址
	 * @paramrequestMethod请求方式（GET、POST）
	 * @paramoutputStr提交的数据
	 * @return JSONObject
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			//jsonObject = JSONObject.fromObject(buffer.toString());
			jsonObject= JSON.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;

	}

	/**
	 * 接口解析 get方式获取 （经测试不稳定，偶尔存在解析失败问题）
	 *
	 * @param url
	 * @return map
	 */
	public static Map<String, Object> httpGetM(String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String jsons = EntityUtils.toString(response.getEntity());
				if (jsons == null || jsons.length() <= 0) {
					log.info("----接收数据失败----");
				}
				Object succesResponse = JSON.parse(jsons); // 先转换成Object
				map = (Map<String, Object>) succesResponse;
			} else {
				log.info("----解析接口失败----");
			}
		} catch (IOException e) {
		}
		return map;
	}

	/**
	 * 接口解析 POST方法获取
	 *
	 * @param url
	 * @paramparam请求参数如：String
	 * @return
	 */
	public static JSONObject sendPost(String url, String param) {
		String json = "{\"type\":\"news\"," + " \"offset\":\"1\", " + " \"count\" :\"20\"}";
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			OutputStreamWriter outWriter = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
			out = new PrintWriter(outWriter);
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return JSON.parseObject(result.toString());
	}

	/**
	 * 获取ip地址
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * URLEncoder链接
	 *
	 * @param url
	 * @return
	 */
	public static String URLEncoder(String url) {
		return URLEncoder.encode(url);
	}

	public static int getRandom(int i) {
		return (int) Math.round(Math.random() * (i));
	}

	/**
	 * 获得随机数
	 *
	 * @return String
	 */
	public static String getRandomString() {
		String randomStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		int length = 10;
		StringBuffer sb = new StringBuffer();
		int len = randomStr.length();
		for (int i = 0; i < length; i++) {
			sb.append(randomStr.charAt(getRandom(len - 1)));
		}
		return sb.toString();
	}

	/**
	 * 获得随机数
	 *
	 * @return String
	 */
	private String getRandomString(int length) {
		// 随机生成字符串
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String getValue(String value) {
		HttpServletRequest request = null;
		String pam = request.getParameter(value);
		if (pam == null)
			pam = "";
		try {
			pam = new String(request.getParameter(value).getBytes("ISO-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pam;
	}

	/**
	 * 判断是不是手机号码
	 *
	 * @param phone
	 * @return
	 */
	public static Boolean checkPhone(String phone) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	/**
	 * 获取mac
	 * @return
	 * @throws SocketException
	 */
	public String getLocalMac() throws SocketException {

		StringBuffer sb = new StringBuffer("");
		try {
			// 得到IP，输出PC-201309011313/122.206.73.83
			InetAddress ia = InetAddress.getLocalHost();
			// 获取网卡，获取地址
			byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			System.out.println("mac数组长度：" + mac.length);
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				// 字节转换为整数
				int temp = mac[i] & 0xff;
				String str = Integer.toHexString(temp);
				System.out.println("每8位:" + str);
				if (str.length() == 1) {
					sb.append("0" + str);
				} else {
					sb.append(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString().toUpperCase();
	}

}