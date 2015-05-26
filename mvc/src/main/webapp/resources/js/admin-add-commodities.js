(function () {
    // validate data before submit
    $('#publishCommodity').click(function (e) {
        if (!validateBranchAndCategoryInput(e)) {
            return false;
        } else if ($('input[name=showId]').size() == 0) {
            alert("未录入商品！");
            e.preventDefault();
            return false;
        } else {
            showSubmitInformation();
            return true;
        }
    });

    function get$ShowIds() {
        return $('div.content div.link td.showIds input');
    }

    function getShowIdInputs() {
        var ids = [];
        get$ShowIds().each(function () {
            var value = $.trim($(this).val());
            if (value != '' && !JSUtils.isArrayContains(ids, value)) {
                ids.push(value);
            }
        });
        return ids;
    }

    angularUtils.controller(function ($scope, $http) {
        initBranchAndCategorySelectForm($scope, $http);

        function getCommodityByShowId(showId) {
            for (var i = 0, len = $scope.commodities.length; i < len; i++) {
                var commodity = $scope.commodities[i];
                if (commodity.showId == showId) {
                    return commodity;
                }
            }
            return null;
        }

        $scope.deleteCommodity = function (index) {
            JSUtils.removeArrayItem($scope.commodities, index);
        };

        $scope.deleteImage = function (showId, index) {
            var commodity = getCommodityByShowId(showId);
            if (commodity) {
                JSUtils.removeArrayItem(commodity.imageUrls, index);
            }
        };

        $scope.deleteDetailImage = function (showId, index) {
            var commodity = getCommodityByShowId(showId);
            if (commodity) {
                JSUtils.removeArrayItem(commodity.detailImageUrls, index);
            }
        };

        $scope.enlargeImage = function (showId, index, type) {
            var $div = $('#enlargeImage_' + showId);
            var size = $div.size();
            if (size <= 0) {
                return;
            } else if (size == 1) {
                enlargeImage.set$Div($div);
            } else {
                enlargeImage.set$Div($div.eq(0));
            }

            enlargeImage.hideAll();
            var $targetImage = enlargeImage.show$Image(type, index);
            //enlargeImage.adjustImageSize($targetImage);
            enlargeImage.adjustImagePosition($targetImage);
            enlargeImage.show();
        };
        $scope.closeEnlargeImage = function () {
            enlargeImage.hide();
        };

        $scope.addShowId = function (event) {
            var $this = $(event.target);
            $this.prev().clone().val('').insertBefore($this).focus();
        };

        function initCommodities() {
            $scope.commodities = [];
            /*$scope.commodities.push({
             showId: 'AAAAAAAAAAAA',
             url: 'AAAAAAAAAAAAAAAAAAAaaa',
             buyUrl: 'BBBBBBBBBBBBBB',
             name: 'AAAAAAAAAAAA',
             imageUrls: [],
             detailImageUrls: [],
             parameters: 'HH'
             });*/
        }

        $scope.runCrawler = function () {
            var ids = getShowIdInputs();
            if (ids.length == 0) {
                showCrawlerInfo($scope, '请录入商品ID');
                return;
            }

            initCommodities();

            crawlCommoditiesInfo({
                '$scope': $scope,
                '$http': $http,
                'showIds': ids,
                'callback': function (commodity) {
                    $scope.commodities.push(commodity);
                }
            });
        };
        initCommodities();
    });
})();
function commodityDescriptionKeyup(target) {
    var text = $(target).val();
    var id = target.id.replace('commodityDescription', 'commodityDescriptionEffect');
    loadCommodityParameters(id, text);
}
