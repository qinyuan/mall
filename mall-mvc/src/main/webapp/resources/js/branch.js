;
(function () {
    angularUtils.controller(function ($scope, $http) {
        $http.get('json/branchGroups.json').success(function (data) {
            $scope.branchGroups = data;
        });
        $scope.letterClick = function (letter) {
            $('div.branchGroup').each(function () {
                if ($.trim($(this).find('div.title').text()) == $.trim(letter)) {
                    scrollTop($(this));
                    return false;
                } else {
                    return true;
                }
            });
        };
    });
})();
