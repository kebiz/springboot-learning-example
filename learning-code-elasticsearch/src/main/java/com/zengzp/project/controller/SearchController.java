package com.zengzp.project.controller;

import com.zengzp.project.entity.GoodsInfo;
import com.zengzp.project.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/1 14:25
 * @description：搜索控制类
 * @modified By：
 * @version: 1$
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;
    @GetMapping("/search")
    public String  search(Model model,@RequestParam Map<String,Object> parmMap) throws IOException {
        if(parmMap.get("pageNo")==null){
            parmMap.put("pageNo","1");
        }
        if(parmMap.get("sort")==null){
            parmMap.put("sort","price");
        }
        if(parmMap.get("sortOrder")==null){
            parmMap.put("sortOrder","desc");
        }
        Map<String,Object> result =searchService.search(parmMap);
        model.addAttribute("code",1000);
        model.addAttribute("msg","处理成功");
        model.addAttribute("data",result);
        return "index";
    }
}
