package org.ssriot.cityoa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class User {
    @NotNull
    private Integer id;
    private String username;
    @JsonIgnore//让spring把当前对象转换为json的时候忽略pwd
    private String password;
    private String userPic;
    private LocalDateTime createTime;

    // 关联属性
    private List<MyDevice> ownedDevices; // 一对多关系
    private List<MyDevice> sharedDevices; // 多对多关系



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public List<MyDevice> getOwnedDevices() {
        return ownedDevices;
    }

    public void setOwnedDevices(List<MyDevice> ownedDevices) {
        this.ownedDevices = ownedDevices;
    }

    public List<MyDevice> getSharedDevices() {
        return sharedDevices;
    }

    public void setSharedDevices(List<MyDevice> sharedDevices) {
        this.sharedDevices = sharedDevices;
    }
}
