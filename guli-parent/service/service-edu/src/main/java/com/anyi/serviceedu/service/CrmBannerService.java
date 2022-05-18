package com.anyi.serviceedu.service;

import com.anyi.serviceedu.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author anyi
 * @since 2022-05-16
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getPage(Page<CrmBanner> pageParam);
}
