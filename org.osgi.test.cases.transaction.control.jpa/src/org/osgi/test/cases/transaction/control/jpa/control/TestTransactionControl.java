/*
 * Copyright (c) OSGi Alliance (2018). All Rights Reserved.
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
package org.osgi.test.cases.transaction.control.jpa.control;

import java.util.List;
import java.util.Map;

import javax.transaction.xa.XAResource;

import org.osgi.service.transaction.control.LocalResource;
import org.osgi.service.transaction.control.TransactionControl;

public interface TestTransactionControl extends TransactionControl {

	public List<LocalResource> getEnlistedLocalResources();

	public Map<XAResource,String> getEnlistedXAResources();

	public List<Throwable> finish(boolean rollback);

}