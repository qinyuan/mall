;
(function () {
    var keywordInput = buildInput({
        $form: $('#keywordForm'),
        get$Url: function () {
            return this.$form.find('input[name=url]');
        },
        get$Word: function () {
            return this.$form.find('textarea[name=word]');
        },
        get$Description: function () {
            return this.$form.find('textarea[name=description]');
        },
        validate: function () {
            return validateTextInput(this.get$Url(), '页面链接未设置');
        }
    });

    angularUtils.controller(function ($scope) {
        $scope.validateInput = buildNormalValidationCallback(keywordInput);
        $scope.editKeyword = function (event) {
            var $tr = getParent($(event.target), 'tr');
            var id = $tr.parseIntegerInId();
            var url = $tr.find('td.url a').attr('title');
            var word = $tr.find('td.word').text();
            var description = $tr.find('td.description').text();

            keywordInput.get$Id().val(id);
            keywordInput.get$Url().val(url).focusOrSelect();
            keywordInput.get$Word().val(word);
            keywordInput.get$Description().val(description);

            keywordInput.toEditMode();
        };
        $scope.deleteKeyword = function (event) {
            var id = getTableRowIdByImgElement(event.target);
            $.post('admin-seo-keyword-delete', {
                id: id
            }, normalSubmitCallback);
        }
    });
})();
