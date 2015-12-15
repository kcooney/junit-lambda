/*
 * Copyright 2015 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.gen5.engine.junit5.resolver;

import org.junit.gen5.engine.TestDescriptor;
import org.junit.gen5.engine.TestPlanSpecification;

import java.util.List;

public interface TestResolverRegistry {
	void notifyResolvers(TestDescriptor parent, TestPlanSpecification testPlanSpecification);

	void notifyResolvers(List<TestDescriptor> parents, TestPlanSpecification testPlanSpecification);

	void register(TestResolver testResolver);
}
