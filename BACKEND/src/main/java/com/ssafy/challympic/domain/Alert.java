package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Alert {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_no")
    private int alert_no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    private String alert_content;

    private boolean alert_confirm;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date alert_regDate;

}
