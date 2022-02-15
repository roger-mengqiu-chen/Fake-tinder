package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.PaymentRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping("/add")
    public ResponseEntity<JsonResponse> addPayment (@RequestBody PaymentRequest request){

        //TODO
        return null;

    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<JsonResponse> getPayment (@PathVariable String cardNumber){
        //TODO
        return null;
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updatePayment (@RequestBody PaymentRequest request) {
        //TODO
        return null;
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<JsonResponse> deletePayment (@PathVariable String cardNumber) {
        //ToDo
        return null;
    }
}
