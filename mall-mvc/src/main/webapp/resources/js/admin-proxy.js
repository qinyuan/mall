;
(function () {
    JSUtils.recordScrollStatus();
    angularUtils.controller(function ($scope) {
        $scope.reactivate = function (proxyRejectionId) {
            $.post('admin-proxy-reactivate.json', {
                'proxyRejectionId': proxyRejectionId
            }, normalSubmitCallback);
        }
    });
})();
