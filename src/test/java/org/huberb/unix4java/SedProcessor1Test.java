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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author berni3
 */
public class SedProcessor1Test {

    SedProcessor1 instance;

    public SedProcessor1Test() {
    }

    @Before
    public void setUp() {
        instance = new SedProcessor1();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testProcessScript() throws IOException {

        String text = "123\n"
                + "aaa\n"
                + "ccc\n";
        final StringReader sr = new StringReader(text);
        final StringWriter sw = new StringWriter();
        try (Reader reader = sr; Writer writer = sw) {
            instance.processScript(reader, writer);
        }
        String result = sw.toString();
        assertEquals("789\n"
                + "bbb\n"
                + "ccc\n", result);
    }

    public void testProcessScriptHamcrest() throws IOException {
        String text = "123\n"
                + "aaa\n"
                + "ccc\n";
        final StringReader sr = new StringReader(text);
        final StringWriter sw = new StringWriter();
        try (Reader reader = sr; Writer writer = sw) {
            instance.processScript(reader, writer);
        }
        String result = sw.toString();
        String expected = "789\n"
                + "bbb\n"
                + "ccc\n";

        org.hamcrest.MatcherAssert.assertThat(result, org.hamcrest.CoreMatchers.equalTo(expected));
    }

    @Test
    public void testProcessSimpleReplace() throws IOException {

        String text = "123\n"
                + "aaa\n"
                + "ccc\n";
        final StringReader sr = new StringReader(text);
        final StringWriter sw = new StringWriter();
        try (Reader reader = sr; Writer writer = sw) {
            instance.processSimpleReplace(reader, writer);
        }
        String result = sw.toString();
        assertEquals("123\n"
                + "bbb\n"
                + "ccc\n", result);
    }

}
