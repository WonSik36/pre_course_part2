package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDto;
import org.zerock.guestbook.dto.PageRequestDto;
import org.zerock.guestbook.dto.PageResultDto;
import org.zerock.guestbook.entity.Guestbook;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookServiceTest {
    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister() {
        GuestbookDto dto = GuestbookDto.builder()
                .title("Title...!")
                .content("Content...!")
                .writer("Writer...!")
                .build();

        Long gno = service.register(dto);
        System.out.println("Registered gno: "+gno);
    }

    @Test
    public void testList() {
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDto<GuestbookDto, Guestbook> resultDto = service.getList(pageRequestDto);

        System.out.println("PREV: "+resultDto.isPrev());
        System.out.println("NEXT: "+resultDto.isNext());
        System.out.println("TOTAL: "+resultDto.getTotalPage());

        System.out.println("------------------------------------");
        for(GuestbookDto dto: resultDto.getDtoList()) {
            System.out.println(dto);
        }

        System.out.println("====================================");
        resultDto.getPageList().forEach(System.out::println);
    }

    @Test
    public void testSearch() {
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("한글")
                .build();

        PageResultDto<GuestbookDto, Guestbook> resultDto = service.getList(pageRequestDto);

        System.out.println("PREV: "+resultDto.isPrev());
        System.out.println("NEXT: "+resultDto.isNext());
        System.out.println("TOTAL: "+resultDto.getTotalPage());

        System.out.println("----------------------------------");
        for (GuestbookDto guestbookDto : resultDto.getDtoList()) {
            System.out.println(guestbookDto);
        }

        System.out.println("==================================");
        resultDto.getPageList().forEach(System.out::println);
    }
}