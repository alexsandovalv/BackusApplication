'use strict';

angular.module('backus3App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


