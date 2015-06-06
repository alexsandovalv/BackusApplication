'use strict';

angular.module('backus3App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('costoProduccion', {
                parent: 'entity',
                url: '/costoProduccion',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.costoProduccion.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/costoProduccion/costoProduccions.html',
                        controller: 'CostoProduccionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('costoProduccion');
                        return $translate.refresh();
                    }]
                }
            })
            .state('costoProduccionDetail', {
                parent: 'entity',
                url: '/costoProduccion/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.costoProduccion.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/costoProduccion/costoProduccion-detail.html',
                        controller: 'CostoProduccionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('costoProduccion');
                        return $translate.refresh();
                    }]
                }
            });
    });
