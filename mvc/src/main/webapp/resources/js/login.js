;
(function () {
    var $closeLink = $('#closeLink');
    var $loginDiv = $('div.content div.login');
    var $username = $('input[name=j_username]');

    $closeLink.click(function () {
        $loginDiv.fadeOut(500);
    });
    $username.focus();
    angularUtils.controller(function ($scope, $http) {
    });
})();
