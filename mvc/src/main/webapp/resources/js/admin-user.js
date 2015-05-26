;
(function () {
    var input = buildInput({
        $form: $('#userForm'),
        get$Username: function () {
            return this.$form.find('input[name=username]');
        },
        get$Password: function () {
            return this.$form.find('input[name=password]');
        },
        validate: function () {
            if ($.trim(this.get$Username().val()) == '') {
                alert('名称不能为空');
                this.get$Username().focusOrSelect();
                return false;
            } else if ($.trim(this.get$Password().val()) == '') {
                alert('密码不能为空');
                this.get$Password().focusOrSelect();
                return false;
            } else {
                return true;
            }
        }
    }).focus();

    angularUtils.controller(function ($scope) {
        $scope.validateInput = buildNormalValidationCallback(input);
        $scope.deleteUser = function (event) {
            var target = event.target;
            $.post('admin-user-delete', {
                id: getTableRowIdByImgElement(target)
            }, buildSubmitCallback(function () {
                removeTableRow(target);
            }));
        };
        $scope.editUser = function (event) {
            var $this = $(event.target);
            var $tr = getParent($this, 'tr');
            var id = $tr.parseIntegerInId();
            var username = $.trim($tr.find('td.username').text());
            var password = $.trim($tr.find('td.password').text());

            input.get$Id().val(id);
            input.get$Username().val(username).focusOrSelect();
            input.get$Password().val(password);
            input.toEditMode();
        };
    });
})();
