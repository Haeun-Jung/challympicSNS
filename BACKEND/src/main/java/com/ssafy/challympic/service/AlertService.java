package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Alert;
import com.ssafy.challympic.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;

    public void saveAlert(Alert alert) {
        alertRepository.saveAlert(alert);
    }

    public List<Alert> findAlertByUserNo(int userNo) {
        return alertRepository.findAlertByUserNo(userNo);
    }
}
