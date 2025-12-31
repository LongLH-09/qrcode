package com.example.qrcode.controller;

import com.example.qrcode.dto.QrCodeDTO;
import com.example.qrcode.service.impl.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qr-code")
public class QrCodeController {

    @Autowired
    private QrCodeService qrCodeService;

    @PostMapping("/generate")
    public ResponseEntity<byte[]> qrCode(@RequestBody QrCodeDTO dto) {
        if (dto.getData() == null || dto.getData().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        byte[] imageBytes = qrCodeService.generate(dto.getData());
        if (imageBytes == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageBytes.length);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
