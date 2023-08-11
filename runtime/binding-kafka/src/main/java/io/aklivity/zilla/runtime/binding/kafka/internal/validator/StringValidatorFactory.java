/*
 * Copyright 2021-2023 Aklivity Inc.
 *
 * Aklivity licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.aklivity.zilla.runtime.binding.kafka.internal.validator;

import io.aklivity.zilla.runtime.binding.kafka.internal.config.KafkaTopicKeyValueConfig;

public class StringValidatorFactory implements ValidatorFactorySpi
{
    @Override
    public String type()
    {
        return "string";
    }

    @Override
    public StringValidator create(
        KafkaTopicKeyValueConfig config)
    {
        return new StringValidator(config);
    }
}
