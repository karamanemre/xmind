package com.xmind.services.log;

import com.xmind.entity.LogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("FileLogService")
@RequiredArgsConstructor
public class FileLogService implements Log {
    @Override
    public void save(LogEntity logEntity) {
        // file log can be implemented
    }
}
