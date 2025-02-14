package com.Payvang.Login.Repositories;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.Payvang.Login.Constants.UserType;
import com.Payvang.Login.DataAccess.Models.LoginHistory;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

	 List<LoginHistory> findByEmailId(String userId);
	
	 @Query("SELECT lh FROM LoginHistory lh JOIN User su ON lh.emailId = su.emailId " +
		       "WHERE lh.emailId = :emailId AND su.userType = :userType ORDER BY lh.id DESC")
		Page<LoginHistory> findLoginHistoryByUser(@Param("emailId") String emailId,
		                                          @Param("userType") UserType userType,
		                                          Pageable pageable);
}
