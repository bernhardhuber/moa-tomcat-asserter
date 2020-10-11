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
import org.huberb.unix4java.Asserts.MoaAsserts;
import org.huberb.unix4java.Asserts.TomcatAsserts;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author berni3
 */
public class AssertsIT {

    File moaDir = new File("D:\\tmp\\moa-tomcat-basedir\\moa-id-auth-final-4.1.4");
    File tomcatDir = new File("D:\\tmp\\moa-tomcat-basedir\\apache-tomcat-9.0.38");

    @Test
    public void testAssertMoaUnzipped() {
        MoaAsserts moaAsserts = new Asserts.MoaAsserts(moaDir);
        assertTrue(moaAsserts.assertMoaUnzippedLs());
        assertTrue(moaAsserts.assertMoaUnzippedDetail());
    }

    @Test
    public void testAssertTomcatUnzipped() {
        TomcatAsserts tomcatAsserts = new Asserts.TomcatAsserts(tomcatDir);
        assertTrue(tomcatAsserts.assertTomcatUnzippedLs());
        assertTrue(tomcatAsserts.assertTomcatUnzippedDetail());
    }

    @Test
    public void testAssertTomcatInstalled() {
        TomcatAsserts tomcatAsserts = new Asserts.TomcatAsserts(tomcatDir);
        assertTrue(tomcatAsserts.assertTomcatInstalledLs());
        assertTrue(tomcatAsserts.assertTomcatInstalledDetail());
    }
}
