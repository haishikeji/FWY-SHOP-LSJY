package cn.px.zly.module.op.param;

import cn.px.zly.module.op.ienum.RecordTypeEnum;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class OpRecordParam {

	@ApiParam(value="用户编号", hidden=true)
	private Long userId;

	@ApiParam(value="操作类型(见RecordTypeEnum)", required=true)
	@NotNull(message="操作类型为空")
	private RecordTypeEnum recordType;

	@ApiParam(value="业务编号", required=true)
	@Range(min=0, max=Long.MAX_VALUE, message="外部关联编号")
	private long outBusiId;
}
