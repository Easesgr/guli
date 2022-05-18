package com.anyi.serviceedu.service.impl;

import com.anyi.serviceedu.entity.CrmBanner;
import com.anyi.serviceedu.mapper.CrmBannerMapper;
import com.anyi.serviceedu.service.CrmBannerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author anyi
 * @since 2022-05-16
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> getPage(Page<CrmBanner> pageParam) {
        IPage<CrmBanner> page = page(pageParam, null);
        return page.getRecords();
    }
}
