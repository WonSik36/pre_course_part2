package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDto;
import org.zerock.guestbook.dto.PageRequestDto;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {
    private final GuestbookService service;

    @GetMapping("/")
    public String list() {
        log.info("list.......");

        return "/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDto dto, Model model) {
        log.info("list.................."+dto);

        model.addAttribute("result", service.getList(dto));
    }

    @GetMapping("/register")
    public void register() {
        log.info("reister get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDto dto, RedirectAttributes redirectAttributes) {
        log.info("dto...."+dto);

        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping("/read")
    public void read(long gno, @ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("gno: "+gno);

        GuestbookDto dto = service.read(gno);

        model.addAttribute("dto", dto);
    }
}
