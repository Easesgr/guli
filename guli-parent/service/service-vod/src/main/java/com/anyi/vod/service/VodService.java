package com.anyi.vod.service;

import com.anyi.commonutils.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 安逸i
 * @version 1.0
 */
public interface VodService {
    R uploadVodFile(MultipartFile file);
    // 删除视频
    R deleteVod(String vodId);

    // 批量删除视频

    R getPlayAuth(String id);

    R batchDeleteVideo(List<String> videoIds);
}
