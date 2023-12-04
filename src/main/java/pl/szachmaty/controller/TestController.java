package pl.szachmaty.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Profile({"dev", "local"})
@CrossOrigin
@RestController
public class TestController {

    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getCorsStatus() {
        return ResponseEntity.ok(
                Map.of("status", "cors are working correctly")
        );
    }

}
