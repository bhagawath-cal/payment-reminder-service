package com.paymentreminder.service.web;

import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthz")
public class HealthController {
  @GetMapping
  public Map<String, String> health() { return Map.of("status", "UP"); }
}
