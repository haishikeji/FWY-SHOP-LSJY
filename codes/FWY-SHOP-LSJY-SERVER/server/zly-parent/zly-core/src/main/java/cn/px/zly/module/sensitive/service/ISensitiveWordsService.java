package cn.px.zly.module.sensitive.service;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface ISensitiveWordsService {

    boolean containSensitiveWords(String str);

    List<String> list();

    boolean addSensitiveWord(String sensitiveWord);
}
