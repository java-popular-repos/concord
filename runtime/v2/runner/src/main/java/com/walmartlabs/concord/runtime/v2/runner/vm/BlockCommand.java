package com.walmartlabs.concord.runtime.v2.runner.vm;

/*-
 * *****
 * Concord
 * -----
 * Copyright (C) 2017 - 2019 Walmart Inc.
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

import com.walmartlabs.concord.svm.Runtime;
import com.walmartlabs.concord.svm.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Adds specified {@link #commands} to the stack.
 */
public class BlockCommand implements Command {

    private static final long serialVersionUID = 1L;

    private final List<Command> commands;

    public BlockCommand(Command... commands) {
        this(Arrays.asList(commands));
    }

    public BlockCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public Command copy() {
        return new BlockCommand(commands.stream().map(Command::copy).toList());
    }

    @Override
    public void eval(Runtime runtime, State state, ThreadId threadId) {
        Frame frame = state.peekFrame(threadId);
        frame.pop();

        // sequential execution is very simple: we just need to add
        // each command of the block onto the stack

        List<Command> l = new ArrayList<>(commands);

        // to preserve the original order the commands must be added onto
        // the stack in the reversed order
        Collections.reverse(l);
        l.forEach(frame::push);
    }
}
