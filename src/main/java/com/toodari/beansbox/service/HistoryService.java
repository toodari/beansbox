package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.PageRequestDTO;

public interface HistoryService {
    Object getList(PageRequestDTO pageRequestDTO);
}
