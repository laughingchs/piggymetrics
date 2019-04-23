package com.piggymetrics.auth.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Optional;
import java.util.Properties;

import com.piggymetrics.auth.domain.User;
import com.piggymetrics.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository repository;

    private String userStorePath = "/data/oss/authService-oss/user";

    private String nasUserStorePath = "/data/nas/authService-nas/user";

    private String yunPanUserStorePath = "/data/yunpan/authService-yunpan/user";

    @Override
    public void create(User user) {

        log.info("new user create start: {}", user.getUsername());

        Optional<User> existing = repository.findById(user.getUsername());
        existing.ifPresent(it -> {
            throw new IllegalArgumentException("user already exists: " + it.getUsername());
        });

        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);

        repository.save(user);

        createUserFile(user, userStorePath);

        createUserFile(user, nasUserStorePath);

        createUserFile(user, yunPanUserStorePath);

        log.info("new user has been created: {}", user.getUsername());
    }

    private void createUserFile(User user, String storePath) {

        OutputStreamWriter outputStreamWriter = null;
        try {
            log.info("new userFile create start: {},{}", user.getUsername(), storePath);

            File userDirectory = new File(storePath);

            if (!userDirectory.exists()) {
                userDirectory.mkdirs();
            }

            File file = new File(userDirectory, user.getUsername());
            if (!file.exists()) {
                file.createNewFile();
            }

            Properties properties = new Properties();
            properties.put("userName", user.getUsername());
            properties.put("password", user.getPassword());

            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

            properties.store(outputStreamWriter, "userStoreByAuth");

            log.info("new userFile create success: {},{}", user.getUsername(), storePath);
        } catch (Exception e) {
            log.error("userFile create error:" + user.getUsername(), e);
        } finally {
            if (null != outputStreamWriter) {
                try {
                    outputStreamWriter.close();
                } catch (Exception e1) {
                    log.error("userFile create close file error:" + user.getUsername(), e1);
                }
            }
        }
    }

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        User user = new User();
        user.setUsername("helloWorld");
        user.setPassword("hello@1235");
        userService.createUserFile(user, "/tmp/acsToAck/user");
    }

}
