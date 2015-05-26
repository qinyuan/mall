(function () {
    var $selectButtons = $('div.content div.basic button');
    $selectButtons.click(function () {
        $(this).css({
            'background-color': '#ffffff',
            'background-image': 'url("resources/css/images/edit-commodity/select.png")'
        });
    });

    $(document).click(function (event) {
        var $target = $(event.target);
        if ($target.is('button')) {
            var $next = $target.next();
            if ($next.is('div') && $next.hasClass('branchDropdown')) {
                return;
            }
        }
        $('div.branchDropdown').hide();
    });
})();

function initBranchAndCategorySelectForm($scope, $http) {
    var $initBranchId = $('#initBranchId');
    var $initCategoryId = $('#initCategoryId');

    var selectForm = {
        select: function (dataModel, idToSelect, subDataModel, url, callBack, retryTimes) {
            if (retryTimes == null) {
                retryTimes = 0;
            } else if (retryTimes >= 100) {
                return;
            }
            for (var i = 0, len = dataModel.items.length; i < len; i++) {
                var item = dataModel.items[i];
                if (item.id == idToSelect) {
                    dataModel.selected.id = item.id;
                    dataModel.selected.name = item.name;
                    if (subDataModel) {
                        selectForm.load(url + "?parentId=" + item.id, subDataModel, callBack);
                    }
                    setTimeout(function () {
                        $scope.$apply();
                    }, 100);
                    return;
                }
            }
            var self = this;
            setTimeout(function () {
                self.select(dataModel, idToSelect, subDataModel, url, callBack, retryTimes + 1);
            }, 50);
        },
        load: function (url, dataModel, callBack) {
            var items = dataModel.items;
            $http.get(url).success(function (data) {
                items.splice(0, items.length);
                dataModel.disabled = (data.length == 0);
                dataModel.selected.id = dataModel['default'].id;
                dataModel.selected.name = dataModel['default'].name;
                $.each(data, function () {
                    items.push(this);
                });
                if (callBack) {
                    callBack();
                }
            });
        }
    };

    var category = {
        init: function () {
            $scope.category = {
                'default': {id: 0, 'name': '(一级分类)'},
                selected: {id: 0, 'name': '(一级分类)'},
                items: []
            };
            var categoryJsonUrl = "json/category.json";
            selectForm.load(categoryJsonUrl, $scope.category);
            $scope.subCategory = {
                disabled: true,
                'default': {id: 0, name: '(二级分类)'},
                selected: {id: 0, name: '(二级分类)'},
                items: []
            };
            var recordCategoryKey = 'record-category';
            $scope.selectCategory = function (id) {
                $.cookie(recordCategoryKey, id);
                selectForm.select($scope.category, id, $scope.subCategory, categoryJsonUrl);
            };
            $scope.selectSubCategory = function (id) {
                $.cookie(recordCategoryKey, id);
                selectForm.select($scope.subCategory, id);
            };
            var initCategoryId = $initCategoryId.val();
            if (initCategoryId != '') {
                this.setInitialValue(initCategoryId, categoryJsonUrl);
            } else if ($.cookie(recordCategoryKey) && $.url.param('id') == null) {
                this.setInitialValue($.cookie(recordCategoryKey), categoryJsonUrl);
            }
        },
        setInitialValue: function (initCategoryId, categoryJsonUrl) {
            setTimeout(function () {
                $http.get('json/parentCategory.json?categoryId=' + initCategoryId).success(function (data) {
                    var categoryId = data['categoryId'];
                    if (categoryId) {
                        selectForm.select($scope.category, categoryId, $scope.subCategory, categoryJsonUrl, function () {
                            var subCategoryId = data["subCategoryId"];
                            if (subCategoryId) {
                                selectForm.select($scope.subCategory, subCategoryId);
                            }
                        });
                    }
                });
            }, 100);
        }
    };

    var branch = {
        init: function () {
            $scope.showBranchesToSelect = function (event) {
                var $next = $(event.target).next();
                if ($next.css('display') == 'none') {
                    $next.show();
                } else {
                    $next.hide();
                }
            };
            $scope.getBranchGroupLetterStyle = function (index) {
                return index == 0 ? 'selected' : '';
            };
            $scope.getBranchGroupStyle = function (index) {
                return index > 0 ? 'display:none;' : '';
            };
            $scope.showBranchGroup = function (event, index) {
                showBranchGroup($(event.target).parent().parent(), index);
            };
            $scope.branch = {
                'default': {id: 0, name: '(品牌选择)'},
                selected: {id: 0, name: '(品牌选择)'},
                items: []
            };
            var branchJsonUrl = "json/partialBranchGroupsIgnoreEmpty.json";
            selectForm.load(branchJsonUrl, $scope.branch);
            $scope.subBranch1 = {
                disabled: true,
                'default': {id: 0, name: '(一级子品牌)'},
                selected: {id: 0, name: '(一级子品牌)'},
                items: []
            };
            $scope.subBranch2 = {
                disabled: true,
                'default': {id: 0, name: '(二级子品牌)'},
                selected: {id: 0, name: '(二级子品牌)'},
                items: []
            };
            var recordBranchKey = 'record-branch';

            $scope.selectBranch = function (id) {
                $.cookie(recordBranchKey, id);
                branch.select($scope.branch, id, $scope.subBranch1, branchJsonUrl);
                $scope.subBranch2.disabled = true;
            };
            $scope.selectSubBranch1 = function (id) {
                $.cookie(recordBranchKey, id);
                branch.select($scope.subBranch1, id, $scope.subBranch2, branchJsonUrl);
            };
            $scope.selectSubBranch2 = function (id) {
                $.cookie(recordBranchKey, id);
                branch.select($scope.subBranch2, id);
            };
            var initBranchId = $initBranchId.val();
            if (initBranchId != '') {
                this.setInitialValue(initBranchId, branchJsonUrl);
            } else if ($.cookie(recordBranchKey) && $.url.param('id') == null) {
                this.setInitialValue($.cookie(recordBranchKey), branchJsonUrl);
            }
        },
        setInitialValue: function (initBranchId, branchJsonUrl) {
            setTimeout(function () {
                $http.get('json/parentBranch.json?branchId=' + initBranchId).success(function (data) {
                    var branchId = data['branchId'];
                    if (branchId) {
                        branch.select($scope.branch, branchId, $scope.subBranch1, branchJsonUrl, function () {
                            var subBranch1Id = data["subBranch1Id"];
                            if (subBranch1Id) {
                                branch.select($scope.subBranch1, subBranch1Id, $scope.subBranch2, branchJsonUrl, function () {
                                    var subBranch2Id = data["subBranch2Id"];
                                    if (subBranch2Id) {
                                        branch.select($scope.subBranch2, subBranch2Id);
                                    }
                                });
                            }
                        });
                    }
                });
            }, 100)
        },
        select: function (dataModel, idToSelect, subDataModel, url, callBack, retryTimes) {
            if (retryTimes == null) {
                retryTimes = 0;
            } else if (retryTimes >= 100) {
                return;
            }
            for (var i = 0, len = dataModel.items.length; i < len; i++) {
                for (var j = 0, len2 = dataModel.items[i]['branches'].length; j < len2; j++) {
                    var branch = dataModel.items[i]['branches'][j];
                    if (branch.id == idToSelect) {
                        dataModel.selected.id = branch.id;
                        dataModel.selected.name = branch.name;
                        if (subDataModel) {
                            selectForm.load(url + "?parentId=" + branch.id, subDataModel, callBack);
                        }
                        setTimeout(function () {
                            $scope.$apply();
                        }, 100);
                        return;
                    }
                }
            }
            var self = this;
            setTimeout(function () {
                self.select(dataModel, idToSelect, subDataModel, url, callBack, retryTimes + 1);
            }, 50);
        }
    };

    category.init();
    branch.init();
}

