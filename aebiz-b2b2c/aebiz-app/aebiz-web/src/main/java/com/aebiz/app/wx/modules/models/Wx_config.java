package com.aebiz.app.wx.modules.models;

import com.aebiz.baseframework.base.model.BaseModel;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

/**
 * Created by gaoen on 2017-2-15 15:35:18
 */
@Table("wx_config")
public class Wx_config extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column
    @Name
    @Comment("ID")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @Prev(els = {@EL("uuid()")})
    private String id;

    @Column
    @Comment("公众号名称")
    @ColDefine(type = ColType.VARCHAR, width = 120)
    private String appname;

    @Column
    @Comment("原始ID")
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String ghid;

    @Column
    @Comment("Appid")
    @ColDefine(type = ColType.VARCHAR, width = 50)
    private String appid;

    @Column
    @Comment("Appsecret")
    @ColDefine(type = ColType.VARCHAR, width = 50)
    private String appsecret;

    @Column
    @Comment("EncodingAESKey")
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String encodingAESKey;

    @Column
    @Comment("Token")
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String token;

    @Column
    @Comment("access_token")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String access_token;

    @Column
    @Comment("access_token_expires")
    @ColDefine(type = ColType.INT)
    private Integer access_token_expires;

    @Column
    @Comment("access_token_lastat")
    @ColDefine(type = ColType.VARCHAR, width = 50)
    private String access_token_lastat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getGhid() {
        return ghid;
    }

    public void setGhid(String ghid) {
        this.ghid = ghid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getEncodingAESKey() {
        return encodingAESKey;
    }

    public void setEncodingAESKey(String encodingAESKey) {
        this.encodingAESKey = encodingAESKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getAccess_token_expires() {
        return access_token_expires;
    }

    public void setAccess_token_expires(Integer access_token_expires) {
        this.access_token_expires = access_token_expires;
    }

    public String getAccess_token_lastat() {
        return access_token_lastat;
    }

    public void setAccess_token_lastat(String access_token_lastat) {
        this.access_token_lastat = access_token_lastat;
    }
}
