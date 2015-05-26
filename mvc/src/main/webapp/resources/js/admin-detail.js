;
(function () {
    var imageInput = {
        $form: $('#imageForm'),
        get$Id: function () {
            return this.$form.find('input[name=id]');
        },
        get$Url: function () {
            return this.$form.find('input[name=image]');
        },
        get$UploadFile: function () {
            return this.$form.find('input[name=imageFile]');
        },
        get$Link: function () {
            return this.$form.find('input[name=link]');
        },
        get$AddSubmit: function () {
            return $('#addImageSubmit');
        },
        get$EditSubmit: function () {
            return $('#editImageSubmit');
        },
        show: function (id, url, link) {
            this.get$Id().val(id);
            this.get$Url().val(url);
            this.get$Link().val(link);
            transparentBackground.show();
            this.$form.show();
            this.get$Url().focusOrSelect();
        },
        hide: function () {
            this.$form.hide();
            transparentBackground.hide();
        },
        validate: function () {
            if ($.trim(this.get$Url().val()) == '' &&
                $.trim(this.get$UploadFile().val()) == '') {
                alert('url和上传文件至少设置一项');
                this.get$Url().focusOrSelect();
                return false;
            } else {
                return true;
            }
        }
    };

    angularUtils.controller(function ($scope) {
        $scope.validateImageInput = buildNormalValidationCallback(imageInput);
        $scope.cancelImageInput = function () {
            imageInput.hide();
        };
        $scope.addImage = function () {
            imageInput.get$EditSubmit().hide();
            imageInput.get$AddSubmit().show();
            imageInput.show(-1, '', '');
        };
        $scope.editImage = function (event) {
            var $this = $(event.target);
            var $tr = getParent($this, 'tr');
            var id = $tr.parseIntegerInId();
            var url = $.trim($tr.find('a:eq(0)').attr('title'));
            var link = $.trim($tr.find('a:eq(1)').attr('title'));
            imageInput.get$EditSubmit().show();
            imageInput.get$AddSubmit().hide();
            imageInput.show(id, url, link);
        };
        $scope.deleteImage = function (event) {
            $.post('admin-detail-image-delete', {
                id: getTableRowIdByImgElement(event.target)
            }, normalSubmitCallback);
        };
    });
    $('input[type=text]').eq(0).focusOrSelect();
})();
