package com.sandeep.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AngularJsForwardController {
    @RequestMapping(value ={ "/playground*","/session*","/playground/*"})
    public String redirect() {
        return "forward:/";
    }
}
//,"/**/{[path:[^\\.]*}"