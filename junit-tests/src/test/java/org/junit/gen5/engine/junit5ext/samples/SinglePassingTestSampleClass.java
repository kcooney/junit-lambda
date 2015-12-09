/*
 * Copyright 2015 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.gen5.engine.junit5ext.samples;

import org.junit.gen5.api.Test;

public class SinglePassingTestSampleClass {
	@Test
	void singlePassingTest() throws Exception {
		System.out.println("Test got executed!");
	}
}
