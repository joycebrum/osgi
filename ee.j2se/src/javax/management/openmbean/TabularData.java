/*
 * Copyright (c) OSGi Alliance (2001, 2009). All Rights Reserved.
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

package javax.management.openmbean;
public interface TabularData {
	java.lang.Object[] calculateIndex(javax.management.openmbean.CompositeData var0);
	void clear();
	boolean containsKey(java.lang.Object[] var0);
	boolean containsValue(javax.management.openmbean.CompositeData var0);
	boolean equals(java.lang.Object var0);
	javax.management.openmbean.CompositeData get(java.lang.Object[] var0);
	javax.management.openmbean.TabularType getTabularType();
	int hashCode();
	boolean isEmpty();
	java.util.Set<?> keySet();
	void put(javax.management.openmbean.CompositeData var0);
	void putAll(javax.management.openmbean.CompositeData[] var0);
	javax.management.openmbean.CompositeData remove(java.lang.Object[] var0);
	int size();
	java.lang.String toString();
	java.util.Collection<?> values();
}
