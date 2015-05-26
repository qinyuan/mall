(function () {
    var $commodityImages = $('div.content div.right div.image img');
    var $commodityDescription = $('div.content div.right div.description a');
    var $rightDiv = $('div.content div.right');
    var $paginationDiv = $('div.content div.right div.pagination');
    var $paginationUl = $('div.content div.right div.pagination ul.pagination');

    function adjustPaginationPosition() {
        var paddingLeft = ($rightDiv.width() - $paginationUl.width()) / 2;
        $paginationDiv.css('padding-left', paddingLeft + 'px');
    }

    adjustPaginationPosition();

    $commodityImages.hover(function () {
        $(this).addClass('deepTransparent');
    }, function () {
        $(this).removeClass('deepTransparent');
    });
    $commodityDescription.hover(function () {
        $(this).css({
            'color': '#4A2B1B',
            'text-decoration': 'none',
            'text-shadow': '2px 2px 5px rgb(238, 238, 238)'
        });
    }, function () {
        $(this).css({
            'color': '#7F4B2F',
            'text-shadow': 'none'
        });
    });
    $('div.images a.edit').each(function () {
        var search = $.trim(location.search);
        if (search[0] == '?') {
            search = search.substr(1);
        }

        if (search != '') {
            this.href = this.href + '&' + search;
        }
    });

    $('#searchCommit').click(function () {
        getParent($(this), 'form').submit();
    });
    $('#searchInput').focusOrSelect();
    JSUtils.recordScrollStatus();

    $('#branchSelect > button').blur(function () {
        var $this = $(this);
        setTimeout(function () {
            $this.next().hide();
        }, 250);
    });

    angularUtils.controller(function ($scope) {
        $scope.showBranchesToSelect = function (event) {
            var $next = $(event.target).next();
            if ($next.css('display') == 'none') {
                $next.show();
            } else {
                $next.hide();
            }
        };
        $scope.showBranchGroup = function (event, index) {
            showBranchGroup($(event.target).parent().parent(), index);
        };
        $scope.selectBranch = function (branchId) {
            var url = "admin.html?branchId=" + branchId;
            var categoryId = $.url.param('categoryId');
            if (categoryId != null && categoryId != '') {
                url = url + '&categoryId=' + categoryId;
            }
            var keyWord = $.url.param('keyWord');
            if (keyWord != null && keyWord != '') {
                url = url + '&keyWord=' + keyWord;
            }
            location.href = url;
        };
        $scope.selectAllBranch = function () {
            $scope.selectBranch(0);
        };
    });
})();
/*$('#testButton').blur(function () {
 console.log('aafdsak');
 });*/