function validateBranchAndCategoryInput(submitEvent) {
    var $branchId = $('input[name=branchId]');
    var $categoryId = $('input[name=categoryId]');
    if (parseInt($branchId.val()) <= 0) {
        alert('品牌未设置!');
        submitEvent.preventDefault();
        return false;
    } else if (parseInt($categoryId.val()) <= 0) {
        alert('商品分类未设置!');
        submitEvent.preventDefault();
        return false;
    } else {
        return true;
    }
}

function showSubmitInformation() {
    var $submitInfo = $('#submitInfo');
    $submitInfo.fadeIn(200);
}

function hideCrawlerInfo($scope) {
    $scope.crawlerInfo = '';
    $scope.showCrawlerInfo = false;
}

function showCrawlerInfo($scope, info) {
    $scope.crawlerInfo = info;
    $scope.showCrawlerInfo = true;
}

function getCrawlerUrlByShowId(showId) {
    return "http://s.etao.com/detail/" + showId + ".html";
}

function crawlCommoditiesInfo(options) {
    var url = "commodities-crawler.json";
    var showIds = options['showIds'];
    for (var i = 0, len = showIds.length; i < len; i++) {
        var showId = showIds[i];
        if (i == 0) {
            url += "?showIds=" + showId;
        } else {
            url += "&showIds=" + showId;
        }
        url += "&crawlerUrls=" + getCrawlerUrlByShowId(showId);
    }
    showCrawlerInfo(options.$scope, '正在抓取网页...');
    options.$http.get(url).success(function (data) {
        var errorInfo = "";
        for (var i = 0, len = data.length; i < len; i++) {
            var commodity = data[i];
            if (!commodity.success) {
                if (errorInfo != '') {
                    errorInfo += ', ';
                }
                errorInfo += commodity.detail;
                continue;
            }

            if (options.callback) {
                options.callback(commodity);
            }
        }
        if (errorInfo == '') {
            hideCrawlerInfo(options.$scope);
        } else {
            showCrawlerInfo(options.$scope, errorInfo);
        }
        setTimeout(function () {
            $('textarea[name=parameters]').trigger('keyup');
        }, 500);
    });
}

