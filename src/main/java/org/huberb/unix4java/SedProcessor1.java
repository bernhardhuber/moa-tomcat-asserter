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

import java.io.Reader;
import java.io.Writer;
import org.unix4j.Unix4j;
import org.unix4j.unix.sed.SedOptions;

/**
 *
 * @author berni3
 */
public class SedProcessor1 {

    void processScript(Reader reader, Writer writer) {
        Unix4j.from(reader)
                .sed(SedOptions.EMPTY, "aaa", "bbb")
                .sed(SedOptions.EMPTY, "123", "789")
                .toWriter(writer);
    }

    void processSimpleReplace(Reader reader, Writer writer) {
        Unix4j.from(reader)
                .sed(SedOptions.EMPTY, "aaa", "bbb")
                .toWriter(writer);
    }
}