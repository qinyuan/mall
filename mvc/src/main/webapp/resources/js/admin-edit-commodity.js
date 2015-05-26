(function () {
    var $commodityDescription = $('#commodityDescription');
    var $showId = $('input[name=showId]');
    var $commodityName = $('input[name=commodityName]');

    // validate data before submit
    $('#publishCommodity').click(function (e) {
        if (!validateBranchAndCategoryInput(e)) {
            return false;
        }
        if ($.trim($commodityName.val()) == '') {
            alert('商品名称未设置!');
            e.preventDefault();
            return false;
        } else {
            showSubmitInformation();
            return true;
        }
    });

    $commodityDescription.keyup(function () {
        var text = $(this).val();
        loadCommodityParameters('commodityDescriptionEffect', text);
    }).trigger('keyup');

    angularUtils.controller(function ($scope, $http) {
        $scope.runCrawler = function () {
            var showId = $.trim($showId.val());
            if (showId == "") {
                showCrawlerInfo($scope, '请输入商品ID!');
                $showId.focusOrSelect();
                return;
            }

            crawlCommodityInfo({
                '$scope': $scope,
                '$http': $http,
                'showId': showId,
                'callback': function (commodity) {
                    var crawlerUrl = commodity['crawlerUrl'];
                    $('#crawlerLink').text(crawlerUrl).attr('href', crawlerUrl).prev().val(crawlerUrl);

                    var buyUrl = commodity['buyUrl'];
                    $('#buyUrl').text(buyUrl).attr('href', buyUrl).prev().val(buyUrl);

                    var name = commodity['name'];
                    $('#commodityName').text(name).attr('href', crawlerUrl).prev().val(name);

                    $commodityDescription.val(commodity['parameters']).trigger('keyup');

                    var imageUrls = commodity['imageUrls'];
                    $scope.imageUrls = imageUrls;
                    $scope.originalImageUrls = JSUtils.copyArray(imageUrls);

                    var detailImageUrls = commodity['detailImageUrls'];
                    $scope.detailImageUrls = detailImageUrls;
                    $scope.originalDetailImageUrls = JSUtils.copyArray(detailImageUrls);
                }
            });
        };
        $scope.addAppraiseGroup = function (event, name) {
            var input = '<input type="text" class="form-control" name="' + name + '"/>';
            $(event.target).before(input).prev().focus();
        };

        var images = {
            init: function () {
                $scope.deleteImage = function (index) {
                    JSUtils.removeArrayItem($scope.imageUrls, index);
                    JSUtils.removeArrayItem($scope.originalImageUrls, index);
                };
                $scope.deleteDetailImage = function (index) {
                    JSUtils.removeArrayItem($scope.detailImageUrls, index);
                    JSUtils.removeArrayItem($scope.originalDetailImageUrls, index);
                };
                $scope.enlargeImage = function (index, type) {
                    enlargeImage.hideAll();
                    var $targetImage = enlargeImage.show$Image(type, index);
                    //enlargeImage.adjustImageSize($targetImage);
                    enlargeImage.adjustImagePosition($targetImage);
                    enlargeImage.show();
                };
                $scope.closeEnlargeImage = function () {
                    enlargeImage.hide();
                };
                $scope.imageUrls = [];
                $scope.detailImageUrls = [];
                var commodityId = $.url.param("id");
                if (commodityId) {
                    // load image after 1 second, or page will be blocked
                    setTimeout(function () {
                        $http.get("commodityPicture.json?commodityId=" + commodityId).success(function (data) {
                            $scope.imageUrls = data['pictures'];
                            $scope.originalImageUrls = data['originalPictures'];
                            $scope.detailImageUrls = data['detailPictures'];
                            $scope.originalDetailImageUrls = data['originalDetailPictures'];
                        });
                    }, 1000);
                }
            }
        };
        images.init();

        initBranchAndCategorySelectForm($scope, $http);
    });
})();
