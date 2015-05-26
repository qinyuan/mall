;
(function () {
    if (isIE7()) {
        $('div.content > div.left div.snapshot').css({
            'margin-top': '0'
        }).find('div.right').css({
            'width': '340px',
            'z-index': '4'
        }).find('div.name').css({
            'width': '290px',
            'z-index': '4'
        }).next().css({
            'z-index': '4'
        });
        $('div.content > div.left div.description div.title').css({
            'padding-top': '7px',
            'padding-bottom': '0',
            'height': '30px'
        });
    }
})();
