package com.tadaah.freelancer.service;

import com.tadaah.freelancer.dao.NotificationRepository;
import com.tadaah.freelancer.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "freelancer-topic", groupId = "group_id")
    public void consume(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notificationRepository.save(notification);
    }
}
