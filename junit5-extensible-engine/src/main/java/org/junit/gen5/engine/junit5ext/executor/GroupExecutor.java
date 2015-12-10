/*
 * Copyright 2015 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.gen5.engine.junit5ext.executor;

import static org.junit.gen5.engine.junit5ext.executor.ExecutionContext.cloneContext;

import org.junit.gen5.engine.TestDescriptor;
import org.junit.gen5.engine.junit5ext.descriptor.GroupDescriptor;
import org.opentestalliance.TestAbortedException;
import org.opentestalliance.TestSkippedException;

public class GroupExecutor implements TestExecutor {
	private TestExecutorRegistry testExecutorRegistry;

	@Override
	public void setTestExecutorRegistry(TestExecutorRegistry testExecutorRegistry) {
		this.testExecutorRegistry = testExecutorRegistry;
	}

	@Override
	public boolean canExecute(ExecutionContext context) {
		return context.getTestDescriptor() instanceof GroupDescriptor;
	}

	@Override
	public void execute(ExecutionContext context) throws TestSkippedException, TestAbortedException, AssertionError {
		GroupDescriptor groupDescriptor = context.getTestDescriptor();
		for (TestDescriptor child : groupDescriptor.getChildren()) {
			ExecutionContext subContext = cloneContext(context).withTestDescriptor(child).build();
			testExecutorRegistry.executeAll(subContext);
		}
	}
}
