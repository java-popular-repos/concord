package com.walmartlabs.concord.runtime.v2.runner.vm;

/*-
 * *****
 * Concord
 * -----
 * Copyright (C) 2017 - 2020 Walmart Inc.
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

import com.walmartlabs.concord.runtime.v2.model.ExitStep;
import com.walmartlabs.concord.runtime.v2.runner.SynchronizationService;
import com.walmartlabs.concord.svm.Command;
import com.walmartlabs.concord.svm.Runtime;
import com.walmartlabs.concord.svm.State;
import com.walmartlabs.concord.svm.ThreadId;

public class ExitCommand extends StepCommand<ExitStep> {

    private static final long serialVersionUID = 1L;

    public ExitCommand(ExitStep step) {
        super(step);
    }

    @Override
    public Command copy() {
        return new ExitCommand(getStep());
    }

    @Override
    protected void execute(Runtime runtime, State state, ThreadId threadId) {
        runtime.getService(SynchronizationService.class).stop();
    }
}
