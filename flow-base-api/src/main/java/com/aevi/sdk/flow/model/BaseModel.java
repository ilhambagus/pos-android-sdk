/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.aevi.sdk.flow.model;


import com.aevi.util.json.Sendable;

public abstract class BaseModel implements Sendable {

    private final String id;

    protected BaseModel(String id) {
        this.id = id;
    }

    /**
     * Get the id of this model.
     *
     * @return The id.
     */
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{" + "id='" + id + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseModel baseModel = (BaseModel) o;

        return id != null ? id.equals(baseModel.id) : baseModel.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
