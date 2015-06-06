'use strict';

angular.module('backus3App')
    .factory('Password', function ($resource) {
        return $resource('api/account/change_password', {}, {
        });
    });
