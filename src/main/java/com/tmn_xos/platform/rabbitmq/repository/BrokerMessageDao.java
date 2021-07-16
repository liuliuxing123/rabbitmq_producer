package com.tmn_xos.platform.rabbitmq.repository;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tmn_xos.platform.rabbitmq.domain.BrokerMessage;

/**
 * 
 */
@Repository
public interface BrokerMessageDao extends JpaRepository<BrokerMessage, Integer> {
	
	public BrokerMessage findByMessageId(String messageId);
	

	String sql = "SELECT * "
			+ " FROM "
			+ " broker_message m"
			+ " WHERE m.`status` = :status AND m.next_retry < :nextRetry";
    @Query(value = sql,nativeQuery = true)
    List<BrokerMessage> getBrokerMessage(@Param("status") String status, @Param("nextRetry") Date nextRetry);
    
    String sql1 = "UPDATE broker_message bm "
                  +" SET bm.try_count = bm.try_count + 1, "
                  +" bm.update_time = :updateTime "
                  +" WHERE bm.message_id =:brokerMessageId ";

    @Transactional
    @Modifying
    @Query(value = sql1,nativeQuery = true)
    public int update4TryCount(@Param("brokerMessageId")String brokerMessageId, @Param("updateTime")Date updateTime);
    
}






