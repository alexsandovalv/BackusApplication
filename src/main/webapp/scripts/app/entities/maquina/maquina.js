'use strict';

angular.module('backus3App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('maquina', {
                parent: 'entity',
                url: '/maquina',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.maquina.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/maquina/maquinas.html',
                        controller: 'MaquinaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('maquina');
                        return $translate.refresh();
                    }]
                }
            })
            .state('maquinaDetail', {
                parent: 'entity',
                url: '/maquina/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.maquina.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/maquina/maquina-detail.html',
                        controller: 'MaquinaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('maquina');
                        return $translate.refresh();
                    }]
                }
            });
    });
