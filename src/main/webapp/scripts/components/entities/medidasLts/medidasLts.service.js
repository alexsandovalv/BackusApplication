'use strict';

angular.module('backus3App')
    .factory('MedidasLts', function ($resource) {
        return $resource('api/medidasLtss/:id', {}, {
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
