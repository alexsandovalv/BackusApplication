'use strict';

angular.module('backus3App')
    .controller('ProductoDetailController', function ($scope, $stateParams, Producto, CapacidadProduccion) {
        $scope.producto = {};
        $scope.load = function (id) {
            Producto.get({id: id}, function(result) {
              $scope.producto = result;
            });
        };
        $scope.load($stateParams.id);
    });
