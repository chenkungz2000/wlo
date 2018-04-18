package com.wlo.web.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/r")
public class RandomController {
    final static Logger logger = LoggerFactory.getLogger(RandomController.class);

    @GetMapping(value = "/get")
    @ResponseBody
    public Map<String, Object> getRNum(String num) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        List<Integer> list = new ArrayList<Integer>();
        try {
            list = getRandomList(getNum(), Integer.valueOf(num));
            map.put("list", list);
            map.put("size", list.size());
            flag = true;
        } catch (Exception e) {
            map.put("msg", e.getMessage());
        }

        map.put("flag", flag);
        return map;
    }

    public List<Integer> getNum() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 82; i++) {
            list.add(i);
        }
        return list;
    }

    public List<Integer> getRandomList(List<Integer> getlist, int count) {
        if (getlist.size() < count)
            return getlist;
        Random random = new Random();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < count; i++) {
            // 随机数作为list的索引
            int getrandomnum = random.nextInt(getlist.size());
            if (list.contains(getrandomnum)) {
                logger.info("--------list.contains--------" + i + "-------------list.contains----------");
                continue;
            }
            list.add(getrandomnum);
        }
        Collections.sort(list);
        return list;
    }

}
