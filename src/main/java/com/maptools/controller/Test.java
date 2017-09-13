package com.maptools.controller;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import static javax.print.attribute.ResolutionSyntax.DPI;

/**
 * Created by mr on 2017/8/29.
 */
public class Test {

    private BufferedImage gridImage;

    public static void main(String[] args) throws Exception {
    File infile = new File("D:/test/baidu.jpg");
    File outfile = new File("D:/test/b.jpg");

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

    public static BufferedImage toBufferedImage(Image image, int type) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        BufferedImage result = new BufferedImage(w, h, type);
        Graphics2D g = result.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return result;
    }

    private void saveGridImage(File output) throws IOException {
        output.delete();

        final String formatName = "png";

        for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(formatName); iw.hasNext();) {
            ImageWriter writer = iw.next();
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
            IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
            if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
                continue;
            }

            setDPI(metadata);

            final ImageOutputStream stream = ImageIO.createImageOutputStream(output);
            try {
                writer.setOutput(stream);
                writer.write(metadata, new IIOImage(gridImage, null, metadata), writeParam);
            } finally {
                stream.close();
            }
            break;
        }
    }

    private void setDPI(IIOMetadata metadata) throws IIOInvalidTreeException {

        // for PMG, it's dots per millimeter
        double dotsPerMilli = 1.0 * DPI / 10 / 2.54;

        IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
        horiz.setAttribute("value", Double.toString(dotsPerMilli));

        IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
        vert.setAttribute("value", Double.toString(dotsPerMilli));

        IIOMetadataNode dim = new IIOMetadataNode("Dimension");
        dim.appendChild(horiz);
        dim.appendChild(vert);

        IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
        root.appendChild(dim);
        metadata.mergeTree("javax_imageio_1.0", root);
    }

}
