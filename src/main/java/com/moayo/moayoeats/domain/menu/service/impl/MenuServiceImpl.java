package com.moayo.moayoeats.domain.menu.service.impl;

import com.moayo.moayoeats.domain.menu.repository.MenuRepository;
import com.moayo.moayoeats.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

}
