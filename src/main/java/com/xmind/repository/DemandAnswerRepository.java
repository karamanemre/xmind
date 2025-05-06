package com.xmind.repository;

import com.xmind.entity.DemandAnswerEntity;
import com.xmind.entity.DemandEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandAnswerRepository extends JpaRepository<DemandAnswerEntity, Long> {

    DemandAnswerEntity findByDemand(DemandEntity demand);
}
