/*
 * $Header$
 * 
 * Copyright (c) OSGi Alliance (2009). All Rights Reserved.
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
package org.osgi.test.cases.composite;

public class TestException extends RuntimeException {
		public static int NO_SERVICE_REFERENCE = 1;
	public static final int NO_SERVICE = 2;
	public static final int WRONG_SERVICE_PROPERTY = 3;
	public static final int WRONG_CONTENT_HANDER = 4;
	public static final int WRONG_STREAM_HANDER = 5;
	public static final int NO_PROTOCOL = 6;
	public static final int EVENT_HOOK = 7;
	public static final int FIND_HOOK = 8;
	public static final int LISTENER_HOOK = 9;
	private final int testCode;
	
	public TestException(String message, int testCode) {
		super(message);
		this.testCode = testCode;
	}

	public int getTestCode() {
		return testCode;
	}
}