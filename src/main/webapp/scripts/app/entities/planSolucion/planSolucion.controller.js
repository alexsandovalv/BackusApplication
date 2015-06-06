'use strict';

angular.module('backus3App')
    .controller('PlanSolucionController', function ($scope, PlanSolucion, ProgramaProduccion, CostoProduccion, Maquina, ParseLinks) {
        $scope.planSolucions = [];
        $scope.programaproduccions = ProgramaProduccion.query();
        $scope.costoproduccions = CostoProduccion.query();
        $scope.maquinas = Maquina.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            PlanSolucion.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.planSolucions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            PlanSolucion.update($scope.planSolucion,
                function () {
                    $scope.loadAll();
                    $('#savePlanSolucionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            PlanSolucion.get({id: id}, function(result) {
                $scope.planSolucion = result;
                $('#savePlanSolucionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            PlanSolucion.get({id: id}, function(result) {
                $scope.planSolucion = result;
                $('#deletePlanSolucionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PlanSolucion.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePlanSolucionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.planSolucion = {diaTrabajada: null, cantidad: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
