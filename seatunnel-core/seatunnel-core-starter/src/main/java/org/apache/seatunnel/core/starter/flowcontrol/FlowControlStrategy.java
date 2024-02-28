/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.seatunnel.core.starter.flowcontrol;

import org.apache.seatunnel.shade.com.typesafe.config.Config;

import java.util.Map;
import java.util.Optional;

import static org.apache.seatunnel.api.env.EnvCommonOptions.READ_LIMIT_BYTES_PER_SECOND;
import static org.apache.seatunnel.api.env.EnvCommonOptions.READ_LIMIT_ROW_PER_SECOND;

public final class FlowControlStrategy {

    private final int bytesPerSecond;

    private final int countPreSecond;

    FlowControlStrategy(int bytesPerSecond, int countPreSecond) {
        if (bytesPerSecond <= 0 || countPreSecond <= 0) {
            throw new IllegalArgumentException(
                    "bytesPerSecond and countPreSecond must be positive");
        }
        this.bytesPerSecond = bytesPerSecond;
        this.countPreSecond = countPreSecond;
    }

    public int getBytesPerSecond() {
        return bytesPerSecond;
    }

    public int getCountPreSecond() {
        return countPreSecond;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int bytesPerSecond = Integer.MAX_VALUE;

        private int countPreSecond = Integer.MAX_VALUE;

        private Builder() {}

        public Builder bytesPerSecond(int bytesPerSecond) {
            this.bytesPerSecond = bytesPerSecond;
            return this;
        }

        public Builder countPerSecond(int countPreSecond) {
            this.countPreSecond = countPreSecond;
            return this;
        }

        public FlowControlStrategy build() {
            return new FlowControlStrategy(bytesPerSecond, countPreSecond);
        }
    }

    public static FlowControlStrategy of(int bytesPerSecond, int countPreSecond) {
        return FlowControlStrategy.builder()
                .bytesPerSecond(bytesPerSecond)
                .countPerSecond(countPreSecond)
                .build();
    }

    public static FlowControlStrategy ofBytes(int bytesPerSecond) {
        return FlowControlStrategy.builder().bytesPerSecond(bytesPerSecond).build();
    }

    public static FlowControlStrategy ofCount(int countPreSecond) {
        return FlowControlStrategy.builder().countPerSecond(countPreSecond).build();
    }

    public static FlowControlStrategy fromMap(Map<String, Object> envOption) {
        Builder builder = FlowControlStrategy.builder();
        if (envOption == null || envOption.isEmpty()) {
            return builder.build();
        }
        final Object bytePerSecond = envOption.get(READ_LIMIT_BYTES_PER_SECOND.key());
        final Object countPerSecond = envOption.get(READ_LIMIT_ROW_PER_SECOND.key());
        Optional.ofNullable(bytePerSecond)
                .ifPresent(bps -> builder.bytesPerSecond(Integer.parseInt(bps.toString())));
        Optional.ofNullable(countPerSecond)
                .ifPresent(cps -> builder.countPerSecond(Integer.parseInt(cps.toString())));
        return builder.build();
    }

    public static FlowControlStrategy fromConfig(Config envConfig) {
        Builder builder = FlowControlStrategy.builder();
        if (envConfig.hasPath(READ_LIMIT_BYTES_PER_SECOND.key())) {
            builder.bytesPerSecond(envConfig.getInt(READ_LIMIT_BYTES_PER_SECOND.key()));
        }
        if (envConfig.hasPath(READ_LIMIT_ROW_PER_SECOND.key())) {
            builder.countPerSecond(envConfig.getInt(READ_LIMIT_ROW_PER_SECOND.key()));
        }
        return builder.build();
    }
}
