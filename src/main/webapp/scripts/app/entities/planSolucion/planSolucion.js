'use strict';

angular.module('backus3App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('planSolucion', {
                parent: 'entity',
                url: '/planSolucion',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.planSolucion.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/planSolucion/planSolucions.html',
                        controller: 'PlanSolucionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('planSolucion');
                        return $translate.refresh();
                    }]
                }
            })
            .state('planSolucionDetail', {
                parent: 'entity',
                url: '/planSolucion/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.planSolucion.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/planSolucion/planSolucion-detail.html',
                        controller: 'PlanSolucionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('planSolucion');
                        return $translate.refresh();
                    }]
                }
            });
    });
