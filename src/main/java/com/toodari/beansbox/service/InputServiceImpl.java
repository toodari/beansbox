package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.PageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class InputServiceImpl implements InputService{
    @Override
    public Object getList(PageRequestDTO pageRequestDTO) {
        return null;
    }
}
