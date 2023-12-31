package com.colivingspacemanager.app.validation;

import com.colivingspacemanager.app.entity.ColivingSpace;
import com.colivingspacemanager.app.entity.Occupancy;
import com.colivingspacemanager.app.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {

    public boolean isValidUser(User user) {

        if (user == null) {
            return false;
        }

        if (user.getRole() == null) {
            return false;
        }

        if (user.getUsername() == null || user.getUsername().length() < 3 || user.getUsername().length() > 10) {
            return false;
        }

        if (user.getEmail() == null || !user.getEmail().endsWith("@gmail.com")) {
            return false;
        }

        if (user.getPassword() == null || user.getPassword().length() < 6 || user.getPassword().length() > 10) {
            return false;
        }

        return true;
    }


}
