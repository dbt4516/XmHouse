package com.cnc.xmhouse.framework.security;

import com.cnc.xmhouse.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuangjy on 2016/7/21.
 */
@Service
public class WsUserDetailService implements UserDetailsService {
    @Autowired
    private BaseDao<User> baseDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Collection<GrantedAuthority> auths = new ArrayList<>();
        Map<String,Object> hs = new HashMap<>();
        hs.put("mail",userName);
        com.cnc.xmhouse.lagacy_entity.User user = (com.cnc.xmhouse.lagacy_entity.User)
                baseDao.uniqueResult("From User WHERE mail=:mail",hs);
        GrantedAuthority auth = new GrantedAuthorityImpl(user.getAuth());
        auths.add(auth);

        User userAuth = new UserInfo(user.getId(),user.getUserName(),userName, user.getPassWord(), true, true, true, true, auths);
        return userAuth;
    }

}
