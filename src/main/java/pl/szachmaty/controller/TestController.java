package pl.szachmaty.controller;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import java.util.Map;

@Profile({"dev", "local"})
@CrossOrigin
@Controller
public class TestController {

//    @GetMapping("/test")
//    @ResponseBody
//    ResponseEntity<Map<String, String>> getCorsStatus() {
//        return ResponseEntity.ok(
//                Map.of("status", "cors are working correctly")
//        );
//    }
    private final SimpUserRegistry simpUserRegistry;

    public TestController(SimpUserRegistry simpUserRegistry) {
        this.simpUserRegistry = simpUserRegistry;
    }

    @MessageMapping("/authentication")
    void authenticate(Map<String, String> request, Principal principal) {
        System.out.println(principal);
        simpUserRegistry.getUsers();
    }

}
