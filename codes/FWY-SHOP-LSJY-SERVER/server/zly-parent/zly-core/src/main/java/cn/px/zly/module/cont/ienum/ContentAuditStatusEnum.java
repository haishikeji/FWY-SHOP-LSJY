package cn.px.zly.module.cont.ienum;

import cn.px.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentAuditStatusEnum implements BaseEnum {

	Todo(1, "待审核")
	,Pass(2, "审核通过")
	,NoPass(3, "审核不通过")
	,Unshelve(4,"下架");
	;

	private int num;
	private String name;
}
