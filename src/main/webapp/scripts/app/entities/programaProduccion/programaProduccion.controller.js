'use strict';

angular.module('backus3App')
    .controller('ProgramaProduccionController', function ($scope, ProgramaProduccion, MedidasLts, Producto, PlanSolucion, ParseLinks) {
        $scope.programaProduccions = [];
        $scope.medidasltss = MedidasLts.query();
        $scope.productos = Producto.query();
        $scope.plansolucions = PlanSolucion.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            ProgramaProduccion.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.programaProduccions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
        
        $scope.processLingo = function () {
            ProgramaProduccion.get("lingo");
        };

        $scope.create = function () {
            ProgramaProduccion.update($scope.programaProduccion,
                function () {
                    $scope.loadAll();
                    $('#saveProgramaProduccionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            ProgramaProduccion.get({id: id}, function(result) {
                $scope.programaProduccion = result;
                $('#saveProgramaProduccionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            ProgramaProduccion.get({id: id}, function(result) {
                $scope.programaProduccion = result;
                $('#deleteProgramaProduccionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ProgramaProduccion.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProgramaProduccionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.programaProduccion = {cantidad: null, diaTrabajada: null, fechaProgramada: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
