package cn.px.zly.admin.base;

import com.google.common.collect.ImmutableMap;

import java.util.*;

public enum LoginType {

    ZLY("zly", "zly")
    ;

    public final String code;
    public final String desc;

    private LoginType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public final static LoginType codeOf(String code) {
        Optional<LoginType> type = Arrays.stream(LoginType.values())
                .filter(loginType -> loginType.code.equals(code)).findFirst();
        if (type.isPresent()) {
            return type.get();
        }
        return null;
    }

    public static List<Map<String, Object>> toListMap() {
        List<Map<String, Object>> list = new ArrayList<>(LoginType.values().length);
        for (LoginType loginType : LoginType.values()) {
            list.add(ImmutableMap.<String, Object>builder()
                    .put("desc", loginType.desc)
                    .put("code", loginType.code)
                    .build());
        }
        return list;
    }
}
