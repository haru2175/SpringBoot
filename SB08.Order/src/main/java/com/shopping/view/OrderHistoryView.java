package com.shopping.view;

import com.shopping.constant.OrderStatus;
import com.shopping.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistoryView {
    private Long orderId ; // 주문 아이디(송장 번호)
    private String orderDate ; // 주문 일자
    private OrderStatus orderStatus ; // 주문 상태(주문, 주문 취소) 정보

    // 주문 상품 리스트 : 한번 주문시 물건은 여러 개 구매 가능합니다.
    private List<OrderProductView> opvList = new ArrayList<>();

    public void addOrderProductView(OrderProductView opv){
        this.opvList.add(opv) ;
    }

    public OrderHistoryView(Order order) {
        this.orderId = order.getId() ;
        String pattern = "yyyy-MM-dd HH:mm";
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern(pattern));
        this.orderStatus = order.getOrderStatus() ;
    }
}
