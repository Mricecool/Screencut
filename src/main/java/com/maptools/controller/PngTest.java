package com.maptools.controller;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by mr on 2017/8/30.
 */
public class PngTest {

    public static void main(String[] args) throws Exception {
        File infile = new File("D:/test/p.png");
        File outfile = new File("D:/test/p1.png");

        ImageReader reader = ImageIO.getImageReadersByFormatName("jpeg").next();
        reader.setInput(new FileImageInputStream(infile), true, false);
        IIOMetadata data = reader.getImageMetadata(0);
        BufferedImage image = ImageIO.read(infile);

        int w = 2550, h = -1;


        Element tree = (Element) data.getAsTree("javax_imageio_jpeg_image_1.0");
        Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
        for (int i = 0; i < jfif.getAttributes().getLength(); i++) {
            Node attribute = jfif.getAttributes().item(i);
            System.out.println(attribute.getNodeName() + "="
                    + attribute.getNodeValue());
        }
        FileOutputStream fos = new FileOutputStream(outfile);
        JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
        JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(image);
        jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
        jpegEncodeParam.setXDensity(300);
        jpegEncodeParam.setYDensity(300);
        jpegEncoder.encode(image, jpegEncodeParam);
        fos.close();
    }

}
