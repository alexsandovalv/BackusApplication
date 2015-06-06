'use strict';

angular.module('backus3App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('producto', {
                parent: 'entity',
                url: '/producto',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.producto.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/producto/productos.html',
                        controller: 'ProductoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('producto');
                        return $translate.refresh();
                    }]
                }
            })
            .state('productoDetail', {
                parent: 'entity',
                url: '/producto/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.producto.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/producto/producto-detail.html',
                        controller: 'ProductoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('producto');
                        return $translate.refresh();
                    }]
                }
            });
    });
