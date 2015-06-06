'use strict';

angular.module('backus3App')
    .controller('CapacidadProduccionDetailController', function ($scope, $stateParams, CapacidadProduccion, Maquina, MedidasLts, Producto) {
        $scope.capacidadProduccion = {};
        $scope.load = function (id) {
            CapacidadProduccion.get({id: id}, function(result) {
              $scope.capacidadProduccion = result;
            });
        };
        $scope.load($stateParams.id);
    });
