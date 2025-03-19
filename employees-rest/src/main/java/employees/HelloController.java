package employees;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String sayHello() {
        return "Hello Spring! " + LocalDateTime.now();
    }
}
