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

import org.codice.testify.objects.AssertionStatus;
import org.codice.testify.objects.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ValidHTTPStatusCodesAssertionTest {

    @Test
    public void testNoAssertionInfo() {
        Response response = new Response(null);
        response.setResponseCode(200);
        ValidHTTPStatusCodesAssertion validHTTPStatusCodesAssertion = new ValidHTTPStatusCodesAssertion();
        AssertionStatus assertionStatus = validHTTPStatusCodesAssertion.evaluateAssertion(null, response);
        assert ( assertionStatus.getFailureDetails().equals("No HTTP status codes provided in test file") );
    }

    @Test
    public void testNoResponseCode() {
        Response response = new Response(null);
        String validCodes = "200, 201, 202";
        ValidHTTPStatusCodesAssertion validHTTPStatusCodesAssertion = new ValidHTTPStatusCodesAssertion();
        AssertionStatus assertionStatus = validHTTPStatusCodesAssertion.evaluateAssertion(validCodes, response);
        assert ( assertionStatus.getFailureDetails().equals("No response code set by processor") );
    }

    @Test
    public void testValidStatusCode() {
        Response response = new Response(null);
        response.setResponseCode(200);
        String validCodes = "200, 201, 202";
        ValidHTTPStatusCodesAssertion validHTTPStatusCodesAssertion = new ValidHTTPStatusCodesAssertion();
        AssertionStatus assertionStatus = validHTTPStatusCodesAssertion.evaluateAssertion(validCodes, response);
        assert ( assertionStatus.isSuccess() );
    }

    @Test
    public void testInvalidStatusCode() {
        Response response = new Response(null);
        response.setResponseCode(500);
        String validCodes = "200, 201, 202";
        ValidHTTPStatusCodesAssertion validHTTPStatusCodesAssertion = new ValidHTTPStatusCodesAssertion();
        AssertionStatus assertionStatus = validHTTPStatusCodesAssertion.evaluateAssertion(validCodes, response);
        assert ( assertionStatus.getFailureDetails().equals("Response code 500 is not valid") );
    }

}
