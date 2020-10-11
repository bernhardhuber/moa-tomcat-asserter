/*
 * Copyright 2020 berni3.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.huberb.unix4java;

import java.io.File;

/**
 *
 * @author berni3
 */
public class MoaTomcatAsserterMain {

    private static final String MOA_DIR_DEFAULT = "D:\\tmp\\moa-unisys\\moa-id-auth-final-4.1.4";
    private static final String TOMCAT_DIR_DEFAULT = "D:\\tmp\\moa-unisys\\apache-tomcat-9.0.38";
    private final File moaDir;
    private final File tomcatDir;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String _moaDir = retrieveSystemProperty("moaDir", MOA_DIR_DEFAULT);
        String _tomcatDir = retrieveSystemProperty("tomcatDir", TOMCAT_DIR_DEFAULT);

        new MoaTomcatAsserterMain(
                new File(_moaDir),
                new File(_tomcatDir)).process();
    }

    public MoaTomcatAsserterMain(File moaDir, File tomcatDir) {
        this.moaDir = moaDir;
        this.tomcatDir = tomcatDir;
    }

    static String retrieveSystemProperty(String k, String def) {
        String v = System.getProperty(k);
        if (v == null || v.isEmpty()) {
            v = def;
        }
        return v;
    }

    public void process() {
        testAssertMoaUnzipped();
        testAssertTomcatUnzipped();
        testAssertTomcatInstalled();
    }

    public void testAssertMoaUnzipped() {
        Asserts.MoaAsserts moaAsserts = new Asserts.MoaAsserts(moaDir);
        assertTrue("testAssertMoaUnzippedLs", moaAsserts.assertMoaUnzippedLs());
        assertTrue("testAssertMoaUnzippedDetail", moaAsserts.assertMoaUnzippedDetail());
    }

    public void testAssertTomcatUnzipped() {
        Asserts.TomcatAsserts tomcatAsserts = new Asserts.TomcatAsserts(tomcatDir);
        assertTrue("testAssertTomcatUnzippedLs", tomcatAsserts.assertTomcatUnzippedLs());
        assertTrue("testAssertTomcatUnzippedDetail", tomcatAsserts.assertTomcatUnzippedDetail());
    }

    public void testAssertTomcatInstalled() {
        Asserts.TomcatAsserts tomcatAsserts = new Asserts.TomcatAsserts(tomcatDir);
        assertTrue("testAssertTomcatInstalledLs", tomcatAsserts.assertTomcatInstalledLs());
        assertTrue("testAssertTomcatInstalledDetail", tomcatAsserts.assertTomcatInstalledDetail());
    }

    void assertTrue(String m, boolean v) {
        System.out.println(String.format("%s result %s", m, v ? "passed" : "failed"));
    }
}
