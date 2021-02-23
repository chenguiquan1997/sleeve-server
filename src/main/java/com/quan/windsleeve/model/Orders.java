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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;
    private Long userId;
    private Integer totalCount;
    private BigDecimal totalPrice;
    private BigDecimal finalTotalPrice;
    private String snapImg;
    private String snapTitle;
    private String snapItems;
    private String snapAddress;
    private String prepayId;
    private Integer status;
    private Date expireTime;
    private Date placedTime;

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
