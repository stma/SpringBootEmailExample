package com.progmatic.emaildemo;

import com.progmatic.emaildemo.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @Autowired
    private EmailSender sender;

    @GetMapping("/cheers")
    public @ResponseBody String getCheersEmail(
            @RequestParam String name,
            @RequestParam String email,
            EmailTemplateBuilder template
    ) {
        template.setName(name);
        sender.send(email, template.build());
        return "Cheers " + name + "!";
    }
}
