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

package java.util.concurrent.atomic;
public class AtomicMarkableReference<V> {
	public AtomicMarkableReference(V var0, boolean var1) { } 
	public boolean attemptMark(V var0, boolean var1) { return false; }
	public boolean compareAndSet(V var0, V var1, boolean var2, boolean var3) { return false; }
	public V get(boolean[] var0) { return null; }
	public V getReference() { return null; }
	public boolean isMarked() { return false; }
	public void set(V var0, boolean var1) { }
	public boolean weakCompareAndSet(V var0, V var1, boolean var2, boolean var3) { return false; }
}
