'use strict';

angular.module('backus3App')
    .controller('ProgramaProduccionDetailController', function ($scope, $stateParams, ProgramaProduccion, MedidasLts, Producto, PlanSolucion) {
        $scope.programaProduccion = {};
        $scope.load = function (id) {
            ProgramaProduccion.get({id: id}, function(result) {
              $scope.programaProduccion = result;
            });
        };
        $scope.load($stateParams.id);
    });
