package com.example.qrcode.service.impl;

import com.example.qrcode.service.IQrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Hashtable;

@Slf4j
@Service
public class QrCodeService implements IQrCodeService {

    @Override
    public byte[] generate(String data) {
        try {
            int size = 360;
            Hashtable<EncodeHintType, Comparable> hintMap = new Hashtable<>();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hintMap.put(EncodeHintType.MARGIN, 1);

            BitMatrix byteMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, size, size, hintMap);

            BufferedImage qrImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            qrImage.createGraphics();

            Graphics2D graphics = (Graphics2D) qrImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, size, size);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

//            //Lay anh logo
//            InputStream is = getClass().getClassLoader().getResourceAsStream("images" + File.separator + "logo.jpg");
//            if (is == null) {
//                throw new FileNotFoundException("Logo qrImage not found!");
//            }
//            BufferedImage logo = ImageIO.read(is);
//            //Resize logo (20% kich thuoc qr code)
//            int logoWidth = size / 5;
//            int logoHeight = size / 5;
//            Image scaledLogo = logo.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
//
//            //Chen logo vao anh qr
//            int centerX = (qrImage.getWidth() - logoWidth) / 2;
//            int centerY = (qrImage.getHeight() - logoHeight) / 2;
//            graphics.drawImage(scaledLogo, centerX, centerY, null);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (ImageIO.write(qrImage, "png", os)) return os.toByteArray();
            return null;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

}
