'use strict';

angular.module('backus3App')
    .controller('PlanSolucionDetailController', function ($scope, $stateParams, PlanSolucion, ProgramaProduccion, CostoProduccion, Maquina) {
        $scope.planSolucion = {};
        $scope.load = function (id) {
            PlanSolucion.get({id: id}, function(result) {
              $scope.planSolucion = result;
            });
        };
        $scope.load($stateParams.id);
    });
