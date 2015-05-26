;
(function () {
    function loadPicturesData(elementId) {
        var pictures = $('#' + elementId).text();
        pictures = pictures.substring(1, pictures.length - 1);
        pictures = pictures.split(',');
        for (var i = 0, len = pictures.length; i < len; i++) {
            pictures[i] = $.trim(pictures[i]);
        }
        return pictures;
    }

    var pictures = loadPicturesData('pictures');
    var middlePictures = loadPicturesData('middlePictures');

    var historyTrend = {
        _chartCreated: false,
        _data: null,
        loadData: function () {
            //var commodityId = parseInt($.url.param('id'));
            var commodityId = parseInt($('#commodityId').val());
            var url = "priceHistory.json?commodityId=" + commodityId;
            var self = this;
            $.get(url, function (data) {
                var xSerial = [], ySerial = [];
                if (data[commodityId] && data[commodityId]['prices']) {
                    $.each(data[commodityId]['prices'], function () {
                        xSerial.push(this['date']);
                        ySerial.push(this['price']);
                    });
                }
                self._data = {
                    xSerial: xSerial,
                    ySerial: ySerial,
                    width: 300,
                    height: 200
                };
            });
        },
        show: function () {
            $elements.priceHistory.show();
            if (!this._chartCreated) {
                priceLineChart($elements.trendChart.attr('id'), this._data);
                this._chartCreated = true;
            }
        },
        hide: function () {
            $elements.priceHistory.hide();
        }
    };
    historyTrend.loadData();

    var $elements = {
        smallImages: $('div.snapshot div.left div.smallImage img'),
        trendChart: $('#trendChart'),
        trendDiv: $('div.content > div.left div.snapshot div.right div.price div.trend'),
        trendTextDiv: $('div.content > div.left div.snapshot div.right div.price div.trend div.text'),
        priceHistory: $('div.content > div.left div.snapshot div.right div.priceHistory'),
        descriptionTitles: $('div.description div.title span'),
        descriptionDetail: $('div.description div.detail'),
        snapshotRightPrice: $('div.content > div.left div.snapshot div.right div.price'),
        largeImage: $('div.content > div.left div.snapshot div.left div.largeImage'),
        enlargeImage: $('div.content > div.left div.snapshot div.left div.enlargeImage'),
        enlargeIcon: $('div.content > div.left div.snapshot div.left div.largeImage div.enlargeIcon'),
        couponLink: $('#couponLink'),
        closeEnlargeIcon: $('div.content > div.left div.snapshot div.left div.enlargeImage div.closeEnlargeIcon'),
        otherCommodityImages: $('div.content > div.right div.whiteBack div.other > div img')
    };

    //initFoundTime();
    $elements.smallImages.mouseover(function () {
        $elements.smallImages.filter('.selected').removeClass('selected');
        var $this = $(this);
        $this.addClass('selected');
        var index = $this.dataOptions()['index'];
        $elements.largeImage.find('img.boxShadow').hide().eq(index).show().get(0).onload();
        $elements.enlargeImage.find('img').hide().eq(index).show().get(0).onload();
        // TODO
    }).eq(0).trigger('mouseover');
    $elements.trendDiv.hover(function () {
        historyTrend.show();
    }, function () {
        historyTrend.hide();
    });
    $elements.trendTextDiv.hover(function () {
        historyTrend.show();
    }, function () {
    });
    $elements.descriptionTitles.mouseover(function () {
        $elements.descriptionTitles.filter('.selected').removeClass('selected');
        $(this).addClass('selected');
        if ($(this).hasClass('goodsDetail')) {
            $elements.descriptionDetail.find('div.goodsDetail').show();
            $elements.descriptionDetail.find('div.shoppe').hide();
        } else {
            $elements.descriptionDetail.find('div.goodsDetail').hide();
            $elements.descriptionDetail.find('div.shoppe').show();
        }
    });
    $elements.snapshotRightPrice.hover(function () {
    }, function () {
        historyTrend.hide();
    });
    $elements.enlargeIcon.click(function () {
        transparentBackground.show();
        $elements.enlargeImage.fadeIn(250).find('>img').each(function () {
            this.onload();
        });
    }).hover(function () {
        $(this).removeClass('mediumTransparent');
    }, function () {
        $(this).addClass('mediumTransparent');
    });
    $elements.closeEnlargeIcon.click(function () {
        $elements.enlargeImage.fadeOut(250);
        transparentBackground.hide();
    });
    $elements.otherCommodityImages.hover(function () {
        $(this).addClass('deepTransparent').css('border-color', '#F33286');
    }, function () {
        $(this).removeClass('deepTransparent').css('border-color', '#ffffff');
    });
    loadCommodityParameters("commodityParameters", $('#commodityParametersData').text());
    setHoverColor($elements.couponLink, '#3E3E3E');
    angularUtils.controller(function () {
    });
})();
