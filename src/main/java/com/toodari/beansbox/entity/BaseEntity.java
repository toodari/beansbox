package com.toodari.beansbox.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 테이블로 생성되지 않는다.
@EntityListeners(value = {AuditingEntityListener.class})
// JPA 내부에서 객체가 생성/변경되는 것을 감시한다.
@Getter
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "regdate", updatable = false) // 수정x
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;

}

// 코드로 배우는 스프링부트 프로젝트 133p 참고