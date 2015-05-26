/**
 * Function to draw a line chart. This is a simple example:
 * linechart('chartDiv', {
 *     xSerial: ['2014-01-01', '2014-01-02', '2014-01-03', '2014-01-04', '2014-01-05', '2014-01-06'],
 *     ySerial: [2, 3, 2, 3, 2, 3],
 *     xLabel: '日期',
 *     yLabel: '价格',
 *     yUnit: '￥',
 *     width: 300,
 *     height: 150
 * });
 * @param elementId Element Id of line chart in HTML
 * @param config configure object
 */
function linechart(elementId, config) {
    // parse configuration
    var width = config.width;
    var height = config.height;
    var xSerial = config.xSerial;
    var ySerial = config.ySerial;
    var xLabel = config.xLabel;
    var yLabel = config.yLabel;
    var yUnitString = config.yUnit;
    var serialSize = Math.min(xSerial.length, ySerial.length);

    // define constant
    var X_MARGIN = 40;
    var Y_MARGIN = 30;
    var Y_GRID_COUNT = 5;

    var paper = Raphael(elementId, width, height);
    var xGridWidth = (width - X_MARGIN * 2) / (serialSize - 1);
    var yGridHeight = (height - Y_MARGIN * 2) / Y_GRID_COUNT;

    var yMin = _.min(ySerial);
    var yMax = _.max(ySerial);
    var yDiff = yMax - yMin;

    drawGrid();
    drawYAxis();
    drawXAxis();
    drawLine();
    bindOverEvent();

    function getDots() {
        var yUnit;
        if (yDiff == 0) {
            yUnit = (Y_GRID_COUNT - 1 ) * yGridHeight / yMax;
        } else {
            yUnit = (height - Y_MARGIN * 2 - 2 * yGridHeight) / yDiff;
        }
        var dots = [];
        for (var i = 0; i < serialSize; i++) {
            var x = X_MARGIN + i * xGridWidth;
            var y = Y_MARGIN + yGridHeight + (yMax - ySerial[i]) * yUnit;
            dots.push([x, y]);
        }
        return dots;
    }

    function drawLine() {
        function getLineString(dots) {
            var lineStr = '';
            for (var i = 0, len = dots.length; i < len; i++) {
                var dot = dots[i];
                if (i == 0) {
                    lineStr += 'M ' + dot[0] + ',' + dot[1];
                } else {
                    lineStr += ' L ' + dot[0] + ',' + dot[1];
                }
            }
            return lineStr;
        }

        var dots = getDots();
        var lineString = getLineString(dots);
        paper.path(lineString).attr({
            'stroke': '#F93416',
            'stroke-width': 2
        });
    }

    function drawGrid() {
        var xEnd = width - X_MARGIN;
        for (var i = 0; i <= Y_GRID_COUNT; i++) {
            var y = Y_MARGIN + yGridHeight * i;

            var lineString = 'M ' + X_MARGIN + ',' + y + ' L ' + xEnd + ',' + y;
            paper.path(lineString).attr({
                'stroke': '#EEEEEE',
                'stroke-width': 1
            });

            lineString = 'M ' + X_MARGIN * 0.8 + ',' + y + ' L ' + X_MARGIN + ',' + y;
            paper.path(lineString).attr({
                'stroke': '#DEDEDE',
                'stroke-width': 2
            });
        }
    }

    function drawYAxis() {
        function round(num) {
            if (num < 10) {
                console.log(num);
                return Math.round(num * Math.pow(10, 2)) / Math.pow(10, 2);
            } else if (num < 100) {
                return Math.round(num * Math.pow(10, 1)) / Math.pow(10, 1);
            } else {
                return parseInt(num);
            }
        }

        // draw vertical line of y axis
        var lineString = 'M ' + X_MARGIN + ',' + Y_MARGIN + ' L ' + X_MARGIN + ',' + (height - Y_MARGIN);
        paper.path(lineString).attr({
            'stroke': '#DEDEDE',
            'stroke-width': 1
        });

        // draw gradation values of y axis
        var yValueOfOneGrid;
        if (yDiff == 0) {
            yValueOfOneGrid = yMax / (Y_GRID_COUNT - 1);
        } else {
            yValueOfOneGrid = yDiff / (Y_GRID_COUNT - 2);
        }

        var x = yMax >= 1000 ? 13 : 10;
        for (var i = 0; i <= Y_GRID_COUNT; i++) {
            var y = Y_MARGIN + yGridHeight * i;
            var yValue = round(yMax - yValueOfOneGrid * (i - 1));
            paper.text(x, y, yValue).attr({
                'font-size': 10,
                'fill': '#666666'
            });
        }
    }

    function drawXAxis() {
        function drawLine(x, text) {
            var lineString = 'M ' + x + ',' + (height - Y_MARGIN) + ' L ' + x + ',' + (height - Y_MARGIN * 0.8);
            paper.path(lineString).attr({
                'stroke': '#DEDEDE',
                'stroke-width': 2
            });
            paper.text(x - text.length / 2, height - Y_MARGIN * 0.4, text).attr({
                'font-size': 10,
                'fill': '#666666'
            });
        }

        drawLine(X_MARGIN, xSerial[0]);
        drawLine(width - X_MARGIN, xSerial[xSerial.length - 1]);
        var xMiddle = parseInt((xSerial.length - 1) / 2);
        drawLine(X_MARGIN + xGridWidth * xMiddle, xSerial[xMiddle]);
    }

    function bindOverEvent() {
        function hideMark() {
            rectAbove.attr('x', -10);
            rectBelow.attr('x', -10);
            circle.attr('cx', -10);
            descriptionPanel.hide();
        }

        function createLine() {
            return paper.rect(-10, Y_MARGIN, 0.1, height - 2 * Y_MARGIN).attr({
                'stroke': '#aaaaaa'
            });
        }

        var descriptionPanel = {
            width: 110,
            height: 35,
            init: function () {
                this._$panel = $('<div></div>').css({
                    'position': 'absolute',
                    'height': this.height,
                    'width': this.width,
                    'color': '#565656',
                    'font-size': '9pt',
                    'background-color': '#ffffff',
                    'border-radius': '5px',
                    '-moz-box-shadow': '1px 1px 10px rgb(213, 213, 213)',
                    '-webkit-box-shadow': '1px 1px 10px rgb(213, 213, 213)',
                    'box-shadow': '1px 1px 10px rgb(213, 213, 213)',
                    'padding': '12px 5px',
                    'line-height': '18px',
                    'display': 'none',
                    'opacity': '0.8',
                    'filter': 'alpha(opacity=80)'
                });
                this._$panel.appendTo($('#' + elementId));

                var $xDiv = $('<div></div>');
                $xDiv.appendTo(this._$panel);

                var $xSpanLabel = $('<span>' + xLabel + ': </span>');
                $xSpanLabel.appendTo($xDiv);

                this._$xSpanValue = $('<span></span>');
                this._$xSpanValue.appendTo($xDiv);

                var $yDiv = $('<div></div>');
                $yDiv.appendTo(this._$panel);

                var $ySpanLabel = $('<span>' + yLabel + ': </span>');
                $ySpanLabel.appendTo($yDiv);

                this._$ySpanValue = $('<span></span>');
                this._$ySpanValue.appendTo($yDiv);
            },
            xValue: function (xValue) {
                if (xValue) {
                    this._$xSpanValue.text(xValue);
                    return this;
                } else {
                    return this._$xSpanValue;
                }
            },
            yValue: function (yValue) {
                if (yValue) {
                    this._$ySpanValue.text(yValue);
                    return this;
                } else {
                    return this._$ySpanValue;
                }
            },
            top: function (top) {
                if (top) {
                    this._$panel.css('top', top);
                    return this;
                } else {
                    return this._$panel.css('top');
                }
            },
            left: function (left) {
                if (left) {
                    this._$panel.css('left', left);
                    return this;
                } else {
                    return this._$panel.css('left');
                }
            },
            hide: function () {
                this._$panel.hide();
            },
            show: function () {
                this._$panel.show();
            }
        };

        descriptionPanel.init();
        descriptionPanel.top((height - descriptionPanel.height) / 2);

        var rectAbove = createLine();
        var rectBelow = createLine();
        var circleRadius = 3;
        var circle = paper.circle(-10, -10, circleRadius).attr({
            'stroke': 'red',
            'stroke-width': 2,
            'fill': '#ffffff'
        });
        var dots = getDots();

        $('<div></div>').css({
            'position': 'absolute',
            'width': width,
            'height': height,
            'left': 0,
            'top': 0,
            'background-color': 'transparent'
        }).appendTo($('#' + elementId)).mousemove(function (e) {
            var x = JSUtils.getOffsetByEvent(e).offsetX;
            if (x >= X_MARGIN && x < width - X_MARGIN) {
                var xIndex = parseInt((x - X_MARGIN) / xGridWidth + 0.5);
                var xOfGrid = X_MARGIN + xIndex * xGridWidth;
                var y = dots[xIndex][1];
                rectAbove.attr('x', xOfGrid);
                rectBelow.attr('x', xOfGrid);
                circle.attr('cx', xOfGrid);

                rectAbove.attr('height', y - Y_MARGIN - circleRadius);
                rectBelow.attr('y', y + circleRadius);
                rectBelow.attr('height', height - Y_MARGIN - rectBelow.attr('y'));
                circle.attr('cy', y);

                descriptionPanel.show();
                if (x > width / 2) {
                    descriptionPanel.left(x - descriptionPanel.width - 20);
                } else {
                    descriptionPanel.left(x + 10);
                }
                descriptionPanel.xValue(xSerial[xIndex]);
                descriptionPanel.yValue(yUnitString + ySerial[xIndex]);
            } else {
                hideMark();
            }
        }).mouseout(function () {
            hideMark();
        });
    }
}


