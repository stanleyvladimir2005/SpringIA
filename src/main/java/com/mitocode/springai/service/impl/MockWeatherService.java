package com.mitocode.springai.service.impl;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class MockWeatherService implements Function<MockWeatherService.Request, MockWeatherService.Response> {

    public enum Unit {C, F}
    public record Request(String location, Unit unit){}
    public record Response(double temp, Unit unit){}

    public Response apply(Request request) {
        return new Response(30, Unit.C);
    }

    @Tool(name = "WeatherInfo", description = "Get the weather in a location")
    public String weatherInfo(String location) {
        return "The weather in " + location + " is 30 C";
    }

}
