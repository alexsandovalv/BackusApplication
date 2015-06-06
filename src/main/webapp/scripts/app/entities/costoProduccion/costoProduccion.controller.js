'use strict';

angular.module('backus3App')
    .controller('CostoProduccionController', function ($scope, CostoProduccion, PlanSolucion, ParseLinks) {
        $scope.costoProduccions = [];
        $scope.plansolucions = PlanSolucion.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            CostoProduccion.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.costoProduccions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            CostoProduccion.update($scope.costoProduccion,
                function () {
                    $scope.loadAll();
                    $('#saveCostoProduccionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            CostoProduccion.get({id: id}, function(result) {
                $scope.costoProduccion = result;
                $('#saveCostoProduccionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            CostoProduccion.get({id: id}, function(result) {
                $scope.costoProduccion = result;
                $('#deleteCostoProduccionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            CostoProduccion.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCostoProduccionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.costoProduccion = {manoObra: null, horaMaquina: null, total: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
