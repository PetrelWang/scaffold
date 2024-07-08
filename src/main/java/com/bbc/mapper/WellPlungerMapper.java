package com.bbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bbc.entity.WellPlunger;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (WellPlunger)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-22 19:32:27
 */
public interface WellPlungerMapper extends BaseMapper<WellPlunger> {

    List<WellPlunger> getDataList(@Param("wellName") String wellName, @Param("startDate") String startDate, @Param("endDate") String endDate);
}

