;
(function () {
    angularUtils.controller(function ($scope) {
        $scope.reactivate = function (proxyRejectionId) {
            $.post('admin-proxy-reactivate.json', {
                'proxyRejectionId': proxyRejectionId
            }, function (data) {
                if (data['success']) {
                    alert('代理激活成功');
                } else {
                    alert(data['detail']);
                }
            });
        }
    });
})();
