package com.xmind.repository;

import com.xmind.entity.DemandEntity;
import com.xmind.models.enums.DemandStatus;
import com.xmind.security.entity.UserEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandRepository extends JpaRepository<DemandEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE DemandEntity d SET d.status=:status WHERE d.id=:id")
    void updateStatus(@Param("status") DemandStatus demandStatus, @Param("id") Long id);

    @Query("SELECT d FROM DemandEntity d WHERE (:status IS NULL OR d.status = :status) AND d.user=:user")
    List<DemandEntity> getByParameters(@Param("status") DemandStatus status, Pageable pageable, UserEntity user);

    @Query("SELECT d FROM DemandEntity d WHERE (:status IS NULL OR d.status = :status)")
    List<DemandEntity> getByParameters(@Param("status") DemandStatus status, Pageable pageable);
}
