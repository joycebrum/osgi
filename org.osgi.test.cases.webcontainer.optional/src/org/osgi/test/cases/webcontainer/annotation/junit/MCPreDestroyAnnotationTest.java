/*
 * Copyright (c) IBM Corporation (2009). All Rights Reserved.
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

package org.osgi.test.cases.webcontainer.annotation.junit;

import org.osgi.test.cases.webcontainer.WebContainerTestBundleControl;

/**
 * @version $Rev$ $Date$
 */
public class MCPreDestroyAnnotationTest extends WebContainerTestBundleControl {
    long beforeUninstall;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        super.prepare("/tw3");

        super.cleanupPropertyFile();

        // install + start the war file
        log("install war file: tw3.war at context path " + this.warContextPath);
        this.b = installBundle(super.getWarURL("tw3.war", this.options), true);
        
        // capture a time before uninstall
        this.beforeUninstall = System.currentTimeMillis();
    }

    /*
     * test @preDestroy annotated public method is not called
     * when the metadata-complete attribute is set to true.
     */
    public void testPreDestroy001() throws Exception {
        super.tearDown();
        // TODO do we need to wait till the war is uninstalled properly?
        log("verify annotated methods are not invoked");
        assertEquals(this.timeUtil.getTimeFromLog(
                "PostConstructPreDestroyServlet1", "cleanup"), 0);
        assertEquals(this.timeUtil.getTimeFromLog(
                "PostConstructPreDestroyServlet2", "cleanup"), 0);
        assertEquals(this.timeUtil.getTimeFromLog(
                "PostConstructPreDestroyServlet3", "cleanup"), 0);
    }
}