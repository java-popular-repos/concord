package com.walmartlabs.concord.runtime.v2.runner.vm;

/*-
 * *****
 * Concord
 * -----
 * Copyright (C) 2017 - 2024 Walmart Inc.
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

import com.walmartlabs.concord.svm.Command;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.fail;

public class CommandCopyTest {

    @Test
    public void makeSureAllCommandsImplementCopy() {
        var classesWithoutCopy = new Reflections("com.walmartlabs.concord.runtime.v2")
                .getSubTypesOf(Command.class).stream()
                .filter(klass -> !Modifier.isAbstract(klass.getModifiers()))
                .filter(klass -> {
                    try {
                        klass.getDeclaredMethod("copy");
                        return false;
                    } catch (NoSuchMethodException e) {
                        return true;
                    }
                })
                .toList();

        if (!classesWithoutCopy.isEmpty()) {
            classesWithoutCopy.forEach(klass -> System.out.println(klass.getName()));
            fail("Some commands missing copy() implementation.");
        }
    }
}
