;
(function () {
    angularUtils.controller(function ($scope) {
        var input = buildInput({
            $form: $('#branchForm'),
            get$Name: function () {
                return this.$form.find('input[name=name]');
            },
            get$FirstLetter: function () {
                return this.$form.find('input[name=firstLetter]');
            },
            get$ParentId: function () {
                return this.$form.find('select[name=parentId]');
            },
            get$Logo: function () {
                return this.$form.find('input[name=logo]');
            },
            get$LogoFile: function () {
                return this.$form.find('input[name=logoFile]');
            },
            get$SquareLogo: function () {
                return this.$form.find('input[name=squareLogo]');
            },
            get$Poster: function () {
                return this.$form.find('input[name=poster]');
            },
            get$SquareLogoFile: function () {
                return this.$form.find('input[name=squareLogoFile]');
            },
            get$Slogan: function () {
                return this.$form.find('textarea[name=slogan]');
            },
            validate: function () {
                if (!validateTextInput(this.get$Name(), '名称未设置')
                    || !validateTextInput(this.get$FirstLetter(), '首字母未设置')) {
                    return false;
                }

                if ($.trim(this.get$Logo().val()) == '' &&
                    $.trim(this.get$LogoFile().val()) == '') {
                    alert('矩形logo未设置');
                    this.get$Logo().focusOrSelect();
                    return false;
                }

                if ($.trim(this.get$SquareLogo().val()) == '' &&
                    $.trim(this.get$SquareLogoFile().val()) == '') {
                    alert('方形logo未设置');
                    this.get$SquareLogo().focusOrSelect();
                    return false;
                }

                return true;
            },
            afterEditCancel: function () {
                $scope.shoppes = [];
                $scope.$apply();
            }
        });
        input.get$Name().keyup(function () {
            var name = $(this).val();
            if (name != '') {
                name = encodeURI(name); // we must encode Chinese character in IE 8
                $.post('chinese-letter.json?string=' + name, function (data) {
                    if (data.result) {
                        input.get$FirstLetter().val(data.result);
                    }
                });
            } else {
                input.get$FirstLetter().val("");
            }
        });

        $scope.editBranch = function (event) {
            var $tr = getParent($(event.target), 'tr');
            var id = $tr.parseIntegerInId();
            var name = $tr.find('td.name').text();
            var firstLetter = $tr.find('td.firstLetter').text();
            var parentId = $tr.find('td.parent').dataOptions()['parentId'];
            var $logoAnchors = $tr.find('td.logo a');
            var logo = $logoAnchors.eq(0).attr('title');
            var squareLogo = $logoAnchors.eq(1).attr('title');
            var poster = $logoAnchors.eq(2).attr('title');
            var slogan = $tr.find('td.slogan').text();

            input.get$Id().val(id);
            input.get$Name().val(name);
            input.get$FirstLetter().val(firstLetter);
            input.get$ParentId().val(parentId);
            input.get$Logo().val(logo);
            input.get$SquareLogo().val(squareLogo);
            input.get$Poster().val(poster);
            input.get$Slogan().val(slogan);

            $scope.shoppes = [];
            $tr.find('td.shoppe a').each(function () {
                var $this = $(this);
                $scope.shoppes.push({
                    url: $this.attr('href'),
                    name: $this.text()
                });
            });

            input.toEditMode();
        };
        $scope.deleteBranch = function (event) {
            var id = getTableRowIdByImgElement(event.target);
            $.post('admin-branch-deletable', {
                id: id
            }, function (data) {
                if (data.success) {
                    doDelete();
                } else {
                    if (confirm(data['detail'])) {
                        doDelete();
                    }
                }
            });
            function doDelete() {
                $.post('admin-branch-delete', {
                    id: id
                }, normalSubmitCallback);
            }
        };
        $scope.shoppes = [];
        $scope.addShoppe = function () {
            $scope.shoppes.push({name: '', url: ''});
            setTimeout(function () {
                $('div.content form div.shoppe div.right div:last').find('input:first').focus();
            }, 100);
        };
        $scope.deleteShoppe = function (index) {
            JSUtils.removeArrayItem($scope.shoppes, index);
        };
    });
})();
