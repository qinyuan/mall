(function () {
    var $linksDiv = $('div.content > div.links');
    var $logoGroup = $linksDiv.find('div.logoGroup');
    $logoGroup.hover(function () {
        var $this = $(this);
        $this.find('div.cover').fadeIn(300);
        $this.find('div.text div.back').fadeOut(300);
    }, function () {
        var $this = $(this);
        $this.find('div.cover').fadeOut(300);
        $this.find('div.text div.back').fadeIn(300);
    });

    // foot image
    var img = '<img src="' + $.trim($('#footPoster').html()) + '"/>';
    var footPosterLink = $.trim($('#footPosterLink').html());
    if (footPosterLink != '') {
        img = '<a href="' + footPosterLink + '">' + img + '</a>';
    }
    $('<div></div>').addClass('footImage').append(img).css({
        top: $linksDiv.height() + 370
    }).appendTo($('body'));
    angularUtils.controller(function () {
    });

    $('#searchCommit').click(function () {
        getParent($(this), 'form').submit();
    });
    $('#searchInput').focusOrSelect();
})();