'use strict';

angular.module('backus3App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('programaProduccion', {
                parent: 'entity',
                url: '/programaProduccion',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.programaProduccion.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/programaProduccion/programaProduccions.html',
                        controller: 'ProgramaProduccionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('programaProduccion');
                        return $translate.refresh();
                    }]
                }
            })
            .state('programaProduccionDetail', {
                parent: 'entity',
                url: '/programaProduccion/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.programaProduccion.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/programaProduccion/programaProduccion-detail.html',
                        controller: 'ProgramaProduccionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('programaProduccion');
                        return $translate.refresh();
                    }]
                }
            });
    });
