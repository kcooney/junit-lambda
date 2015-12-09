/*
 * Copyright 2015 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.gen5.engine;

public interface TestEngine {

	default String getId() {
		return getClass().getCanonicalName();
	}

	default void initialize() {
	}

	TestDescriptor discoverTests(TestPlanSpecification specification);

	void execute(ExecutionRequest request);
}
