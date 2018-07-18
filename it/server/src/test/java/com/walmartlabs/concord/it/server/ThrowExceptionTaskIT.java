package com.walmartlabs.concord.it.server;

/*-
 * *****
 * Concord
 * -----
 * Copyright (C) 2017 - 2018 Walmart Inc.
 * -----
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =====
 */

import com.walmartlabs.concord.client.ProcessApi;
import com.walmartlabs.concord.client.ProcessEntry;
import com.walmartlabs.concord.client.StartProcessResponse;
import org.junit.Test;

import java.net.URI;

import static com.walmartlabs.concord.it.common.ITUtils.archive;
import static com.walmartlabs.concord.it.common.ServerClient.assertLog;
import static com.walmartlabs.concord.it.common.ServerClient.waitForCompletion;
import static org.junit.Assert.assertEquals;

public class ThrowExceptionTaskIT extends AbstractServerIT {

    @Test(timeout = 60000)
    public void testThrowException() throws Exception {
        URI uri = ThrowExceptionTaskIT.class.getResource("throwExceptionTask").toURI();
        byte[] payload = archive(uri, ITConstants.DEPENDENCIES_DIR);

        // start the process

        ProcessApi processApi = new ProcessApi(getApiClient());
        StartProcessResponse spr = start(payload);

        // wait for completion

        ProcessEntry pir = waitForCompletion(processApi, spr.getInstanceId());
        assertEquals(ProcessEntry.StatusEnum.FAILED, pir.getStatus());

        // check logs
        byte[] ab = getLog(pir.getLogFileName());
        assertLog(".*Catch that!.*", 2, ab);
    }

    @Test(timeout = 60000)
    public void testThrowExceptionMessage() throws Exception {
        URI uri = ThrowExceptionTaskIT.class.getResource("throwExceptionMessage").toURI();
        byte[] payload = archive(uri, ITConstants.DEPENDENCIES_DIR);

        // start the process

        ProcessApi processApi = new ProcessApi(getApiClient());
        StartProcessResponse spr = start(payload);

        // wait for completion

        ProcessEntry pir = waitForCompletion(processApi, spr.getInstanceId());
        assertEquals(ProcessEntry.StatusEnum.FAILED, pir.getStatus());

        // check logs
        byte[] ab = getLog(pir.getLogFileName());
        assertLog(".*Kaboom!! Error occurred.*", 3, ab);
    }
}
