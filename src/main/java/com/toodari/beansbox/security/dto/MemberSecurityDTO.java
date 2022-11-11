package com.toodari.beansbox.security.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class MemberSecurityDTO extends User {

    private String mid;
    private String mpw;
    private String mname;
    private String mphone;
    private Long myear;
    private Long mmonth;
    private Long mday;

    public MemberSecurityDTO(String username, String password, String mname, String mphone, Long myear, Long mmonth,
                             Long mday, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.mid = username;
        this.mpw = password;
        this.mname = mname;
        this.mphone = mphone;
        this.myear = myear;
        this.mmonth = mmonth;
        this.mday = mday;
    }
}
