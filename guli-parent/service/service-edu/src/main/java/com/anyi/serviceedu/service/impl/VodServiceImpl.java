package com.anyi.serviceedu.service.impl;

import com.anyi.commonutils.R;
import com.anyi.serviceedu.service.VodService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 安逸i
 * @version 1.0
 */
public class VodServiceImpl implements VodService {
    @Override
    public R deleteVod(String id) {
        return R.ok().message("删除视频失败！");
    }

    @Override
    public R batchDeleteVideo(List<String> videoIds) {
        return R.ok().message("批量删除视频失败！");
    }
}
