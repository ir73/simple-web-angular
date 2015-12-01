(function () {
    'use strict';

    angular
        .module("common")

        .directive('dateInput', function() {
            return {
                require: 'ngModel',
                link: function(scope, element, attrs, ngModelController) {

                    scope.parseDate = function (date) {
                        var yyyy = date.getFullYear().toString();
                        var mm = (date.getMonth()+1).toString();
                        var dd  = date.getDate().toString();
                        return {
                            year:yyyy,
                            month:(mm[1]?mm:"0"+mm[0]),
                            date:(dd[1]?dd:"0"+dd[0])
                        };
                    };

                    ngModelController.$parsers.push(function(data) {
                        if (!data) {
                            return data;
                        }
                        var parsedDate = scope.parseDate(new Date(data));
                        return parsedDate.year + "-" + parsedDate.month + "-" + parsedDate.date;
                    });

                    ngModelController.$formatters.push(function(data) {
                        if (!data) {
                            return data;
                        }
                        return new Date(data).toLocaleDateString();
                        //var parsedDate = _.parseDate(new Date(data));
                        //return parsedDate.date + "." + parsedDate.month + "." + parsedDate.year;
                    });
                }
            };
        });

})();