package cn.easyz.server.service.v1.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.easyz.common.model.mapper.ApAnnouncementMapper;
import cn.easyz.common.model.pojo.mysql.ApAnnouncement;
import cn.easyz.server.service.v1.ApAnnouncementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * The type Ap announcement service.
 */
@Service
public class ApAnnouncementServiceImpl implements ApAnnouncementService {
    @Resource
    private ApAnnouncementMapper apAnnouncementMapper;
    @Override
    public IPage<ApAnnouncement> queryAnnouncementList(Integer page, Integer size) {
        return this.apAnnouncementMapper.selectPage(new Page<ApAnnouncement>(page, size),
                new QueryWrapper<ApAnnouncement>().orderByDesc("created"));
    }
}
