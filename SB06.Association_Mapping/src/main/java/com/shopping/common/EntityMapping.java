package com.shopping.common;

import com.shopping.entity.Member;
import com.shopping.entity.Order;
import com.shopping.entity.OrderProduct;
import com.shopping.entity.Product;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.OrderProductRepository;
import com.shopping.repository.OrderRepository;
import com.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityMapping {
    @Autowired
    protected ProductRepository pr ;

    @Autowired
    protected OrderRepository or ;

    @Autowired
    protected MemberRepository mr ;

    @Autowired
    protected OrderProductRepository opr ;

    @PersistenceContext
    protected EntityManager em ; // 엔터티 관리자

    protected Order createOrder() {
        Order order = new Order();
        for (int i = 0; i < 3; i++) {
            Product product = GenerateData.createProduct(true, i);
            pr.save(product);

            OrderProduct op = new OrderProduct();
            op.setProduct(product);
            op.setCount(10);
            op.setPrice(1000);
            op.setOrder(order);

            order.getOrderProducts().add(op);
        }

        Member member = new Member();
        mr.save(member);

        order.setMember(member);
        or.save(order);

        return order;
    }
}
