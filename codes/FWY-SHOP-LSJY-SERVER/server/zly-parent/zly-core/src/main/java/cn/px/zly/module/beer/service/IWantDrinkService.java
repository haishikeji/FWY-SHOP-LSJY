package cn.px.zly.module.beer.service;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IWantDrinkService {
    boolean isWantDrink(Long userId, Long beerId);

    int queryCount(Long beerId);

    boolean wantDrink(Long userId, Long beerId);
}
