package com.wlo.web;

import com.wlo.model.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/p")
public class OpenFS_CliController {


    @GetMapping(value = "p1")
    @ResponseBody
    public Map<String, Object> p1() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        String[] cmd = {"C:\\Program Files\\FreeSWITCH\\fs_cli.exe", "originate user/1001 1000"};
        try {
            Process process = null;
            Runtime runtime = Runtime.getRuntime();
            System.out.print(runtime);
            process = runtime.exec(cmd);
            flag = true;
        } catch (Exception e) {
            map.put("msg", e.getMessage());
        }
        map.put("flag", flag);
        return map;
    }
    @GetMapping(value = "p2")
    @ResponseBody
    public Map<String, Object> p2() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        String cmd = "C:\\Program Files\\FreeSWITCH\\fs_cli.exe";
        try {
            map.put("path",cmd);
            Process p=new ProcessBuilder(cmd).start();
            flag = true;
        } catch (Exception e) {
            map.put("msg", e.getMessage());
        }
        map.put("flag", flag);
        return map;
    }

    @GetMapping(value = "p3")
    @ResponseBody
    public Map<String, Object> p3() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        try {
            Process process = null;
            Runtime runtime = Runtime.getRuntime();
            final String command = "notepad";// 记事本
            process = runtime.exec("C:\\Program Files\\Notepad++\\notepad++.exe");
            flag = true;
        } catch (Exception e) {
            map.put("msg", e.getMessage());
        }
        map.put("flag", flag);
        return map;
    }

}
