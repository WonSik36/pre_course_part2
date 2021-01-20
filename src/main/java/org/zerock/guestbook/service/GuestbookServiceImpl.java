package org.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDto;
import org.zerock.guestbook.dto.PageRequestDto;
import org.zerock.guestbook.dto.PageResultDto;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {
    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDto dto) {
        Guestbook entity = repository.save(this.dtoToEntity(dto));

        return entity.getGno();
    }

    @Override
    public PageResultDto<GuestbookDto, Guestbook> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDto);

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        return new PageResultDto<>(result, this::entityToDto);
    }

    @Override
    public GuestbookDto read(Long gno) {
        Guestbook result = repository.findById(gno).orElseThrow();

        return entityToDto(result);
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDto dto) {
        Guestbook entity = repository.findById(dto.getGno()).orElseThrow();

        entity.changeTitle(dto.getTitle());
        entity.changeContent(dto.getContent());

        repository.save(entity);
    }

    private BooleanBuilder getSearch(PageRequestDto requestDto) {
        String type = requestDto.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = requestDto.getKeyword();

        BooleanExpression expression = qGuestbook.gno.gt(0L);

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }

        if(type.contains("c")) {
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }

        if(type.contains("w")) {
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
