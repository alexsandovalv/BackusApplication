'use strict';

angular.module('backus3App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('capacidadProduccion', {
                parent: 'entity',
                url: '/capacidadProduccion',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.capacidadProduccion.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/capacidadProduccion/capacidadProduccions.html',
                        controller: 'CapacidadProduccionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('capacidadProduccion');
                        return $translate.refresh();
                    }]
                }
            })
            .state('capacidadProduccionDetail', {
                parent: 'entity',
                url: '/capacidadProduccion/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.capacidadProduccion.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/capacidadProduccion/capacidadProduccion-detail.html',
                        controller: 'CapacidadProduccionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('capacidadProduccion');
                        return $translate.refresh();
                    }]
                }
            });
    });
