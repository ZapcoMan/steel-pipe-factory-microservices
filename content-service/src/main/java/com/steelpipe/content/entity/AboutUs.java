package com.steelpipe.content.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AboutUs {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Integer id;
    private String aboutUs;  // Java字段名aboutUs，DB列名about_us
    @JsonIgnore
    private String features;  // JSON字符串，序列化/反序列化时不暴露
    private String contactPhone;
    private String contactEmail;
    private String contactAddress;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;

    /**
     * 获取features列表，用于JSON序列化
     */
    @JsonProperty("features")
    public List<String> getFeaturesList() {
        if (features == null || features.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return OBJECT_MAPPER.readValue(features, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 设置features列表，用于JSON反序列化
     */
    @JsonProperty("features")
    public void setFeaturesFromList(List<String> list) {
        if (list == null || list.isEmpty()) {
            this.features = "[]";
            return;
        }
        try {
            this.features = OBJECT_MAPPER.writeValueAsString(list);
        } catch (Exception e) {
            this.features = "[]";
        }
    }
}