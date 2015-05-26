;
(function () {
    function getLocationHref(orderType, statisticType) {
        var href = location.pathname;
        var params = [];
        if (orderType) {
            params.push('orderType=' + orderType);
        }
        if (statisticType) {
            params.push('statisticType=' + statisticType);
        }
        if (params.length > 0) {
            href = href + '?' + params.join('&');
        }
        return href;
    }

    var $orderType = $('#orderType');
    $orderType.change(function () {
        location.href = getLocationHref($(this).val(), $.url.param('statisticType'));
    });
    var $statisticType = $('#statisticType');
    $statisticType.change(function () {
        location.href = getLocationHref($.url.param('orderType'), $(this).val());
    });

    var orderType = $.url.param('orderType');
    if ($.trim(orderType) != '') {
        $orderType.val(orderType);
    }
    var statisticType = $.url.param('statisticType');
    if ($.trim(statisticType) != '') {
        $statisticType.val(statisticType);
    }

    var $commodityCounts = $('td.commodityCount');

    var maxCommodityCount = getMaxCommodityCount();
    var maxShowBarWidth = 150;
    $commodityCounts.each(function () {
        var $this = $(this);
        var commodityCount = parseInt($.trim($this.text()));
        var width = commodityCount / maxCommodityCount * maxShowBarWidth;
        if (width > 0) {
            $this.find('span.showBar').css('width', width);
        }
    });

    function getMaxCommodityCount() {
        var maxCommodityCount = 0;
        $commodityCounts.each(function () {
            var commodityCount = parseInt($.trim($(this).text()));
            if (maxCommodityCount < commodityCount) {
                maxCommodityCount = commodityCount;
            }
        });
        return maxCommodityCount;
    }

    angularUtils.controller(function ($scope) {
    });
})();
