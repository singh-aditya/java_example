package com.company.todos;

import com.company.todos.db.entity.AuthorityEntity;
import com.company.todos.db.entity.RoleEntity;
import com.company.todos.db.entity.UserEntity;
import com.company.todos.db.repository.AuthorityRepository;
import com.company.todos.db.repository.RoleRepository;
import com.company.todos.db.repository.UserRepository;
import com.company.todos.security.auth.AuthorityName;
import com.company.todos.security.auth.RoleName;
import com.company.todos.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Component
public class InitialSetup {

    @Autowired
    AppProperties appProperties;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("Ready: " + appProperties.getAppMessage().orElse("Not able to read application.properties"));
        createRolesAndAuthorities();
        createSysAdmin();
    }

    private void createRolesAndAuthorities() {
        AuthorityEntity readAuthority = createAuthority(AuthorityName.READ.name());
        AuthorityEntity writeAuthority = createAuthority(AuthorityName.WRITE.name());
        AuthorityEntity deleteAuthority = createAuthority(AuthorityName.DELETE.name());

        createRole(RoleName.SYSADMIN.toString(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
        createRole(RoleName.ADMIN.toString(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
        createRole(RoleName.USER.toString(), Arrays.asList(readAuthority, writeAuthority));
    }

    private void createSysAdmin() {
        UserEntity sysAdmin = userRepository.findByEmail("sysadmin@company.com");
        if (sysAdmin != null) return;

        sysAdmin= new UserEntity()
                .setFirstName("Sys")
                .setLastName("admin")
                .setEmail("sysadmin@company.com")
                .setUserId(utils.generateUserId(UserEntity.userIdLength))
                .setEncryptedPassword(bCryptPasswordEncoder.encode("admin123"))
                .setRole(roleRepository.findByName(RoleName.SYSADMIN.toString()));

        userRepository.save(sysAdmin);
    }


    @Transactional
    private AuthorityEntity createAuthority(String name) {
        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority != null) return authority;

        authority = new AuthorityEntity(name);
        authorityRepository.save(authority);
        return authority;
    }

    @Transactional
    private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
        RoleEntity role = roleRepository.findByName(name);
        if (role != null) return role;

        role = new RoleEntity(name)
                .setAuthorities(authorities);
        roleRepository.save(role);
        return role;
    }
}
