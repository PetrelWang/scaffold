package com.bbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bbc.mapper.DataItemMapper;
import com.bbc.entity.DataItem;
import com.bbc.service.DataItemService;
import org.springframework.stereotype.Service;

/**
 * (DataItem)表服务实现类
 *
 * @author wanghaowei
 * @since 2024-04-09 10:41:25
 */
@Service("dataItemService")
public class DataItemServiceImpl extends ServiceImpl<DataItemMapper, DataItem> implements DataItemService {

}

