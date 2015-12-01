(function () {
    'use-strict';

    angular
        .module("common", ["ngResource", "ngCookies", "pascalprecht.translate", "ui.bootstrap"])

        .constant("API_SERVER_URL", "@[serverEndpoint]")

        .config(["$translateProvider", "$httpProvider", function($translateProvider){
            $translateProvider.useCookieStorage(true);
            $translateProvider.useUrlLoader("/api/res/messages/");
            $translateProvider.fallbackLanguage("en");
            $translateProvider.preferredLanguage("en");
            $translateProvider.useSanitizeValueStrategy('escapeParameters');
        }])

        .config(["$httpProvider", function($httpProvider){
            //Enable cross domain calls
            $httpProvider.defaults.useXDomain = true;
            // make sure JSESSIONID is passed when having frontend deployed to
            // a different domain rather than server
            $httpProvider.defaults.withCredentials = true;

            $httpProvider.interceptors.push("errorInterceptor");
            $httpProvider.interceptors.push('apiBaseUrlAppender');
        }])

        .config(["$resourceProvider", function($resourceProvider){
            $resourceProvider.defaults.stripTrailingSlashes = false;
        }])

        .factory("AuthService", ["$http", "$resource", "$log", function ($http, $resource, $log) {

            return {
                getUserInfo: function(credentials) {
                    var headers = credentials ? {
                        authorization : "Basic " +
                            btoa(credentials.username + ":" +
                            credentials.password)
                    } : {};

                    var User = $resource('/api/user', {}, {
                        'getUserInfo': {
                            method: "GET",
                            commonErrorHandler: false,
                            headers:headers
                        }
                    });

                    return User.getUserInfo().$promise;
                }
            };
        }])

        .factory('apiBaseUrlAppender', ["API_SERVER_URL", function(API_SERVER_URL) {
            return {
                request: function(config) {
                    if (config.url.indexOf("/api/") === 0) {
                        config.url = API_SERVER_URL + config.url;
                    }
                    return config;
                }
            };
        }])

        .factory("errorInterceptor", ["$rootScope", "$window", "$log", "$q",
            function ($rootScope, $window, $log, $q) {
                return {
                    responseError: function (errorData) {
                        // if common exception handler is enabled...
                        if (errorData.config.commonErrorHandler) {
                            $log.warn("Common error handler interceptor", errorData);
                            // redirect to login on authentication error
                            if (errorData.status == 401) {
                                $window.location = "/";
                            }

                            // for common errors show a generic error
                            else if (errorData.status >= 400) {
                                // TODO: add showing a toast
                                // TODO: try to implement a feture, so that if you
                                // have an inline handler defined for $q.reject(), then
                                // no common handler would be triggered
                            }
                        }

                        // handle as success
                        return $q.reject(errorData);
                    }
                };
            }])


    ;
})();