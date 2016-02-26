define(['angular', './sample-module'], function(angular, sampleModule) {
    'use strict';
    return sampleModule.controller('ShowLatheCtrl', ['$scope','$http','$stateParams', function($scope,$http,$stateParams) {
        $scope.stateid = $stateParams.data;
        console.log('data = ' + $stateParams.data);
        $scope.showlathe = function($id){
            console.log('lathe id = ' + $id);
        };
    }]);
});