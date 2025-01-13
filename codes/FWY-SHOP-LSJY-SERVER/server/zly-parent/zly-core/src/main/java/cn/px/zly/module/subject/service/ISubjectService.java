package cn.px.zly.module.subject.service;

import cn.px.zly.module.subject.entity.SubjectPO;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface ISubjectService {

    void newSubject(SubjectPO subject);

    List<SubjectPO> list();

    SubjectPO getById(Long subId);

    void update(SubjectPO po);
}
