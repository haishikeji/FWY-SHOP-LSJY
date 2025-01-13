package cn.px.zly.module.beer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("beer_manage")
public class BeerManagePO {

  @TableId(type = IdType.AUTO)
  private Long id;

  //beer_type_id
  private Long typeId;

  //原厂名称
  private String originalName;

  //厂家id  breweryId
  private Long factoryId;

  private String materialId;

  private String introduce;
}
