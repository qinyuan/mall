;
(function () {
    if (isIE7()) {
        adjustHeight($('div.navigationName'), 0.6);
        $('div.search div.subCategory').css('width', '190px');
        $('div.search div.subCategory > div').each(function () {
            var textLength = $(this).text().length;
            var width = textLength * 12 + 16;
            $(this).css('width', width + 'px');
        });
        adjustHeight($('div.search > div.right div.searchFormBack'), 0.4);
    }
})();
