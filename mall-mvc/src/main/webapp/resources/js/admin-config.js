;
(function () {
    var input = {
        $form: $('#mainForm'),
        get$GlobalLogo: function () {
            return this.$form.find('input[name=globalLogo]');
        },
        get$GlobalLogoFile: function () {
            return this.$form.find('input[name=globalLogoFile]');
        },
        get$GlobalBanner: function () {
            return this.$form.find('input[name=globalBanner]');
        },
        get$GlobalBannerFile: function () {
            return this.$form.find('input[name=globalBannerFile]');
        },
        get$indexHeadPoster: function () {
            return this.$form.find('input[name=indexHeadPoster]');
        },
        get$indexHeadPosterFile: function () {
            return this.$form.find('input[name=indexHeadPosterFile]');
        },
        get$indexFootPoster: function () {
            return this.$form.find('input[name=indexFootPoster]');
        },
        get$indexFootPosterFile: function () {
            return this.$form.find('input[name=indexFootPosterFile]');
        },
        get$BranchRankImage: function () {
            return this.$form.find('input[name=branchRankImage]');
        },
        get$BranchRankImageFile: function () {
            return this.$form.find('input[name=branchRankImageFile]');
        },
        get$NoFoundImage: function () {
            return this.$form.find('input[name=noFoundImage]');
        },
        get$NoFoundImageFile: function () {
            return this.$form.find('input[name=noFoundImageFile]');
        },
        validate: function () {
            if ($.trim(this.get$GlobalBanner().val()) == '' &&
                $.trim(this.get$GlobalBannerFile().val()) == '') {
                alert('页头部横幅未设置!');
                this.get$GlobalBanner().focusOrSelect();
                return false;
            } else if ($.trim(this.get$GlobalLogo().val()) == '' &&
                $.trim(this.get$GlobalLogoFile().val()) == '') {
                alert('页头Logo未设置!');
                this.get$GlobalLogo().focusOrSelect();
                return false;
            } else if ($.trim(this.get$indexHeadPoster().val()) == '' &&
                $.trim(this.get$indexHeadPosterFile().val()) == '') {
                alert('首页头部海报未设置!');
                this.get$indexHeadPoster().focusOrSelect();
                return false;
            } else if ($.trim(this.get$indexFootPoster().val()) == '' &&
                $.trim(this.get$indexFootPosterFile().val()) == '') {
                alert('首页尾部海报未设置!');
                this.get$indexFootPoster().focusOrSelect();
                return false;
            } else if ($.trim(this.get$BranchRankImage().val()) == '' &&
                $.trim(this.get$BranchRankImageFile().val()) == '') {
                alert('品牌排行图片未设置!');
                this.get$BranchRankImage().focusOrSelect();
                return false;
            } else if ($.trim(this.get$NoFoundImage().val()) == '' &&
                $.trim(this.get$NoFoundImageFile().val()) == '') {
                alert('无对应商品时显示的图片未设置!');
                this.get$NoFoundImage().focusOrSelect();
                return false;
            } else {
                return true;
            }
        }
    };
    input.get$GlobalBanner().focusOrSelect();

    var footLinkInput = buildInput({
        $form: $('#footLinkForm'),
        get$Text: function () {
            return this.$form.find('input[name=text]');
        },
        get$Link: function () {
            return this.$form.find('input[name=link]');
        },
        setValue: function (id, text, link) {
            this.get$Id().val(id);
            this.get$Text().val(text);
            this.get$Link().val(link);
            return this;
        },
        validate: function () {
            return validateTextInput(this.get$Text(), '显示文字不能为空')
                && validateTextInput(this.get$Link(), '目标链接不能为空');
        }
    });

    angularUtils.controller(function ($scope) {
        $scope.validateConfigInput = buildNormalValidationCallback(input);
        $scope.addFootLink = function () {
            footLinkInput.toAddModeAndShow();
        };
        $scope.resetFootLink = function () {
            if (confirm("确定恢复初始设置？")) {
                $.post('admin-foot-link-reset', {}, normalSubmitCallback);
            }
        };
        $scope.editFootLink = function (event) {
            var $tr = getParent($(event.target), 'tr');
            var id = $tr.parseIntegerInId();
            var text = $.trim($tr.find('td.text').text());
            var link = $.trim($tr.find('td.link').text());
            footLinkInput.setValue(id, text, link).toEditModeAndShow();
        };
        $scope.rankUpFootLink = function (event) {
            $.post('admin-foot-link-rank-up', {
                id: getTableRowIdByImgElement(event.target)
            }, normalSubmitCallback);
        };
        $scope.rankDownFootLink = function (event) {
            $.post('admin-foot-link-rank-down', {
                id: getTableRowIdByImgElement(event.target)
            }, normalSubmitCallback);
        };
        $scope.deleteFootLink = function (event) {
            $.post('admin-foot-link-delete', {
                id: getTableRowIdByImgElement(event.target)
            }, normalSubmitCallback);
        };
    });
})();
