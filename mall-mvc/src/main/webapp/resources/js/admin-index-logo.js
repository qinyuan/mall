;
(function () {
    var input = buildInput({
        $form: $('#indexLogoForm'),
        get$link: function () {
            return this.$form.find('input[name=link]');
        },
        get$Logo: function () {
            return this.$form.find('input[name=logo]');
        },
        get$LogoFile: function () {
            return this.$form.find('input[name=logoFile]');
        },
        get$Description: function () {
            return this.$form.find('input[name=description]');
        },
        validate: function () {
            if ($.trim(this.get$Logo().val()) == '' &&
                $.trim(this.get$LogoFile().val()) == '') {
                alert('图片未设置');
                this.get$Logo().focusOrSelect();
                return false;
            }
            return true;
        }
    }, true).focus();

    angularUtils.controller(function ($scope) {
        $scope.validateInput = buildNormalValidationCallback(input);
        $scope.editIndexLogo = function (event) {
            var $tr = getParent($(event.target), 'tr');
            var id = $tr.parseIntegerInId();
            var link = $tr.find('td.link a').attr('title');
            var path = $tr.find('td.path a').attr('title');
            var description = $tr.find('td.description').text();

            input.get$Id().val(id);
            input.get$link().val(link);
            input.get$Logo().val(path).focusOrSelect();
            input.get$Description().val(description);
            input.toEditMode();
        };
        $scope.deleteIndexLogo = function (event) {
            var target = event.target;
            $.post('admin-index-logo-delete', {
                id: getTableRowIdByImgElement(target)
            }, buildSubmitCallback(function () {
                removeTableRow(target);
            }));
        };
        $scope.rankUpIndexLogo = function (event) {
            var target = event.target;
            $.post('admin-index-logo-rank-up', {
                id: getTableRowIdByImgElement(target)
            }, buildSubmitCallback(function () {
                moveUpTableRow(target);
            }));
        };
        $scope.rankDownIndexLogo = function (event) {
            var target = event.target;
            $.post('admin-index-logo-rank-down', {
                id: getTableRowIdByImgElement(target)
            }, buildSubmitCallback(function () {
                moveDownTableRow(target);
            }));
        };
    });
})();
