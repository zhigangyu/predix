define(['angular', './sample-module'], function(angular, sampleModule) {
    'use strict';
    return sampleModule.controller('LatheStatusCtrl', ['$scope','$http','$state', function($scope,$http,$state) {

        $scope.decks='Decks';

        $scope.clock={
            now:'IN CYCLE'
        };
        
        var updateClock = function(){
            //$scope.clock.now = new Date();
            $http.get('/api/ms/lathes')
                .success(function(response) {
                    $scope.lathes = response;
                }).error(function(){
            });
        };
        setInterval(function(){
            $scope.$apply(updateClock);
        },9000);

        updateClock();

        

        $scope.showlathe = function($id){
            console.log('lathe id = ' + $id);
            $state.go('showlathe', {data: $id});
        };
    }]);
});