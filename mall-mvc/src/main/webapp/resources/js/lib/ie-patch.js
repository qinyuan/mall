if (!Array.isArray) {
    Array.isArray = $.isArray;
}

if (!Object.create) {
    Object.create = (function () {
        function F() {
        }

        return function (o) {
            if (arguments.length != 1) {
                throw new Error('Object.create implementation only accepts one parameter.');
            }
            F.prototype = o;
            return new F()
        };
    })();
}

Object.keys = function (object) {
    var r = [];
    for (var key in object) {
        r.hasOwnProperty.call(object, key) && r.push(key);
    }
    return r;
};

function isIE7() {
    var userAgent = navigator['userAgent'].toString();
    return userAgent.indexOf('MSIE 7.0') >= 0 || userAgent.indexOf('MSIE 6.0') >= 0;
}

$(function () {
    if (isIE7()) {
        $('input[type=text]').each(function () {
            adjustHeight($(this), 0.60);
        });
        adjustHeight($('body > div.header'), 0.72);
        adjustUnsafeAnchor();
    }

    function adjustUnsafeAnchor() {
        var currentHref = location.href.toString();
        var baseHref = currentHref.replace(/\/[^/]+$/g, '') + '/';
        setTimeout(function () {
            $('a').each(function () {
                var href = this.href;
                if (href == null) {
                    return;
                }

                if (href.indexOf('unsafe:') >= 0) {
                    $(this).attr('href', href.replace('unsafe:', baseHref));
                }
            });
        }, 1000);
    }
});
function adjustHeight($target, rate) {
    //$target.css('height', ($target.height() * rate) + 'px');
}

function _supportPlaceholder() {
    return 'placeholder' in document.createElement('input');
}

function patchPlaceholder(id, url) {
    if (_supportPlaceholder()) {
        return;
    }

    var $element = $('#' + id);
    var IMAGE = 'background-image';
    var REPEAT = 'background-repeat';
    var POSITION = 'background-position';
    var X = POSITION + '-x';
    var Y = POSITION + '-y';

    var oldStyle = {};
    oldStyle[IMAGE] = $element.css(IMAGE);
    oldStyle[REPEAT] = $element.css(REPEAT);
    oldStyle[POSITION] = $element.css(X) + ' ' + $element.css(Y);

    var newStyle = {};
    newStyle[IMAGE] = 'url(' + url + ')';
    newStyle[REPEAT] = 'no-repeat';
    newStyle[POSITION] = 'left center';

    $element.keyup(updateStyle).blur(updateStyle);
    updateStyle();

    function updateStyle() {
        if ($element.val() == '') {
            $element.css(newStyle);
        } else {
            $element.css(oldStyle);
        }
    }
}
