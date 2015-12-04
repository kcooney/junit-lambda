/*
 * Copyright 2015 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.gen5.engine.junit5.descriptor;

class JUnit5NestedClass extends JUnit5Class {

	private final Class<?> containerClass;

	JUnit5NestedClass(String uniqueId, Class<?> javaClass, Class<?> containerClass) {
		super(uniqueId, javaClass);
		this.containerClass = containerClass;
	}

	@Override
	void accept(Visitor visitor) {
		visitor.visitNestedClass(getUniqueId(), getJavaClass(), this.containerClass);
	}

	Class<?> getContainerClass() {
		return this.containerClass;
	}

}
