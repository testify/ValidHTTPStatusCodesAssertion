/*
 * Copyright 2015 Codice Foundation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ValidHTTPStatusCodes;

import org.codice.testify.assertions.Assertion;
import org.codice.testify.objects.AssertionStatus;
import org.codice.testify.objects.TestifyLogger;
import org.codice.testify.objects.Response;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The ValidHTTPStatusCodesAssertion class is a Testify Assertion service that checks that the response code matches one a set of comma separated status codes
 */
public class ValidHTTPStatusCodesAssertion implements BundleActivator, Assertion {

    @Override
    public AssertionStatus evaluateAssertion(String assertionInfo, Response response) {

        TestifyLogger.debug("Running ValidHTTPStatusCodesAssertion", this.getClass().getSimpleName());

        //Get the processor response code
        int responseCode = response.getResponseCode();

        //If no assertion info is provided, return a failure
        if (assertionInfo == null) {
            return new AssertionStatus("No HTTP status codes provided in test file");

        //If the response code from the processor is not set, return a failure
        } else if (responseCode == -1) {
            return new AssertionStatus("No response code set by processor");

        } else {

            //Split the assertionInfo by comma into an array of codes
            String[] codeArray = assertionInfo.split(",");

            //Loop through each code in the list of valid codes
            for (String code : codeArray) {

                //If the response code matches a valid code, return failure details of null meaning a successful assertion
                if (responseCode == Integer.parseInt(code.trim())) {
                    return new AssertionStatus(null);
                }
            }

            //If the response code is not in the list of valid codes, return a failure
            return new AssertionStatus("Response code " + responseCode + " is not valid");
        }
    }


    @Override
    public void start(BundleContext bundleContext) throws Exception {

        //Register the ValidHTTPStatusCodes service
        bundleContext.registerService(Assertion.class.getName(), new ValidHTTPStatusCodesAssertion(), null);

    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}