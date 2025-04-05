package cn.easyz.temp.controller;

import cn.easyz.temp.service.TitleGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TitleController {

    private final TitleGeneratorService titleGeneratorService;

    @Autowired
    public TitleController(TitleGeneratorService titleGeneratorService) {
        this.titleGeneratorService = titleGeneratorService;
    }

    @PostMapping("/generate-title")
    public String generateTitle(@RequestParam String text) {
        return titleGeneratorService.generateTitle(text);
    }
}
