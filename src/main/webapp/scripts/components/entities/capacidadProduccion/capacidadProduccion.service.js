'use strict';

angular.module('backus3App')
    .factory('CapacidadProduccion', function ($resource) {
        return $resource('api/capacidadProduccions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
