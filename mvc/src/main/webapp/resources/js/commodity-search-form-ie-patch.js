;
(function () {
    $('div.content div.searchForm span.searchCommit').css({
        border: 'none'
    });
    var $searchInput = $('#searchInput');
    $searchInput.css('margin-top', 0);
    if (isIE7()) {
        $('div.searchForm div.searchType').css({
            'width': '80px',
            'float': 'left',
            'z-index': '10'
        });
        $searchInput.css({
            'width': '338px',
            'margin-top': 0
        });

        $('div.searchForm > div').eq(0).css('z-index', 11);
    }
    $(document).trigger('scroll');
    patchPlaceholder('searchInput', "resources/css/images/list/placeholder.png");
})();
