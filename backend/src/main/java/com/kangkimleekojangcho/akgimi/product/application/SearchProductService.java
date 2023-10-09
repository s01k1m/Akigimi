package com.kangkimleekojangcho.akgimi.product.application;

import com.kangkimleekojangcho.akgimi.product.application.port.QueryProductDbPort;
import com.kangkimleekojangcho.akgimi.product.application.request.BunchOfSearchProductServiceRequest;
import com.kangkimleekojangcho.akgimi.product.application.response.BunchOfSearchProductServiceResponse;
import com.kangkimleekojangcho.akgimi.product.application.response.SearchProductResponse;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchProductService {

    private final QueryProductDbPort queryProductDbPort;

    public BunchOfSearchProductServiceResponse search(
            BunchOfSearchProductServiceRequest serviceRequest
    ) {
        String name = serviceRequest.getName();
        Integer startMoney = serviceRequest.getStartMoney();
        Integer endMoney = serviceRequest.getEndMoney();
        List<Product> products = queryProductDbPort.findByNameAndStartMoneyAndendMoney(name, startMoney, endMoney);
        List<SearchProductResponse> responses = new ArrayList<>();
        for(Product product : products){
            responses.add(
                    SearchProductResponse.builder()
                            .Id(product.getId())
                            .image(product.getImage())
                            .name(product.getName())
                            .price(product.getPrice())
                            .build()
            );
        }
        return BunchOfSearchProductServiceResponse.builder()
                .searchProductResponseList(responses)
                .build();
    }
}
