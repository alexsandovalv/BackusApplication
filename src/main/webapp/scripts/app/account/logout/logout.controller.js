'use strict';

angular.module('backus3App')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
