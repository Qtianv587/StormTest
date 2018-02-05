package elasticsearch;

public interface ESJavaClient {
    <T> void put(T object,Class<T> klass);
}
