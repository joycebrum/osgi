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

package java.util;
public class PriorityQueue<E> extends java.util.AbstractQueue<E> implements java.io.Serializable {
	public PriorityQueue() { } 
	public PriorityQueue(int var0) { } 
	public PriorityQueue(int var0, java.util.Comparator<? super E> var1) { } 
	public PriorityQueue(java.util.Collection<? extends E> var0) { } 
	public PriorityQueue(java.util.PriorityQueue<? extends E> var0) { } 
	public PriorityQueue(java.util.SortedSet<? extends E> var0) { } 
	public java.util.Comparator<? super E> comparator() { return null; }
	public java.util.Iterator<E> iterator() { return null; }
	public boolean offer(E var0) { return false; }
	public E peek() { return null; }
	public E poll() { return null; }
	public int size() { return 0; }
}
