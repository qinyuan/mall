;
function initSnapshot($scope, $http) {
    $('div.goods div.sort div.title div').click(function () {
        var $this = $(this);
        if ($this.hasClass('all')) {
            $scope.inLowPrice = false;
        } else if ($this.hasClass('lowest')) {
            $scope.inLowPrice = true;
        }
        $scope.pageNumber = 0;
        loadSnapshot($scope, $http);
        $this.siblings().css('border-bottom-width', '0');
        $this.css('border-bottom-width', '2px');
    });
    $scope.switchSortLinks = function (event, orderField) {
        var $sortLinks = $('div.sort > div.links a');
        var $this = $(event.target);
        var $image = $this.find('img');
        $scope.orderField = orderField;
        $scope.orderType = 'asc';
        switch ($image.attr('src')) {
            case images.unSort:
                $sortLinks.find('img').attr('src', images.unSort);
                $image.attr('src', images.arrowUp);
                break;
            case images.arrowDown:
                $image.attr('src', images.arrowUp);
                break;
            case images.arrowUp:
                $image.attr('src', images.arrowDown);
                $scope.orderType = 'desc';
                break;
        }
        $scope.pageNumber = 0;
        loadSnapshot($scope, $http);
    };
    $scope.loadMore = function () {
        if ($scope.hasNext) {
            $scope.pageNumber = $scope.pageNumber + 1;
            loadSnapshot($scope, $http);
        }
    };
    $scope.orderField = 'discoverTime';
    $scope.orderType = 'desc';
    $scope.pageNumer = 0;
    loadSnapshot($scope, $http);
    $(document).scroll(function () {
        if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
            $scope.loadMore();
        }
    });
}
function _buildSnapshotUrl($scope, pageNumber) {
    var params = [];
    if ($scope.categoryId) {
        params.push("categoryId=" + $scope.categoryId);
    }
    if ($scope.branchId) {
        params.push('branchId=' + $scope.branchId);
    }
    if ($scope.keyWord) {
        params.push("keyWord=" + encodeURI($scope.keyWord));
    }
    if ($scope.inLowPrice) {
        params.push("inLowPrice=true");
    } else {
        params.push('inLowPrice=false');
    }
    if ($scope.orderField) {
        params.push("orderField=" + $scope.orderField);
    }
    if ($scope.orderType) {
        params.push("orderType=" + $scope.orderType);
    }
    if ($scope.pageNumber == null) {
        $scope.pageNumber = 0;
    }
    params.push('pageNumber=' + $scope.pageNumber);
    return "json/commoditySnapshot.json?" + params.join('&');
}

function _get$LoadMoreDiv() {
    return $('div.content div.goods > div.loadMore');
}

function _get$LoadingDiv() {
    return $('div.content div.goods > div.loading');
}

function loadSnapshot($scope, $http) {
    var url = _buildSnapshotUrl($scope);
    _get$LoadingDiv().show();
    $http.get(url).success(function (data) {
        if ($scope.pageNumber == 0) {
            $scope.snapshots = data.snapshots;
        } else {
            $scope.snapshots = $scope.snapshots.concat(data.snapshots);
        }
        _get$LoadingDiv().hide();
        $scope.hasNext = data.hasNext;
        if ($scope.hasNext) {
            _get$LoadMoreDiv().show();
        } else {
            _get$LoadMoreDiv().hide();
        }
    });
    if (window.afterLoadSnapshot) {
        window.afterLoadSnapshot();
    }
}

(function () {
    var $collectButton = $('#collectButton');
    $collectButton.click(function () {
    });
    var $refreshButton = $('#refreshButton');
    $refreshButton.click(function () {
        window.location.reload();
    });
})();
