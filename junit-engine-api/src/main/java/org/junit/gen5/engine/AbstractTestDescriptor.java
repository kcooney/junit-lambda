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

import static java.util.Collections.emptySet;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.gen5.commons.util.Preconditions;

/**
 * @since 5.0
 */
public abstract class AbstractTestDescriptor implements MutableTestDescriptor {

	private final String uniqueId;

	private MutableTestDescriptor parent;

	private TestSource source;

	private final Set<MutableTestDescriptor> children = new LinkedHashSet<>();

	protected AbstractTestDescriptor(String uniqueId) {
		Preconditions.notBlank(uniqueId, "uniqueId must not be null or empty");
		this.uniqueId = uniqueId;
	}

	public Set<MutableTestDescriptor> allChildren() {
		Set<MutableTestDescriptor> all = new HashSet<>();
		all.addAll(this.children);
		for (MutableTestDescriptor child : this.children) {
			all.addAll(((AbstractTestDescriptor) child).allChildren());
		}
		return all;
	}

	@Override
	public final String getUniqueId() {
		return this.uniqueId;
	}

	@Override
	public Optional<MutableTestDescriptor> getParent() {
		return Optional.ofNullable(this.parent);
	}

	@Override
	public final void setParent(MutableTestDescriptor parent) {
		this.parent = parent;
	}

	@Override
	public void removeChild(MutableTestDescriptor child) {
		this.children.remove(child);
		child.setParent(null);
	}

	protected void removeFromHierarchy() {
		if (isRoot()) {
			throw new UnsupportedOperationException("You cannot remove the root of a hierarchy.");
		}
		this.parent.removeChild(this);
		this.children.clear();
	}

	@Override
	public Optional<? extends TestDescriptor> findByUniqueId(String uniqueId) {
		if (getUniqueId().equals(uniqueId)) {
			return Optional.of(this);
		}
		for (TestDescriptor child : this.children) {
			Optional<? extends TestDescriptor> result = child.findByUniqueId(uniqueId);
			if (result.isPresent()) {
				return result;
			}
		}
		return Optional.empty();
	}

	@Override
	public final void addChild(MutableTestDescriptor child) {
		Preconditions.notNull(child, "child must not be null");
		child.setParent(this);
		this.children.add(child);
	}

	@Override
	public final Set<MutableTestDescriptor> getChildren() {
		return Collections.unmodifiableSet(this.children);
	}

	protected final void setSource(TestSource source) {
		Preconditions.notNull(source, "test source must not be null");
		this.source = source;
	}

	@Override
	public void accept(Visitor visitor) {
		Runnable remove = this::removeFromHierarchy;
		visitor.visit(this, remove);
		new HashSet<>(getChildren()).forEach(child -> child.accept(visitor));
	}

	@Override
	public Set<TestTag> getTags() {
		return emptySet();
	}

	@Override
	public Optional<TestSource> getSource() {
		return Optional.ofNullable(this.source);
	}

	@Override
	public final boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (this.getClass() != other.getClass()) {
			return false;
		}
		TestDescriptor otherDescriptor = (TestDescriptor) other;
		return this.getUniqueId().equals(otherDescriptor.getUniqueId());
	}

	@Override
	public final int hashCode() {
		return this.uniqueId.hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + getUniqueId();
	}

}
