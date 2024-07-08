package com.bbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bbc.mapper.DailyProductionMapper;
import com.bbc.entity.DailyProduction;
import com.bbc.service.DailyProductionService;
import org.springframework.stereotype.Service;

/**
 * (DailyProduction)表服务实现类
 *
 * @author makejava
 * @since 2024-02-05 16:45:32
 */
@Service("dailyProductionService")
public class DailyProductionServiceImpl extends ServiceImpl<DailyProductionMapper, DailyProduction> implements DailyProductionService {

}

