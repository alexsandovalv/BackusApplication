'use strict';

angular.module('backus3App')
    .controller('MaquinaDetailController', function ($scope, $stateParams, Maquina, CapacidadProduccion) {
        $scope.maquina = {};
        $scope.load = function (id) {
            Maquina.get({id: id}, function(result) {
              $scope.maquina = result;
            });
        };
        $scope.load($stateParams.id);
    });