/**
 * Function to draw a price line chart. This is a simple example:
 * priceLineChart('chartDiv', {
 *     xSerial: ['2014-01-01', '2014-01-05', '2014-01-18', '2014-02-04', '2014-03-05', '2014-03-16'],
 *     ySerial: [2, 3, 2, 3, 2, 3],
 *     width: 300,
 *     height: 150
 * });
 * @param elementId
 * @param config
 */
function priceLineChart(elementId, config) {
    var xSerial = config.xSerial;
    var ySerial = config.ySerial;
    var width = config.width;
    var height = config.height;
    var serialSize = Math.min(xSerial.length, ySerial.length);
    if (serialSize <= 1) {
        return;
    }

    var newXSerial = [];
    var newYSerial = [];

    function getDateString(date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        return year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
    }

    function getDateBetween(startDateString, endDateString) {
        var dates = [];
        var startTimeStamp = JSUtils.newDate(startDateString).getTime();
        var endTimeStamp = JSUtils.newDate(endDateString).getTime();

        // switch start date end end date if start date is later than end date
        if (startTimeStamp > endTimeStamp) {
            var temp = startTimeStamp;
            startTimeStamp = endTimeStamp;
            endTimeStamp = temp;
        }

        var date = JSUtils.newDate(startTimeStamp);
        var milliSecondOfOneDay = 24 * 3600 * 1000;
        while (date.getTime() < endTimeStamp) {
            dates.push(getDateString(date));
            date.setTime(date.getTime() + milliSecondOfOneDay);
        }
        return dates;
    }

    for (var i = 1; i < serialSize; i++) {
        var dates = getDateBetween(xSerial[i - 1], xSerial[i]);
        for (var j = 0, len = dates.length; j < len; j++) {
            newXSerial.push(dates[j]);
            newYSerial.push(ySerial[i - 1]);
        }
    }
    newXSerial.push(xSerial[serialSize - 1]);
    newYSerial.push(ySerial[serialSize - 1]);

    linechart(elementId, {
        xSerial: newXSerial,
        ySerial: newYSerial,
        xLabel: '日期',
        yLabel: '价格',
        yUnit: '￥',
        width: width,
        height: height
    });
}
