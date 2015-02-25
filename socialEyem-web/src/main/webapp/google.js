/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

        /**
         * Handler for the signin callback triggered after the user selects an account.
         * 
         */
        function onSignInCallback(resp) {
            gapi.client.load('plus', 'v1', apiClientLoaded);
        }

        /**
         * Sets up an API call after the Google API client loads.
         */
        function apiClientLoaded() {
            gapi.client.plus.people.get({userId: 'me'}).execute(handleEmailResponse);
        }

        /**
         * Response callback for when the API client receives a response.
         *
         * @param resp The API response object with the user email and profile information.
         */
        function handleEmailResponse(resp) {
            var primaryEmail;
            var username = resp.displayName;
            var userimage = resp.image.url;
            for (var i = 0; i < resp.emails.length; i++) {
                if (resp.emails[i].type === 'account')
                    primaryEmail = resp.emails[i].value;
            }
            
            document.getElementById('poiemail').value = primaryEmail;
            document.getElementById('poinombre').value = username;
            document.getElementById('poiimagen').value = userimage;
            document.getElementById('poi-form:miBotonaso').click();
        }
