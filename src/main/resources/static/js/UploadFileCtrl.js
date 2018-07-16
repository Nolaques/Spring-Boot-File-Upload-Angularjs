//controller upload file
mainApp.controller('UploadFileController', function ($scope, $http) {

    $scope.uploadResult = "";

    $scope.myForm = {
        description: "",
        files: []
    }

    $scope.doUploadFile = function () {

        var url = "/rest/uploadMultiFiles";

        var data = new FormData();

        data.append("description", $scope.myForm.description);
        for (i = 0; i < $scope.myForm.files.length; i++) {
            data.append("files", $scope.myForm.files[i]);
        }

        var config = {
            transformRequest: angular.identity,
            transformResponse: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }

        $http.post(url, data, config).then(
            // success
            function (response) {
                $scope.uploadResult = response.data;
            },
            //error
            function (response) {
                $scope.uploadResult = response.data;
            });
        };
    });
