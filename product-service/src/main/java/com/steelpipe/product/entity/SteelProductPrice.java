package com.steelpipe.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SteelProductPrice {

    private Integer id;

    @NotBlank(message = "产品名称不能为空")
    private String name;

    @NotBlank(message = "规格不能为空")
    private String spec;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    private BigDecimal change;

    private String date;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
