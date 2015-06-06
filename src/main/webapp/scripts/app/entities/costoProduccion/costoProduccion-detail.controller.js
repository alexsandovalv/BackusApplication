'use strict';

angular.module('backus3App')
    .controller('CostoProduccionDetailController', function ($scope, $stateParams, CostoProduccion, PlanSolucion) {
        $scope.costoProduccion = {};
        $scope.load = function (id) {
            CostoProduccion.get({id: id}, function(result) {
              $scope.costoProduccion = result;
            });
        };
        $scope.load($stateParams.id);
    });
