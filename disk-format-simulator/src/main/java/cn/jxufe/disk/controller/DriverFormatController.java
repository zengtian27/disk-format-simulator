package cn.jxufe.disk.controller;

import cn.jxufe.disk.model.FormatView;
import cn.jxufe.disk.service.DriverFormatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class DriverFormatController {

    private static final Logger log = LoggerFactory.getLogger(DriverFormatController.class);

    private final DriverFormatService driverFormatService;

    public DriverFormatController(DriverFormatService driverFormatService) {
        this.driverFormatService = driverFormatService;
    }

    @GetMapping("/driver/format")
    public String format(@RequestParam("letter") String letter, Model model) {
        try {
            FormatView result = driverFormatService.format(letter);
            model.addAttribute("result", result);
        } catch (Exception e) {
            log.error("格式化失败，驱动器={}", letter, e);
            model.addAttribute("result",
                    FormatView.failure(
                            Optional.ofNullable(letter).orElse("未知驱动器"),
                            "发生错误，请检查服务日志了解详情"
                    )
            );
        }
        return "driver/result";
    }
}