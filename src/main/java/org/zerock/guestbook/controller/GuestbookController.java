package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}