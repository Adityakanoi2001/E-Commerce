package com.example.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.ecommerce.entity.UserTable;
import java.util.List;


@Repository
public interface UserTableRepository extends JpaRepository <UserTable,Integer>
{
    UserTable findByEmail(String email);
}
