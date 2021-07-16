package com.tmn_xos.platform.rabbitmq.domain;

import java.io.Serializable;

import javax.persistence.*;



import java.util.Date;


/**
 * The persistent class for the m_user database table.
 * 
 */

@Entity
@Table(name="broker_message")
public class BrokerMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	 /**
     * 
     */
    @Id
    @Column(name="message_id")
    private String messageId;

    /**
     * 
     */
    @Column(name="message")
    private String message;

    /**
     * 
     */
    @Column(name="try_count")
    private Integer tryCount;

    /**
     * (0.送信中 1.成功 2.失敗)
     */
    @Column(name="status")
    private String status;

    /**
     * 次回リトライ時間
     */
    @Column(name="next_retry")
    private Date nextRetry;

    /**
     * 
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time")
    private Date createTime;

    /**
     * 
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="update_time")
    private Date updateTime;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getTryCount() {
		return tryCount;
	}

	public void setTryCount(Integer tryCount) {
		this.tryCount = tryCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getNextRetry() {
		return nextRetry;
	}

	public void setNextRetry(Date nextRetry) {
		this.nextRetry = nextRetry;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

    
    
}