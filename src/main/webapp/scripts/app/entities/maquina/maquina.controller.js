'use strict';

angular.module('backus3App')
    .controller('MaquinaController', function ($scope, Maquina, CapacidadProduccion, ParseLinks) {
        $scope.maquinas = [];
        $scope.capacidadproduccions = CapacidadProduccion.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Maquina.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.maquinas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Maquina.update($scope.maquina,
                function () {
                    $scope.loadAll();
                    $('#saveMaquinaModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Maquina.get({id: id}, function(result) {
                $scope.maquina = result;
                $('#saveMaquinaModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Maquina.get({id: id}, function(result) {
                $scope.maquina = result;
                $('#deleteMaquinaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Maquina.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMaquinaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.maquina = {costo: null, nombre: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
