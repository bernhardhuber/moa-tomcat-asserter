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
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.unix4j.Unix4j;
import org.unix4j.unix.Ls;

/**
 *
 * @author berni3
 */
public class Asserts {

    static class TomcatAsserts {

        final File tomcatDir;

        public TomcatAsserts(File tomcatDir) {
            this.tomcatDir = tomcatDir;
        }

        boolean assertTomcatUnzippedLs() {
            String moaDirLsOptionsRahLong = Unix4j.ls(Ls.Options.R.h.l, tomcatDir).toStringResult();

            String reason = String.format("tomcatDir %s is not valid ", tomcatDir.getAbsolutePath(), moaDirLsOptionsRahLong);
            MatcherAssert.assertThat(reason, moaDirLsOptionsRahLong,
                    Matchers.allOf(
                            CoreMatchers.containsString("conf:"),
                            CoreMatchers.containsString("webapps:")
                    )
            );

            return true;
        }

        boolean assertTomcatUnzippedDetail() {
            assertThatDirectoryCanRead(tomcatDir);

            final File tomcatWebapps = new File(tomcatDir, "webapps");
            assertThatDirectoryCanRead(tomcatWebapps);

            final File tomcatConf = new File(tomcatDir, "conf");
            assertThatDirectoryCanRead(tomcatConf);

            return true;
        }

        boolean assertTomcatInstalledLs() {
            String moaDirLsOptionsRahLong = Unix4j.ls(Ls.Options.R.h.l, tomcatDir).toStringResult();

            String reason = String.format("tomcatDir %s is not valid ", tomcatDir.getAbsolutePath());
            MatcherAssert.assertThat(reason, moaDirLsOptionsRahLong,
                    Matchers.allOf(
                            CoreMatchers.containsString("conf:"),
                            CoreMatchers.containsString("conf/moa-id:"),
                            CoreMatchers.containsString("moa-id.properties"),
                            CoreMatchers.containsString("conf/moa-id-configuration:"),
                            CoreMatchers.containsString("moa-id-configtool.properties"),
                            CoreMatchers.containsString("conf/moa-id-oa:"),
                            CoreMatchers.containsString("oa.properties"),
                            CoreMatchers.containsString("webapps:"),
                            CoreMatchers.containsString("webapps/moa-id-auth-final:"),
                            CoreMatchers.containsString("moa-id-auth-final.war"),
                            CoreMatchers.containsString("webapps/moa-id-configuration:"),
                            CoreMatchers.containsString("moa-id-configuration.war"),
                            CoreMatchers.containsString("webapps/oa:"),
                            CoreMatchers.containsString("oa.war")
                    )
            );

            return true;
        }

        boolean assertTomcatInstalledDetail() {
            assertThatDirectoryCanRead(tomcatDir);

            final File tomcatWebapps = new File(tomcatDir, "webapps");
            assertThatDirectoryCanRead(tomcatWebapps);

            final File tomcatWebappsMoaIdAuthFinal = new File(tomcatWebapps, "moa-id-auth-final");
            assertThatDirectoryCanRead(tomcatWebappsMoaIdAuthFinal);
            final File tomcatWebappsMoaIdConfiguration = new File(tomcatWebapps, "moa-id-configuration");
            assertThatDirectoryCanRead(tomcatWebappsMoaIdConfiguration);
            final File tomcatWebappsOa = new File(tomcatWebapps, "oa");
            assertThatDirectoryCanRead(tomcatWebappsOa);

            final File tomcatConf = new File(tomcatDir, "conf");
            assertThatDirectoryCanRead(tomcatConf);

            return true;
        }
    }

    static class MoaAsserts {

        final File moaDir;

        public MoaAsserts(File moaDir) {
            this.moaDir = moaDir;
        }

        boolean assertMoaUnzippedLs() {
            String moaDirLsOptionsRahLong = Unix4j.ls(Ls.Options.R.h.l, moaDir).toStringResult();

            String reason = String.format("moaDir %s is not valid ", moaDir.getAbsolutePath());
            MatcherAssert.assertThat(reason, moaDirLsOptionsRahLong,
                    Matchers.allOf(
                            CoreMatchers.containsString("conf:"),
                            CoreMatchers.containsString("conf/moa-id:"),
                            CoreMatchers.containsString("moa-id.properties"),
                            CoreMatchers.containsString("conf/moa-id-configuration:"),
                            CoreMatchers.containsString("moa-id-configtool.properties"),
                            CoreMatchers.containsString("conf/moa-id-oa:"),
                            CoreMatchers.containsString("oa.properties"),
                            CoreMatchers.containsString("moa-id-auth-final.war"),
                            CoreMatchers.containsString("moa-id-configuration.war"),
                            CoreMatchers.containsString("oa.war")
                    )
            );
            MatcherAssert.assertThat(reason, moaDirLsOptionsRahLong,
                    Matchers.allOf(
                            CoreMatchers.containsString("tomcat:"),
                            CoreMatchers.containsString("tomcat/win32:"),
                            CoreMatchers.containsString("startTomcat.bat"),
                            CoreMatchers.containsString("stopTomcat.bat"),
                            CoreMatchers.containsString("tomcat/unix:"),
                            CoreMatchers.containsString("tomcat-start.sh"),
                            CoreMatchers.containsString("tomcat-stop.sh")
                    )
            );

            return true;
        }

        boolean assertMoaUnzippedDetail() {
            assertThatDirectoryCanRead(moaDir);

            final File moaDirConf = new File(moaDir, "conf");
            assertThatDirectoryCanRead(moaDirConf);

            final File moaDirTomcat = new File(moaDir, "tomcat");
            assertThatDirectoryCanRead(moaDirTomcat);

            final File moaDirTomcatWin32 = new File(moaDirTomcat, "win32");
            assertThatDirectoryCanRead(moaDirTomcat);

            final File moaDirTomcatWin32TomcatStart = new File(moaDirTomcatWin32, "startTomcat.bat");
            assertThatFileCanRead(moaDirTomcatWin32TomcatStart);

            final File moaDirTomcatWin32TomcatStop = new File(moaDirTomcatWin32, "stopTomcat.bat");
            assertThatFileCanRead(moaDirTomcatWin32TomcatStop);

            return true;
        }
    }

    public static void assertThatDirectoryCanRead(File d) {
        MatcherAssert.assertThat(String.format("%s is not a directory", d.getAbsolutePath()), d.canRead() && d.isDirectory());
    }

    public static void assertThatFileCanRead(File f) {
        MatcherAssert.assertThat(String.format("%s is not a file", f.getAbsolutePath()), f.canRead() && f.isFile());
    }

}
