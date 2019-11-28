package epam.itnight.plp.util;

import io.grpc.stub.StreamObserver;
import reactor.core.publisher.MonoSink;

public class StreamObserverAdapter<T> implements StreamObserver<T> {

  private final MonoSink<T> delegate;

  public StreamObserverAdapter(MonoSink<T> delegate) {
    this.delegate = delegate;
  }

  @Override
  public void onNext(T value) {
    delegate.success(value);
  }

  @Override
  public void onError(Throwable t) {
    delegate.error(t);
  }

  @Override
  public void onCompleted() {
  }
}
