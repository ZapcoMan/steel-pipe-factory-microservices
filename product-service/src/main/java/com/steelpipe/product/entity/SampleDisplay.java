package com.steelpipe.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SampleDisplay {

    private Integer id;

    @NotBlank(message = "样品名称不能为空")
    private String name;

    @NotBlank(message = "规格不能为空")
    private String spec;

    private String material;

    @JsonIgnore
    private String features;  // JSON字符串，不直接返回前端

    private String application;

    private String image;

    private String category;

    private Integer isNew;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @JsonProperty("features")
    public List<String> getFeaturesList() {
        if (features == null || features.trim().isEmpty()) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(features, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return null;
        }
    }

    @JsonProperty("features")
    public void setFeaturesFromList(List<String> featuresList) {
        if (featuresList == null || featuresList.isEmpty()) {
            this.features = null;
            return;
        }
        try {
            this.features = new ObjectMapper().writeValueAsString(featuresList);
        } catch (Exception e) {
            this.features = null;
        }
    }
}
