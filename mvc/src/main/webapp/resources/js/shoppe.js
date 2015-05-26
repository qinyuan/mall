;
(function () {
    angularUtils.controller(function ($scope, $http) {
        $scope.branchId = $.url.param('id');
        $scope.inLowPrice = true;
        initSnapshot($scope, $http);
    });
})();