package cn.px.zly.admin.module.permission.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * VUE路由的结构
 * {
 * 		"name": "权限管理",
 * 		"path": "/admin",
 * 		"component": "Layout",
 * 		"meta": {
 * 			"title": "权限管理",
 * 			"icon": "authority"
 *       },
 * 		"children": [
 *            {
 * 				"name": "用户管理",
 * 				"path": "user",
 * 				"component": "admin/user",
 * 				"meta": {
 * 					"title": "用户管理",
 * 					"icon": "user"
 *               }
 *          },
 *          {
 * 			    "name": "角色管理",
 * 			    "path": "role",
 * 			    "component": "admin/role",
 * 			    "meta": {
 * 				    "title": "角色管理",
 * 				    "icon": "peoples"
 *              }
 *          }
 * 	    ]
 * 	}
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterMenuVO {

    private String name;
    private String path;
    private String redirect;
    private String component;
    private Boolean alwaysShow;
    private RouterMenuMetaVO meta;
    private List<RouterMenuVO> children = new ArrayList<>();


    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        RouterMenuVO menu = (RouterMenuVO)obj;
        if (this.name.equals(menu.getName())) {
            return true;
        }
        return false;
    }
}
