package com.walmartlabs.concord.server.process.pipelines.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walmartlabs.concord.common.Constants;
import com.walmartlabs.concord.server.process.Payload;
import com.walmartlabs.concord.server.process.ProcessException;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

/**
 * Stores payload's request data as a JSON file.
 */
public class RequestDataStoringProcessor implements PayloadProcessor {

    @Override
    public Payload process(Payload payload) {
        Path workspace = payload.getHeader(Payload.WORKSPACE_DIR);
        Path dst = workspace.resolve(Constants.REQUEST_DATA_FILE_NAME);

        Map<?, ?> meta = payload.getHeader(Payload.REQUEST_DATA_MAP);
        if (meta == null) {
            meta = Collections.emptyMap();
        }

        ObjectMapper om = new ObjectMapper();
        try (Writer writer = Files.newBufferedWriter(dst)) {
            om.writeValue(writer, meta);
        } catch (IOException e) {
            throw new ProcessException("Error while saving a metadata file: " + dst, e);
        }

        return payload;
    }
}
