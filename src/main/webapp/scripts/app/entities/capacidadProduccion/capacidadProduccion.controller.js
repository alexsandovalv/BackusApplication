'use strict';

angular.module('backus3App')
    .controller('CapacidadProduccionController', function ($scope, CapacidadProduccion, Maquina, MedidasLts, Producto) {
        $scope.capacidadProduccions = [];
        $scope.maquinas = Maquina.query();
        $scope.medidasltss = MedidasLts.query();
        $scope.productos = Producto.query();
        $scope.loadAll = function() {
            CapacidadProduccion.query(function(result) {
               $scope.capacidadProduccions = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            CapacidadProduccion.update($scope.capacidadProduccion,
                function () {
                    $scope.loadAll();
                    $('#saveCapacidadProduccionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            CapacidadProduccion.get({id: id}, function(result) {
                $scope.capacidadProduccion = result;
                $('#saveCapacidadProduccionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            CapacidadProduccion.get({id: id}, function(result) {
                $scope.capacidadProduccion = result;
                $('#deleteCapacidadProduccionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            CapacidadProduccion.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCapacidadProduccionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.capacidadProduccion = {capacidad: null, velocidad: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
