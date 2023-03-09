package com.siit.finalproject.order;

import com.siit.finalproject.dto.DestinationResponse;
import com.siit.finalproject.dto.OrderDto;
import com.siit.finalproject.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;


@RestController
@RequestMapping("/order")
public class OrderController
{
    private final OrderService service;
    private final DestinationResponse destinationResponse;

    public OrderController(OrderService service, DestinationResponse destinationResponse) {
        this.service = service;
        this.destinationResponse = destinationResponse;
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadDestinationCsv(@RequestParam(name = "filePath") String filePath)
    {
        try
        {   BufferedReader file = new BufferedReader(new FileReader(filePath));
            service.saveOrders(file);

        } catch (Exception e) {
            return new ResponseEntity<>("Please select a file with orders to upload. ", HttpStatus.BAD_REQUEST);
        }
        if(destinationResponse.getResponse().size() >= 1)
        {
            return new ResponseEntity<>("The following destinations are not in DB "
                    + destinationResponse.getResponse().toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Orders file is loading. ", HttpStatus.OK);
    }

    @PostMapping("/add")
    public void addOrder(@Valid @RequestBody OrderDto orderDto){

        Long orderAdd = service.addOrder(orderDto);
    }

//    @PutMapping("/current-date")
//    public ResponseEntity<Resource> updateResource(@PathVariable Long id, @RequestBody Resource updatedResource, @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date currentDate) {
//        // logic to update the resource using the updatedResource object, the id parameter, and the currentDate parameter
//        Resource savedResource = .updateResource(id, updatedResource, currentDate);
//
//        return ResponseEntity.ok(savedResource);
//    }
}
