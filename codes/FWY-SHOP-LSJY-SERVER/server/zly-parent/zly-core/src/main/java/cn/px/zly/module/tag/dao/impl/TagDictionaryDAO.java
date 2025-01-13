package cn.px.zly.module.tag.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.px.core.common.exeption.BusinessException;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.base.BasePageParam;
import cn.px.zly.module.tag.dao.ITagDictionaryDAO;
import cn.px.zly.module.tag.entity.TagDictionaryPO;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Log4j2
@Repository
public class TagDictionaryDAO extends BaseDAOImpl<TagDictionaryDAO.TagDictionaryMapper,TagDictionaryPO> implements ITagDictionaryDAO {

    @Override
    public Boolean addBySN(String SN, String name) {
        QueryWrapper<TagDictionaryPO> qw = new QueryWrapper<>();
        qw.lambda().eq(TagDictionaryPO::getSn,SN);
        TagDictionaryPO snPO = this.getOne(qw);
        if(snPO == null){
            log.error("SN:{}不存在",SN);
            throw new BusinessException("SN NOT EXSIT!");
        }
        List<TagDictionaryPO> list = listByParentId(snPO.getId());
        TagDictionaryPO po = new TagDictionaryPO();
        po.setParentId(snPO.getId());
        po.setId(snPO.getId() + Long.valueOf(list.size()+1));
        po.setName(name);
        return this.save(po);
    }

    @Override
    public List<TagDictionaryPO> listByParentId(Long parentId){
        QueryWrapper<TagDictionaryPO> qw = new QueryWrapper<>();
        qw.lambda().eq(TagDictionaryPO::getParentId,parentId);
        List<TagDictionaryPO> list = this.list(qw);
        return list;
    }

    @Override
    public TagDictionaryPO queryBySN(String sn) {
        QueryWrapper<TagDictionaryPO> qw = new QueryWrapper<>();
        qw.lambda().eq(TagDictionaryPO::getSn,sn);
        TagDictionaryPO po = this.getOne(qw);
        return po;
    }

    @Override
    public IPage<TagDictionaryPO> queryPage(BasePageParam pageParam) {
        IPage<TagDictionaryPO> page = new Page();
        page.setSize(pageParam.getPageSize());
        page.setCurrent(pageParam.getCurrentPage());
        QueryWrapper<TagDictionaryPO> qw = new QueryWrapper<>();
        qw.lambda().orderByDesc(TagDictionaryPO::getId);
        return this.page(page,qw);
    }

    @Mapper
    @Component
    interface TagDictionaryMapper extends BaseMapper<TagDictionaryPO>{
    }
}
