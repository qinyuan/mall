;
(function () {
    function getCategoryId() {
        return $('#categoryId').val();
    }

    var branchPoster = {
        speed: 500,
        $div: $('div.search div.right div.branch div.poster'),
        $imageDiv: $('div.search div.right div.branch div.poster div.image'),
        stop: false,
        show: function () {
            $branchTitle.hide();
            $branchLogo.hide();
            this.$div.show();
            this.$imageDiv.hide().fadeIn(this.speed);
            this.stop = false;
        },
        hide: function () {
            $branchTitle.fadeIn(this.speed);
            $branchLogo.fadeIn(this.speed);
            this.$imageDiv.hide();
            this.$div.hide();
            this.stop = true;
        },
        init: function () {
            var self = this;
            $.get('json/category-poster.json', {
                'categoryId': getCategoryId()
            }, function (data) {
                if (data.length > 0) {
                    loadPoster(data[0]);
                    if (data.length > 1) {
                        var posterIndex = 0;
                        setInterval(function () {
                            if (posterIndex < data.length - 1) {
                                posterIndex++;
                            } else {
                                posterIndex = 0;
                            }
                            if (!self.stop) {
                                loadPoster(data[posterIndex]);
                            }
                        }, 4000);
                    }
                }
            });

            function loadPoster(posterObject) {
                self.$imageDiv.css('background-image', 'url(' + posterObject.path + ')');
                if (posterObject.link != null && $.trim(posterObject.link) != '') {
                    self.$imageDiv.parent().attr('href', posterObject.link).css('cursor', 'pointer');
                } else {
                    self.$imageDiv.parent().attr('href', 'javascript:void(0)').css('cursor', 'default');
                }
                self.$imageDiv.hide().fadeIn(self.speed);
            }
        }
    };
    branchPoster.init();

    var subCategoryLinks = {
        originalColor: 'rgb(102, 102, 102)',
        _unSelect: function ($target) {
            $target.removeClass('orangeBack').removeClass('selected').css('color', this.originalColor);
        },
        click: function ($link) {
            if ($link.hasClass('selected')) {
                this._unSelect($link);
                $hotWords.hide();
                branchPoster.show();
            } else {
                this._unSelect($subCategoryLinks.filter('.orangeBack'));
                $link.addClass('orangeBack').addClass('selected').css('color', '#ffffff');
                $hotWords.show();
                branchPoster.hide();
            }
        },
        over: function ($link) {
            if (!$link.hasClass('selected')) {
                $link.addClass('orangeBack').css('color', '#ffffff');
            }
        },
        off: function ($link) {
            if (!$link.hasClass('selected')) {
                this._unSelect($link);
            }
        }
    };

    var $subCategoryLinks = $('div.search > div.subCategory a');
    var $branchLogo = $('div.branch div.logos');
    var $branchTitle = $('div.branch div.title');
    var $goodsImages = $('div.goods div.images div.image img');
    var $hotWords = $('div.content > div.searchForm div.hotWords');
    $subCategoryLinks.hover(function () {
        subCategoryLinks.over($(this));
    }, function () {
        subCategoryLinks.off($(this));
    });

    $goodsImages.hover(function () {
        $(this).addClass('deepTransparent');
    }, function () {
        $(this).removeClass('deepTransparent');
    });

    angularUtils.controller(function ($scope, $http) {
        $scope.inLowPrice = true;
        $scope.categoryId = getCategoryId();
        initBranch();
        $scope.showMore = function () {
            if ($scope.hideBranches.length == 0) {
                return;
            }
            get$HideBranch().show();
            get$MoreBranch().hide();
        };
        $scope.hideMore = function (event) {
            if ($scope.hideBranches.length == 0) {
                return;
            }

            var target = event.target;
            var $target = $(target);
            if (!($target.is('div'))) {
                return;
            }

            if ($target.hasClass("logos") || $target.hasClass("branch")
                || $target.hasClass('right') || $target.hasClass('search')
                || $target.hasClass('split') || $target.hasClass('title')) {
                get$HideBranch().hide();
                get$MoreBranch().show();
            }
        };
        /*
         $scope.selectSubCategory = function (event) {
         var $this = $(event.target);
         selectSubCategory($this);
         };
         */
        function selectSubCategory($categoryAnchor) {
            if ($categoryAnchor.hasClass('selected')) {
                $scope.categoryId = getCategoryId();
            } else {
                $scope.categoryId = $categoryAnchor.dataOptions()['id'];
            }
            loadHotWord();
            loadSnapshot($scope, $http);
            initBranch();
            subCategoryLinks.click($categoryAnchor);
        }

        var subCategory = $.url.param('subCategory');
        if (subCategory) {
            $scope.categoryId = parseInt(subCategory);
            initSnapshot($scope, $http);
            $('div.search div.subCategory a').each(function () {
                var $this = $(this);
                if ($this.dataOptions()['id'] == parseInt(subCategory)) {
                    var href = $this.attr('href');
                    $this.attr('href', href.replace('&subCategory=' + subCategory, ''));
                    selectSubCategory($this);
                }
            })
        } else {
            initSnapshot($scope, $http);
        }

        function get$HideBranch() {
            return  $('div.search > div.right div.branch > div.logos div.hideBranch');
        }

        function get$MoreBranch() {
            return $('div.search > div.right div.branch > div.logos div.moreBranch');
        }

        function initBranch() {
            var url = "json/branch.json?categoryId=" + $scope.categoryId;
            $http.get(url).success(function (branches) {
                $scope.moreBranch = {logo: 'resources/css/images/list/more-branch.png'};
                if (branches.length > 14) {
                    $scope.showBranches = branches.slice(0, 13);
                    $scope.hideBranches = branches.slice(13);
                    $scope.moreBranch.show = true;
                } else {
                    $scope.showBranches = branches;
                    $scope.hideBranches = [];
                    $scope.moreBranch.show = false;
                }
            });
        }

        function loadHotWord() {
            var url = 'json/hotSearchWord.json?size=8&categoryId=' + $scope.categoryId;
            $http.get(url).success(function (data) {
                $scope.hotWords = data;
                $.each($scope.hotWords, function () {
                    if (this['hot']) {
                        this['style'] = 'noLineAnchor red';
                    } else {
                        this['style'] = 'noLineAnchor';
                    }
                });
            });
        }
    });
    focusSearchInput();
    JSUtils.recordScrollStatus();
})();
function showBranchBorder(element) {
    getParent($(element), 'div').prev().show();
}
function hideBranchBorder(element) {
    getParent($(element), 'div').prev().hide();
}
