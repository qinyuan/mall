;
(function () {
    var $viewDiv = $('#viewDiv');
    var $editDiv = $('#editDiv');
    var $backgroundColor = $editDiv.find('input[name=backgroundColor]');
    var editor = CKEDITOR.replace('content', {
        'height': getTotalHeight() - 290
    });

    editor.on('instanceReady', function () {
        updateBackgroundColor();
        focusEditor();
    });

    function getBackgroundColor() {
        var color = $backgroundColor.val();
        if (!(color.charAt(0) == '#')) {
            color = '#' + color;
        }
        return color;
    }

    function updateBackgroundColor(color) {
        var color = getBackgroundColor();
        editor.document.getBody().setStyle('background-color', color);
        $viewDiv.css('background-color', color);
    }

    function focusEditor() {
        editor.focus();
    }

    angularUtils.controller(function ($scope) {
        function getContent() {
            return editor.getData();
        }

        function toEditMode() {
            $viewDiv.hide();
            $editDiv.show();
            focusEditor();
        }

        function toViewMode() {
            $editDiv.hide();
            $viewDiv.show().find('div.article').empty().html(getContent());
        }


        $scope.publish = function (event) {
            if ($.trim(getContent()) == '') {
                alert('文章内容不能为空');
                event.preventDefault();
                focusEditor();
                return false;
            } else {
                return true;
            }
        };
        $scope.view = toViewMode;
        $scope.cancelView = toEditMode;
        $scope.cancelEdit = function () {
            location.href = "admin-article-list.html";
        };
        $scope.toSetColor = function () {
            toViewMode();
        };
    });
    colorChange = function () {
        updateBackgroundColor();
    };
})();
var colorChange;
