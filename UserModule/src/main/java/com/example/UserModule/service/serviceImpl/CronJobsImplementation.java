package com.example.UserModule.service.serviceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.UserModule.entity.UserInsights;
import com.example.UserModule.entity.UserTable;
import com.example.UserModule.helper.Constants;
import com.example.UserModule.helper.UserActivity;
import com.example.UserModule.repo.UserInsightsRepository;
import com.example.UserModule.repo.UserTableRepository;

import lombok.extern.slf4j.Slf4j;
@Lazy
@Slf4j
@Service
public class CronJobsImplementation {
  @Autowired
  UserTableRepository userRepository;
  @Autowired
  UserInsightsRepository userInsightsRepository;

  @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Kolkata")
  public void resetAttempts() {
    List<UserTable> users = userRepository.findAll();
    List<Integer> ids = users.stream().map(UserTable::getId).collect(Collectors.toList());
    userInsightsRepository.updatePasswordResetAttempts(ids, Constants.PASSWORD_ATTEMPTS);
  }

  @Scheduled(cron = "0 40 16 * * ?", zone = "Asia/Kolkata")
  @Transactional
  public void updateUserStatus() {
    log.info("Invoking the Function to Update the User Status");
    List<UserInsights> userInsightsList = userInsightsRepository.findAll();
    List<UserInsights> updatedUserInsightsList =
        userInsightsList.stream().map(this::updateUserActivity).collect(Collectors.toList());
    userInsightsRepository.saveAll(updatedUserInsightsList);
  }

  private UserInsights updateUserActivity(UserInsights userInsights) {
    Date lastLoginDate = userInsights.getLastLoginDate();
    String activity = isUserActive(lastLoginDate);
    userInsights.setUserActivity(activity);
    return userInsights;
  }

  private String isUserActive(Date lastLoginDate) {
    LocalDateTime lastLoginDateTime = lastLoginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    LocalDateTime currentDateTime = LocalDateTime.now();
    long secondsDifference = ChronoUnit.SECONDS.between(lastLoginDateTime, currentDateTime);
    return (secondsDifference <= 604800) ? UserActivity.ACTIVE.getName() : UserActivity.DORMANT.getName();
  }
}
