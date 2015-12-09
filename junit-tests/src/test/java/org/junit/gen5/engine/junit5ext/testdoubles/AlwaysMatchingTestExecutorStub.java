package org.junit.gen5.engine.junit5ext.testdoubles;

import org.junit.gen5.engine.TestDescriptor;
import org.junit.gen5.engine.junit5ext.executor.TestExecutor;
import org.opentestalliance.TestAbortedException;
import org.opentestalliance.TestSkippedException;

public class AlwaysMatchingTestExecutorStub implements TestExecutor {
    @Override
    public boolean canExecute(TestDescriptor testDescriptor) {
        return true;
    }

    @Override
    public void execute(TestDescriptor testDescriptor) throws TestSkippedException, TestAbortedException, AssertionError {

    }
}
