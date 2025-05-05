package com.xmind.services.log;

import com.xmind.entity.LogEntity;
import com.xmind.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("DbLogService")
@RequiredArgsConstructor
public class DbLogService implements Log {

    private final LogRepository logRepository;

    @Override
    public void save(LogEntity logEntity) {
        this.logRepository.save(logEntity);
    }
}
