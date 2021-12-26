package zhami.com.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class MsgController {

    @GetMapping("/msg")
    @ResponseBody
    public String test() {
        return "hello_world";
    }

    @GetMapping("/test")
    public ResponseEntity<String> test2() {
        return new ResponseEntity<>("hello_world", HttpStatus.OK);
    }

}
