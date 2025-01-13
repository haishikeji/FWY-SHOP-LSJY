package cn.px.zly.module.cont.ienum;

import cn.px.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentStatusEnum implements BaseEnum {

	Using(1, "在用")
	,Deleted(2, "已删除")
	;

	private int num;
	private String name;
}
