(function () {
    'use-strict';

    angular
        .module("common", ["ngResource", "ngCookies", "pascalprecht.translate", "ui.bootstrap"])

        .constant("API_SERVER_URL", "http://localhost:8080/")

        .config(["$translateProvider", "$httpProvider", function($translateProvider){
            $translateProvider.useCookieStorage(true);
            $translateProvider.useUrlLoader("api/res/messages/");
            $translateProvider.fallbackLanguage("en");
            $translateProvider.preferredLanguage("en");
            $translateProvider.useSanitizeValueStrategy('escapeParameters');
        }])

        .config(["$httpProvider", function($httpProvider){
            $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
            $httpProvider.interceptors.push("errorInterceptor");
            $httpProvider.interceptors.push('apiBaseUrlAppender');
        }])

        .config(["$resourceProvider", function($resourceProvider){
            $resourceProvider.defaults.stripTrailingSlashes = false;
        }])

        .directive('dateInput', function() {
            return {
                require: 'ngModel',
                link: function(scope, element, attrs, ngModelController) {
                    ngModelController.$parsers.push(function(data) {
                        if (!data) {
                            return data;
                        }
                        return new Date(data).getTime();
                    });

                    ngModelController.$formatters.push(function(data) {
                        if (!data) {
                            return data;
                        }
                        var date = new Date(data);
                        var yyyy = date.getFullYear().toString();
                        var mm = (date.getMonth()+1).toString(); // getMonth() is zero-based
                        var dd  = date.getDate().toString();
                        return (dd[1]?dd:"0"+dd[0]) + "/" + (mm[1]?mm:"0"+mm[0]) + "/" + yyyy;
                    });
                }
            };
        })

        .factory("AuthService", ["$http", "$resource", "$log", function ($http, $resource, $log) {

            return {
                getUserInfo: function(credentials) {
                    var headers = credentials ? {
                        authorization : "Basic " +
                            btoa(credentials.username + ":" +
                            credentials.password)
                    } : {};

                    var User = $resource('api/user', {}, {
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
                    if (config.url.indexOf("api/") == 0
                        || config.url.indexOf("/api/") == 0) {
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
                        }

                        // if there is an "response=OK/ERROR" field, then let a
                        // success handler to fire up.
                        if (errorData.data.response) {
                            // handle as success
                            return $q.resolve(errorData);
                        }

                        // handle as success
                        return $q.reject(errorData);
                    }
                };
            }])


    ;
})();