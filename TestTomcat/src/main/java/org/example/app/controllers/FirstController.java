package org.example.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("suername");

        System.out.println(name + surname);

        return "first/hello";

    }

    @GetMapping("/bb")
    public String bbPage(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "sur", required = false) String sur,
                         Model model) {

        model.addAttribute("message", name + " " + sur);
        //System.out.println(name + sur);

        return "first/bb";
    }

}
