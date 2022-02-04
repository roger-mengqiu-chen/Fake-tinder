package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.PaymentRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping("/add")
    public JsonResponse addPayment (@RequestBody PaymentRequest request){

        //TODO
        return null;

    }

    @GetMapping("/{cardNumber}")
    public JsonResponse getPayment (@PathVariable String cardNumber){
        //TODO
        return null;
    }

    @PutMapping()
    public JsonResponse updatePayment (@RequestBody PaymentRequest request) {
        //TODO
        return null;
    }

    @DeleteMapping("/{paymentId}")
    public JsonResponse deletePayment (@PathVariable String cardNumber) {
        //ToDo
        return null;
    }
}
