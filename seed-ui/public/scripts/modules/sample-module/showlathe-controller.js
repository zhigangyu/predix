define(['angular', './sample-module'], function(angular, sampleModule) {
    'use strict';
    return sampleModule.controller('ShowLatheCtrl', ['$scope','$http','$stateParams', function($scope,$http,$stateParams) {
        $scope.stateid = $stateParams.data;
        $scope.options = { thickness: 5 , mode: 'gauge' , total: 100};
        $scope.gaugedata = [{ label: 'Temperature' , value: 0, suffix: '\xB0C' , color: '#d62728' }];

        var updateLatheStatus = function(){
            $http.get('/api/ms/lathe/' + $stateParams.data)
                .success(function(response) {
                    $scope.lathestatus = response;

                    angular.forEach(response, function(data){
                        console.log(data.lable + '=' + data.value);
                        switch(data.lable){
                            case 'rapid traverse':
                                $scope.rapidTraverse = data.value;
                                break;
                            case 'temperature':
                                $scope.gaugedata[0].value = data.value;
                                break;

                        }
                    });

                }).error(function(){
            });
        };
        setInterval(function(){
            $scope.$apply(updateLatheStatus);
        },9000);

        updateLatheStatus();
        $scope.showlathe = function($id){
            console.log('lathe id = ' + $id);
        };

        

    }]);
});