package hamcrestAssertion;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Collects Hamcrest assertion failures for one test invocation.
 *
 * <p>Create a fresh local instance in a try-with-resources block. Each instance
 * belongs to its creating thread; separate invocations can collect failures
 * concurrently without sharing state. Custom matchers and examined values must
 * also avoid shared mutable state.
 *
 * <p>Only assertion errors from Hamcrest checks are collected. Other exceptions
 * propagate immediately. If the block throws, Java attaches any failure from
 * closing this collector as a suppressed exception on the original throwable.
 */
public final class HamcrestSoftAssertions implements AutoCloseable {

    private final Thread ownerThread = Thread.currentThread();
    private final List<AssertionError> failures = new ArrayList<>();
    private boolean closed;


    public <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        checkOwnerThread();
        if (closed) {
            throw new IllegalStateException("This Hamcrest soft assertion collector is already closed.");
        }
        Objects.requireNonNull(reason, "Assertion reason must not be null.");
        Objects.requireNonNull(matcher, "Hamcrest matcher must not be null.");

        try {
            MatcherAssert.assertThat(reason, actual, matcher);
        } catch (AssertionError failure) {
            failures.add(failure);
        }
    }

    /**
     * Reports failures in check order, retaining each original error and stack
     * trace as a suppressed exception. Closing again on the owner thread is a no-op.
     */
    @Override
    public void close() {
        checkOwnerThread();
        if (closed) {
            return;
        }
        closed = true;

        if (failures.isEmpty()) {
            return;
        }

        StringBuilder message = new StringBuilder("Hamcrest assertions failed (")
                .append(failures.size()).append("):");
        for (int i = 0; i < failures.size(); i++) {
            message.append(System.lineSeparator()).append(i + 1).append(") ")
                    .append(failures.get(i).getMessage());
        }

        AssertionError aggregate = new AssertionError(message.toString());
        failures.forEach(aggregate::addSuppressed);
        failures.clear();
        throw aggregate;
    }

    private void checkOwnerThread() {
        if (Thread.currentThread() != ownerThread) {
            throw new IllegalStateException(
                    "A Hamcrest soft assertion collector must be used and closed on its creating thread.");
        }
    }
}
