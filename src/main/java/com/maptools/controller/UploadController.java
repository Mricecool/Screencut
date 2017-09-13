package com.maptools.controller;

import com.maptools.utils.FileUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 * Created by mr on 2017/8/29.
 */
@Controller
public class UploadController {

    @RequestMapping(value = "/upload2", method = {RequestMethod.POST})
    @ResponseBody
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

        //获取服务器地址
        ServletContext sc = request.getSession().getServletContext();
        String filePath = sc.getRealPath("/upload/");
        //如果文件夹不存在，则创建文件夹
        if (!new File(filePath).exists()) {
            new File(filePath).mkdirs();
        }
        String fileName = UUID.randomUUID().toString()+".jpg";
        String imgFilePath = filePath  + fileName;

        byte[] buffer = new byte[1024 * 1024];

        InputStream input = request.getInputStream();
        OutputStream output = new FileOutputStream(imgFilePath);
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1){
            output.write(buffer, 0, bytesRead);
        }
        output.close();
        input.close();
        response.getOutputStream().print(fileName);
    }


    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    @ResponseBody
    public String petUpgradeTarget(HttpServletRequest request, String data) {
        Base64 base64 = new Base64();
        try {
            //注意点：实际的图片数据是从 data:image/jpeg;base64, 后开始的
            byte[] k = base64.decode(data.substring(23));
            InputStream is = new ByteArrayInputStream(k);

            //获取服务器地址
            ServletContext sc = request.getSession().getServletContext();
            String filePath = sc.getRealPath("/upload/");
            //如果文件夹不存在，则创建文件夹
            if (!new File(filePath).exists()) {
                new File(filePath).mkdirs();
            }
            String fileName = UUID.randomUUID().toString()+".jpg";
            FileUtil.upFile(is, fileName, filePath);

//            if(file.exists()){
//                String outFile= serverPath + "\\upload\\out\\" + fileName + ".jpg";
//                File of = new File(outFile);
//                if(!of.exists()){
//                    of.mkdirs();
//                }
//                ImageReader reader = ImageIO.getImageReadersByFormatName("jpeg").next();
//                reader.setInput(new FileImageInputStream(file), true, false);
//                IIOMetadata d = reader.getImageMetadata(0);
//                BufferedImage img = ImageIO.read(file);
//
//                int w = 2550, h = -1;
//
//
//                Element tree = (Element) d.getAsTree("javax_imageio_jpeg_image_1.0");
//                Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
//                for (int i = 0; i < jfif.getAttributes().getLength(); i++) {
//                    Node attribute = jfif.getAttributes().item(i);
//                    System.out.println(attribute.getNodeName() + "="
//                            + attribute.getNodeValue());
//                }
//                FileOutputStream fos = new FileOutputStream(of);
//                JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
//                JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(img);
//                jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
//                jpegEncodeParam.setXDensity(300);
//                jpegEncodeParam.setYDensity(300);
//                jpegEncoder.encode(img, jpegEncodeParam);
//                fos.close();
//            }
            return fileName;
        } catch (Exception e) {
            return "error";
        }
    }
}
