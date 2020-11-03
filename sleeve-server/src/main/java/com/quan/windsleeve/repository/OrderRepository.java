package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

    /**
     * 查找指定用户所有待支付的订单
     * @param now 当前时间
     * @param userId 用户id
     * @param status 订单状态
     * @return
     * 在使用Spring Data JPA 时，无论是使用jpa自带的查询语句，还是使用自定义sql，在使用分页查询时
     * 都可以将Pageable当作参数使用
     */
    @Query(value = "select o from Orders o \n" +
            " where o.userId = :userId and o.status = :status and o.expireTime > :now")
    Page<Orders> findWaitPayOrders(@Param("now") Date now, @Param("userId") Long userId,
                                   @Param("status") Integer status, Pageable pageable);

    /**
     * 获取当前用户的所有订单
     * @param userId
     * @param pageable
     * @return
     */
    Page<Orders> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    /**
     * 根据状态获取相应订单集合
     * @param userId
     * @param status
     * @param pageable
     * @return
     */
    Page<Orders> findByUserIdAndStatusOrderByCreateTimeDesc(@Param("userId") Long userId,
                                                            @Param("status") Integer status,
                                                            Pageable pageable);

    /**
     * 根据orderId查询指定的订单
     * @param id
     * @return
     */
    Orders findOneById(Long id);

    /**
     * 根据订单id和userId查询指定的订单
     * @param id
     * @param userId
     * @return
     */
    Optional<Orders> findOneByIdAndUserId(Long id, Long userId);

    /**
     * 通过orderId 修改当前订单的prepayId号
     * @param orderId
     * @param userId
     * @param prepayId
     * @return
     */
    @Modifying
    @Query("update Orders o set o.prepayId = :prepayId\n" +
            " where o.id = :orderId and o.userId = :userId ")
    int updatePrepayIdByOrderId(@Param("orderId") Long orderId,
                                @Param("userId") Long userId,
                                @Param("prepayId") String prepayId);

    /**
     * 将订单的状态从"待支付"-->"已取消"
     * @param orderId
     * @return
     */
    @Modifying
    @Query("update Orders o set o.status = 5 \n" +
            " where o.status = 1 and o.id = :orderId and o.userId = :userId")
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("userId") Long userId);

    /**
     * 将订单的状态从"待支付"-->"已支付"
     * @param orderId
     * @param userId
     * @return
     */
    @Modifying
    @Query("update Orders o set o.status = 2 \n" +
            " where o.status = 1 and o.id = :orderId and o.userId = :userId")
    int updateOrderStatusToAlreadyPay(@Param("orderId") Long orderId, @Param("userId") Long userId);
}
