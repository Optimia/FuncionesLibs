package org.optimia.libs;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public interface Functional {

    @FunctionalInterface
    interface TriFunctionIOException<T, P, K, U> {
        T apply(P a, K b, U c) throws IOException;
    }

    @FunctionalInterface
    interface TriFunctionEException<T, P, K, U> {
        T apply(P a, K b, U c) throws ExecutionException;
    }

    @FunctionalInterface
    interface TriFunctionException<T, P, K, U> {
        T apply(P a, K b, U c) throws ExecutionException, InterruptedException, IOException;
    }

    @FunctionalInterface
    interface BiFunctionIOException<T, P, K> {
        T apply(P a, K b) throws IOException, ClassNotFoundException;
    }

    @FunctionalInterface
    interface FunctionException<T, P> {
        T apply(P a) throws ExecutionException, InterruptedException, IOException;
    }

    @FunctionalInterface
    interface FunctionSuchAlgorithm<T, P> {
        T apply(P a) throws NoSuchAlgorithmException;
    }

    @FunctionalInterface
    interface BiFunctionException<T, P, K> {
        T apply(P a, K b) throws ExecutionException, InterruptedException, IOException;
    }

    @FunctionalInterface
    interface TriFunction<T, P, K, U> {
        T apply(P a, K b, U c);
    }

    @FunctionalInterface
    interface SupplierException<T> {
        T get() throws ExecutionException, InterruptedException, IOException;
    }

    @FunctionalInterface
    interface SupplierIOException<T> {
        T get() throws IOException;
    }

    @FunctionalInterface
    interface QuadFunctionException<T, P, K, U, R> {
        T apply(P a, K b, U c, R d) throws ExecutionException, InterruptedException, IOException;
    }

    @FunctionalInterface
    interface TriConsumer< P, K, U> {
        void accept(P a, K b, U c);
    }

    @FunctionalInterface
    interface QuadConsumer< P, K, U, R> {
        void accept(P a, K b, U c, R d);
    }
}
