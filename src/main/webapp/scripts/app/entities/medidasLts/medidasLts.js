'use strict';

angular.module('backus3App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('medidasLts', {
                parent: 'entity',
                url: '/medidasLts',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.medidasLts.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/medidasLts/medidasLtss.html',
                        controller: 'MedidasLtsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('medidasLts');
                        return $translate.refresh();
                    }]
                }
            })
            .state('medidasLtsDetail', {
                parent: 'entity',
                url: '/medidasLts/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'backus3App.medidasLts.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/medidasLts/medidasLts-detail.html',
                        controller: 'MedidasLtsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('medidasLts');
                        return $translate.refresh();
                    }]
                }
            });
    });
