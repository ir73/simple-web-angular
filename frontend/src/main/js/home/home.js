(function () {
    'use-strict';

    angular

        .module("home", ["common"])

        .factory("CustomerService", ["$resource", function($resource) {
            var Customer = $resource("/api/customers/:customerId", {customerId: "@id"}, {
                getAll: {
                    method: "GET",
                    isArray: true,
                    commonErrorHandler:true
                },
                update: {
                    method:'PUT',
                    commonErrorHandler:false
                },
                save: {
                    method:'POST',
                    commonErrorHandler:false
                },
                delete: {
                    method:'DELETE',
                    commonErrorHandler:false
                }

            });
            return Customer;
        }])

        .controller("HomeCtrl", ["$scope", "$log", "AuthService", "$location", "$window", "$resource", "CustomerService", "$uibModal", "$translate",
            function($scope, $log, AuthService, $location, $window, $resource, CustomerService, $uibModal, $translate) {

                $scope.onAddClick = function() {
                    $scope.openCustomerDialog();
                };

                $scope.resetAll = function() {
                    $scope.error = null;
                    $scope.success = null;
                };

                $scope.onCustomerClick = function(customer) {
                    $log.info("Editing customer", customer);
                    $scope.resetAll();
                    var customerCopy = angular.copy(customer);
                    $scope.openCustomerDialog(customerCopy);
                };

                $scope.onCustomerDeleteClick = function(customer) {
                    $log.info("Deleting customer");
                    $scope.resetAll();
                    customer.$delete()
                        .then(function(resultData) {
                            $scope.getAllCustomers();
                        }, function(resultData) {
                            $scope.error = $translate.instant("home.errors.delete_error");
                        });
                };

                $scope.getAllCustomers = function() {
                    $scope.customers = CustomerService.getAll();
                };

                $scope.openCustomerDialog = function (customer) {
                    var modalInstance = $uibModal.open({
                        animation: true,
                        templateUrl: 'add_customer.html',
                        controller: 'AddCustomerModalCtrl',
                        resolve: {
                            customer: function() {
                                return customer;
                            }
                        }
                    });

                    modalInstance.result.then(function (saveObject) {
                        var customer = saveObject.customer;
                        var isNewCustomer = saveObject.isNewCustomer;
                        $log.info("Dialog close handler", customer, isNewCustomer);

                        var promise;
                        var customerDao = new CustomerService(customer);
                        if (isNewCustomer) {
                            promise = customerDao.$save();
                            $log.info("Creating customer", promise, customerDao);
                        } else {
                            promise = customerDao.$update();
                            $log.info("Updating customer", promise, customerDao);
                        }

                        promise.then(function (resultData) {
                            $log.info("Saved customer successfully", resultData);
                            $scope.success = $translate.instant("home.success.save");
                            $scope.getAllCustomers();
                        }, function (resultData) {
                            $log.info("Saving customer failed", resultData);
                            // TODO: replace with resultData.data.field in all error
                            // handlers of http requests
                            var errorField = resultData.field;
                            var msg = $translate.instant("home.errors.other");
                            if (errorField) {
                                msg = $translate.instant("home.errors." + errorField);
                            }
                            $scope.error = msg;
                        });

                    }, function () {
                        $log.info("Modal dismissed at: " + new Date());

                    });
                };

                $scope.resetAll();
                $scope.getAllCustomers();

            }])

        .controller('AddCustomerModalCtrl', ["$scope", "$uibModalInstance", "customer", "$log", "$filter",
            function ($scope, $uibModalInstance, customer, $log, $filter) {

                $scope.newCustomer = !customer;
                $scope.customer = customer || {
                        dateOfBirth: new Date().getTime()
                    };

                $log.info("Opening customer modal dialog", $scope.customer,
                    $scope.newCustomer);

                $scope.ok = function () {
                    $uibModalInstance.close({
                        customer:$scope.customer,
                        isNewCustomer:$scope.newCustomer
                    });
                };

                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            }])

    ;
})();