/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifiso.assessmentitsi;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.tomcat.util.http.fileupload.FileUtils;

/**
 *
 * @author sifiso.mtshweni
 */
public class ZipEncryption {

    public static void encryptFolderToZip(File sourceFile, File destinationFile, char[] password) {
        String tmp = OSPicker.getTmpDir(sourceFile.getName().substring(0, sourceFile.getName().indexOf("."))).getAbsolutePath();
        encryptZip(sourceFile, destinationFile, tmp, password);
    }

    /**
     * This class can be made to pass parameter base on you design, on this
     * example we don't
     */
    private static ZipParameters assignZipParameters() {
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        //Set the encryption flag to true
        parameters.setEncryptFiles(true);
        //Set the encryption method to AES Zip Encryption
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        //file encrypted with key strength of 256,
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        return parameters;
    }

    public static void encryptZip(File sourceFile, File destinationFile, String tmp, char[] password) {

        try {
            long start = System.currentTimeMillis();

            if (sourceFile == null || destinationFile == null || tmp == null) {
                return;
            }

            if (destinationFile.exists()) {
//                FileUtils.forceDelete(destinationFile);
                Logger.getLogger(ZipEncryption.class.getName()).log(Level.INFO, "{0} File Already Exists", destinationFile.getName());
                return;
            }

            if (!sourceFile.exists()) {
                Logger.getLogger(ZipEncryption.class.getName()).log(Level.INFO, "Invalid Source File");
                return;
            }

            ZipFile zf = new ZipFile(destinationFile);
            UnZipper.unzip(sourceFile, new File(tmp), Charset.forName("UTF-8"));

            ZipParameters parameters = assignZipParameters();
//        //Set password
            parameters.setPassword(password);

//            zf.createZipFileFromFolder(tmp, parameters, false, 0);
            zf.addFolder(tmp, parameters);

            long end = System.currentTimeMillis();
            Logger.getLogger(ZipEncryption.class.getName()).log(Level.INFO, "Process Time : {0}ms", Elapsed.getElapsed(start, end));
        } catch (IOException | ZipException ex) {
            Logger.getLogger(ZipEncryption.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (tmp != null && !tmp.isEmpty()) {
                    FileUtils.deleteDirectory(new File(tmp));
                }
            } catch (IOException ex) {
                Logger.getLogger(ZipEncryption.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
