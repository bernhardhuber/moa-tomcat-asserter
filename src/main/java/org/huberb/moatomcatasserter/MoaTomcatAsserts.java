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
package org.huberb.moatomcatasserter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.unix4j.Unix4j;
import org.unix4j.unix.Grep;
import org.unix4j.unix.Ls;

/**
 *
 * @author berni3
 */
public class MoaTomcatAsserts {

    static class TomcatAsserts {

        private final File tomcatDir;

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

            Arrays.asList(new File(tomcatDir, "webapps"),
                    new File(tomcatDir, "conf"))
                    .forEach(f -> assertThatDirectoryCanRead(f));

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

            Arrays.asList(
                    new File(tomcatDir, "webapps"),
                    new File(tomcatDir, "webapps/moa-id-auth-final"),
                    new File(tomcatDir, "webapps/moa-id-configuration"),
                    new File(tomcatDir, "webapps/oa"))
                    .forEach(f -> assertThatDirectoryCanRead(f));

            Arrays.asList(new File(tomcatDir, "conf"),
                    new File(tomcatDir, "conf/moa-id"),
                    new File(tomcatDir, "conf/moa-id-configuration"),
                    new File(tomcatDir, "conf/moa-id-oa"))
                    .forEach(d -> assertThatDirectoryCanRead(d));

            Arrays.asList(new File(tomcatDir, "conf/moa-id/moa-id.properties"),
                    new File(tomcatDir, "conf/moa-id-configuration/moa-id-configtool.properties"),
                    new File(tomcatDir, "conf/moa-id-oa/oa.properties"))
                    .forEach(f -> assertThatFileCanRead(f));

            return true;
        }

        boolean assertTomcatMoaConfiguration() {
            final File tomcatConf = new File(tomcatDir, "conf");
            for (File f : Arrays.asList(new File(tomcatConf, "moa-id/moa-id.properties"),
                    new File(tomcatConf, "moa-id-configuration/moa-id-configtool.properties"),
                    new File(tomcatConf, "conf/moa-id-oa/oa.properties"))) {

                assertThatFileCanRead(f);

                try (FileReader fr = new FileReader(f)) {
                    final Properties props = new Properties();
                    props.load(fr);
                    for (String k : props.stringPropertyNames()) {
                        String v = props.getProperty(k, "");
                        if (v.startsWith("file:")) {
                            assertThatFileIsHierachical(new URI(v));
                        }
                    }
                } catch (URISyntaxException | IOException ex) {
                    throw new AssertionError("assertTomcatMoaConfiguration file " + f, ex);
                }
            }
            return true;
        }
    }

    static class MoaAsserts {

        private final File moaDir;

        public MoaAsserts(File moaDir) {
            this.moaDir = moaDir;
        }

        boolean assertMoaUnzippedLs() {
            final String moaDirLsOptionsRahLong = Unix4j.ls(Ls.Options.R.h.l, moaDir).toStringResult();

            final String reason = String.format("moaDir %s is not valid ", moaDir.getAbsolutePath());
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

            final List<String> moaDirConfSubDirs = Arrays.asList("conf", "conf/moa-id", "conf/moa-id-configuration", "conf/moa-id-oa");
            moaDirConfSubDirs.forEach(d -> {
                final File dFile = new File(moaDir, d);
                assertThatDirectoryCanRead(dFile);
            });

            final List<String> moaDirTomcatSubDirs = Arrays.asList("tomcat", "tomcat/win32");
            moaDirTomcatSubDirs.forEach(d -> {
                final File dFile = new File(moaDir, d);
                assertThatDirectoryCanRead(dFile);
            });
            final List<String> moaDirTomcatSubFiles = Arrays.asList("tomcat/win32/startTomcat.bat", "tomcat/win32/stopTomcat.bat");
            moaDirTomcatSubFiles.forEach(f -> {
                final File fFile = new File(moaDir, f);
                assertThatFileCanRead(fFile);
            });

            return true;
        }

        boolean assertsMoaStartTomcat() {
            final File moaDirTomcat = new File(moaDir, "tomcat");
            final File moaDirTomcatWin32 = new File(moaDirTomcat, "win32");
            final File moaDirTomcatWin32TomcatStart = new File(moaDirTomcatWin32, "startTomcat.bat");
            final List<String> expectingSetNameList = Arrays.asList("FILE_ENCODING",
                    "RAND_FILE",
                    "CONFIG_OPT_SPSS%",
                    "CONFIG_OPT_ID",
                    "LOGGING_OPT",
                    "LOGGING_LOGBACK_OPT",
                    "CONFIGTOOL_OPT",
                    "CONFIGTOOL_USER_OPT",
                    "DEMOOA_OPT STORK_OPT");
            for (String expectingSetName : expectingSetNameList) {
                final String matchingSetNameEqual = "set " + expectingSetName + "=";
                final String matchingCount = Unix4j.fromFile(moaDirTomcatWin32TomcatStart).grep(Grep.Options.count, matchingSetNameEqual).toStringResult();
                final String reason = String.format("Exact 1 Match of %s", matchingSetNameEqual);
                MatcherAssert.assertThat(reason, matchingCount, Matchers.equalTo("1"));

                final String matchingPercentNamePercent = "%" + expectingSetName + "%";
                final String matchingCountPercentNamePercent = Unix4j.fromFile(moaDirTomcatWin32TomcatStart).grep(Grep.Options.count, matchingPercentNamePercent).toStringResult();
                final String reasonPercentNamePercent = String.format("Exact 1 Match of %s", matchingPercentNamePercent);
                MatcherAssert.assertThat(reasonPercentNamePercent, matchingCountPercentNamePercent, Matchers.equalTo("1"));
            }
            return true;
        }
    }

    static void assertThatDirectoryCanRead(File d) {
        MatcherAssert.assertThat(String.format("%s is not a directory", d.getAbsolutePath()), d.canRead() && d.isDirectory());
    }

    static void assertThatFileCanRead(File f) {
        MatcherAssert.assertThat(String.format("%s is not a file", f.getAbsolutePath()), f.canRead() && f.isFile());
    }

    static void assertThatFileIsHierachical(URI uri) {
        MatcherAssert.assertThat("uri.isAbsolute", uri.isAbsolute());
        MatcherAssert.assertThat("uri.isOpaque", !uri.isOpaque());
        try {
            final File f = new File(uri);
        } catch (IllegalArgumentException iaex) {
            String reason = "File construction " + iaex.getMessage();
            throw new AssertionError(reason);
        }
    }
}
