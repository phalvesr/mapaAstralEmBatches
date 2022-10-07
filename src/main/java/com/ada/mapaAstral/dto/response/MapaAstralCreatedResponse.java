package com.ada.mapaAstral.dto.response;

import java.time.ZonedDateTime;

public record MapaAstralCreatedResponse (
    String code,
    ZonedDateTime uploadedAt
) {  }
