package com.quan.windsleeve.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.quan.windsleeve.core.enums.OrderStatus;
import com.quan.windsleeve.dto.OrderAddressDTO;
import com.quan.windsleeve.util.GenericAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
public class Orders extends BaseEntity{
    /**
     * 数据库自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 用户 id
     */
    private Long userId;
    /**
     * 订单中商品的总数量
     */
    private Integer totalCount;
    /**
     * 订单应付总金额
     */
    private BigDecimal totalPrice;
    /**
     * 订单实付总金额
     */
    private BigDecimal finalTotalPrice;
    /**
     * 订单主图
     */
    private String snapImg;
    /**
     * 订单标题-用于小程序用户端的订单信息展示
     */
    private String snapTitle;
    /**
     * 商品概要标题-用于CMS端订单列表中的概要展示
     */
    private String summaryTitle;
    /**
     * 订单中的商品详情信息
     */
    private String snapItems;
    /**
     * 用户收货的信息，包括地址，联系方式，收货人等
     */
    private String snapAddress;
    /**
     * 微信支付时，预支付id
     */
    private String prepayId;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 订单过期时间
     */
    private Date expireTime;
    /**
     * 下单时间
     */
    private Date placedTime;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 联系电话
     */
    private String phone;

    public List<OrderSku> getSnapItems() {
        List<OrderSku> list = GenericAndJson.jsonToObject(this.snapItems,
                new TypeReference<List<OrderSku>>() {
                });
        return list;
    }

    public OrderAddressDTO getSnapAddress() {
        if (this.snapAddress == null) {
            return null;
        }
        OrderAddressDTO o = GenericAndJson.jsonToObject(this.snapAddress,
                new TypeReference<OrderAddressDTO>() {
                });
        return o;
    }

    public void setSnapAddress(OrderAddressDTO address) {
        this.snapAddress = GenericAndJson.objectToJson(address);
    }

    public void setSnapItems(List<OrderSku> orderSkuList) {
        if (orderSkuList.isEmpty()) {
            return;
        }
        this.snapItems = GenericAndJson.objectToJson(orderSkuList);
    }

    /**
     * 当用户支付当前订单时，需要对当前订单的状态、是否过期做校验
     * @return
     */
    public boolean payOrderValidate() {
        //如果当前订单的支付状态 != 待支付，就不允许支付
       if(!this.getStatus().equals(OrderStatus.UNPAID.getCode())) {
           return false;
       }
       //判断当前订单是否过期
        if(this.getExpireTime().before(new Date())) {
            return false;
        }
        return true;
    }
}
