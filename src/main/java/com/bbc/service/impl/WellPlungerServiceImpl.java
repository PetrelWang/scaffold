package com.bbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bbc.mapper.WellPlungerMapper;
import com.bbc.entity.WellPlunger;
import com.bbc.service.WellPlungerService;
import org.springframework.stereotype.Service;

/**
 * (WellPlunger)表服务实现类
 *
 * @author makejava
 * @since 2024-02-22 19:32:27
 */
@Service("wellPlungerService")
public class WellPlungerServiceImpl extends ServiceImpl<WellPlungerMapper, WellPlunger> implements WellPlungerService {

}

