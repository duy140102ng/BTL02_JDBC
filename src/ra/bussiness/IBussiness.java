package ra.bussiness;

import ra.entity.Product;

import java.util.List;

public interface IBussiness<T, K> {
    List<T> findAll();

    boolean create(T t);

    boolean update(T t);
    boolean updateStatus(T t);

    List<T> findByName(K k);

}
