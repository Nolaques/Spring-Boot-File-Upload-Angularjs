mainApp.controller('GetFilesController', function ($scope, $http) {

    $scope.allFiles = [];

    $scope.getAllFiles = function () {

        //REST URL:
        var url = "/rest/getAllFiles";
        $http.get(url).then(
            //success
            function (response) { /*alert("OK");*/
                $scope.allFiles = response.data;
            },
            //error
            function (response) {
                alert("Error: " + response.data)
            }
        );
    };
});