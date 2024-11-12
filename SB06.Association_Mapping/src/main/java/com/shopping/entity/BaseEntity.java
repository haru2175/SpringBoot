package com.shopping.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// @EntityListeners는 엔터티 리스너를 생성하고자 할 때 사용하는 어노테이션입니다.
// AuditingEntityListener : Entity 영속성 및 업데이트에 대한 감사(Audition) 정보를 캡쳐해주는 JPA Entity Listener입니다.
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass // 부모 클래스에 정의를 해 놓고, 서브 클래스에 대하여 매핑 정보를 제공해 줍니다.
@Getter @Setter
public abstract class BaseEntity {
    @CreatedBy // Entity 생성자의 사용자 id 정보를 기록하겠습니다.
    @Column(updatable = false) // Entity 수정시 이 컬럼은 갱신하지 않겠습니다.
    private String createdBy ; // 등록,생성한 사람 정보

    @LastModifiedBy // Entity 수정자의 사용자 id 정보를 기록하겠습니다.
    private String modifiedBy ; // 수정한 사람 정보

    @CreatedDate // Entity 생성시 자동으로 시간을 정보를 기록하겠습니다.
    @Column(updatable = false)
    private LocalDateTime regDate ; // 최초 등록된 시간

    @LastModifiedDate // Entity 수정시 자동으로 시간 정보를 기록하겠습니다.
    private LocalDateTime updateDate ; // 수정된 시간

}
