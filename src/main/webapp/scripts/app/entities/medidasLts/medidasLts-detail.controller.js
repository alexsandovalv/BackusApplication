'use strict';

angular.module('backus3App')
    .controller('MedidasLtsDetailController', function ($scope, $stateParams, MedidasLts, CapacidadProduccion) {
        $scope.medidasLts = {};
        $scope.load = function (id) {
            MedidasLts.get({id: id}, function(result) {
              $scope.medidasLts = result;
            });
        };
        $scope.load($stateParams.id);
    });
