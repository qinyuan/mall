function loadCommodityParameters(divId, text) {
    var $div = $('#' + divId);
    text = $.trim(text);
    var lines = text.split('\n');

    if (lines.length == 0) {
        $div.html('');
        return;
    }

    while (lines.length % 3 != 0) {
        lines.push("");
    }
    var html = '<table>';
    var columnCount = 3, MAX_LENGTH = 30;
    for (var i = 0, len = lines.length; i < len; i++) {
        if (i % columnCount == 0) {
            html += '<tr>';
        }
        var line = $.trim(lines[i]);
        html += '<td><div title="' + getValue(line) + '">' + JSUtils.getChineseSubString(line, MAX_LENGTH)
            + '</div></td>';
        if (i % columnCount == columnCount - 1) {
            html += '</tr>';
        }
    }
    html += '</table>';
    $div.empty().html(html);

    function getValue(keyValue) {
        var splitIndex = keyValue.indexOf(':');
        if (splitIndex >= 0 && splitIndex < keyValue.length - 1) {
            return keyValue.substr(splitIndex + 1);
        }
        splitIndex = keyValue.indexOf('：');
        if (splitIndex >= 0 && splitIndex < keyValue.length - 1) {
            return keyValue.substr(splitIndex + 1);
        } else {
            return keyValue;
        }
    }
}
