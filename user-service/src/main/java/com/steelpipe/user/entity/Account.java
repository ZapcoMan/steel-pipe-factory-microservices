package com.steelpipe.user.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class Account {
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "角色不能为空")
    private String role;
    private String name;
    private String token;
    private String avatar;
    private String newpassword;
    private String new2password;
}
