;
(function () {
    var $searchTypeList = $('div.searchForm div.searchType li');
    var $searchCategoryId = $('#searchCategoryId');

    $searchTypeList.click(function () {
        var $this = $(this);
        var text = $this.text();
        var $div = getParent($this, 'div');
        $div.find('span.text').text(text);
        $searchCategoryId.val($this.dataOptions()['id']);
        focusSearchInput();
    });
    var categoryId = $.url.param('categoryId');
    if (categoryId != null && $.trim(categoryId) != '') {
        $searchTypeList.each(function () {
            var $this = $(this);
            var categoryIdOfLi = $this.dataOptions()['id'];
            if (categoryIdOfLi == parseInt(categoryId)) {
                $this.trigger('click');
            }
        });
    }
    $('#searchCommit').click(function () {
        getParent($(this), 'form').submit();
    });
})();
function focusSearchInput() {
    $('#searchInput').focus();
}
