'use strict';

angular.module('backus3App')
    .controller('ProductoController', function ($scope, Producto, CapacidadProduccion, ParseLinks) {
        $scope.productos = [];
        $scope.capacidadproduccions = CapacidadProduccion.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Producto.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.productos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Producto.update($scope.producto,
                function () {
                    $scope.loadAll();
                    $('#saveProductoModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Producto.get({id: id}, function(result) {
                $scope.producto = result;
                $('#saveProductoModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Producto.get({id: id}, function(result) {
                $scope.producto = result;
                $('#deleteProductoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Producto.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProductoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.producto = {nombre: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
