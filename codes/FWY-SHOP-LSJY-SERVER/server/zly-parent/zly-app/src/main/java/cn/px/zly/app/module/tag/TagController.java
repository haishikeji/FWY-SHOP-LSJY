package cn.px.zly.app.module.tag;

import cn.px.core.common.result.JsonResult;
import cn.px.zly.app.module.tag.vo.FiltrateTagVO;
import cn.px.zly.module.beer.dto.SimpleBeerTypeDTO;
import cn.px.zly.module.beer.service.IBeerTypeManageService;
import cn.px.zly.module.brewery.dto.SimpleBreweryDTO;
import cn.px.zly.module.brewery.service.IBreweryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Api(tags = "标签相关")
@Log4j2
@Validated
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private IBeerTypeManageService beerTypeManageService;

    @Autowired
    private IBreweryService breweryService;

    @ApiOperation("筛选列表")
    @GetMapping("/filtrate/list")
    public JsonResult<FiltrateTagVO> list(){
        List<SimpleBreweryDTO> simpleBreweryDTOS = breweryService.simpleList();
        List<SimpleBeerTypeDTO> styleList = beerTypeManageService.simpleList();
        FiltrateTagVO vo = new FiltrateTagVO();
        vo.setStyleList(styleList);
        vo.setBreweryTagList(simpleBreweryDTOS);
        return JsonResult.ok(vo);
    }

//    private static List<TagValueVO> listValueOf(List<TagValueDTO> list){
//        if(!list.isEmpty()){
//            List<TagValueVO> ret = new ArrayList<>();
//            for (TagValueDTO dto : list) {
//                TagValueVO vo = new TagValueVO();
//                vo.setTagId(dto.getTagId());
//                vo.setTagValueId(dto.getTagValueId());
//                vo.setName(dto.getTagName());
//                vo.setTagValue(dto.getTagValue());
//                ret.add(vo);
//            }
//            return ret;
//        }
//        return Collections.EMPTY_LIST;
//    }
}
