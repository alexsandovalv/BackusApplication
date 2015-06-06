'use strict';

angular.module('backus3App')
    .controller('MedidasLtsController', function ($scope, MedidasLts, CapacidadProduccion, ParseLinks) {
        $scope.medidasLtss = [];
        $scope.capacidadproduccions = CapacidadProduccion.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            MedidasLts.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.medidasLtss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            MedidasLts.update($scope.medidasLts,
                function () {
                    $scope.loadAll();
                    $('#saveMedidasLtsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            MedidasLts.get({id: id}, function(result) {
                $scope.medidasLts = result;
                $('#saveMedidasLtsModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            MedidasLts.get({id: id}, function(result) {
                $scope.medidasLts = result;
                $('#deleteMedidasLtsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MedidasLts.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMedidasLtsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.medidasLts = {nombre: null, cantidad: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
