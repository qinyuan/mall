;
(function () {
    var $filterForm = $('#filterForm');

    var success = $.url.param('success');
    if (success != null) {
        $filterForm.find('select[name=success]').val(success);
    }

    var showId = $.url.param('showId');
    if(showId!=null) {
        $filterForm.find('input[name=showId]').val(showId);
    }

    angularUtils.controller(function ($scope) {
    });
})();
