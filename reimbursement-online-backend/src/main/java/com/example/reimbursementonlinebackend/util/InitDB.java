package com.example.reimbursementonlinebackend.util;

import com.example.reimbursementonlinebackend.domain.Employee;
import com.example.reimbursementonlinebackend.domain.OauthClientDetail;
import com.example.reimbursementonlinebackend.domain.Role;
import com.example.reimbursementonlinebackend.domain.User;
import com.example.reimbursementonlinebackend.repository.EmployeeRepository;
import com.example.reimbursementonlinebackend.repository.OauthClientDetailRepository;
import com.example.reimbursementonlinebackend.repository.RoleRepository;
import com.example.reimbursementonlinebackend.repository.UserRepository;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@PropertySource("classpath:data.properties")
public class InitDB {

    private OauthClientDetailRepository oauthClientDetailRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private EmployeeRepository employeeRepository;

    public InitDB(OauthClientDetailRepository oauthClientDetailRepository, RoleRepository roleRepository,
                  PasswordEncoder passwordEncoder, UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.oauthClientDetailRepository = oauthClientDetailRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void init() {
        initOauthClientDetails();
        initRoles();
        initUser();
        initAdmin();
    }

    private void initOauthClientDetails() {
        List<OauthClientDetail> oauthClientDetailList = oauthClientDetailRepository.findAll();
        if (oauthClientDetailList.isEmpty()) {
            OauthClientDetail oauthClientDetail = new OauthClientDetail();
            oauthClientDetail.setClientId("adminapp");
            oauthClientDetail.setResourceIds("mw/adminapp,ms/admin,ms/user");
            oauthClientDetail.setClientSecret(passwordEncoder.encode("adminapp"));
            oauthClientDetail.setAuthorizedGrantTypes("authorization_code,password,refresh_token,implicit");
            oauthClientDetail.setWebServerRedirectUri(null);
            oauthClientDetail.setAuthorities(null);
            oauthClientDetail.setAdditionalInformation("{}");
            oauthClientDetail.setAutoApprove(null);
            oauthClientDetail.setAccessTokenValidity(9000L);
            oauthClientDetail.setRefreshTokenValidity(3600L);
            oauthClientDetail.setScope("write,read");
            oauthClientDetailRepository.save(oauthClientDetail);
        }
    }

    private void initRoles() {
        List<String> roleNames = Arrays.asList(Constants.USER, Constants.ADMIN);
        roleNames.forEach(roleName -> {
            Optional<Role> roleFound = roleRepository.findOneByName(roleName);
            if (!roleFound.isPresent()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        });
    }

    private void initUser() {
        User user = userRepository.findOneByUsername("user").orElse(new User());
        user.setEmail("user@gmail.com");
        user.setUsername("user");
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(Arrays.asList(roleRepository.findOneByName(Constants.USER).get()));
        User savedUser = userRepository.save(user);

        Employee employeeUser = employeeRepository.findByUser(savedUser);
        if (null == employeeUser) {
            employeeUser = new Employee();
            employeeUser.setFullName("User");
            employeeUser.setBankAccount("098459619107411");
            employeeUser.setUser(savedUser);
            employeeRepository.save(employeeUser);
        }
    }

    private void initAdmin() {
        User admin = userRepository.findOneByUsername("admin").orElse(new User());
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setEnabled(true);
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(Arrays.asList(roleRepository.findOneByName(Constants.ADMIN).get()));
        User savedAdmin = userRepository.save(admin);

        Employee employeeAdmin = employeeRepository.findByUser(savedAdmin);
        if (null == employeeAdmin) {
            employeeAdmin = new Employee();
            employeeAdmin.setFullName("Admin");
            employeeAdmin.setBankAccount("092719219102121");
            employeeAdmin.setUser(savedAdmin);
            employeeRepository.save(employeeAdmin);
        }
    }
}