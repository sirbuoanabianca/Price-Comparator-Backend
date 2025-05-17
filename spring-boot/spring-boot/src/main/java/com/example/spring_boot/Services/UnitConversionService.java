package com.example.spring_boot.Services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;


@Service
public class UnitConversionService {

    private Map<String, String> standardUnit = new HashMap<>();
    private Map<String, BigDecimal> standardConversion = new HashMap<>();

    @PostConstruct
    public void initialize() {
        standardUnit.put("g", "kg");
        standardUnit.put("ml", "l");
        standardUnit.put("pachet", "buc");
        standardUnit.put("role", "buc");

        standardConversion.put("g_kg", new BigDecimal("0.001"));
        standardConversion.put("ml_l", new BigDecimal("0.001"));
        standardConversion.put("pachet_buc", BigDecimal.ONE);
        standardConversion.put("role_buc", BigDecimal.ONE);
    }

    public String getStandardUnit(String unit) {
        return standardUnit.getOrDefault(unit, unit);
    }

    public BigDecimal getConversionRate(String unit) {
        String resolvedStandardUnit = getStandardUnit(unit);
        String conversionKey = unit + "_" + resolvedStandardUnit;
        return standardConversion.getOrDefault(conversionKey, BigDecimal.ONE);
    }

}