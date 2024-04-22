package com.example.UserModule.repo;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.UserModule.entity.UserTable;


@Repository
public interface UserTableRepository extends JpaRepository<UserTable, Integer> {
  UserTable findByEmail(String email);

  /**
   * Query to Update the Password in Database
   *
   * @param id
   * @param newPassword
   */
  @Modifying
  @Transactional
  @Query("UPDATE UserTable ut SET ut.password = :newPassword WHERE ut.id = :id")
  void updatePassword(@Param("id") Integer id, @Param("newPassword") String newPassword);

  /**
   * @param userId
   */
  @Modifying
  @Transactional
  @Query("DELETE FROM AuthenticationToken at WHERE at.user.id = :userId")
  void deleteRelatedRecordsInAuthenticationToken(@Param("userId") Integer userId);

  /**
   * @param userId
   */
  @Modifying
  @Transactional
  @Query("DELETE FROM UserInsights si WHERE si.user.id = :userId")
  void deleteRelatedRecordsInUserInsights(@Param("userId") Integer userId);

  /**
   * @param userId
   */
  @Modifying
  @Transactional
  @Query("DELETE FROM UserTable ut WHERE ut.id = :userId")
  void deleteUserAndRelatedRecords(@Param("userId") Integer userId);

}
