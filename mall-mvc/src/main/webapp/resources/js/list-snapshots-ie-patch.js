;
(function () {
    function adjustImageUrl() {
        function getBackgroundImageFromNgStyle(ngStyle) {
            var arr = ngStyle.split(';');
            for (var i = 0; i < arr.length; i++) {
                if (arr[i].indexOf('background-image') >= 0) {
                    return arr[i].replace('background-image', '').replace(/^\s*:\s*/g, '');
                }
            }
            return null;
        }

        $('div.images div.imageGroup').css({
            'width': '305px'
        });
        $('div.images div.description').css({
            'width': '270px',
            'padding-right': '10px',
            'height': '60px'
        });
        $('div.images div.price').css({
            'height': '20px'
        });
        $('div.images div.image').css({
            'cursor': 'pointer'
        });
        $('div.images div.image div').each(function () {
            var ngStyle = $(this).attr('ng-style');
            var backgroundImage = getBackgroundImageFromNgStyle(ngStyle);
            $(this).css({
                'background-image': backgroundImage,
                'cursor': 'pointer'
            });
        });
    }

    if (isIE7()) {
        window.afterLoadSnapshot = function () {
            setTimeout(function () {
                adjustImageUrl();
            }, 500);
        }
    }
})();