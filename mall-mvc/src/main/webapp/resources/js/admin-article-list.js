;
(function () {
    angularUtils.controller(function ($scope) {
        $scope.addArticle = function () {
            location.href = "admin-article-edit.html";
        };
        $scope.editArticle = function (event) {
            location.href = "admin-article-edit.html?id=" + getTableRowIdByImgElement(event.target);
        };
        $scope.deleteArticle = function (event) {
            $.post("admin-article-delete", {
                id: getTableRowIdByImgElement(event.target)
            }, normalSubmitCallback);
        }
    });
})();
