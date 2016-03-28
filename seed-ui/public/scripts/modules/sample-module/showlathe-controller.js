define(['angular', './sample-module'], function(angular, sampleModule) {
    'use strict';
    return sampleModule.controller('ShowLatheCtrl', ['$scope','$http','$stateParams','$timeout', function($scope,$http,$stateParams,$timeout) {
        $scope.stateid = $stateParams.data;
        $scope.options = { thickness: 5 , mode: 'gauge' , total: 100};
        $scope.gaugedata = [{ label: 'Temperature' , value: 0, suffix: '\xB0C' , color: '#d62728' }];

        $scope.value = 0;
        $scope.upperLimit = 6000;
        $scope.lowerLimit = 0;
        $scope.unit = 'r';
        $scope.precision = 0;
        $scope.ranges = [{
            min: 0,
            max: 1200,
            color: '#DEDEDE'
        }, {
            min: 1200,
            max: 2400,
            color: '#8DCA2F'
        }, {
            min: 2400,
            max: 3600,
            color: '#FDC702'
        }, {
            min: 3600,
            max: 4800,
            color: '#FF7700'
        }, {
            min: 4800,
            max: 6000,
            color: '#C50200'
        }];
        var updateLatheStatus = function(){
            $http.get('/api/ms/lathe/' + $stateParams.data)
                .success(function(response) {
                    $scope.lathestatus = response;
                    var power,program,alarm;
                    angular.forEach(response, function(data){
                    

                        switch(data.lable){
                            case 'rapid traverse':
                                $scope.rapidTraverse = data.value;
                                break;
                            case 'spindle speed':
                                $scope.gaugedata[0].value =  parseInt(data.value);
                                console.log('spindle speed = ' + parseInt(data.value));
                                break;
                            case 'power':
                                power = data.value;
                                break;
                            case 'program':
                                program = data.value;
                                break;
                            case 'alarm':
                                alarm = data.value;
                                break;
                        }

                    });
                    var img = 'gray';
                    if(power === '1'){
                        if(program === '1'){
                            img = 'green';
                        }else{
                            img = 'yellow';
                        }
                    }
                    if(alarm === '1'){
                        img = 'red';
                    }
                    $scope.status = img;
                }).error(function(){
            });
        };
        setInterval(function(){
            $scope.$apply(updateLatheStatus);
        },5000);

        updateLatheStatus();

        function update() {
            $timeout(function() {
                $scope.value = $scope.gaugedata[0].value;
                if ($scope.value > $scope.upperLimit) {
                    $scope.value = $scope.lowerLimit;
                }

                var now = new Date();
                var days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
                var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

                var day = days[now.getDay()];

                var month = months[now.getMonth()];
                var dd = now.getDate();
                var yyyy = now.getFullYear();

                var hour=now.getHours();
                var minu=now.getMinutes();
                var sec=now.getSeconds();
                if(sec<10) {sec='0'+sec;}
                if(minu<10) {minu='0'+minu;}
                if(hour<10) {hour='0'+hour;}
                var tt = hour + ':' + minu + ':' + sec;
                $scope.myDate = dd;
                $scope.myTime = tt;
                $scope.myYear = yyyy;
                $scope.myMonth = month;
                $scope.myDay = day;

                update();
            }, 1000);
        }
        update();

    }]);
});