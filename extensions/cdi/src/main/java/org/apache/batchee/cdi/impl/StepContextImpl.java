/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.batchee.cdi.impl;

import org.apache.batchee.cdi.scope.StepScoped;

import java.lang.annotation.Annotation;

import javax.enterprise.inject.Typed;

@Typed
public class StepContextImpl extends BaseContext {

    private ThreadLocal<Long> currentStepContext = new ThreadLocal<Long>();

    @Override
    public Class<? extends Annotation> getScope() {
        return StepScoped.class;
    }

    @Override
    protected Long currentKey() {
        return currentStepContext.get();
    }

    public void enterStep(final Long stepContextId) {
        currentStepContext.set(stepContextId);
    }

    public void exitStep() {
        Long stepContextId = currentKey();
        endContext(stepContextId);
        currentStepContext.set(null);
        currentStepContext.remove();
    }



}
