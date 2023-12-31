package com.colivingspacemanager.app.controller;

import com.colivingspacemanager.app.entity.ColivingSpace;
import com.colivingspacemanager.app.entity.SpaceStatus;
import com.colivingspacemanager.app.entity.User;
import com.colivingspacemanager.app.repository.ColivingSpaceRepository;
import com.colivingspacemanager.app.repository.UserRepository;
import com.colivingspacemanager.app.validation.ValidationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ColivingSpaceRepository colivingSpaceRepository;

    @GetMapping(value = {"/login", "/"})
    public String login() {
        return "login";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ValidationService validationService;

    @PostMapping("/register")
    public String processRegistration(User user, Model model, RedirectAttributes redirectAttributes) {
        if (user.getRole().equals("ADMIN")) {
            return "redirect:/register";
        }
        if (!validationService.isValidUser(user)) {
            redirectAttributes.addFlashAttribute("error", "Username, password or email is not valid. Please try again.");
            return "redirect:/register";
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("error", "Username is already taken. Try another username.");
            return "redirect:/register";
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            redirectAttributes.addFlashAttribute("error", "Email is already taken. Try another email.");
            return "redirect:/register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String generatedCode = null;
        String message;
        if (user.getRole().equals("ADMIN")) {
            generatedCode = "CLA" + RandomStringUtils.randomNumeric(7);
            while (userRepository.findByCode(generatedCode) != null) {
                generatedCode = "CLA" + RandomStringUtils.randomNumeric(7);
            }
            message = "You've registered successfully as an admin and your code is " + generatedCode;
        } else if (user.getRole().equals("SPACE_MANAGER")) {
            generatedCode = "CLM" + RandomStringUtils.randomNumeric(7);
            while (userRepository.findByCode(generatedCode) != null) {
                generatedCode = "CLM" + RandomStringUtils.randomNumeric(7);
            }
            message = "You've registered successfully as a space manager and your code is " + generatedCode;
        } else {
            generatedCode = "CLT" + RandomStringUtils.randomNumeric(7);
            while (userRepository.findByCode(generatedCode) != null) {
                generatedCode = "CLT" + RandomStringUtils.randomNumeric(7);
            }
            message = "You've registered successfully as an tenant and your code is " + generatedCode;
        }
        user.setCode(generatedCode);
        user.setCreated(new Date());
        user.setLastUpdated(new Date());
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("registerSuccess", message);
        return "redirect:/login";
    }


    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/spacemanager/addColivingSpace")
    public String addColivingSpace() {
        return "/spacemanager/addColivingSpace";
    }

    @GetMapping("/spacemanager/overview")
    public String overview(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        String spaceManagerCode = user.getCode();
        long totalCount = colivingSpaceRepository.countBySpaceManagerCode(spaceManagerCode);
        long activeCount = colivingSpaceRepository.countBySpaceStatusAndSpaceManagerCode(SpaceStatus.ACTIVE, spaceManagerCode);
        long closedCount = colivingSpaceRepository.countBySpaceStatusAndSpaceManagerCode(SpaceStatus.ACTIVE, spaceManagerCode);
        long pendingForApprovalCount = colivingSpaceRepository.countBySpaceStatusAndSpaceManagerCode(SpaceStatus.ACTIVE, spaceManagerCode);
        long suspendedCount = colivingSpaceRepository.countBySpaceStatusAndSpaceManagerCode(SpaceStatus.ACTIVE, spaceManagerCode);
        long inactiveCount = colivingSpaceRepository.countBySpaceStatusAndSpaceManagerCode(SpaceStatus.ACTIVE, spaceManagerCode);
        long fullCapacityCount = colivingSpaceRepository.countBySpaceStatusAndSpaceManagerCode(SpaceStatus.ACTIVE, spaceManagerCode);
        long notApprovedCount = colivingSpaceRepository.countBySpaceStatusAndSpaceManagerCode(SpaceStatus.ACTIVE, spaceManagerCode);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("closedCount", closedCount);
        model.addAttribute("pendingForApprovalCount", pendingForApprovalCount);
        model.addAttribute("suspendedCount", suspendedCount);
        model.addAttribute("notApprovedCount", notApprovedCount);
        model.addAttribute("inactiveCount", inactiveCount);
        model.addAttribute("fullCapacityCount", fullCapacityCount);
        return "/spacemanager/overview";
    }


    @GetMapping("/tenant/overview")
    public String tenantoverview() {
        return "/tenant/overview";
    }

    @GetMapping("/admin/profile")
    public String adminProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("firstname", user.getFirstname());
        model.addAttribute("lastname", user.getLastname());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("code", user.getCode());
        model.addAttribute("role", user.getRole());
        model.addAttribute("created", user.getCreated());
        model.addAttribute("updated", user.getLastUpdated());
        return "/admin/profile";
    }

    @GetMapping("/tenant/profile")
    public String tenantProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("firstname", user.getFirstname());
        model.addAttribute("lastname", user.getLastname());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("code", user.getCode());
        model.addAttribute("role", user.getRole());
        model.addAttribute("created", user.getCreated());
        model.addAttribute("updated", user.getLastUpdated());
        return "/tenant/profile";
    }

    @GetMapping("/spacemanager/profile")
    public String spacemanagerProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("firstname", user.getFirstname());
        model.addAttribute("lastname", user.getLastname());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("code", user.getCode());
        model.addAttribute("role", user.getRole());
        model.addAttribute("created", user.getCreated());
        model.addAttribute("updated", user.getLastUpdated());
        return "/spacemanager/profile";
    }

    @GetMapping("/tenant/dashbaord")
    public String tenantdashbaord() {
        return "/tenant/dashbaord";
    }

    @GetMapping("/admin/overview")
    public String adminOverview(Model model) {
        long totalCount = colivingSpaceRepository.count();
        long activeCount = colivingSpaceRepository.countBySpaceStatus(SpaceStatus.ACTIVE);
        long closedCount = colivingSpaceRepository.countBySpaceStatus(SpaceStatus.CLOSED);
        long pendingForApprovalCount = colivingSpaceRepository.countBySpaceStatus(SpaceStatus.PENDING_APPROVAL);
        long suspendedCount = colivingSpaceRepository.countBySpaceStatus(SpaceStatus.SUSPENDED);
        long inactiveCount = colivingSpaceRepository.countBySpaceStatus(SpaceStatus.INACTIVE);
        long fullCapacityCount = colivingSpaceRepository.countBySpaceStatus(SpaceStatus.FULL_CAPACITY);
        long notApprovedCount = colivingSpaceRepository.countBySpaceStatus(SpaceStatus.NOT_APPROVED);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("closedCount", closedCount);
        model.addAttribute("pendingForApprovalCount", pendingForApprovalCount);
        model.addAttribute("suspendedCount", suspendedCount);
        model.addAttribute("inactiveCount", inactiveCount);
        model.addAttribute("fullCapacityCount", fullCapacityCount);
        model.addAttribute("notApprovedCount", notApprovedCount);
        return "/admin/overview";
    }

    @PostMapping("/spacemanager/addColivingSpace")
    public String addColivingSpace(ColivingSpace space, RedirectAttributes redirectAttributes) {
        space.setOverallRating(0.0d);
        String generatedCode = "CLS" + RandomStringUtils.randomNumeric(7);
        space.setAvailableDoubleOccupancyRooms(space.getTotalDoubleOccupancyRooms());
        space.setAvailableSingleOccupancyRooms(space.getTotalSingleOccupancyRooms());
        space.setAvailableTripleOccupancyRooms(space.getTotalTripleOccupancyRooms());
        space.setSpaceStatus(SpaceStatus.PENDING_APPROVAL);
        space.setSpaceCode(generatedCode);
        colivingSpaceRepository.save(space);
        redirectAttributes.addFlashAttribute("spaceAdded", "You've added a new space - " + generatedCode);
        return "redirect:/spacemanager/dashboard";
    }

    @GetMapping("/tenant/dashboard")
    public String tenantDashboard() {
        return "/tenant/dashboard";
    }

    @GetMapping("/spacemanager/dashboard")
    public String spaceManagerDashboard() {
        return "/spacemanager/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "/admin/dashboard";
    }

    @PostMapping("/admin/updateSpaceStatus")
    public String updateStatus(@RequestParam String spaceCode, @RequestParam String status, RedirectAttributes redirectAttributes) {
        ColivingSpace colivingSpace = colivingSpaceRepository.findBySpaceCode(spaceCode);
        LOG.info("Fetched this coliving space {}", colivingSpace);
        switch (status) {
            case "ACTIVE":
                colivingSpace.setSpaceStatus(SpaceStatus.ACTIVE);
                break;
            case "PENDING_APPROVAL":
                colivingSpace.setSpaceStatus(SpaceStatus.PENDING_APPROVAL);
                break;
            case "NOT_APPROVED":
                colivingSpace.setSpaceStatus(SpaceStatus.NOT_APPROVED);
                break;
        }
        colivingSpaceRepository.save(colivingSpace);
        redirectAttributes.addFlashAttribute("statusUpdate", "Status of " + spaceCode + " moved to " + status);
        return "redirect:/admin/pendingForApprovalRequests";
    }

    @GetMapping("/admin/pendingForApprovalRequests")
    public String pendingForApprovalRequests(Model model) {
        List<ColivingSpace> pendingSpaces = colivingSpaceRepository.findBySpaceStatus(SpaceStatus.PENDING_APPROVAL);
        List<String> pendingSpacesCodes = new ArrayList<>();
        for (ColivingSpace colivingSpace : pendingSpaces) {
            pendingSpacesCodes.add(colivingSpace.getSpaceCode() + " " + colivingSpace.getPropertyName());
        }
        model.addAttribute("pendingSpacesCodes", pendingSpacesCodes);
        List<String> spaceStatusValues = Arrays.asList("PENDING_APPROVAL", "ACTIVE", "NOT_APPROVED");
        model.addAttribute("spaceStatusValues", spaceStatusValues);
        return "/admin/pendingForApprovalRequests";
    }

}
