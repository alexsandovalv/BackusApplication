'use strict';

angular.module('backus3App')
    .factory('ProgramaProduccion', function ($resource) {
        return $resource('api/programaProduccions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.fechaProgramada = new Date(data.fechaProgramada);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
