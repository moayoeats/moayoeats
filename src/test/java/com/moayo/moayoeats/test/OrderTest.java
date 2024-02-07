package com.moayo.moayoeats.test;

import static com.moayo.moayoeats.test.MenuTest.TEST_MENUS;

import com.moayo.moayoeats.backend.domain.order.dto.response.OrderResponse;
import java.util.ArrayList;
import java.util.List;

public interface OrderTest extends CommonTest {

    Long TEST_ORDER_ID = 1L;
    String TEST_ORDER_STORE = "store";

    OrderResponse TEST_ORDER_RES =
        OrderResponse.builder()
            .id(TEST_ORDER_ID)
            .store(TEST_ORDER_STORE)
            .receiverName(TEST_USER_NICKNAME)
            .menus(TEST_MENUS)
            .build();

    List<OrderResponse> TEST_ORDERS = new ArrayList<>() {
        {
            add(TEST_ORDER_RES);
        }
    };
}