function crawlCommodityInfo(options) {
    var crawlerUrl = getCrawlerUrlByShowId(options.showId);
    showCrawlerInfo(options.$scope, '正在抓取网页...');
    var url = "commodity-crawler.json?showId=" + options.showId + "&crawlerUrl=" + crawlerUrl;

    var commodityId = $.url.param("id");
    if (commodityId != null) {
        url += "&id=" + commodityId;
    }

    options.$http.get(url).success(function (data) {
        if (!data.success) {
            showCrawlerInfo(options.$scope, data.detail);
            return;
        }

        if (options.callback) {
            options.callback(data);
        }
        hideCrawlerInfo(options.$scope);
    });
}

var enlargeImage = {
    $div: $('#enlargeImage'),
    set$Div: function ($div) {
        this.$div = $div;
    },
    get$SubDiv: function (className) {
        return this.$div.find('div.' + className);
    },
    get$Images: function () {
        return this.$div.find('img');
    },
    get$NormalDiv: function () {
        return this.get$SubDiv('normal');
    },
    get$DetailDiv: function () {
        return this.get$SubDiv('detail');
    },
    hideAll: function () {
        this.get$Images().hide();
        this.get$NormalDiv().hide();
        this.get$DetailDiv().hide();
    },
    show$Image: function (type, index) {
        var $subDiv = this.get$SubDiv(type).eq(index).show();
        return $subDiv.find('img').show();
    },
    adjustImagePosition: function ($image) {
        var totalWidth = this.$div.parent().width();
        var x = (totalWidth - JSUtils.getImageWidth($image)) / 2;

        var $window = $(window);
        var y = $window.scrollTop() - 200;
        y = y + ($window.height() - JSUtils.getImageHeight($image)) / 2;
        if (y < -200) {
            y = -200;
        }

        this.$div.css({
            'left': x + 'px',
            'top': y + 'px'
        });
    },
    show: function () {
        transparentBackground.show(5);
        this.$div.show();
    },
    hide: function () {
        this.$div.hide();
        transparentBackground.hide();
    }
};
