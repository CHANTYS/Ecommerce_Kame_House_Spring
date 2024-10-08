package com.KameHouse.ecom.repo;

import com.KameHouse.ecom.entity.User;
import com.KameHouse.ecom.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);

    User findByRole(UserRole role);

    List<User> findAllByRole(UserRole role);


}
