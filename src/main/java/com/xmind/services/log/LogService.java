package com.xmind.services.log;

import com.xmind.entity.LogEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    private final Log log;

    public LogService(@Qualifier("DbLogService") Log log) {
        this.log = log;
    }

    @Async
    public void save(LogEntity logEntity) {
        this.log.save(logEntity);
    }
}
