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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @since 5.0
 */
public interface TestDescriptor {

	/**
	 * Get the unique identifier (UID) for the described test.
	 *
	 * <p>Uniqueness must be guaranteed across an entire test plan,
	 * regardless of how many engines are used behind the scenes.
	 */
	String getUniqueId();

	String getDisplayName();

	Optional<? extends TestDescriptor> getParent();

	boolean isTest();

	default boolean isRoot() {
		return !getParent().isPresent();
	}

	Set<TestTag> getTags();

	Set<? extends TestDescriptor> getChildren();

	default Set<? extends TestDescriptor> allDescendants() {
		Set<TestDescriptor> all = new HashSet<>();
		all.addAll(getChildren());
		for (TestDescriptor child : getChildren()) {
			all.addAll(child.allDescendants());
		}
		return all;
	}

	default long countStaticTests() {
		AtomicLong staticTests = new AtomicLong(0);
		Visitor visitor = (descriptor, remove) -> {
			if (descriptor.isTest()) {
				staticTests.incrementAndGet();
			}
		};
		accept(visitor);
		return staticTests.get();
	}

	default boolean hasTests() {
		return (isTest() || getChildren().stream().anyMatch(TestDescriptor::hasTests));
	}

	default Optional<? extends TestDescriptor> findByUniqueId(String uniqueId) {
		if (getUniqueId().equals(uniqueId)) {
			return Optional.of(this);
		}
		// else
		return getChildren().stream().filter(
			testDescriptor -> testDescriptor.getUniqueId().equals(uniqueId)).findFirst();
	}

	interface Visitor {

		void visit(TestDescriptor descriptor, Runnable remove);
	}

	void accept(Visitor visitor);

	Optional<TestSource> getSource();

}
