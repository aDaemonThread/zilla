/*
 * Copyright 2021-2024 Aklivity Inc
 *
 * Licensed under the Aklivity Community License (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a copy of the
 * License at
 *
 *   https://www.aklivity.io/aklivity-community-license/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.aklivity.zilla.runtime.model.core.internal;

import org.agrona.DirectBuffer;

import io.aklivity.zilla.runtime.engine.Configuration;
import io.aklivity.zilla.runtime.engine.event.EventFormatterSpi;
import io.aklivity.zilla.runtime.model.core.internal.types.StringFW;
import io.aklivity.zilla.runtime.model.core.internal.types.event.CoreModelEventExFW;
import io.aklivity.zilla.runtime.model.core.internal.types.event.CoreModelValidationFailedExFW;
import io.aklivity.zilla.runtime.model.core.internal.types.event.EventFW;

public final class CoreModelEventFormatter implements EventFormatterSpi
{
    private final EventFW eventRO = new EventFW();
    private final CoreModelEventExFW coreModelEventExFW = new CoreModelEventExFW();

    CoreModelEventFormatter(
        Configuration config)
    {
    }

    public String format(
        DirectBuffer buffer,
        int index,
        int length)
    {
        final EventFW event = eventRO.wrap(buffer, index, index + length);
        final CoreModelEventExFW extension = coreModelEventExFW
            .wrap(event.extension().buffer(), event.extension().offset(), event.extension().limit());
        String result = null;
        switch (extension.kind())
        {
        case VALIDATION_FAILED:
        {
            CoreModelValidationFailedExFW ex = extension.validationFailed();
            result = String.format(
                    "A message payload failed validation. A field was not the expected type (%s).",
                    asString(ex.error())
            );
            break;
        }
        }
        return result;
    }

    private static String asString(
        StringFW stringFW)
    {
        String s = stringFW.asString();
        return s == null ? "" : s;
    }
}
