package com.tmn_xos.platform.rabbitmq.producer.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmn_xos.platform.rabbitmq.api.pojo.Message;
import com.tmn_xos.platform.rabbitmq.domain.BrokerMessage;
import com.tmn_xos.platform.rabbitmq.producer.broker.RabbitBroker;
import com.tmn_xos.platform.rabbitmq.producer.constant.BrokerMessageStatus;
import com.tmn_xos.platform.rabbitmq.repository.BrokerMessageDao;

import cn.hutool.json.JSONUtil;

public class ExcuteJob implements Job {

	private static final Logger log = LoggerFactory.getLogger(ExcuteJob.class);

	@Autowired
	private RabbitBroker rabbitBroker;
	@Autowired
	private BrokerMessageDao brokerMessageDao;

	private static final int MAX_RETRY_COUNT = 3;

	@Override
	public void execute(JobExecutionContext context) {
		try {
			System.out.println(context.getScheduler().getSchedulerInstanceId() + "--"
					+ new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
		} catch (SchedulerException e) {
			log.error("Job実行失敗", e);
		}

//		Example example = new Example(BrokerMessage.class);
//		example.createCriteria().andEqualTo("status", BrokerMessageStatus.SENDING.getStatus()).andLessThan("nextRetry",
//				new Date());
		// List<Map<String, BrokerMessage>>
		List<BrokerMessage> brokerMessages = brokerMessageDao.getBrokerMessage(BrokerMessageStatus.SENDING.getStatus(),
				new Date());

		log.info("【CheckMessageList】：{}", brokerMessages);

		Date now = new Date();
		brokerMessages.forEach(brokerMessage -> {

			String messageId = brokerMessage.getMessageId();

			// リトライ回数＞設定リトライ回数の場合、送信失敗
			if (brokerMessage.getTryCount() >= MAX_RETRY_COUNT) {
				log.info("【This is Max retry count】：{}", brokerMessage);
				BrokerMessage bm = new BrokerMessage();
				bm.setMessageId(messageId);
				bm.setMessage(brokerMessage.getMessage());
				bm.setStatus(BrokerMessageStatus.SEND_FAIL.getStatus());
				bm.setUpdateTime(now);
				bm.setNextRetry(brokerMessage.getNextRetry());
				bm.setTryCount(brokerMessage.getTryCount());
				bm.setCreateTime(brokerMessage.getCreateTime());

				brokerMessageDao.saveAndFlush(bm);
				log.warn(" -----送信失敗，メッセージID: {} -------", messageId);
			} else {
				log.info("【ReSend Message】：{}", brokerMessage);

				// try count更新
				brokerMessageDao.update4TryCount(messageId, now);
				// リトライ
				String message = brokerMessage.getMessage();
				rabbitBroker.reliantSend(JSONUtil.toBean(message, Message.class));
			}

		});
	}
}