package com.wlo.web.tools;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/base64")
public class Base64PicController {

    final static Logger logger = LoggerFactory.getLogger(Base64PicController.class);

    @GetMapping(value = "/get")
    @ResponseBody
    public Map<String, Object> getBasePic() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        try {
            InputStream inputStream = null;
            byte[] data = null;
            inputStream = new FileInputStream("D://2.jpg");
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
            Base64 base64 = new Base64();
            data = base64.encode(data);
            String s = new String(data);
            map.put("pic", s);
            flag = true;
        } catch (Exception e) {
            map.put("msg", e.getMessage());
        }
        map.put("flag", flag);
        return map;
    }

    /**
     * 加密
     */
    public static String encodeStr(String plainText) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream("D://ludashi.jpg");
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Base64 base64 = new Base64();
        data = base64.encode(data);
        String s = new String(data);
        return s;
    }

    /**
     * 解密
     */
    public static String decodeStr(String encodeStr) {
        byte[] b = encodeStr.getBytes();
        Base64 base64 = new Base64();
        b = base64.decode(b);
        String s = new String(b);
        return s;
    }

}
