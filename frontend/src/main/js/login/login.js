(function () {
    'use-strict';

    angular

        .module("login", ["common"])

        .controller("LoginCtrl", ["$scope", "$log", "AuthService", "$location", "$window",
            function($scope, $log, AuthService, $location, $window) {

                $scope.loginInfo = {
                    username: "",
                    password: ""
                };
                $scope.hasError = false;
                $scope.isAuthenticated = false;

                $scope.setIsAuthenticated = function (isAuthenticated) {
                    $scope.isAuthenticated = isAuthenticated;
                    $log.info("is authenticated:", $scope.isAuthenticated);
                    if ($scope.isAuthenticated) {
                        $window.location = "/home.html";
                    }
                };

                /**
                 * Fetches user's information object from server. If not set, then
                 * app considers that user has not logged in. Otherwise redirects to /home.html
                 * @param loginData Login object with username and password
                 * @param showError Show error message
                 */
                $scope.getUserInfo = function(loginData, showError) {

                    var handler = function (data) {
                        $log.info("Login result", data, showError);

                        var isSuccess = data.response == "OK";
                        $scope.setIsAuthenticated(isSuccess);

                        if (showError) {
                            $scope.hasError = !isSuccess;
                        }
                    };
                    AuthService.getUserInfo(loginData)
                        .then(handler, handler);
                };

                /**
                 * A handler when user presses LOGIN button
                 */
                $scope.login = function () {

                    $log.info("Logging in with " + $scope.loginInfo.username + ":" + $scope.loginInfo.password);
                    $scope.getUserInfo($scope.loginInfo, true);

                };

                // as a first thing on the page we try to get protected data
                // from the server and see if we are authenticated. If yes, we redirect to /home.html
                $scope.getUserInfo();
        }])

    ;
})();