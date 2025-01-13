package cn.px.zly.module.index.bo;

import lombok.Data;

/**
 * zly 精酿类型
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class ZlyCraftVo {
    //精酿类型名
    private String craftName;

    //精酿类型log
    private String craftImg;

    public ZlyCraftVo(String craftName, String craftImg){
        this.craftName = craftName;
        this.craftImg = craftImg;
    }
}
