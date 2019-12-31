package com.uhg.reports.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;
@Component

public class ReportsChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
    	System.out.println("beforeChunk : {}"+ context);
    }

    @Override
    public void afterChunk(ChunkContext context) {
    	System.out.println("afterChunk : {}"+ context);
    }

    @Override
    public void afterChunkError(ChunkContext context) {
    	System.out.println("afterChunkError : {}"+ context);
    }
}
