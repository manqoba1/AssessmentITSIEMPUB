/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifiso.assessmentitsi;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sifiso.mtshweni
 */
public class ZipEncryptionTest {

    public ZipEncryptionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of encryptFolderToZip method, of class ZipEncryption.
     */
    @Test
    public void testEncryptFolderToZip() {
        System.out.println("encryptFolderToZip");
        File sourceFile = new File("C:\\Users\\sifiso.mtshweni\\Documents\\dumps\\childrens-literature.epub");
        File destinationFile = new File("C:\\Users\\sifiso.mtshweni\\Documents\\dumps\\childrens-literature.zip");
        char[] password = "Hello".toCharArray();
        ZipEncryption.encryptFolderToZip(sourceFile, destinationFile, password);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of encryptZip method, of class ZipEncryption.
     */
//    @Test
    public void testEncryptZip() {
        System.out.println("encryptZip");
        File sourceFile = null;
        File destinationFile = null;
        String tmp = "";
        char[] password = null;
        ZipEncryption.encryptZip(sourceFile, destinationFile, tmp, password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
