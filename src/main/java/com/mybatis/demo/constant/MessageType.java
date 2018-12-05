package com.mybatis.demo.constant;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {
    ONLINE(1, "onLine"),
    OFFLINE(2, "offLine"),
    ONLINENAMELIST(3, "onLineNameList"),
    NORMAL(4, "normal"),

    UNKNOWN(-999, "未知");
    private int id;
    private String desc;

    MessageType(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public static MessageType valueOfStr(int id) {
        MessageType type = UNKNOWN;
        for (MessageType t : MessageType.values()) {
            if (t.getId() == id) {
                type = t;
                break;
            }
        }
        return type;
    }

    public static Map<Integer, Object> getValuesMap() {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        for (MessageType t : MessageType.values()) {
            map.put(t.getId(), t.getDesc());
        }
        map.remove(UNKNOWN.id);
        return map;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

}
