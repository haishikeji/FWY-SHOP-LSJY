package cn.px.zly.module.product.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.core.util.StringUtil;
import cn.px.zly.base.ienum.BaseStatusEnum;
import cn.px.zly.module.product.dao.IProductDAO;
import cn.px.zly.module.product.entity.ProductPO;
import cn.px.zly.module.product.param.QueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class ProductDAO extends BaseDAOImpl<ProductDAO.ProductMapper, ProductPO> implements IProductDAO {

    /**
     *
     * @param productIdList
     * @param lastProductId 上一页最后一条数据的id 默认一页20条数据
     * @return
     */
    @Override
    public List<ProductPO> queryByIdList(List<Long> productIdList, Long lastProductId) {
        QueryWrapper<ProductPO> qw = new QueryWrapper<>();
        if(productIdList != null && !productIdList.isEmpty()){
            qw.lambda().in(ProductPO::getId,productIdList);
        }
        if(lastProductId != null && lastProductId != 0l){
            //倒序 所以是小于
            qw.lambda().lt(ProductPO::getId,lastProductId);
        }
        qw.lambda().eq(ProductPO::getStatus, BaseStatusEnum.NORMAL)
                .orderByDesc(ProductPO::getId);
        qw.lambda().last("LIMIT 20");
        return list(qw);
    }

    @Override
    public List<ProductPO> queryByGoodsId(List<Long> goodIdList) {
        if(goodIdList == null || goodIdList.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<ProductPO> qw = new QueryWrapper<>();
        qw.lambda().in(ProductPO::getWeimobGoodsId,goodIdList);
        return list(qw);
    }

    @Override
    public IPage<ProductPO> queryPageByParam(QueryParam queryParam) {
        QueryWrapper<ProductPO> qw = new QueryWrapper<>();
        if(queryParam.getProductId() != null){
            qw.lambda().eq(ProductPO::getId,queryParam.getProductId());
        }
        if(StringUtil.isNotBlank(queryParam.getName())){
            qw.lambda().like(ProductPO::getName,queryParam.getName());
        }
        qw.lambda().orderByDesc(ProductPO::getCreateTime);
        IPage page = new Page();
        page.setCurrent(queryParam.getCurrentPage());
        page.setSize(queryParam.getPageSize());
        return page(page,qw);
    }

    @Mapper
    @Component
    interface  ProductMapper extends BaseMapper<ProductPO>{

    }
}
