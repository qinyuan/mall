function getTableRowIdByImgElement(image) {
    var $tr = getParent($(image), "tr");
    return $tr.parseIntegerInId();
}

function buildNormalValidationCallback(inputObject) {
    return function (event) {
        if (!inputObject.validate()) {
            event.preventDefault();
            return false;
        } else {
            return true;
        }
    };
}

function moveUpTableRow(elementInTableRow) {
    var $tr = getParent($(elementInTableRow), 'tr');
    var $prevTr = $tr.prev();
    if ($prevTr.size() > 0) {
        $tr.insertBefore($prevTr);
    }
}

function moveDownTableRow(elementInTableRow) {
    var $tr = getParent($(elementInTableRow), 'tr');
    var $nextTr = $tr.next();
    if ($nextTr.size() > 0) {
        $tr.insertAfter($nextTr);
    }
}

function removeTableRow(elementInTableRowRow) {
    setTimeout(function () {
        getParent($(elementInTableRowRow), 'tr').remove();
    }, 500);
}

function buildInput(attrs) {
    attrs.get$AddSubmit = function () {
        return this.$form.find('button.add');
    };
    attrs.get$EditSubmit = function () {
        return this.$form.find('button.edit');
    };
    attrs.get$EditCancel = function () {
        return this.$form.find('button.cancel');
    };
    attrs.get$Id = function () {
        return this.$form.find('input[name=id]');
    };
    if (attrs.$form.attr('enctype') != 'multipart/form-data') {
        attrs.$form.ajaxForm(normalSubmitCallback);
    }
    attrs.isFixed = function () {
        return attrs.$form.hasClass('fixedForm');
    };
    if (attrs.isFixed()) {
        attrs.get$EditCancel().text('取消');
    }
    attrs.toEditMode = function () {
        this.get$AddSubmit().attr({'disabled': true, 'type': 'button'}).show();
        this.get$EditSubmit().attr({'disabled': false, 'type': 'submit'}).show();
        this.get$EditCancel().attr('disabled', false);
        if (this.isFixed()) {
            this.get$AddSubmit().hide();
        } else {
            scrollTop(this.$form);
        }
        return this.focus();
    };
    attrs.toEditModeAndShow = function () {
        return this.show().toEditMode();
    };
    attrs.show = function () {
        if (this.isFixed()) {
            transparentBackground.show();
            this.$form.show();
        }
        return this;
    };
    attrs.focus = function () {
        this.$form.find('input[type=text]').eq(0).focusOrSelect();
        return this;
    };
    attrs.toAddMode = function () {
        this.$form[0].reset();
        this.get$Id().val(null);
        this.get$AddSubmit().attr({'disabled': false, 'type': 'submit'}).show();
        this.get$EditSubmit().attr({'disabled': true, 'type': 'button'}).show();
        if (this.isFixed()) {
            this.get$EditSubmit().hide();
            this.get$EditCancel().attr('disabled', false);
        } else {
            this.get$EditCancel().attr('disabled', true);
        }
        return this.focus();
    };
    attrs.toAddModeAndShow = function () {
        return this.show().toAddMode();
    };
    attrs.hide = function () {
        if (this.isFixed()) {
            this.$form.hide();
            transparentBackground.hide();
        }
        return this;
    };
    attrs.get$EditCancel().click(function () {
        attrs.toAddMode();
        if (attrs['afterEditCancel']) {
            attrs['afterEditCancel']();
        }
        attrs.hide();
    });
    attrs.get$AddSubmit().click(buildNormalValidationCallback(attrs));
    attrs.get$EditSubmit().click(buildNormalValidationCallback(attrs));
    return attrs;
}

function validateTextInput($input, info) {
    if ($.trim($input.val()) == '') {
        alert(info);
        $input.focusOrSelect();
        return false;
    } else {
        return true;
    }
}

(function () {
    var index = 0;
    $('input[type=text],input[type=password],button,textarea').each(function () {
        var $this = $(this);
        if (!$this.attr('tabindex')) {
            $this.attr('tabindex', ++index);
        }
    });
    JSUtils.recordScrollStatus();
})();
